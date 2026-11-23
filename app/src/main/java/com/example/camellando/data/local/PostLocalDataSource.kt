package com.example.camellando.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "camellando_post_prefs")

data class PostLocalInteractions(
    val isLiked: Boolean = false,
    val extraLikes: Int = 0,
    val isSaved: Boolean = false,
    val comments: List<String> = emptyList()
)

class PostLocalDataSource(private val context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val KEY_LIKED_POST_IDS = stringSetPreferencesKey("liked_post_ids")
        private val KEY_SAVED_POST_IDS = stringSetPreferencesKey("saved_post_ids")
        private fun keyLikes(postId: String) = intPreferencesKey("likes_$postId")
        private fun keyComments(postId: String) = stringPreferencesKey("comments_$postId")
        private fun keyImages(postId: String) = stringPreferencesKey("images_$postId")
        private fun keyTarifa(postId: String) = stringPreferencesKey("tarifa_$postId")
    }

    val likedPostIds: Flow<Set<String>> = dataStore.data.map { prefs ->
        prefs[KEY_LIKED_POST_IDS] ?: emptySet()
    }

    val savedPostIds: Flow<Set<String>> = dataStore.data.map { prefs ->
        prefs[KEY_SAVED_POST_IDS] ?: emptySet()
    }

    fun getPostInteractions(postId: String): Flow<PostLocalInteractions> = dataStore.data.map { prefs ->
        val likedSet = prefs[KEY_LIKED_POST_IDS] ?: emptySet()
        val savedSet = prefs[KEY_SAVED_POST_IDS] ?: emptySet()
        val likes = prefs[keyLikes(postId)] ?: 0
        val rawComments = prefs[keyComments(postId)] ?: ""
        val commentsList = if (rawComments.isBlank()) emptyList() else rawComments.split("\n---\n")

        PostLocalInteractions(
            isLiked = likedSet.contains(postId),
            extraLikes = likes,
            isSaved = savedSet.contains(postId),
            comments = commentsList
        )
    }

    suspend fun getPostInteractionsSnapshot(postId: String): PostLocalInteractions {
        val prefs = dataStore.data.first()
        val likedSet = prefs[KEY_LIKED_POST_IDS] ?: emptySet()
        val savedSet = prefs[KEY_SAVED_POST_IDS] ?: emptySet()
        val likes = prefs[keyLikes(postId)] ?: 0
        val rawComments = prefs[keyComments(postId)] ?: ""
        val commentsList = if (rawComments.isBlank()) emptyList() else rawComments.split("\n---\n")

        return PostLocalInteractions(
            isLiked = likedSet.contains(postId),
            extraLikes = likes,
            isSaved = savedSet.contains(postId),
            comments = commentsList
        )
    }

    suspend fun toggleLike(postId: String): Boolean {
        var nowLiked = false
        dataStore.edit { prefs ->
            val currentLikes = prefs[KEY_LIKED_POST_IDS]?.toMutableSet() ?: mutableSetOf()
            val count = prefs[keyLikes(postId)] ?: 0
            if (currentLikes.contains(postId)) {
                currentLikes.remove(postId)
                prefs[keyLikes(postId)] = (count - 1).coerceAtLeast(0)
                nowLiked = false
            } else {
                currentLikes.add(postId)
                prefs[keyLikes(postId)] = count + 1
                nowLiked = true
            }
            prefs[KEY_LIKED_POST_IDS] = currentLikes
        }
        return nowLiked
    }

    suspend fun toggleSave(postId: String): Boolean {
        var nowSaved = false
        dataStore.edit { prefs ->
            val currentSaved = prefs[KEY_SAVED_POST_IDS]?.toMutableSet() ?: mutableSetOf()
            if (currentSaved.contains(postId)) {
                currentSaved.remove(postId)
                nowSaved = false
            } else {
                currentSaved.add(postId)
                nowSaved = true
            }
            prefs[KEY_SAVED_POST_IDS] = currentSaved
        }
        return nowSaved
    }

    suspend fun addComment(postId: String, comment: String): List<String> {
        if (comment.isBlank()) return getComments(postId)
        var updatedList: List<String> = emptyList()
        dataStore.edit { prefs ->
            val raw = prefs[keyComments(postId)] ?: ""
            val list = if (raw.isBlank()) mutableListOf() else raw.split("\n---\n").toMutableList()
            list.add(comment.trim())
            prefs[keyComments(postId)] = list.joinToString("\n---\n")
            updatedList = list
        }
        return updatedList
    }

    suspend fun getComments(postId: String): List<String> {
        val prefs = dataStore.data.first()
        val raw = prefs[keyComments(postId)] ?: ""
        return if (raw.isBlank()) emptyList() else raw.split("\n---\n")
    }

    suspend fun savePostImages(postId: String, paths: List<String>) {
        dataStore.edit { prefs ->
            prefs[keyImages(postId)] = paths.joinToString("\n---\n")
        }
    }

    suspend fun getPostImages(postId: String): List<String> {
        val prefs = dataStore.data.first()
        val raw = prefs[keyImages(postId)] ?: ""
        return if (raw.isBlank()) emptyList() else raw.split("\n---\n").filter { it.isNotBlank() }
    }

    suspend fun addPostImages(postId: String, newPaths: List<String>) {
        if (newPaths.isEmpty()) return
        dataStore.edit { prefs ->
            val raw = prefs[keyImages(postId)] ?: ""
            val current = if (raw.isBlank()) mutableListOf() else raw.split("\n---\n").filter { it.isNotBlank() }.toMutableList()
            current.addAll(newPaths)
            prefs[keyImages(postId)] = current.distinct().joinToString("\n---\n")
        }
    }

    suspend fun removePostImage(postId: String, pathToRemove: String) {
        dataStore.edit { prefs ->
            val raw = prefs[keyImages(postId)] ?: ""
            val current = if (raw.isBlank()) emptyList() else raw.split("\n---\n").filter { it.isNotBlank() }
            val updated = current.filter { it != pathToRemove }
            prefs[keyImages(postId)] = updated.joinToString("\n---\n")
        }
    }

    suspend fun savePostTarifa(postId: String, tarifa: String) {
        dataStore.edit { prefs ->
            prefs[keyTarifa(postId)] = tarifa
        }
    }

    suspend fun getPostTarifa(postId: String): String? {
        val prefs = dataStore.data.first()
        return prefs[keyTarifa(postId)]?.ifBlank { null }
    }
}

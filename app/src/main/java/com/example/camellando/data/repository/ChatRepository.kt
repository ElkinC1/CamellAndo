package com.example.camellando.data.repository

import com.example.camellando.data.model.Chat
import com.example.camellando.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatsForUser(userId: String): Result<List<Chat>>
    suspend fun findChatBetweenUsers(firstUserId: String, secondUserId: String): Result<Chat?>
    suspend fun getOrCreateChat(firstUserId: String, secondUserId: String): Result<Chat>
    suspend fun getMessages(chatId: String): Result<List<ChatMessage>>
    fun observeMessages(chatId: String): Flow<List<ChatMessage>>
    suspend fun sendMessage(chatId: String, senderId: String, body: String): Result<ChatMessage>
}

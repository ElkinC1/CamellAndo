package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.Chat
import com.example.camellando.data.model.ChatMessage
import com.example.camellando.data.model.dto.ChatDto
import com.example.camellando.data.model.dto.ChatMessageDto
import com.example.camellando.data.model.dto.CreateChatDto
import com.example.camellando.data.model.dto.CreateChatMessageDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.ChatRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseChatRepository(
    private val supabase: SupabaseClient
) : ChatRepository {

    override suspend fun getChatsForUser(userId: String): Result<List<Chat>> =
        repositorySafeCall {
            val asUserA = supabase.from("chats").select {
                filter { eq("user_a_id", userId) }
            }.decodeList<ChatDto>()
            val asUserB = supabase.from("chats").select {
                filter { eq("user_b_id", userId) }
            }.decodeList<ChatDto>()

            (asUserA + asUserB)
                .distinctBy { it.id }
                .map { it.toDomain() }
        }

    override suspend fun findChatBetweenUsers(
        firstUserId: String,
        secondUserId: String
    ): Result<Chat?> = repositorySafeCall {
        val (userA, userB) = orderedIds(firstUserId, secondUserId)
        supabase.from("chats").select {
            filter {
                eq("user_a_id", userA)
                eq("user_b_id", userB)
            }
        }.decodeList<ChatDto>().firstOrNull()?.toDomain()
    }

    override suspend fun getOrCreateChat(
        firstUserId: String,
        secondUserId: String
    ): Result<Chat> = repositorySafeCall {
        require(firstUserId != secondUserId) { "Un usuario no puede abrir un chat consigo mismo." }
        val (userA, userB) = orderedIds(firstUserId, secondUserId)

        val existing = supabase.from("chats").select {
            filter {
                eq("user_a_id", userA)
                eq("user_b_id", userB)
            }
        }.decodeList<ChatDto>().firstOrNull()

        existing?.toDomain() ?: supabase.from("chats")
            .insert(CreateChatDto(userAId = userA, userBId = userB)) { select() }
            .decodeSingle<ChatDto>()
            .toDomain()
    }

    override suspend fun getMessages(chatId: String): Result<List<ChatMessage>> =
        repositorySafeCall {
            supabase.from("chat_messages").select {
                filter { eq("chat_id", chatId) }
            }.decodeList<ChatMessageDto>().map { it.toDomain() }
        }

    @OptIn(SupabaseExperimental::class)
    override fun observeMessages(chatId: String): Flow<List<ChatMessage>> =
        supabase.from("chat_messages").selectAsFlow(
            primaryKey = ChatMessageDto::id,
            filter = FilterOperation("chat_id", FilterOperator.EQ, chatId)
        ).map { messages -> messages.map { it.toDomain() } }

    override suspend fun sendMessage(
        chatId: String,
        senderId: String,
        body: String
    ): Result<ChatMessage> = repositorySafeCall {
        val dto = CreateChatMessageDto(
            chatId = chatId,
            senderId = senderId,
            content = body
        )
        supabase.from("chat_messages")
            .insert(dto) { select() }
            .decodeSingle<ChatMessageDto>()
            .toDomain()
    }

    private fun orderedIds(firstUserId: String, secondUserId: String): Pair<String, String> =
        if (firstUserId <= secondUserId) firstUserId to secondUserId else secondUserId to firstUserId
}

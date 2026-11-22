package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    val id: String,

    @SerialName("chat_id")
    val chatId: String,

    @SerialName("sender_id")
    val senderId: String,

    @SerialName("body")
    val content: String,

    @SerialName("created_at")
    val createdAt: String
)
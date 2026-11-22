package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatMessageDto(
    @SerialName("chat_id")
    val chatId: String,

    @SerialName("sender_id")
    val senderId: String,

    @SerialName("body")
    val content: String
)
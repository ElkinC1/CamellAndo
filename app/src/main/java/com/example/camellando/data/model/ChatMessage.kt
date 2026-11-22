package com.example.camellando.data.model

data class ChatMessage(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String
)
package com.example.camellando.data.model

data class Notification(
    val id: String,
    val recipientId: String,
    val actorId: String?,
    val type: String,
    val title: String,
    val body: String,
    val relatedType: String?,
    val relatedId: String?,
    val isRead: Boolean,
    val createdAt: String
)

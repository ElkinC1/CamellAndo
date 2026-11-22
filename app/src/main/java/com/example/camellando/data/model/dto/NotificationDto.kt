package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: String,

    @SerialName("recipient_id")
    val recipientId: String,

    @SerialName("actor_id")
    val actorId: String? = null,

    val type: String,
    val title: String,
    val body: String,

    @SerialName("related_type")
    val relatedType: String? = null,

    @SerialName("related_id")
    val relatedId: String? = null,

    @SerialName("is_read")
    val isRead: Boolean,

    @SerialName("created_at")
    val createdAt: String
)

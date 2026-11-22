package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,

    @SerialName("user_a_id")
    val userAId: String,

    @SerialName("user_b_id")
    val userBId: String,

    @SerialName("created_at")
    val createdAt: String
)
package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatDto(
    @SerialName("user_a_id")
    val userAId: String,

    @SerialName("user_b_id")
    val userBId: String
)
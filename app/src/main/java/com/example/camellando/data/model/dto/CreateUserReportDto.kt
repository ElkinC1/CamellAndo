package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserReportDto(
    @SerialName("reporter_id")
    val reporterId: String,

    @SerialName("reported_user_id")
    val reportedUserId: String,

    val reason: String,
    val details: String? = null,
    val status: String = "PENDING"
)

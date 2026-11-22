package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioDto(
    val id: String,

    @SerialName("provider_id")
    val providerId: String,

    val title: String? = null,
    val description: String? = null,

    @SerialName("created_at")
    val createdAt: String
)

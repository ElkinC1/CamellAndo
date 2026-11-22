package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePortfolioDto(
    @SerialName("provider_id")
    val providerId: String,

    val title: String? = null,
    val description: String? = null
)

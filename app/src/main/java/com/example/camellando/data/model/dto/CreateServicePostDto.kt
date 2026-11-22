package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateServicePostDto(
    @SerialName("provider_id")
    val providerId: String,

    val title: String? = null,
    val description: String? = null,
    val category: String,
    val price: Double? = null,
    val currency: String = "USD",
    val status: String = "OPEN"
)
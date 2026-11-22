package com.example.camellando.data.model.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServicePostDto(
    val id: String,

    @SerialName("provider_id")
    val providerId: String,

    val title: String? = null,
    val description: String? = null,
    val category: String,
    val price: Double? = null,
    val currency: String = "USD",
    val status: String,

    @SerialName("created_at")
    val createdAt: String
)
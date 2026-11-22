package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceContractDto(
    val id: String,

    @SerialName("service_post_id")
    val servicePostId: String? = null,

    @SerialName("accepted_offer_id")
    val acceptedOfferId: String? = null,

    @SerialName("client_id")
    val clientId: String,

    @SerialName("provider_id")
    val providerId: String,

    @SerialName("agreed_price")
    val agreedPrice: Double,

    val currency: String = "USD",
    val status: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("completed_at")
    val completedAt: String? = null,

    @SerialName("cancelled_at")
    val cancelledAt: String? = null
)

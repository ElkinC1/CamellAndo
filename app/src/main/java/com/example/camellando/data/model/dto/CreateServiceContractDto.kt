package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateServiceContractDto(
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
    val status: String = "IN_PROGRESS"
)

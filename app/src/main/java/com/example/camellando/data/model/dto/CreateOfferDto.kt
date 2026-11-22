package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOfferDto(
    @SerialName("post_id")
    val postId: String,

    @SerialName("client_id")
    val clientId: String,

    @SerialName("provider_id")
    val providerId: String,

    @SerialName("parent_offer_id")
    val parentOfferId: String? = null,

    val amount: Double,
    val currency: String = "USD",
    val message: String? = null,

    val status: String = "PENDING"
)
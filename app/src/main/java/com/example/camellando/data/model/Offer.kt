package com.example.camellando.data.model

data class Offer(
    val id: String,
    val postId: String,
    val clientId: String,
    val providerId: String,
    val parentOfferId: String?,
    val amountCents: Long,
    val currency: String,
    val message: String?,
    val status: OfferStatus,
    val createdAt: String
)

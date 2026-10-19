package com.example.camellando.data.model

data class Offer(
    val id: String = "",
    val postId: String = "",
    val clientId: String = "",
    val taskDescription: String = "",
    val budget: Double = 0.0,
    val status: String = "PENDING" // PENDING, ACCEPTED, COUNTER_OFFER, REJECTED
)

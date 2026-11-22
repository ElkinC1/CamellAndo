package com.example.camellando.data.model

data class ServiceContract(
    val id: String,
    val servicePostId: String?,
    val acceptedOfferId: String?,
    val clientId: String,
    val providerId: String,
    val agreedPriceCents: Long,
    val currency: String,
    val status: ServiceContractStatus,
    val createdAt: String,
    val completedAt: String?,
    val cancelledAt: String?
)

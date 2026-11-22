package com.example.camellando.data.model

data class ServicePost(
    val id: String,
    val providerId: String,
    val title: String?,
    val description: String?,
    val category: String,
    val priceCents: Long?,
    val currency: String,
    val status: ServicePostStatus,
    val createdAt: String
)

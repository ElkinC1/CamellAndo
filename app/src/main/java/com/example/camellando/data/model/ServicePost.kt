package com.example.camellando.data.model

data class ServicePost(
    val id: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val rating: Float = 5.0f,
    val images: List<String> = emptyList()
)

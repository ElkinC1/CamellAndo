package com.example.camellando.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "client", // "client" o "provider"
    val avatarUrl: String = "",
    val occupation: String = "",
    val rating: Float = 0f,
    val bio: String = ""
)

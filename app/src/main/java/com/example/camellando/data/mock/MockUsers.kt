package com.example.camellando.data.mock

import com.example.camellando.data.model.User

data class MockAccount(
    val user: User,
    val password: String
)

val mockAccounts = listOf(
    MockAccount(
        user = User(
            id = "user_001",
            name = "antonio Martinez",
            email = "Antonio@camellando.com",
            role = "client"
        ),
        password = "Antonio123"
    )
)
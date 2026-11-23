package com.example.camellando.data.repository

import com.example.camellando.data.model.AuthSessionState
import com.example.camellando.data.model.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        role: UserRole,
        location: String,
        birthDate: String,
        identification: String,
        occupation: String? = null,
        experienceYears: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ): Result<String>

    suspend fun signIn(email: String, password: String): Result<String>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUserId(): Result<String?>
    fun observeSession(): Flow<AuthSessionState>
}

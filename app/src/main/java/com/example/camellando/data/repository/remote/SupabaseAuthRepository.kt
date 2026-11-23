package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.AuthSessionState
import com.example.camellando.data.model.UserRole
import com.example.camellando.data.model.dto.CreateProfileDto
import com.example.camellando.data.repository.AuthRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseAuthRepository(
    private val supabase: SupabaseClient
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        role: UserRole,
        location: String,
        birthDate: String,
        identification: String,
        occupation: String?,
        experienceYears: String?,
        minPrice: Double?,
        maxPrice: Double?
    ): Result<String> = repositorySafeCall {
        val profileMetadata = CreateProfileDto(
            displayName = displayName,
            role = role.name,
            location = location,
            birthDate = birthDate,
            identification = identification,
            occupation = occupation,
            experienceYears = experienceYears,
            minPrice = minPrice,
            maxPrice = maxPrice
        ).toAuthMetadata()

        val createdUser = supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = profileMetadata
        }

        createdUser?.id
            ?: supabase.auth.currentUserOrNull()?.id
            ?: error("Supabase no devolvió el ID del usuario registrado.")
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<String> = repositorySafeCall {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        supabase.auth.currentUserOrNull()?.id
            ?: error("El inicio de sesión terminó sin una sesión de usuario.")
    }

    override suspend fun signOut(): Result<Unit> =
        repositorySafeCall {
            supabase.auth.signOut()
        }

    override suspend fun getCurrentUserId(): Result<String?> =
        repositorySafeCall {
            supabase.auth.currentUserOrNull()?.id
        }

    override fun observeSession(): Flow<AuthSessionState> =
        supabase.auth.sessionStatus.map { status ->
            when (status) {
                SessionStatus.Initializing ->
                    AuthSessionState.Checking

                is SessionStatus.Authenticated -> {
                    val userId = status.session.user?.id
                    if (userId == null) {
                        AuthSessionState.SessionUnavailable
                    } else {
                        AuthSessionState.SignedIn(userId)
                    }
                }

                is SessionStatus.NotAuthenticated ->
                    AuthSessionState.SignedOut

                is SessionStatus.RefreshFailure ->
                    AuthSessionState.SessionUnavailable
            }
        }
}

package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.AuthSessionState
import com.example.camellando.data.model.UserRole
import com.example.camellando.data.repository.AuthRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseAuthRepository(
    private val supabase: SupabaseClient
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        role: UserRole
    ): Result<String> = repositorySafeCall {
        val createdUser = supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("display_name", displayName)
                put("role", role.name)
            }
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
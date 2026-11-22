package com.example.camellando.data.model

sealed interface AuthSessionState {
    data object Checking : AuthSessionState
    data object SignedOut : AuthSessionState
    data object SessionUnavailable : AuthSessionState
    data class SignedIn(val userId: String) : AuthSessionState
}
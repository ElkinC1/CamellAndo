package com.example.camellando.ui.auth

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.camellando.data.mock.mockAccounts

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                errorMessage = null
            )
        }
    }

    fun onPasswordVisibilityChange() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    fun onLoginClick(): Boolean {
        val state = _uiState.value
        val email = state.email.trim()

        when {
            email.isBlank() || state.password.isBlank() -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Completa el correo y la contraseña."
                    )
                }
                return false
            }

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Ingresa un correo válido."
                    )
                }
                return false
            }

            else -> {
                val account = mockAccounts.firstOrNull {
                    it.user.email.equals(email, ignoreCase = true) &&
                            it.password == state.password
                }

                return if (account != null) {
                    _uiState.update {
                        it.copy(errorMessage = null)
                    }
                    true
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Correo o contraseña incorrectos."
                        )
                    }
                    false
                }
            }
        }
    }
}
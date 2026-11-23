package com.example.camellando.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camellando.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginNavigationHandled() {
        _uiState.update { it.copy(loginSucceeded = false) }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                errorMessage = null,
                loginSucceeded = false
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                errorMessage = null,
                loginSucceeded = false
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

    fun onLoginClick() {
        val state = _uiState.value
        val email = state.email.trim()

        when {
            email.isBlank() || state.password.isBlank() -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Completa el correo y la contraseña."
                    )
                }
            }

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Ingresa un correo válido."
                    )
                }
            }

            else -> {
                _uiState.update {
                    it.copy(isLoading = true, errorMessage = null, loginSucceeded = false)
                }
                viewModelScope.launch {
                    authRepository.signIn(email, state.password)
                        .onSuccess {
                            _uiState.update {
                                it.copy(isLoading = false, loginSucceeded = true)
                            }
                        }
                        .onFailure {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "No se pudo iniciar sesión. Verifica tus datos e inténtalo de nuevo."
                                )
                            }
                        }
                }
            }
        }
    }
}

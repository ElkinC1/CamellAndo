
package com.example.camellando.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camellando.data.model.ProviderRegistrationDetails
import com.example.camellando.data.model.UserRole
import com.example.camellando.data.repository.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNombreChanged(v: String) = updateForm { copy(nombre = v) }
    fun onApellidoChanged(v: String) = updateForm { copy(apellido = v) }
    fun onCantonChanged(v: String) = updateForm { copy(canton = v) }
    fun onNacimientoChanged(v: String) = updateForm { copy(nacimiento = v) }
    fun onIdentificacionChanged(v: String) = updateForm { copy(identificacion = v) }
    fun onCorreoChanged(v: String) = updateForm { copy(correo = v) }
    fun onContrasenaChanged(v: String) = updateForm { copy(contrasena = v) }
    fun onRepetirContrasenaChanged(v: String) = updateForm { copy(repetirContrasena = v) }

    fun onRegistrationNavigationHandled() {
        _uiState.update {
            it.copy(
                registerSucceeded = false,
                successRole = null,
                requiresEmailConfirmation = false
            )
        }
    }

    fun validateCommonFields(): Boolean {
        val state = _uiState.value
        val email = state.correo.trim()
        val birthDate = state.nacimiento.toIsoDate()

        val validationError = when {
            state.nombre.isBlank() || state.apellido.isBlank() ||
                state.canton == "Selecciona" || state.nacimiento.isBlank() ||
                state.identificacion.isBlank() || email.isBlank() ||
                state.contrasena.isBlank() || state.repetirContrasena.isBlank() ->
                "Completa todos los campos obligatorios."

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Ingresa un correo válido."

            state.contrasena.length < 6 ->
                "La contraseña debe tener al menos 6 caracteres."

            state.contrasena != state.repetirContrasena ->
                "Las contraseñas no coinciden."

            birthDate == null ->
                "La fecha de nacimiento no tiene un formato válido."

            else -> null
        }

        if (validationError != null) {
            _uiState.update { it.copy(errorMessage = validationError, registerSucceeded = false) }
            return false
        }

        _uiState.update { it.copy(errorMessage = null) }
        return true
    }

    fun registerClient() {
        if (validateCommonFields()) submitRegistration(UserRole.CLIENT)
    }

    fun registerProvider(details: ProviderRegistrationDetails) {
        if (validateCommonFields()) submitRegistration(UserRole.PROVIDER, details)
    }

    private fun submitRegistration(
        role: UserRole,
        providerDetails: ProviderRegistrationDetails? = null
    ) {
        val state = _uiState.value
        val email = state.correo.trim()
        val displayName = "${state.nombre.trim()} ${state.apellido.trim()}"
        val birthDate = requireNotNull(state.nacimiento.toIsoDate())

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                registerSucceeded = false,
                requiresEmailConfirmation = false
            )
        }
        viewModelScope.launch {
            authRepository.signUp(
                email = email,
                password = state.contrasena,
                displayName = displayName,
                role = role,
                location = state.canton,
                birthDate = birthDate,
                identification = state.identificacion.trim(),
                occupation = providerDetails?.occupation,
                experienceYears = providerDetails?.experienceYears,
                minPrice = providerDetails?.minPrice,
                maxPrice = providerDetails?.maxPrice
            ).onSuccess {
                val requiresEmailConfirmation =
                    authRepository.getCurrentUserId().getOrNull() == null
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registerSucceeded = true,
                        successRole = role,
                        requiresEmailConfirmation = requiresEmailConfirmation
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo crear la cuenta. Verifica tus datos e inténtalo de nuevo."
                    )
                }
            }
        }
    }

    private fun updateForm(transform: RegisterUiState.() -> RegisterUiState) {
        _uiState.update { it.transform().copy(errorMessage = null, registerSucceeded = false) }
    }

    private fun String.toIsoDate(): String? = runCatching {
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        formatter.isLenient = false
        val parsed = formatter.parse(this) ?: return null
        java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(parsed)
    }.getOrNull()
}

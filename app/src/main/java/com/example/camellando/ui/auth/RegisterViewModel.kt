
package com.example.camellando.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNombreChanged(v: String) = _uiState.update { it.copy(nombre = v) }
    fun onApellidoChanged(v: String) = _uiState.update { it.copy(apellido = v) }
    fun onCantonChanged(v: String) = _uiState.update { it.copy(canton = v) }
    fun onNacimientoChanged(v: String) = _uiState.update { it.copy(nacimiento = v) }
    fun onIdentificacionChanged(v: String) = _uiState.update { it.copy(identificacion = v) }
    fun onTipoIdentificacionChanged(v: String) = _uiState.update { it.copy(tipoIdentificacion = v) }
    fun onCorreoChanged(v: String) = _uiState.update { it.copy(correo = v) }
    fun onContrasenaChanged(v: String) = _uiState.update { it.copy(contrasena = v) }
    fun onRepetirContrasenaChanged(v: String) = _uiState.update { it.copy(repetirContrasena = v) }
}
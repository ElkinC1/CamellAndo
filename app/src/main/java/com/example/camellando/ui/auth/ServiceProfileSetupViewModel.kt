package com.example.camellando.ui.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServiceProfileSetupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceProfileSetupUiState())
    val uiState: StateFlow<ServiceProfileSetupUiState> = _uiState.asStateFlow()

    fun onOficioChanged(oficio: String) {
        _uiState.update {
            it.copy(
                oficioSeleccionado = oficio,
                hayErrorOficio = false,
                mensajeErrorGeneral = null
            )
        }
    }

    fun onExperienciaChanged(experiencia: String) {
        _uiState.update {
            it.copy(
                experienciaSeleccionada = experiencia,
                hayErrorExperiencia = false,
                mensajeErrorGeneral = null
            )
        }
    }

    fun onPrecioMinChanged(precioMin: String) {
        _uiState.update { current ->
            calcularValidacionPrecios(
                current.copy(
                    precioMin = precioMin,
                    haInteractuadoConPrecios = true,
                    mensajeErrorGeneral = null
                )
            )
        }
    }

    fun onPrecioMaxChanged(precioMax: String) {
        _uiState.update { current ->
            calcularValidacionPrecios(
                current.copy(
                    precioMax = precioMax,
                    haInteractuadoConPrecios = true,
                    mensajeErrorGeneral = null
                )
            )
        }
    }

    fun onArchivoCVSeleccionado(uri: Uri?) {
        if (uri != null) {
            _uiState.update {
                it.copy(
                    cvUri = uri,
                    nombreArchivoCV = uri.lastPathSegment ?: "documento_seleccionado.pdf",
                    subdescripcionCV = "Archivo adjuntado con éxito"
                )
            }
        }
    }

    fun onRegistrarClick(): Boolean {
        val state = _uiState.value
        val estadoConPrecios = calcularValidacionPrecios(state.copy(haInteractuadoConPrecios = true))

        val hayErrorOficio = estadoConPrecios.oficioSeleccionado.isBlank()
        val hayErrorExperiencia = estadoConPrecios.experienciaSeleccionada.isBlank()
        val hayErrorPrecios = estadoConPrecios.hayErrorPrecio ||
                estadoConPrecios.precioMin.isBlank() ||
                estadoConPrecios.precioMax.isBlank()

        val tieneErrores = hayErrorOficio || hayErrorExperiencia || hayErrorPrecios

        val mensajeGeneral = when {
            hayErrorOficio && hayErrorExperiencia -> "Selecciona tu oficio y tiempo de experiencia."
            hayErrorOficio -> "Debes seleccionar un oficio."
            hayErrorExperiencia -> "Debes seleccionar tu tiempo de experiencia."
            hayErrorPrecios -> estadoConPrecios.mensajeErrorPrecio ?: "Completa los precios correctamente."
            else -> null
        }

        _uiState.value = estadoConPrecios.copy(
            hayErrorOficio = hayErrorOficio,
            hayErrorExperiencia = hayErrorExperiencia,
            mensajeErrorGeneral = if (tieneErrores) mensajeGeneral else null
        )

        return !tieneErrores
    }

    private fun calcularValidacionPrecios(state: ServiceProfileSetupUiState): ServiceProfileSetupUiState {
        val minVal = state.precioMin.toDoubleOrNull() ?: 0.0
        val maxVal = state.precioMax.toDoubleOrNull() ?: 0.0
        val estaVacio = state.precioMin.isBlank() || state.precioMax.isBlank()
        val esMinMayor = state.precioMin.isNotBlank() && state.precioMax.isNotBlank() && minVal >= maxVal
        val hayError = state.haInteractuadoConPrecios && (estaVacio || esMinMayor)
        val mensajeError = when {
            !state.haInteractuadoConPrecios -> null
            estaVacio -> "Los valores mínimo y máximo no pueden estar vacíos."
            esMinMayor -> "El valor mínimo no puede ser mayor o igual que el máximo."
            else -> null
        }

        return state.copy(
            hayErrorPrecio = hayError,
            mensajeErrorPrecio = mensajeError
        )
    }
}

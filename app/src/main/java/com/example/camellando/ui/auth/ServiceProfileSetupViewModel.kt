package com.example.camellando.ui.auth

import androidx.lifecycle.ViewModel
import com.example.camellando.data.model.ProviderRegistrationDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServiceProfileSetupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceProfileSetupUiState())
    val uiState: StateFlow<ServiceProfileSetupUiState> = _uiState.asStateFlow()

    fun onOficioChanged(oficio: String) {
        _uiState.update {
            it.copy(oficioSeleccionado = oficio, hayErrorOficio = false, mensajeErrorGeneral = null)
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
                current.copy(precioMin = precioMin, haInteractuadoConPrecios = true)
            )
        }
    }

    fun onPrecioMaxChanged(precioMax: String) {
        _uiState.update { current ->
            calcularValidacionPrecios(
                current.copy(precioMax = precioMax, haInteractuadoConPrecios = true)
            )
        }
    }

    fun createRegistrationDetails(): ProviderRegistrationDetails? {
        val current = calcularValidacionPrecios(
            _uiState.value.copy(haInteractuadoConPrecios = true)
        )
        val minPrice = current.precioMin.toDoubleOrNull()
        val maxPrice = current.precioMax.toDoubleOrNull()
        val hasOccupationError = current.oficioSeleccionado.isBlank()
        val hasExperienceError = current.experienciaSeleccionada.isBlank()
        val hasPriceError = minPrice == null || maxPrice == null || minPrice < 0 || maxPrice <= minPrice

        val message = when {
            hasOccupationError && hasExperienceError -> "Selecciona tu oficio y tiempo de experiencia."
            hasOccupationError -> "Debes seleccionar un oficio."
            hasExperienceError -> "Debes seleccionar tu tiempo de experiencia."
            hasPriceError -> "Ingresa precios válidos: el mínimo debe ser menor que el máximo."
            else -> null
        }

        _uiState.value = current.copy(
            hayErrorOficio = hasOccupationError,
            hayErrorExperiencia = hasExperienceError,
            mensajeErrorGeneral = message
        )

        if (message != null) return null
        return ProviderRegistrationDetails(
            occupation = current.oficioSeleccionado,
            experienceYears = current.experienciaSeleccionada,
            minPrice = requireNotNull(minPrice),
            maxPrice = requireNotNull(maxPrice)
        )
    }

    private fun calcularValidacionPrecios(state: ServiceProfileSetupUiState): ServiceProfileSetupUiState {
        val minValue = state.precioMin.toDoubleOrNull()
        val maxValue = state.precioMax.toDoubleOrNull()
        val missingPrice = state.precioMin.isBlank() || state.precioMax.isBlank()
        val invalidPrice = minValue == null || maxValue == null || minValue < 0 || maxValue <= minValue
        val hasError = state.haInteractuadoConPrecios && (missingPrice || invalidPrice)
        val message = when {
            !state.haInteractuadoConPrecios -> null
            missingPrice -> "Los valores mínimo y máximo no pueden estar vacíos."
            invalidPrice -> "El valor mínimo debe ser menor que el máximo y ambos deben ser válidos."
            else -> null
        }

        return state.copy(hayErrorPrecio = hasError, mensajeErrorPrecio = message)
    }
}

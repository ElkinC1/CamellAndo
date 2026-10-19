package com.example.camellando.ui.negotiation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterOfferReceivedViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CounterOfferReceivedUiState())
    val uiState: StateFlow<CounterOfferReceivedUiState> = _uiState.asStateFlow()

    fun cargarContraoferta(contraoferta: ContraOfertaDetalleModel) {
        _uiState.update {
            it.copy(
                contraoferta = contraoferta,
                mostrarDialogo = true,
                mensajeError = null
            )
        }
    }

    fun onAceptarContraoferta(): Boolean {
        val state = _uiState.value
        if (!state.puedeNegociar) {
            _uiState.update {
                it.copy(mensajeError = "No se puede procesar la contraoferta en este momento.")
            }
            return false
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                contraofertaAceptada = true,
                mostrarAlertaExito = false,
                mensajeError = null
            )
        }
        return true
    }

    fun onContraoferta(): Boolean {
        val state = _uiState.value
        if (!state.puedeNegociar) {
            _uiState.update {
                it.copy(mensajeError = "No se puede contraofertar en este momento.")
            }
            return false
        }

        _uiState.update {
            it.copy(
                abrirModalNuevaContraoferta = true,
                mensajeError = null
            )
        }
        return true
    }

    fun onContraofertar(): Boolean = onContraoferta()

    fun onRechazarContraoferta(): Boolean {
        val state = _uiState.value
        if (!state.puedeNegociar) {
            _uiState.update {
                it.copy(mensajeError = "No se puede procesar el rechazo en este momento.")
            }
            return false
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                contraofertaRechazada = true,
                mensajeError = null
            )
        }
        return true
    }

    fun onDismissDialog() {
        _uiState.update {
            it.copy(mostrarDialogo = false)
        }
    }

    fun onDismissAlertaExito() {
        _uiState.update {
            it.copy(mostrarAlertaExito = false)
        }
    }

    fun obtenerContraoferta(): ContraOfertaDetalleModel {
        return _uiState.value.contraoferta
    }

    fun reiniciarEstado() {
        _uiState.value = CounterOfferReceivedUiState()
    }
}

typealias CounterOfferReceivedDialogViewModel = CounterOfferReceivedViewModel
typealias CounterOfferViewModel = CounterOfferReceivedViewModel
typealias ContraOfertaViewModel = CounterOfferReceivedViewModel
typealias ContraofertaRecibidaViewModel = CounterOfferReceivedViewModel

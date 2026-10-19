package com.example.camellando.ui.feed

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Post(
    val autor: String = "Juan Álvarez",
    val texto: String,
    val imagenesUri: List<Uri> = emptyList(),
    val videosUri: List<Uri> = emptyList(),
    val esPublico: Boolean = true
)

class CreatePostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState: StateFlow<CreatePostUiState> = _uiState.asStateFlow()

    fun onTextoChanged(texto: String) {
        _uiState.update {
            it.copy(
                textoPublicacion = texto,
                mensajeError = null
            )
        }
    }

    fun onFotosAgregadas(uris: List<Uri>) {
        if (uris.isNotEmpty()) {
            _uiState.update { current ->
                val nuevasFotos = (current.fotosSeleccionadas + uris).distinct().take(5)
                current.copy(
                    fotosSeleccionadas = nuevasFotos,
                    mensajeError = null
                )
            }
        }
    }

    fun onEliminarFoto(uri: Uri) {
        _uiState.update { current ->
            current.copy(fotosSeleccionadas = current.fotosSeleccionadas - uri)
        }
    }

    fun onVideoAgregado(uri: Uri?) {
        if (uri != null) {
            _uiState.update { current ->
                current.copy(
                    videosSeleccionados = current.videosSeleccionados + uri,
                    mensajeError = null
                )
            }
        }
    }

    fun onEliminarVideo(uri: Uri) {
        _uiState.update { current ->
            current.copy(videosSeleccionados = current.videosSeleccionados - uri)
        }
    }

    fun onTogglePrivacidad() {
        _uiState.update {
            it.copy(esPublico = !it.esPublico)
        }
    }

    fun onPublicarClick(): Boolean {
        val state = _uiState.value
        if (!state.puedePublicar) {
            _uiState.update {
                it.copy(mensajeError = "Debes ingresar texto o adjuntar una foto o video.")
            }
            return false
        }

        _uiState.update {
            it.copy(
                mensajeError = null,
                mostrarAlertaExito = false,
                publicacionExitosa = true
            )
        }
        return true
    }

    fun onDismissAlertaExito() {
        _uiState.update {
            it.copy(mostrarAlertaExito = false)
        }
    }

    fun obtenerPost(): Post {
        val state = _uiState.value
        return Post(
            autor = state.autor,
            texto = state.textoPublicacion,
            imagenesUri = state.fotosSeleccionadas,
            videosUri = state.videosSeleccionados,
            esPublico = state.esPublico
        )
    }

    fun reiniciarEstado() {
        _uiState.value = CreatePostUiState()
    }
}

typealias CreatePostScreenViewModel = CreatePostViewModel
typealias PostViewModel = CreatePostViewModel

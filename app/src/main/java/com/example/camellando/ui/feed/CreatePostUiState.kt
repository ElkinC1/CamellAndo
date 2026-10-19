package com.example.camellando.ui.feed

import android.net.Uri

data class CreatePostUiState(
    val autor: String = "Juan Álvarez",
    val autorInicial: String = "J",
    val esPublico: Boolean = true,
    val textoPublicacion: String = "",
    val fotosSeleccionadas: List<Uri> = emptyList(),
    val videosSeleccionados: List<Uri> = emptyList(),
    val estadoPublicacion: String = "Borrador",
    val isLoading: Boolean = false,
    val mensajeError: String? = null,
    val publicacionExitosa: Boolean = false,
    val mostrarAlertaExito: Boolean = false
) {
    val puedePublicar: Boolean
        get() = textoPublicacion.isNotBlank() || fotosSeleccionadas.isNotEmpty() || videosSeleccionados.isNotEmpty()
}

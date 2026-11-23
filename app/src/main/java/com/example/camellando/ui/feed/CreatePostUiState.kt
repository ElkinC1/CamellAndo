package com.example.camellando.ui.feed

import android.net.Uri

data class CreatePostUiState(
    val autor: String = "Usuario",
    val autorInicial: String = "U",
    val esPublico: Boolean = true,
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "Electricidad",
    val precioEstimado: String = "", // Opcional en dólares
    val tipoTarifa: String = "Por trabajo", // Por trabajo, Por hora, Por cita
    val categoriasDisponibles: List<String> = listOf("Electricidad", "Plomería", "Limpieza", "Pintura", "Construcción", "Otros"),
    val tiposTarifaDisponibles: List<String> = listOf("Por trabajo", "Por hora", "Por cita"),
    val fotosSeleccionadas: List<Uri> = emptyList(),
    val estadoPublicacion: String = "Borrador",
    val isLoading: Boolean = false,
    val mensajeError: String? = null,
    val publicacionExitosa: Boolean = false,
    val mostrarAlertaExito: Boolean = false
) {
    val puedePublicar: Boolean
        get() = (titulo.isNotBlank() || descripcion.isNotBlank() || fotosSeleccionadas.isNotEmpty()) && !isLoading
}

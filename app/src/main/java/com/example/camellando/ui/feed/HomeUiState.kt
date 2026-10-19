package com.example.camellando.ui.feed

import androidx.annotation.DrawableRes

data class ServicioModel(
    val id: String = "",
    val nombreProveedor: String = "",
    val profesion: String = "",
    val esVerificado: Boolean = false,
    val tiempoPublicacion: String = "",
    val categoria: String = "",
    val precio: String = "", // Nuevo campo de precio
    val tituloDescripcion: String = "",
    val descripcionDetallada: String = "",
    @DrawableRes val imagenResId: Int? = null,
    val imagenUri: String? = null,
    val cantidadFotos: String = "1/4",
    val likes: Int = 0,
    val comentarios: Int = 0,
    val estaDisponibleHoy: Boolean = true,
    val esGuardado: Boolean = false
)

data class HomeUiState(
    val pestañaSeleccionada: Int = 0, // 0: Para ti, 1: Mis Guardados
    val categoriaSeleccionada: String = "Todos",
    val listaCategorias: List<String> = listOf("Todos", "Electricidad", "Plomería", "Limpieza", "Pintura"),
    val publicaciones: List<ServicioModel> = emptyList(),
    val isLoading: Boolean = false,
    val mostrarAlertaCreada: Boolean = false
)
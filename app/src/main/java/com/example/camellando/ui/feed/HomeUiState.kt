package com.example.camellando.ui.feed

import android.net.Uri
import androidx.annotation.DrawableRes

data class FotoPostModel(
    val id: String = "",
    val storagePath: String = "",
    val url: String = ""
)

data class ServicioModel(
    val id: String = "",
    val providerId: String = "",
    val nombreProveedor: String = "",
    val profesion: String = "",
    val esVerificado: Boolean = false,
    val tiempoPublicacion: String = "",
    val categoria: String = "",
    val precio: String = "",
    val tipoTarifa: String = "",
    val tituloDescripcion: String = "",
    val descripcionDetallada: String = "",
    val rawTitle: String = "",
    val rawDescription: String = "",
    val priceCents: Long? = null,
    val currency: String = "USD",
    @param:DrawableRes val imagenResId: Int? = null,
    val imagenUri: String? = null,
    val imagenesUrls: List<String> = emptyList(),
    val fotos: List<FotoPostModel> = emptyList(),
    val cantidadFotos: String = "1/1",
    val likes: Int = 0,
    val esLiked: Boolean = false,
    val comentarios: Int = 0,
    val comentariosList: List<String> = emptyList(),
    val estaDisponibleHoy: Boolean = true,
    val esGuardado: Boolean = false,
    val esPropia: Boolean = false
)

data class EditarPostUiState(
    val postId: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val precio: String = "",
    val tipoTarifa: String = "Por trabajo",
    val fotos: List<FotoPostModel> = emptyList(),
    val nuevasFotos: List<Uri> = emptyList(),
    val fotosAEliminar: List<FotoPostModel> = emptyList(),
    val isSaving: Boolean = false,
    val errorMessage: String? = null
) {
    val totalFotos: Int get() = fotos.size + nuevasFotos.size
}

data class HomeUiState(
    val pestañaSeleccionada: Int = 0, // 0: Para ti, 1: Mis Guardados
    val categoriaSeleccionada: String = "Todos",
    val listaCategorias: List<String> = listOf("Todos", "Electricidad", "Plomería", "Limpieza", "Pintura", "Construcción", "Otros"),
    val publicaciones: List<ServicioModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mostrarAlertaCreada: Boolean = false,
    val postParaComentarios: ServicioModel? = null,
    val postIdParaEliminar: String? = null, // Estado centralizado para diálogo de confirmación de eliminación
    val menuAbiertoPostId: String? = null, // ID de la publicación con menú de opciones abierto
    val edicionPostState: EditarPostUiState? = null, // Estado del modal de edición de publicación
    val currentUserId: String? = null
)

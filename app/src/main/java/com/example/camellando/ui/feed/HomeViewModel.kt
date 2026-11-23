package com.example.camellando.ui.feed

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camellando.data.local.PostLocalDataSource
import com.example.camellando.data.model.AccountStatus
import com.example.camellando.data.model.ServicePost
import com.example.camellando.data.repository.AuthRepository
import com.example.camellando.data.repository.ProfileRepository
import com.example.camellando.data.repository.ServicePostRepository
import com.example.camellando.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicePostRepository: ServicePostRepository,
    private val storageRepository: StorageRepository,
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val postLocalDataSource: PostLocalDataSource,
    private val contentResolver: ContentResolver,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarFeed(forzar = true)
    }

    /**
     * Consulta el feed en Supabase.
     * Si no es forzado y ya existen publicaciones en memoria, evita recargas innecesarias.
     */
    fun cargarFeed(forzar: Boolean = false) {
        if (!forzar && _uiState.value.publicaciones.isNotEmpty()) {
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val currentUserId = authRepository.getCurrentUserId().getOrNull()
            val feedResult = servicePostRepository.getFeed()
            if (feedResult.isFailure) {
                _uiState.update {
                    it.copy(
                        currentUserId = currentUserId,
                        isLoading = false,
                        errorMessage = feedResult.exceptionOrNull()?.localizedMessage
                            ?: "Error al cargar las publicaciones."
                    )
                }
                return@launch
            }

            val posts = feedResult.getOrDefault(emptyList())
            val listaServicios = posts.map { post ->
                mapearPostAServicio(post, currentUserId)
            }

            _uiState.update {
                it.copy(
                    currentUserId = currentUserId,
                    publicaciones = listaServicios,
                    isLoading = false,
                    errorMessage = null
                )
            }
        }
    }

    /**
     * Control del menú de tres puntos (⋮) para publicaciones propias
     */
    fun abrirMenuOpciones(servicioId: String) {
        _uiState.update { it.copy(menuAbiertoPostId = servicioId) }
    }

    fun cerrarMenuOpciones() {
        _uiState.update { it.copy(menuAbiertoPostId = null) }
    }

    fun onEditarPublicacion(servicioId: String) {
        iniciarEdicion(servicioId)
    }

    private fun comprimirImagen(uri: Uri): ByteArray? {
        return try {
            val inputBytes = contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: return null
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size, options)

            var inSampleSize = 1
            val maxPx = 1080
            if (options.outHeight > maxPx || options.outWidth > maxPx) {
                val halfHeight = options.outHeight / 2
                val halfWidth = options.outWidth / 2
                while ((halfHeight / inSampleSize) >= maxPx || (halfWidth / inSampleSize) >= maxPx) {
                    inSampleSize *= 2
                }
            }

            val decodeOptions = BitmapFactory.Options().apply {
                this.inSampleSize = inSampleSize
            }
            val originalBitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size, decodeOptions) ?: return null

            val finalBitmap = if (originalBitmap.width > maxPx || originalBitmap.height > maxPx) {
                val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
                val (targetWidth, targetHeight) = if (ratio > 1f) {
                    maxPx to (maxPx / ratio).toInt()
                } else {
                    (maxPx * ratio).toInt() to maxPx
                }
                val scaled = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
                if (scaled != originalBitmap) originalBitmap.recycle()
                scaled
            } else {
                originalBitmap
            }

            val outputStream = ByteArrayOutputStream()
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
            finalBitmap.recycle()
            outputStream.toByteArray()
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Gestión del modal de edición de publicación (título, descripción, precio, categoría y fotos)
     */
    fun iniciarEdicion(servicioId: String) {
        val servicio = _uiState.value.publicaciones.find { it.id == servicioId } ?: return
        val precioFormateado = if (servicio.priceCents != null && servicio.priceCents > 0) {
            if (servicio.priceCents % 100L == 0L) {
                (servicio.priceCents / 100L).toString()
            } else {
                String.format(Locale.US, "%.2f", servicio.priceCents / 100.0)
            }
        } else {
            ""
        }
        val cleanDesc = servicio.rawDescription.replace(Regex("\n?Cobro:\\s*[^\n]+", RegexOption.IGNORE_CASE), "").trim()

        _uiState.update {
            it.copy(
                menuAbiertoPostId = null,
                edicionPostState = EditarPostUiState(
                    postId = servicio.id,
                    titulo = servicio.rawTitle,
                    descripcion = cleanDesc,
                    categoria = servicio.categoria,
                    precio = precioFormateado,
                    tipoTarifa = servicio.tipoTarifa.ifBlank { "Por trabajo" },
                    fotos = servicio.fotos,
                    nuevasFotos = emptyList(),
                    fotosAEliminar = emptyList()
                )
            )
        }
    }

    fun onTituloEdicionChanged(titulo: String) {
        _uiState.update { state ->
            state.copy(edicionPostState = state.edicionPostState?.copy(titulo = titulo))
        }
    }

    fun onDescripcionEdicionChanged(descripcion: String) {
        _uiState.update { state ->
            state.copy(edicionPostState = state.edicionPostState?.copy(descripcion = descripcion))
        }
    }

    fun onCategoriaEdicionChanged(categoria: String) {
        _uiState.update { state ->
            state.copy(edicionPostState = state.edicionPostState?.copy(categoria = categoria))
        }
    }

    fun onPrecioEdicionChanged(precio: String) {
        val sanitized = precio.filter { it.isDigit() || it == '.' }
        _uiState.update { state ->
            state.copy(edicionPostState = state.edicionPostState?.copy(precio = sanitized))
        }
    }

    fun onTipoTarifaEdicionChanged(tipo: String) {
        _uiState.update { state ->
            state.copy(edicionPostState = state.edicionPostState?.copy(tipoTarifa = tipo))
        }
    }

    fun onNuevasFotosEdicionAgregadas(uris: List<Uri>) {
        if (uris.isEmpty()) return
        _uiState.update { state ->
            val actual = state.edicionPostState ?: return@update state
            val espacioDisponible = (3 - actual.fotos.size).coerceAtLeast(0)
            val fotosParaAgregar = (actual.nuevasFotos + uris).distinct().take(espacioDisponible)
            val aviso = if ((actual.nuevasFotos + uris).distinct().size > espacioDisponible) {
                "Límite: máximo 3 fotos por publicación."
            } else null
            state.copy(
                edicionPostState = actual.copy(
                    nuevasFotos = fotosParaAgregar,
                    errorMessage = aviso
                )
            )
        }
    }

    fun onEliminarNuevaFotoEdicion(uri: Uri) {
        _uiState.update { state ->
            val actual = state.edicionPostState ?: return@update state
            state.copy(
                edicionPostState = actual.copy(
                    nuevasFotos = actual.nuevasFotos - uri
                )
            )
        }
    }

    fun onEliminarFotoEnEdicion(foto: FotoPostModel) {
        _uiState.update { state ->
            val actual = state.edicionPostState ?: return@update state
            state.copy(
                edicionPostState = actual.copy(
                    fotos = actual.fotos.filter { it.id != foto.id },
                    fotosAEliminar = actual.fotosAEliminar + foto
                )
            )
        }
    }

    fun cancelarEdicion() {
        _uiState.update { it.copy(edicionPostState = null) }
    }

    fun guardarEdicion() {
        val edicion = _uiState.value.edicionPostState ?: return
        _uiState.update {
            it.copy(edicionPostState = edicion.copy(isSaving = true, errorMessage = null))
        }

        viewModelScope.launch {
            try {
                // 1. Eliminar fotos seleccionadas de Storage, BD y almacenamiento local
                edicion.fotosAEliminar.forEach { foto ->
                    storageRepository.deleteImage(foto.storagePath)
                    servicePostRepository.deletePostImage(foto.id)
                    postLocalDataSource.removePostImage(edicion.postId, foto.url)
                    postLocalDataSource.removePostImage(edicion.postId, foto.storagePath)
                }

                // 2. Procesar y guardar fotos nuevas agregadas
                if (edicion.nuevasFotos.isNotEmpty()) {
                    withContext(Dispatchers.IO) {
                        val imagesDir = File(context.filesDir, "post_images").apply { mkdirs() }
                        val localImagePaths = mutableListOf<String>()

                        edicion.nuevasFotos.forEachIndexed { index, uri ->
                            try {
                                val compressedBytes = comprimirImagen(uri)
                                if (compressedBytes != null) {
                                    val localFile = File(imagesDir, "${edicion.postId}_edit_${index}_${UUID.randomUUID()}.jpg")
                                    localFile.writeBytes(compressedBytes)
                                    localImagePaths.add(localFile.absolutePath)

                                    val storagePath = "posts/${edicion.postId}/img_${UUID.randomUUID()}.jpg"
                                    val uploadResult = storageRepository.uploadImage(
                                        path = storagePath,
                                        bytes = compressedBytes,
                                        contentType = "image/jpeg"
                                    )
                                    val pathToRegister = if (uploadResult.isSuccess) storagePath else localFile.absolutePath
                                    servicePostRepository.addPostImage(
                                        postId = edicion.postId,
                                        storagePath = pathToRegister,
                                        position = edicion.fotos.size + index + 1
                                    )
                                }
                            } catch (_: Exception) {}
                        }

                        if (localImagePaths.isNotEmpty()) {
                            postLocalDataSource.addPostImages(edicion.postId, localImagePaths)
                        }
                    }
                }

                // 3. Guardar modalidad de cobro (tipoTarifa)
                postLocalDataSource.savePostTarifa(edicion.postId, edicion.tipoTarifa)

                // 4. Actualizar registro en service_posts
                val priceCents: Long? = edicion.precio.toDoubleOrNull()?.let {
                    (it * 100).toLong()
                }

                val postOriginal = servicePostRepository.getPost(edicion.postId).getOrNull()
                if (postOriginal != null) {
                    val postActualizado = postOriginal.copy(
                        title = edicion.titulo.trim().ifBlank { null },
                        description = edicion.descripcion.trim().ifBlank { null },
                        category = edicion.categoria,
                        priceCents = priceCents
                    )
                    servicePostRepository.updatePost(postActualizado)
                }

                _uiState.update { it.copy(edicionPostState = null) }
                cargarFeed(forzar = true)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        edicionPostState = edicion.copy(
                            isSaving = false,
                            errorMessage = e.localizedMessage ?: "Error al guardar los cambios."
                        )
                    )
                }
            }
        }
    }

    /**
     * Gestión del estado del diálogo de eliminación en el State Holder
     */
    fun solicitarEliminarPublicacion(servicioId: String) {
        _uiState.update { it.copy(menuAbiertoPostId = null, postIdParaEliminar = servicioId) }
    }

    fun cancelarEliminarPublicacion() {
        _uiState.update { it.copy(postIdParaEliminar = null) }
    }

    fun confirmarEliminarPublicacion() {
        val id = _uiState.value.postIdParaEliminar ?: return
        _uiState.update { it.copy(postIdParaEliminar = null) }
        eliminarPublicacion(id)
    }

    /**
     * Limpieza de publicaciones de prueba en Supabase
     */
    private fun eliminarPublicacion(servicioId: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                // 1. Obtener y eliminar imágenes almacenadas en Storage
                val imagesResult = servicePostRepository.getPostImages(servicioId)
                val images = imagesResult.getOrDefault(emptyList())
                images.forEach { image ->
                    storageRepository.deleteImage(image.storagePath)
                }

                // 2. Eliminar el post en la tabla de Supabase
                val deleteResult = servicePostRepository.deletePost(servicioId)
                if (deleteResult.isSuccess) {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            publicaciones = state.publicaciones.filter { it.id != servicioId },
                            errorMessage = null
                        )
                    }
                } else {
                    val err = deleteResult.exceptionOrNull()?.localizedMessage
                        ?: "No se pudo eliminar la publicación."
                    _uiState.update { it.copy(isLoading = false, errorMessage = err) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Error al eliminar")
                }
            }
        }
    }

    private suspend fun mapearPostAServicio(post: ServicePost, currentUserId: String?): ServicioModel {
        val imagesResult = servicePostRepository.getPostImages(post.id)
        val remoteImages = imagesResult.getOrDefault(emptyList()).sortedBy { it.position }
        val localImagePaths = postLocalDataSource.getPostImages(post.id)

        val remoteFotos = remoteImages.map { img ->
            FotoPostModel(
                id = img.id,
                storagePath = img.storagePath,
                url = storageRepository.getPublicImageUrl(img.storagePath)
            )
        }

        val localFotos = localImagePaths.mapIndexed { index, path ->
            FotoPostModel(
                id = "local_${post.id}_$index",
                storagePath = path,
                url = path
            )
        }

        // Combinar fotos remotas y fotos locales para garantizar que se muestren de inmediato
        val fotosList = if (remoteFotos.isNotEmpty()) {
            val remoteUrls = remoteFotos.map { it.url }.toSet()
            remoteFotos + localFotos.filter { it.url !in remoteUrls }
        } else {
            localFotos
        }

        val urls = fotosList.map { it.url }
        val imagenPrincipal = urls.firstOrNull()
        val cantidadFotosStr = if (urls.isNotEmpty()) "1/${urls.size}" else "0/0"

        val profileResult = profileRepository.getProfile(post.providerId)
        val profile = profileResult.getOrNull()
        val nombre = profile?.displayName?.ifBlank { null } ?: "Proveedor"
        val profesion = profile?.bio?.ifBlank { null } ?: post.category
        val esVerificado = profile?.accountStatus == AccountStatus.ACTIVE

        // Extraer modalidad de cobro (ej: Por hora) y limpiar la descripción
        val rawDesc = post.description ?: ""
        val modalityFromLocal = postLocalDataSource.getPostTarifa(post.id)
        val modalityMatch = Regex("Cobro:\\s*([^\n]+)", RegexOption.IGNORE_CASE).find(rawDesc)
        val modality = modalityFromLocal ?: modalityMatch?.groupValues?.get(1)?.trim() ?: "Por trabajo"
        val cleanDesc = rawDesc.replace(Regex("\n?Cobro:\\s*[^\n]+", RegexOption.IGNORE_CASE), "").trim()

        val suffix = when {
            modality.contains("hora", ignoreCase = true) -> "/ por hora"
            modality.contains("cita", ignoreCase = true) -> "/ por cita"
            modality.contains("trabajo", ignoreCase = true) -> "/ por trabajo"
            else -> "/ $modality"
        }

        val precioStr = if (post.priceCents != null && post.priceCents > 0) {
            val amount = if (post.priceCents % 100L == 0L) {
                "$${post.priceCents / 100L}"
            } else {
                String.format(Locale.US, "$%.2f", post.priceCents / 100.0)
            }
            "$amount $suffix"
        } else {
            "A cotizar"
        }

        val localInteractions = postLocalDataSource.getPostInteractionsSnapshot(post.id)

        return ServicioModel(
            id = post.id,
            providerId = post.providerId,
            nombreProveedor = nombre,
            profesion = profesion,
            esVerificado = esVerificado,
            tiempoPublicacion = formatearFecha(post.createdAt),
            categoria = post.category,
            precio = precioStr,
            tipoTarifa = modality,
            tituloDescripcion = post.title ?: post.category,
            descripcionDetallada = cleanDesc,
            rawTitle = post.title ?: "",
            rawDescription = cleanDesc,
            priceCents = post.priceCents,
            currency = post.currency,
            fotos = fotosList,
            imagenUri = imagenPrincipal,
            imagenesUrls = urls,
            cantidadFotos = cantidadFotosStr,
            likes = localInteractions.extraLikes,
            esLiked = localInteractions.isLiked,
            comentarios = localInteractions.comments.size,
            comentariosList = localInteractions.comments,
            estaDisponibleHoy = true,
            esGuardado = localInteractions.isSaved,
            esPropia = currentUserId != null && post.providerId == currentUserId
        )
    }

    private fun formatearFecha(isoString: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = parser.parse(isoString.substringBefore("."))
            if (date != null) {
                val diffMillis = System.currentTimeMillis() - date.time
                val hours = diffMillis / (1000 * 60 * 60)
                if (hours <= 0) "Hace un momento" else "Hace ${hours} h"
            } else {
                "Reciente"
            }
        } catch (_: Exception) {
            "Reciente"
        }
    }

    fun onPestañaSelected(index: Int) {
        _uiState.update { it.copy(pestañaSeleccionada = index) }
    }

    fun onCategoriaSelected(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }

    fun toggleGuardarServicio(servicioId: String) {
        viewModelScope.launch {
            val isSaved = postLocalDataSource.toggleSave(servicioId)
            _uiState.update { state ->
                val listaActualizada = state.publicaciones.map { servicio ->
                    if (servicio.id == servicioId) {
                        servicio.copy(esGuardado = isSaved)
                    } else {
                        servicio
                    }
                }
                state.copy(publicaciones = listaActualizada)
            }
        }
    }

    fun toggleLikeServicio(servicioId: String) {
        viewModelScope.launch {
            val isLiked = postLocalDataSource.toggleLike(servicioId)
            _uiState.update { state ->
                val listaActualizada = state.publicaciones.map { servicio ->
                    if (servicio.id == servicioId) {
                        val newLikes = if (isLiked) servicio.likes + 1 else (servicio.likes - 1).coerceAtLeast(0)
                        servicio.copy(likes = newLikes, esLiked = isLiked)
                    } else {
                        servicio
                    }
                }
                state.copy(publicaciones = listaActualizada)
            }
        }
    }

    fun abrirComentarios(servicio: ServicioModel) {
        _uiState.update { it.copy(postParaComentarios = servicio) }
    }

    fun cerrarComentarios() {
        _uiState.update { it.copy(postParaComentarios = null) }
    }

    fun agregarComentario(servicioId: String, comentario: String) {
        if (comentario.isBlank()) return
        viewModelScope.launch {
            val updatedComments = postLocalDataSource.addComment(servicioId, comentario)
            _uiState.update { state ->
                val listaActualizada = state.publicaciones.map { servicio ->
                    if (servicio.id == servicioId) {
                        servicio.copy(
                            comentarios = updatedComments.size,
                            comentariosList = updatedComments
                        )
                    } else {
                        servicio
                    }
                }
                val updatedModal = if (state.postParaComentarios?.id == servicioId) {
                    state.postParaComentarios.copy(
                        comentarios = updatedComments.size,
                        comentariosList = updatedComments
                    )
                } else {
                    state.postParaComentarios
                }
                state.copy(
                    publicaciones = listaActualizada,
                    postParaComentarios = updatedModal
                )
            }
        }
    }

    fun mostrarAlertaPublicacionCreada() {
        _uiState.update { it.copy(mostrarAlertaCreada = true) }
        cargarFeed(forzar = true)
    }

    fun ocultarAlertaPublicacionCreada() {
        _uiState.update { it.copy(mostrarAlertaCreada = false) }
    }
}

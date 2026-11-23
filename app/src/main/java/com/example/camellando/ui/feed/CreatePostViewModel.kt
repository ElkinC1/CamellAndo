package com.example.camellando.ui.feed

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camellando.data.local.PostLocalDataSource
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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val servicePostRepository: ServicePostRepository,
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val postLocalDataSource: PostLocalDataSource,
    private val contentResolver: ContentResolver,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        const val MAX_FOTOS = 3 // Límite de 3 fotos para pruebas y plan gratuito
        const val MAX_BYTES_POR_FOTO = 1_500_000 // 1.5 MB límite estricto
        const val MAX_DIMENSION_PX = 1080 // Máx 1080 px ancho/alto
        const val JPEG_QUALITY = 75 // Compresión 75%
    }

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState: StateFlow<CreatePostUiState> = _uiState.asStateFlow()

    init {
        cargarAutor()
    }

    private fun cargarAutor() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId().getOrNull()
            if (userId != null) {
                val profile = profileRepository.getProfile(userId).getOrNull()
                if (profile != null && profile.displayName.isNotBlank()) {
                    val initial = profile.displayName.trim().first().uppercase()
                    _uiState.update {
                        it.copy(
                            autor = profile.displayName,
                            autorInicial = initial
                        )
                    }
                }
            }
        }
    }

    fun onTituloChanged(titulo: String) {
        _uiState.update { it.copy(titulo = titulo, mensajeError = null) }
    }

    fun onDescripcionChanged(descripcion: String) {
        _uiState.update { it.copy(descripcion = descripcion, mensajeError = null) }
    }

    fun onCategoriaChanged(categoria: String) {
        _uiState.update { it.copy(categoria = categoria) }
    }

    fun onPrecioChanged(precio: String) {
        val sanitized = precio.filter { it.isDigit() || it == '.' }
        _uiState.update { it.copy(precioEstimado = sanitized) }
    }

    fun onTipoTarifaChanged(tipo: String) {
        _uiState.update { it.copy(tipoTarifa = tipo) }
    }

    fun onFotosAgregadas(uris: List<Uri>) {
        if (uris.isNotEmpty()) {
            _uiState.update { current ->
                val nuevasFotos = (current.fotosSeleccionadas + uris).distinct().take(MAX_FOTOS)
                val aviso = if ((current.fotosSeleccionadas + uris).distinct().size > MAX_FOTOS) {
                    "Límite de prueba: máximo $MAX_FOTOS fotos por publicación para cuidar el almacenamiento."
                } else null

                current.copy(
                    fotosSeleccionadas = nuevasFotos,
                    mensajeError = aviso
                )
            }
        }
    }

    fun onEliminarFoto(uri: Uri) {
        _uiState.update { current ->
            current.copy(fotosSeleccionadas = current.fotosSeleccionadas - uri)
        }
    }

    fun onTogglePrivacidad() {
        _uiState.update { it.copy(esPublico = !it.esPublico) }
    }

    /**
     * Redimensiona a máx 1080px y comprime a JPEG 75% para optimizar almacenamiento en Supabase.
     */
    private fun comprimirImagen(uri: Uri): ByteArray? {
        return try {
            val inputBytes = contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: return null
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size, options)

            var inSampleSize = 1
            if (options.outHeight > MAX_DIMENSION_PX || options.outWidth > MAX_DIMENSION_PX) {
                val halfHeight = options.outHeight / 2
                val halfWidth = options.outWidth / 2
                while ((halfHeight / inSampleSize) >= MAX_DIMENSION_PX || (halfWidth / inSampleSize) >= MAX_DIMENSION_PX) {
                    inSampleSize *= 2
                }
            }

            val decodeOptions = BitmapFactory.Options().apply {
                this.inSampleSize = inSampleSize
            }
            val originalBitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size, decodeOptions) ?: return null

            val finalBitmap = if (originalBitmap.width > MAX_DIMENSION_PX || originalBitmap.height > MAX_DIMENSION_PX) {
                val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
                val (targetWidth, targetHeight) = if (ratio > 1f) {
                    MAX_DIMENSION_PX to (MAX_DIMENSION_PX / ratio).toInt()
                } else {
                    (MAX_DIMENSION_PX * ratio).toInt() to MAX_DIMENSION_PX
                }
                val scaled = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
                if (scaled != originalBitmap) originalBitmap.recycle()
                scaled
            } else {
                originalBitmap
            }

            val outputStream = ByteArrayOutputStream()
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, outputStream)
            finalBitmap.recycle()
            outputStream.toByteArray()
        } catch (_: Exception) {
            null
        }
    }

    fun publicar(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (!state.puedePublicar) {
            _uiState.update {
                it.copy(mensajeError = "Debes ingresar un título, descripción o adjuntar fotos.")
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, mensajeError = null) }

        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId().getOrNull()
            if (userId == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeError = "No se encontró sesión de usuario activa. Inicia sesión nuevamente."
                    )
                }
                return@launch
            }

            val priceCents: Long? = state.precioEstimado.toDoubleOrNull()?.let {
                (it * 100).toLong()
            }

            // La descripción no debe llevar "Cobro: por hora" al final, ya que debe mostrarse junto al precio
            val finalDescription = state.descripcion.trim().ifBlank { null }
            val finalTitle = state.titulo.trim().ifBlank { null }

            val postResult = servicePostRepository.createPost(
                providerId = userId,
                title = finalTitle,
                description = finalDescription,
                category = state.categoria,
                priceCents = priceCents
            )

            val createdPost = postResult.getOrNull()
            if (createdPost == null) {
                val err = postResult.exceptionOrNull()?.localizedMessage
                    ?: "Error al crear la publicación en Supabase."
                _uiState.update { it.copy(isLoading = false, mensajeError = err) }
                return@launch
            }

            // Guardar modalidad de cobro (ej: Por hora) asociada a esta publicación
            postLocalDataSource.savePostTarifa(createdPost.id, state.tipoTarifa)

            // Procesar y guardar imágenes (tanto local como en Storage)
            if (state.fotosSeleccionadas.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    val localImagePaths = mutableListOf<String>()
                    val imagesDir = File(context.filesDir, "post_images").apply { mkdirs() }

                    state.fotosSeleccionadas.take(MAX_FOTOS).forEachIndexed { index, uri ->
                        try {
                            val compressedBytes = comprimirImagen(uri)
                            if (compressedBytes != null) {
                                // 1. Guardar archivo localmente para visualización inmediata y soporte sin conexión / fallback
                                val localFile = File(imagesDir, "${createdPost.id}_${index}_${UUID.randomUUID()}.jpg")
                                localFile.writeBytes(compressedBytes)
                                localImagePaths.add(localFile.absolutePath)

                                // 2. Subir a Supabase Storage
                                val storagePath = "posts/${createdPost.id}/img_${UUID.randomUUID()}.jpg"
                                val uploadResult = storageRepository.uploadImage(
                                    path = storagePath,
                                    bytes = compressedBytes,
                                    contentType = "image/jpeg"
                                )

                                val pathToRegister = if (uploadResult.isSuccess) storagePath else localFile.absolutePath
                                servicePostRepository.addPostImage(
                                    postId = createdPost.id,
                                    storagePath = pathToRegister,
                                    position = index + 1
                                )
                            }
                        } catch (_: Exception) {
                            // Continuar con siguientes imágenes si alguna falla
                        }
                    }

                    if (localImagePaths.isNotEmpty()) {
                        postLocalDataSource.savePostImages(createdPost.id, localImagePaths)
                    }
                }
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    publicacionExitosa = true,
                    mostrarAlertaExito = true
                )
            }
            onSuccess()
        }
    }

    fun onDismissAlertaExito() {
        _uiState.update { it.copy(mostrarAlertaExito = false) }
    }

    fun reiniciarEstado() {
        _uiState.value = CreatePostUiState()
        cargarAutor()
    }
}

typealias CreatePostScreenViewModel = CreatePostViewModel
typealias PostViewModel = CreatePostViewModel

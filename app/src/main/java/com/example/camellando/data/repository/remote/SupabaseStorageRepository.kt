package com.example.camellando.data.repository.remote

import com.example.camellando.data.repository.StorageRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import io.ktor.http.ContentType
import kotlin.time.Duration.Companion.seconds

class SupabaseStorageRepository(
    private val supabase: SupabaseClient
) : StorageRepository {

    override suspend fun uploadImage(
        path: String,
        bytes: ByteArray,
        contentType: String
    ): Result<String> = repositorySafeCall {
        require(contentType.startsWith("image/")) { "El archivo debe ser una imagen." }
        supabase.storage.from(IMAGES_BUCKET).upload(path, bytes) {
            upsert = false
            this.contentType = ContentType.parse(contentType)
        }
        path
    }

    override suspend fun uploadCv(
        path: String,
        bytes: ByteArray,
        contentType: String
    ): Result<String> = repositorySafeCall {
        require(contentType == PDF_CONTENT_TYPE) { "El CV debe estar en formato PDF." }
        require(path.lowercase().endsWith(".pdf")) { "La ruta del CV debe terminar en .pdf." }
        supabase.storage.from(CV_BUCKET).upload(path, bytes) {
            upsert = false
            this.contentType = ContentType.parse(contentType)
        }
        path
    }

    override fun getPublicImageUrl(path: String): String =
        supabase.storage.from(IMAGES_BUCKET).publicUrl(path)

    override suspend fun createCvSignedUrl(
        path: String,
        expiresInSeconds: Long
    ): Result<String> = repositorySafeCall {
        require(expiresInSeconds > 0) { "La duración del enlace debe ser positiva." }
        supabase.storage.from(CV_BUCKET)
            .createSignedUrl(path, expiresInSeconds.seconds)
    }

    override suspend fun deleteImage(path: String): Result<Unit> = repositorySafeCall {
        supabase.storage.from(IMAGES_BUCKET).delete(path)
        Unit
    }

    override suspend fun deleteCv(path: String): Result<Unit> = repositorySafeCall {
        supabase.storage.from(CV_BUCKET).delete(path)
        Unit
    }

    private companion object {
        const val IMAGES_BUCKET = "camellando-images"
        const val CV_BUCKET = "provider-cvs"
        const val PDF_CONTENT_TYPE = "application/pdf"
    }
}

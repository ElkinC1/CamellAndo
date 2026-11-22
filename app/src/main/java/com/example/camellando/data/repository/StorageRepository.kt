package com.example.camellando.data.repository

interface StorageRepository {
    suspend fun uploadImage(
        path: String,
        bytes: ByteArray,
        contentType: String
    ): Result<String>

    suspend fun uploadCv(
        path: String,
        bytes: ByteArray,
        contentType: String = "application/pdf"
    ): Result<String>

    fun getPublicImageUrl(path: String): String
    suspend fun createCvSignedUrl(path: String, expiresInSeconds: Long): Result<String>
    suspend fun deleteImage(path: String): Result<Unit>
    suspend fun deleteCv(path: String): Result<Unit>
}

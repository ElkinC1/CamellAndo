package com.example.camellando.data.model.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostImageDto(
    val id: String,

    @SerialName("post_id")
    val postId: String,

    @SerialName("storage_path")
    val storagePath: String,

    val position: Int,

    @SerialName("created_at")
    val createdAt: String
)
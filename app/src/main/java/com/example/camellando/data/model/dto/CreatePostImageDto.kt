package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostImageDto(
    @SerialName("post_id")
    val postId: String,

    @SerialName("storage_path")
    val storagePath: String,

    val position: Int = 0
)
package com.example.camellando.data.model

data class PostImage(
    val id: String,
    val postId: String,
    val storagePath: String,
    val position: Int,
    val createdAt: String
)

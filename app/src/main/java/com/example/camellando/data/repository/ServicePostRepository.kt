package com.example.camellando.data.repository

import com.example.camellando.data.model.PostImage
import com.example.camellando.data.model.ServicePost
import com.example.camellando.data.model.ServicePostStatus

interface ServicePostRepository {
    suspend fun getFeed(): Result<List<ServicePost>>
    suspend fun getPost(postId: String): Result<ServicePost>
    suspend fun getPostsByProvider(providerId: String): Result<List<ServicePost>>
    suspend fun createPost(
        providerId: String,
        title: String?,
        description: String?,
        category: String,
        priceCents: Long?
    ): Result<ServicePost>
    suspend fun updatePost(post: ServicePost): Result<ServicePost>
    suspend fun updatePostStatus(postId: String, status: ServicePostStatus): Result<Unit>
    suspend fun addPostImage(postId: String, storagePath: String, position: Int): Result<PostImage>
    suspend fun getPostImages(postId: String): Result<List<PostImage>>
    suspend fun deletePost(postId: String): Result<Unit>
}

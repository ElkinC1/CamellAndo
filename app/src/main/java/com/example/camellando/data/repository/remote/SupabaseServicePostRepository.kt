package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.PostImage
import com.example.camellando.data.model.ServicePost
import com.example.camellando.data.model.ServicePostStatus
import com.example.camellando.data.model.dto.CreatePostImageDto
import com.example.camellando.data.model.dto.CreateServicePostDto
import com.example.camellando.data.model.dto.PostImageDto
import com.example.camellando.data.model.dto.ServicePostDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.ServicePostRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseServicePostRepository(
    private val supabase: SupabaseClient
) : ServicePostRepository {

    override suspend fun getFeed(): Result<List<ServicePost>> = repositorySafeCall {
        supabase.from("service_posts")
            .select {
                filter { eq("status", ServicePostStatus.OPEN.name) }
            }
            .decodeList<ServicePostDto>()
            .map { it.toDomain() }
    }

    override suspend fun getPost(postId: String): Result<ServicePost> = repositorySafeCall {
        supabase.from("service_posts")
            .select { filter { eq("id", postId) } }
            .decodeSingle<ServicePostDto>()
            .toDomain()
    }

    override suspend fun getPostsByProvider(providerId: String): Result<List<ServicePost>> =
        repositorySafeCall {
            supabase.from("service_posts")
                .select { filter { eq("provider_id", providerId) } }
                .decodeList<ServicePostDto>()
                .map { it.toDomain() }
        }

    override suspend fun createPost(
        providerId: String,
        title: String?,
        description: String?,
        category: String,
        priceCents: Long?
    ): Result<ServicePost> = repositorySafeCall {
        val dto = CreateServicePostDto(
            providerId = providerId,
            title = title,
            description = description,
            category = category,
            price = priceCents?.div(100.0),
            currency = "USD",
            status = ServicePostStatus.OPEN.name
        )

        supabase.from("service_posts")
            .insert(dto) { select() }
            .decodeSingle<ServicePostDto>()
            .toDomain()
    }

    override suspend fun updatePost(post: ServicePost): Result<ServicePost> =
        repositorySafeCall {
            supabase.from("service_posts").update({
                set("title", post.title)
                set("description", post.description)
                set("category", post.category)
                set("price", post.priceCents?.div(100.0))
                set("currency", post.currency)
                set("status", post.status.name)
            }) {
                filter { eq("id", post.id) }
                select()
            }.decodeSingle<ServicePostDto>().toDomain()
        }

    override suspend fun updatePostStatus(
        postId: String,
        status: ServicePostStatus
    ): Result<Unit> = repositorySafeCall {
        supabase.from("service_posts").update({
            set("status", status.name)
        }) {
            filter { eq("id", postId) }
        }
        Unit
    }

    override suspend fun addPostImage(
        postId: String,
        storagePath: String,
        position: Int
    ): Result<PostImage> = repositorySafeCall {
        val dto = CreatePostImageDto(
            postId = postId,
            storagePath = storagePath,
            position = position
        )
        supabase.from("post_images")
            .insert(dto) { select() }
            .decodeSingle<PostImageDto>()
            .toDomain()
    }

    override suspend fun getPostImages(postId: String): Result<List<PostImage>> =
        repositorySafeCall {
            supabase.from("post_images")
                .select { filter { eq("post_id", postId) } }
                .decodeList<PostImageDto>()
                .map { it.toDomain() }
        }

    override suspend fun deletePostImage(imageId: String): Result<Unit> = repositorySafeCall {
        supabase.from("post_images").delete {
            filter { eq("id", imageId) }
        }
        Unit
    }

    override suspend fun deletePost(postId: String): Result<Unit> = repositorySafeCall {
        supabase.from("service_posts").delete {
            filter { eq("id", postId) }
        }
        Unit
    }
}

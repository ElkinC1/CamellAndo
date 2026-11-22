package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.Offer
import com.example.camellando.data.model.OfferStatus
import com.example.camellando.data.model.dto.CreateOfferDto
import com.example.camellando.data.model.dto.OfferDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.OfferRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseOfferRepository(
    private val supabase: SupabaseClient
) : OfferRepository {

    override suspend fun getOffersForPost(postId: String): Result<List<Offer>> =
        repositorySafeCall {
            supabase.from("offers")
                .select { filter { eq("post_id", postId) } }
                .decodeList<OfferDto>()
                .map { it.toDomain() }
        }

    override suspend fun getOffersByClient(clientId: String): Result<List<Offer>> =
        repositorySafeCall {
            supabase.from("offers")
                .select { filter { eq("client_id", clientId) } }
                .decodeList<OfferDto>()
                .map { it.toDomain() }
        }

    override suspend fun getOffersByProvider(providerId: String): Result<List<Offer>> =
        repositorySafeCall {
            supabase.from("offers")
                .select { filter { eq("provider_id", providerId) } }
                .decodeList<OfferDto>()
                .map { it.toDomain() }
        }

    override suspend fun createOffer(
        postId: String,
        clientId: String,
        providerId: String,
        amountCents: Long,
        message: String?,
        parentOfferId: String?
    ): Result<Offer> = repositorySafeCall {
        val dto = CreateOfferDto(
            postId = postId,
            clientId = clientId,
            providerId = providerId,
            parentOfferId = parentOfferId,
            amount = amountCents / 100.0,
            currency = "USD",
            message = message,
            status = OfferStatus.PENDING.name
        )
        supabase.from("offers")
            .insert(dto) { select() }
            .decodeSingle<OfferDto>()
            .toDomain()
    }

    override suspend fun updateOfferStatus(
        offerId: String,
        status: OfferStatus
    ): Result<Unit> = repositorySafeCall {
        supabase.from("offers").update({
            set("status", status.name)
        }) {
            filter { eq("id", offerId) }
        }
        Unit
    }
}

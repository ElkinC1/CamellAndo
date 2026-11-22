package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.ContractReview
import com.example.camellando.data.model.dto.ContractReviewDto
import com.example.camellando.data.model.dto.CreateContractReviewDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.ContractReviewRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseContractReviewRepository(
    private val supabase: SupabaseClient
) : ContractReviewRepository {

    override suspend fun getReviewsForContract(contractId: String): Result<List<ContractReview>> =
        repositorySafeCall {
            supabase.from("contract_reviews").select {
                filter { eq("contract_id", contractId) }
            }.decodeList<ContractReviewDto>().map { it.toDomain() }
        }

    override suspend fun getReviewsForUser(userId: String): Result<List<ContractReview>> =
        repositorySafeCall {
            supabase.from("contract_reviews").select {
                filter { eq("reviewed_user_id", userId) }
            }.decodeList<ContractReviewDto>().map { it.toDomain() }
        }

    override suspend fun createReview(
        contractId: String,
        reviewerId: String,
        reviewedUserId: String,
        rating: Int,
        review: String?
    ): Result<ContractReview> = repositorySafeCall {
        val dto = CreateContractReviewDto(
            contractId = contractId,
            reviewerId = reviewerId,
            reviewedUserId = reviewedUserId,
            rating = rating,
            review = review
        )
        supabase.from("contract_reviews")
            .insert(dto) { select() }
            .decodeSingle<ContractReviewDto>()
            .toDomain()
    }
}

package com.example.camellando.data.repository

import com.example.camellando.data.model.ContractReview

interface ContractReviewRepository {
    suspend fun getReviewsForContract(contractId: String): Result<List<ContractReview>>
    suspend fun getReviewsForUser(userId: String): Result<List<ContractReview>>
    suspend fun createReview(
        contractId: String,
        reviewerId: String,
        reviewedUserId: String,
        rating: Int,
        review: String? = null
    ): Result<ContractReview>
}

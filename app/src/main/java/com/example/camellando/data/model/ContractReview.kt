package com.example.camellando.data.model

data class ContractReview(
    val id: String,
    val contractId: String,
    val reviewerId: String,
    val reviewedUserId: String,
    val rating: Int,
    val review: String?,
    val createdAt: String
)

package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateContractReviewDto(
    @SerialName("contract_id")
    val contractId: String,

    @SerialName("reviewer_id")
    val reviewerId: String,

    @SerialName("reviewed_user_id")
    val reviewedUserId: String,

    val rating: Int,
    val review: String? = null
)

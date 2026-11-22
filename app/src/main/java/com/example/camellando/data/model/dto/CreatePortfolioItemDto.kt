package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePortfolioItemDto(
    @SerialName("portfolio_id")
    val portfolioId: String,

    @SerialName("service_post_id")
    val servicePostId: String? = null,

    val title: String? = null,
    val description: String? = null,
    val position: Int = 0
)

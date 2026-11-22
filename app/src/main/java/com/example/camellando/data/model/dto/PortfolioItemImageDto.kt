package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioItemImageDto(
    val id: String,

    @SerialName("portfolio_item_id")
    val portfolioItemId: String,

    @SerialName("storage_path")
    val storagePath: String,

    val position: Int,

    @SerialName("created_at")
    val createdAt: String
)

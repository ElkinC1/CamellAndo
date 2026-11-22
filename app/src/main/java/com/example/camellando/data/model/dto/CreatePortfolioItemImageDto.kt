package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePortfolioItemImageDto(
    @SerialName("portfolio_item_id")
    val portfolioItemId: String,

    @SerialName("storage_path")
    val storagePath: String,

    val position: Int = 0
)

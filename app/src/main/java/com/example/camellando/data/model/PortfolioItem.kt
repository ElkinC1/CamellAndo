package com.example.camellando.data.model

data class PortfolioItem(
    val id: String,
    val portfolioId: String,
    val servicePostId: String?,
    val title: String?,
    val description: String?,
    val position: Int,
    val createdAt: String
)

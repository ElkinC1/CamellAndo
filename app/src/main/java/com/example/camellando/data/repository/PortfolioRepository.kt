package com.example.camellando.data.repository

import com.example.camellando.data.model.Portfolio
import com.example.camellando.data.model.PortfolioItem
import com.example.camellando.data.model.PortfolioItemImage

interface PortfolioRepository {
    suspend fun getPortfolioByProvider(providerId: String): Result<Portfolio?>
    suspend fun createPortfolio(
        providerId: String,
        title: String?,
        description: String?
    ): Result<Portfolio>
    suspend fun updatePortfolio(portfolio: Portfolio): Result<Portfolio>
    suspend fun getItems(portfolioId: String): Result<List<PortfolioItem>>
    suspend fun createItem(
        portfolioId: String,
        servicePostId: String?,
        title: String?,
        description: String?,
        position: Int
    ): Result<PortfolioItem>
    suspend fun updateItem(item: PortfolioItem): Result<PortfolioItem>
    suspend fun getItemImages(itemId: String): Result<List<PortfolioItemImage>>
    suspend fun addItemImage(
        itemId: String,
        storagePath: String,
        position: Int
    ): Result<PortfolioItemImage>
    suspend fun deleteItem(itemId: String): Result<Unit>
}

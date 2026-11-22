package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.Portfolio
import com.example.camellando.data.model.PortfolioItem
import com.example.camellando.data.model.PortfolioItemImage
import com.example.camellando.data.model.dto.CreatePortfolioDto
import com.example.camellando.data.model.dto.CreatePortfolioItemDto
import com.example.camellando.data.model.dto.CreatePortfolioItemImageDto
import com.example.camellando.data.model.dto.PortfolioDto
import com.example.camellando.data.model.dto.PortfolioItemDto
import com.example.camellando.data.model.dto.PortfolioItemImageDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.PortfolioRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabasePortfolioRepository(
    private val supabase: SupabaseClient
) : PortfolioRepository {

    override suspend fun getPortfolioByProvider(providerId: String): Result<Portfolio?> =
        repositorySafeCall {
            supabase.from("portfolios").select {
                filter { eq("provider_id", providerId) }
            }.decodeList<PortfolioDto>().firstOrNull()?.toDomain()
        }

    override suspend fun createPortfolio(
        providerId: String,
        title: String?,
        description: String?
    ): Result<Portfolio> = repositorySafeCall {
        supabase.from("portfolios")
            .insert(CreatePortfolioDto(providerId, title, description)) { select() }
            .decodeSingle<PortfolioDto>()
            .toDomain()
    }

    override suspend fun updatePortfolio(portfolio: Portfolio): Result<Portfolio> =
        repositorySafeCall {
            supabase.from("portfolios").update({
                set("title", portfolio.title)
                set("description", portfolio.description)
            }) {
                filter { eq("id", portfolio.id) }
                select()
            }.decodeSingle<PortfolioDto>().toDomain()
        }

    override suspend fun getItems(portfolioId: String): Result<List<PortfolioItem>> =
        repositorySafeCall {
            supabase.from("portfolio_items").select {
                filter { eq("portfolio_id", portfolioId) }
            }.decodeList<PortfolioItemDto>().map { it.toDomain() }
        }

    override suspend fun createItem(
        portfolioId: String,
        servicePostId: String?,
        title: String?,
        description: String?,
        position: Int
    ): Result<PortfolioItem> = repositorySafeCall {
        val dto = CreatePortfolioItemDto(
            portfolioId = portfolioId,
            servicePostId = servicePostId,
            title = title,
            description = description,
            position = position
        )
        supabase.from("portfolio_items")
            .insert(dto) { select() }
            .decodeSingle<PortfolioItemDto>()
            .toDomain()
    }

    override suspend fun updateItem(item: PortfolioItem): Result<PortfolioItem> =
        repositorySafeCall {
            supabase.from("portfolio_items").update({
                set("service_post_id", item.servicePostId)
                set("title", item.title)
                set("description", item.description)
                set("position", item.position)
            }) {
                filter { eq("id", item.id) }
                select()
            }.decodeSingle<PortfolioItemDto>().toDomain()
        }

    override suspend fun getItemImages(itemId: String): Result<List<PortfolioItemImage>> =
        repositorySafeCall {
            supabase.from("portfolio_item_images").select {
                filter { eq("portfolio_item_id", itemId) }
            }.decodeList<PortfolioItemImageDto>().map { it.toDomain() }
        }

    override suspend fun addItemImage(
        itemId: String,
        storagePath: String,
        position: Int
    ): Result<PortfolioItemImage> = repositorySafeCall {
        val dto = CreatePortfolioItemImageDto(
            portfolioItemId = itemId,
            storagePath = storagePath,
            position = position
        )
        supabase.from("portfolio_item_images")
            .insert(dto) { select() }
            .decodeSingle<PortfolioItemImageDto>()
            .toDomain()
    }

    override suspend fun deleteItem(itemId: String): Result<Unit> = repositorySafeCall {
        supabase.from("portfolio_items").delete {
            filter { eq("id", itemId) }
        }
        Unit
    }
}

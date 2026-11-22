package com.example.camellando.data.repository

import com.example.camellando.data.model.Offer
import com.example.camellando.data.model.OfferStatus

interface OfferRepository {
    suspend fun getOffersForPost(postId: String): Result<List<Offer>>
    suspend fun getOffersByClient(clientId: String): Result<List<Offer>>
    suspend fun getOffersByProvider(providerId: String): Result<List<Offer>>
    suspend fun createOffer(
        postId: String,
        clientId: String,
        providerId: String,
        amountCents: Long,
        message: String? = null,
        parentOfferId: String? = null
    ): Result<Offer>
    suspend fun updateOfferStatus(offerId: String, status: OfferStatus): Result<Unit>
}

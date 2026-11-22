package com.example.camellando.data.repository

import com.example.camellando.data.model.ServiceContract
import com.example.camellando.data.model.ServiceContractStatus

interface ServiceContractRepository {
    suspend fun getContractsForUser(userId: String): Result<List<ServiceContract>>
    suspend fun getContract(contractId: String): Result<ServiceContract>
    suspend fun createContract(
        clientId: String,
        providerId: String,
        agreedPriceCents: Long,
        servicePostId: String? = null,
        acceptedOfferId: String? = null
    ): Result<ServiceContract>
    suspend fun updateContractStatus(
        contractId: String,
        status: ServiceContractStatus
    ): Result<Unit>
}

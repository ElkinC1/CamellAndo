package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.ServiceContract
import com.example.camellando.data.model.ServiceContractStatus
import com.example.camellando.data.model.dto.CreateServiceContractDto
import com.example.camellando.data.model.dto.ServiceContractDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.ServiceContractRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import java.time.Instant

class SupabaseServiceContractRepository(
    private val supabase: SupabaseClient
) : ServiceContractRepository {

    override suspend fun getContractsForUser(userId: String): Result<List<ServiceContract>> =
        repositorySafeCall {
            val asClient = supabase.from("service_contracts").select {
                filter { eq("client_id", userId) }
            }.decodeList<ServiceContractDto>()
            val asProvider = supabase.from("service_contracts").select {
                filter { eq("provider_id", userId) }
            }.decodeList<ServiceContractDto>()
            (asClient + asProvider).distinctBy { it.id }.map { it.toDomain() }
        }

    override suspend fun getContract(contractId: String): Result<ServiceContract> =
        repositorySafeCall {
            supabase.from("service_contracts").select {
                filter { eq("id", contractId) }
            }.decodeSingle<ServiceContractDto>().toDomain()
        }

    override suspend fun createContract(
        clientId: String,
        providerId: String,
        agreedPriceCents: Long,
        servicePostId: String?,
        acceptedOfferId: String?
    ): Result<ServiceContract> = repositorySafeCall {
        val dto = CreateServiceContractDto(
            servicePostId = servicePostId,
            acceptedOfferId = acceptedOfferId,
            clientId = clientId,
            providerId = providerId,
            agreedPrice = agreedPriceCents / 100.0,
            currency = "USD",
            status = ServiceContractStatus.IN_PROGRESS.name
        )
        supabase.from("service_contracts")
            .insert(dto) { select() }
            .decodeSingle<ServiceContractDto>()
            .toDomain()
    }

    override suspend fun updateContractStatus(
        contractId: String,
        status: ServiceContractStatus
    ): Result<Unit> = repositorySafeCall {
        val now = Instant.now().toString()
        supabase.from("service_contracts").update({
            set("status", status.name)
            when (status) {
                ServiceContractStatus.COMPLETED -> {
                    set("completed_at", now)
                    setToNull("cancelled_at")
                }
                ServiceContractStatus.CANCELLED -> {
                    setToNull("completed_at")
                    set("cancelled_at", now)
                }
                ServiceContractStatus.IN_PROGRESS -> {
                    setToNull("completed_at")
                    setToNull("cancelled_at")
                }
            }
        }) {
            filter { eq("id", contractId) }
        }
        Unit
    }
}

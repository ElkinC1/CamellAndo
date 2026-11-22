package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.UserReport
import com.example.camellando.data.model.dto.CreateUserReportDto
import com.example.camellando.data.model.dto.UserReportDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.UserReportRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseUserReportRepository(
    private val supabase: SupabaseClient
) : UserReportRepository {

    override suspend fun createReport(
        reporterId: String,
        reportedUserId: String,
        reason: String,
        details: String?
    ): Result<UserReport> = repositorySafeCall {
        val dto = CreateUserReportDto(
            reporterId = reporterId,
            reportedUserId = reportedUserId,
            reason = reason,
            details = details,
            status = "PENDING"
        )
        supabase.from("user_reports")
            .insert(dto) { select() }
            .decodeSingle<UserReportDto>()
            .toDomain()
    }

    override suspend fun getReportsByReporter(reporterId: String): Result<List<UserReport>> =
        repositorySafeCall {
            supabase.from("user_reports").select {
                filter { eq("reporter_id", reporterId) }
            }.decodeList<UserReportDto>().map { it.toDomain() }
        }
}

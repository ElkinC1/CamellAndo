package com.example.camellando.data.repository

import com.example.camellando.data.model.UserReport

interface UserReportRepository {
    suspend fun createReport(
        reporterId: String,
        reportedUserId: String,
        reason: String,
        details: String? = null
    ): Result<UserReport>
    suspend fun getReportsByReporter(reporterId: String): Result<List<UserReport>>
}

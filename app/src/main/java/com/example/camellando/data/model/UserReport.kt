package com.example.camellando.data.model

data class UserReport(
    val id: String,
    val reporterId: String,
    val reportedUserId: String,
    val reason: String,
    val details: String?,
    val status: UserReportStatus,
    val createdAt: String
)

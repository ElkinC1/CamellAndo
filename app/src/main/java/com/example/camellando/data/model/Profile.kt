package com.example.camellando.data.model

data class Profile(
    val id: String,
    val displayName: String,
    val role: UserRole,
    val bio: String?,
    val avatarPath: String?,
    val accountStatus: AccountStatus,
    val cvPath: String?,
    val cvVisible: Boolean,
    val createdAt: String,
    val location: String? = null,
    val birthDate: String? = null,
    val identification: String? = null,
    val occupation: String? = null,
    val experienceYears: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null
)

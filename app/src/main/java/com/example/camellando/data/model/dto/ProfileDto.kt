package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,

    @SerialName("display_name")
    val displayName: String,

    val role: String,
    val bio: String? = null,

    @SerialName("avatar_path")
    val avatarPath: String? = null,

    @SerialName("account_status")
    val accountStatus: String = "ACTIVE",

    @SerialName("cv_path")
    val cvPath: String? = null,

    @SerialName("cv_visible")
    val cvVisible: Boolean = false,

    @SerialName("created_at")
    val createdAt: String,

    val location: String? = null,

    @SerialName("birth_date")
    val birthDate: String? = null,

    val identification: String? = null,
    val occupation: String? = null,

    @SerialName("experience_years")
    val experienceYears: String? = null,

    @SerialName("min_price")
    val minPrice: Double? = null,

    @SerialName("max_price")
    val maxPrice: Double? = null
)

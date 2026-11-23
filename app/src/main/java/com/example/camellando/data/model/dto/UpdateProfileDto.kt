package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileDto(
    @SerialName("display_name")
    val displayName: String,

    val bio: String?,

    @SerialName("avatar_path")
    val avatarPath: String?,

    @SerialName("cv_path")
    val cvPath: String?,

    @SerialName("cv_visible")
    val cvVisible: Boolean,

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

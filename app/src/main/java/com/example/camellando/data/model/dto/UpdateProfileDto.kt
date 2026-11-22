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
    val cvVisible: Boolean
)
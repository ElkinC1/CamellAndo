package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAccountStatusDto(
    @SerialName("account_status")
    val accountStatus: String
)
package com.example.camellando.data.repository

import com.example.camellando.data.model.AccountStatus
import com.example.camellando.data.model.Profile

interface ProfileRepository {
    suspend fun getProfile(userId: String): Result<Profile>
    suspend fun updateProfile(profile: Profile): Result<Profile>
    suspend fun updateAccountStatus(userId: String, status: AccountStatus): Result<Unit>
}

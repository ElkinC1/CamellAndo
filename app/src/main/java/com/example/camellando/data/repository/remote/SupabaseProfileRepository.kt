package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.AccountStatus
import com.example.camellando.data.model.Profile
import com.example.camellando.data.model.dto.ProfileDto
import com.example.camellando.data.model.dto.UpdateAccountStatusDto
import com.example.camellando.data.model.dto.UpdateProfileDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.ProfileRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseProfileRepository(
    private val supabase: SupabaseClient
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Result<Profile> =
        repositorySafeCall {
            supabase
                .from("profiles")
                .select {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingle<ProfileDto>()
                .toDomain()
        }

    override suspend fun updateProfile(profile: Profile): Result<Profile> =
        repositorySafeCall {
            val updateDto = UpdateProfileDto(
                displayName = profile.displayName,
                bio = profile.bio,
                avatarPath = profile.avatarPath,
                cvPath = profile.cvPath,
                cvVisible = profile.cvVisible
            )

            supabase
                .from("profiles")
                .update(updateDto) {
                    filter {
                        eq("id", profile.id)
                    }
                    select()
                }
                .decodeSingle<ProfileDto>()
                .toDomain()
        }

    override suspend fun updateAccountStatus(
        userId: String,
        status: AccountStatus
    ): Result<Unit> =
        repositorySafeCall {
            supabase
                .from("profiles")
                .update(UpdateAccountStatusDto(status.name)) {
                    filter {
                        eq("id", userId)
                    }
                    select()
                }
                .decodeSingle<ProfileDto>()

            Unit
        }
}
package com.example.camellando.data.repository.remote

import com.example.camellando.data.model.Notification
import com.example.camellando.data.model.dto.NotificationDto
import com.example.camellando.data.model.mapper.toDomain
import com.example.camellando.data.repository.NotificationRepository
import com.example.camellando.data.repository.repositorySafeCall
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseNotificationRepository(
    private val supabase: SupabaseClient
) : NotificationRepository {

    override suspend fun getForUser(recipientId: String): Result<List<Notification>> =
        repositorySafeCall {
            supabase.from("notifications").select {
                filter { eq("recipient_id", recipientId) }
            }.decodeList<NotificationDto>()
                .map { it.toDomain() }
                .sortedByDescending { it.createdAt }
        }

    @OptIn(SupabaseExperimental::class)
    override fun observeForUser(recipientId: String): Flow<List<Notification>> =
        supabase.from("notifications").selectAsFlow(
            primaryKey = NotificationDto::id,
            filter = FilterOperation("recipient_id", FilterOperator.EQ, recipientId)
        ).map { rows -> rows.map { it.toDomain() }.sortedByDescending { it.createdAt } }

    override suspend fun markAsRead(
        notificationId: String,
        recipientId: String
    ): Result<Unit> = repositorySafeCall {
        supabase.from("notifications").update({
            set("is_read", true)
        }) {
            filter {
                eq("id", notificationId)
                eq("recipient_id", recipientId)
            }
        }
        Unit
    }

    override suspend fun markAllAsRead(recipientId: String): Result<Unit> =
        repositorySafeCall {
            supabase.from("notifications").update({
                set("is_read", true)
            }) {
                filter {
                    eq("recipient_id", recipientId)
                    eq("is_read", false)
                }
            }
            Unit
        }
}

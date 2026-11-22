package com.example.camellando.data.repository

import com.example.camellando.data.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getForUser(recipientId: String): Result<List<Notification>>

    fun observeForUser(recipientId: String): Flow<List<Notification>>

    suspend fun markAsRead(notificationId: String, recipientId: String): Result<Unit>

    suspend fun markAllAsRead(recipientId: String): Result<Unit>
}

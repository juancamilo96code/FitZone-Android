package com.vamaju.fitzone.domain.notifications

import com.vamaju.fitzone.domain.notifications.model.Notification
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface NotificationRepository {
    fun getNotifications(): Flow<List<Notification>>
    suspend fun markNotificationAsRead(notificationId: String)
}
package com.vamaju.fitzone.data.notifications.remote

import com.vamaju.fitzone.data.notifications.model.NotificationDto
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface NotificationDataSource {
    fun getNotifications(userId: String): Flow<List<NotificationDto>>
    suspend fun markNotificationAsRead(userId: String, notificationId: String)
}
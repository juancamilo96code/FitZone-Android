package com.vamaju.fitzone.domain.notifications.model

import com.vamaju.fitzone.data.notifications.model.NotificationDto
import java.util.Date

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Date,
    val isRead: Boolean
)

fun NotificationDto.toDomain() = Notification(
    id = id,
    title = title,
    message = message,
    timestamp = timestamp.toDate(),
    isRead = isRead
)
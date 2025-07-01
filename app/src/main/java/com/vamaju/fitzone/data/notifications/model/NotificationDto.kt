package com.vamaju.fitzone.data.notifications.model

import com.google.firebase.Timestamp

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class NotificationDto(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val isRead: Boolean = false
)

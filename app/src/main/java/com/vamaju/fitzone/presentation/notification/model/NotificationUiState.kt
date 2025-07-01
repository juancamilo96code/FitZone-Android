package com.vamaju.fitzone.presentation.notification.model

import com.vamaju.fitzone.domain.notifications.model.Notification

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val errorMessage: String? = null
)

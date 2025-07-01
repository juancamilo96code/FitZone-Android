package com.vamaju.fitzone.domain.notifications.usecases

import com.vamaju.fitzone.domain.notifications.NotificationRepository
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class MarkNotificationAsReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notificationId: String) {
        repository.markNotificationAsRead(notificationId)
    }
}
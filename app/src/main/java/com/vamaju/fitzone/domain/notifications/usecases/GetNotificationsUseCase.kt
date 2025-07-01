package com.vamaju.fitzone.domain.notifications.usecases

import com.vamaju.fitzone.domain.notifications.NotificationRepository
import com.vamaju.fitzone.domain.notifications.model.Notification
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<List<Notification>> {
        return repository.getNotifications()
    }
}
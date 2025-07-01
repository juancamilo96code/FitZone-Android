package com.vamaju.fitzone.data.notifications.repository

import com.vamaju.fitzone.data.notifications.remote.NotificationDataSource
import com.vamaju.fitzone.domain.notifications.NotificationRepository
import com.vamaju.fitzone.domain.notifications.model.Notification
import com.vamaju.fitzone.domain.notifications.model.toDomain
import com.vamaju.fitzone.domain.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: NotificationDataSource
) : NotificationRepository {

    // Inyecta también el UserRepository para obtener el ID del usuario logueado
    @Inject
    lateinit var userRepository: UserRepository

    override fun getNotifications(): Flow<List<Notification>> {
        val userId = userRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in.")
        return remoteDataSource.getNotifications(userId).map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override suspend fun markNotificationAsRead(notificationId: String) {
        val userId = userRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in.")
        remoteDataSource.markNotificationAsRead(userId, notificationId)
    }
}
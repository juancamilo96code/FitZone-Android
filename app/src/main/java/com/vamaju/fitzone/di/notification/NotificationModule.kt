package com.vamaju.fitzone.di.notification

import com.vamaju.fitzone.data.notifications.remote.FirestoreNotificationDataSource
import com.vamaju.fitzone.data.notifications.remote.NotificationDataSource
import com.vamaju.fitzone.data.notifications.repository.NotificationRepositoryImpl
import com.vamaju.fitzone.domain.notifications.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {
    // ... Tus binds existentes

    @Binds
    @Singleton
    abstract fun bindNotificationDataSource(
        firestoreNotificationDataSource: FirestoreNotificationDataSource
    ): NotificationDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}
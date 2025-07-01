package com.vamaju.fitzone.di.subscriptions

import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.subscriptions.remote.FirestoreSubscriptionDataSource
import com.vamaju.fitzone.data.subscriptions.remote.SubscritionDataSource
import com.vamaju.fitzone.data.subscriptions.repository.SubscriptionRepositoryImpl
import com.vamaju.fitzone.domain.suscriptions.SubscriptionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {
    // ... Tus binds existentes para ClassDataSource y ClassRepository
    @Provides
    @Singleton
    fun provideFirestoreSubscriptionDataSource(
        firestore: FirebaseFirestore
    ): SubscritionDataSource {
        return FirestoreSubscriptionDataSource(
            firestore = firestore
        )
    }

    @Provides
    @Singleton
    fun provideSubscriptionRepository(
        subscriptionDataSource: SubscritionDataSource
    ): SubscriptionRepository {
        return SubscriptionRepositoryImpl(
            remoteDataSource = subscriptionDataSource
        )
    }

}
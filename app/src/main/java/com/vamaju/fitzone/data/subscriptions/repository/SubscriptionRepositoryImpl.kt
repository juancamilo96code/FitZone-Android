package com.vamaju.fitzone.data.subscriptions.repository

import com.vamaju.fitzone.data.subscriptions.remote.SubscritionDataSource
import com.vamaju.fitzone.domain.suscriptions.SubscriptionRepository
import com.vamaju.fitzone.domain.suscriptions.model.SubscriptionType
import com.vamaju.fitzone.domain.suscriptions.model.toDomain
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class SubscriptionRepositoryImpl @Inject constructor(
    private val remoteDataSource: SubscritionDataSource
) : SubscriptionRepository {

    override suspend fun getSubscriptionTypes(): List<SubscriptionType> {
        return remoteDataSource.getSubscriptionTypes().map { it.toDomain() }
    }
}
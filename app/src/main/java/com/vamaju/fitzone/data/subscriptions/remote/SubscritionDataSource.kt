package com.vamaju.fitzone.data.subscriptions.remote

import com.vamaju.fitzone.data.subscriptions.model.SubscriptionTypeDto

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface SubscritionDataSource {
    suspend fun getSubscriptionTypes(): List<SubscriptionTypeDto>
}
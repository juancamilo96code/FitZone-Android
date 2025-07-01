package com.vamaju.fitzone.domain.suscriptions

import com.vamaju.fitzone.domain.suscriptions.model.SubscriptionType

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface SubscriptionRepository {

    suspend fun getSubscriptionTypes(): List<SubscriptionType>
}
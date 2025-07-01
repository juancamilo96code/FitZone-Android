package com.vamaju.fitzone.domain.suscriptions.usecases

import com.vamaju.fitzone.domain.suscriptions.SubscriptionRepository
import com.vamaju.fitzone.domain.suscriptions.model.SubscriptionType
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class GetSubscriptionOptionsUseCase @Inject constructor(
    private val bookingRepository: SubscriptionRepository
) {
    suspend operator fun invoke(): List<SubscriptionType> {
        return bookingRepository.getSubscriptionTypes()
    }
}


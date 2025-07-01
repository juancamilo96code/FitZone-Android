package com.vamaju.fitzone.domain.user.usecases

import com.vamaju.fitzone.domain.user.UserRepository
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class CheckSubscriptionStatusUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): SubscriptionStatus {
        val userId = userRepository.getCurrentUserId() ?: return SubscriptionStatus.NotLoggedIn
        val user = userRepository.getUser(userId)

        if (user == null || user.subscriptionType == null || user.startSubscription == null) {
            // Si el usuario no tiene datos de suscripción, actualiza el usuario en Firestore
            // con datos vacíos para asegurar el documento existe
            userRepository.updateSubscription(userId, "", null)
            return SubscriptionStatus.Inactive
        }

        val startDate = user.startSubscription
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val durationInMonths = when (user.subscriptionType) {
            "MONTH" -> 1
            "TRI" -> 3
            "YEAR" -> 12
            else -> 0
        }

        if (durationInMonths > 0) {
            calendar.add(Calendar.MONTH, durationInMonths)
            val endDate = calendar.time
            val now = Date()

            return if (now.before(endDate)) {
                SubscriptionStatus.Active(user.subscriptionType, endDate)
            } else {
                // Si ha expirado, actualiza el usuario para limpiar la suscripción
                userRepository.updateSubscription(userId, "", null)
                SubscriptionStatus.Expired
            }
        }

        return SubscriptionStatus.Inactive
    }
}

sealed class SubscriptionStatus {
    data class Active(val type: String, val endDate: Date) : SubscriptionStatus()
    object Inactive : SubscriptionStatus()
    object Expired : SubscriptionStatus()
    object NotLoggedIn : SubscriptionStatus()
}
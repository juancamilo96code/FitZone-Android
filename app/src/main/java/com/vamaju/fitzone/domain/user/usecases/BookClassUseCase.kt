package com.vamaju.fitzone.domain.user.usecases

import com.vamaju.fitzone.domain.user.UserRepository
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class BookClassUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(classId: String) {
        val userId = userRepository.getCurrentUserId()
        if (userId != null) {
            // El agendamiento y la creación de la notificación se manejan en el data source
            userRepository.bookClass(userId, classId)
        } else {
            throw Exception("User not authenticated.")
        }
    }
}
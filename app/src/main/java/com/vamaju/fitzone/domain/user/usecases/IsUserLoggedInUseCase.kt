package com.vamaju.fitzone.domain.user.usecases

import com.vamaju.fitzone.domain.user.UserRepository
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class IsUserLoggedInUseCase @Inject constructor(
    private val authRepository: UserRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.getCurrentUserId() != null
    }
}
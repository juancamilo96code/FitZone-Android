package com.vamaju.fitzone.domain.user.usecases

import com.vamaju.fitzone.domain.user.UserRepository
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class LoginUseCase @Inject constructor(
    private val authRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.login(email, password)
    }
}
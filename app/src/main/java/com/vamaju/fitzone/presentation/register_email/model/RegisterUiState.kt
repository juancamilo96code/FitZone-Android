package com.vamaju.fitzone.presentation.register_email.model

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerSuccess: Boolean = false
)

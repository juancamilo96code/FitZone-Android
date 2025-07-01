package com.vamaju.fitzone.presentation.login_email.model

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginSuccess: Boolean = false
)

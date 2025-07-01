package com.vamaju.fitzone.presentation.register_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.user.usecases.RegisterUseCase
import com.vamaju.fitzone.presentation.register_email.model.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, registerSuccess = false)
            if (uiState.value.password != uiState.value.confirmPassword) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Las contraseñas no coinciden.")
                return@launch
            }
            try {
                registerUseCase(uiState.value.email, uiState.value.password)
                _uiState.value = _uiState.value.copy(isLoading = false, registerSuccess = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Error desconocido")
            }
        }
    }

    fun resetRegisterState() {
        _uiState.value = _uiState.value.copy(registerSuccess = false, errorMessage = null)
    }
}
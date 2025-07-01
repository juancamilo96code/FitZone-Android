package com.vamaju.fitzone.presentation.login_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.user.usecases.LoginUseCase
import com.vamaju.fitzone.presentation.login_email.model.LoginUiState
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, loginSuccess = false)
            try {
                loginUseCase(uiState.value.email, uiState.value.password)
                _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Error desconocido")
            }
        }
    }

    fun resetLoginState() {
        _uiState.value = _uiState.value.copy(loginSuccess = false, errorMessage = null)
    }
}
package com.vamaju.fitzone.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.user.usecases.IsUserLoggedInUseCase
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
class AuthViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    private val _startDestination = MutableStateFlow("")
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            _startDestination.value = if (isUserLoggedInUseCase()) {
                "Home"
            } else {
                "Login"
            }
        }
    }
}
package com.vamaju.fitzone.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.notifications.usecases.GetNotificationsUseCase
import com.vamaju.fitzone.domain.notifications.usecases.MarkNotificationAsReadUseCase
import com.vamaju.fitzone.presentation.notification.model.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState(isLoading = true))
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase()
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null) }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Error cargando notificaciones: ${e.message}")
                }
                .collect { notifications ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        notifications = notifications,
                        errorMessage = null
                    )
                }
        }
    }

    fun onNotificationClicked(notificationId: String) {
        viewModelScope.launch {
            try {
                markNotificationAsReadUseCase(notificationId)
            } catch (e: Exception) {
                // Puedes mostrar un snackbar o loggear el error si falla al marcar como leída
                _uiState.value = _uiState.value.copy(errorMessage = "Error al marcar como leída: ${e.message}")
            }
        }
    }
}
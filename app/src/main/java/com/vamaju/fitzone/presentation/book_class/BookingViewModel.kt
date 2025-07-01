package com.vamaju.fitzone.presentation.book_class

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.classes.usecases.GetClassDetailsUseCase
import com.vamaju.fitzone.domain.suscriptions.usecases.GetSubscriptionOptionsUseCase
import com.vamaju.fitzone.domain.user.usecases.BookClassUseCase
import com.vamaju.fitzone.domain.user.usecases.CheckSubscriptionStatusUseCase
import com.vamaju.fitzone.domain.user.usecases.SubscriptionStatus
import com.vamaju.fitzone.presentation.book_class.model.BookingUiState
import com.vamaju.fitzone.presentation.book_class.model.SubscriptionTypeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@HiltViewModel
class BookingViewModel @Inject constructor(
    private val checkSubscriptionStatusUseCase: CheckSubscriptionStatusUseCase,
    private val getSubscriptionOptionsUseCase: GetSubscriptionOptionsUseCase,
    private val getClassDetailsUseCase: GetClassDetailsUseCase,
    private val bookClassUseCase: BookClassUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    fun loadClassDetailsAndCheckSubscription(classId: String) {
        _uiState.value = _uiState.value.copy(classId = classId)
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // 1. Cargar los detalles de la clase
                val classDetails = getClassDetailsUseCase(uiState.value.classId)

                // 2. Cargar el estado de la suscripción
                when (val status = checkSubscriptionStatusUseCase()) {
                    is SubscriptionStatus.Active -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            classDetails = classDetails,
                            hasActiveSubscription = true,
                            activeSubscriptionDetails = "Activa: ${status.type} hasta el ${
                                formatDate(
                                    status.endDate
                                )
                            }"
                        )
                    }

                    else -> { // Inactiva, Expirada, No logueado
                        val options = getSubscriptionOptionsUseCase().map {
                            SubscriptionTypeUiModel(
                                id = it.id,
                                name = it.name,
                                price = it.price,
                                type = it.type,
                                icon = mapTypeToIcon(it.type)
                            )
                        }
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            classDetails = classDetails,
                            hasActiveSubscription = false,
                            subscriptionOptions = options
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load data: ${e.message}"
                )
            }
        }
    }

    fun onBookClassClicked() {
        // Muestra el modal de confirmación
        _uiState.value = _uiState.value.copy(showConfirmationModal = true)
    }

    fun onConfirmBooking() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, showConfirmationModal = false)
            try {
                bookClassUseCase(uiState.value.classId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    bookingSuccess = true,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    bookingSuccess = false,
                    errorMessage = "Error al agendar la clase: ${e.message}"
                )
            }
        }
    }

    fun onCancelBooking() {
        _uiState.value = _uiState.value.copy(showConfirmationModal = false)
    }

    // Funciones auxiliares para la UI
    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }

    private fun mapTypeToIcon(type: String): ImageVector {
        // Implementa tu lógica de mapeo de tipo a icono aquí
        return when (type) {
            "MONTH" -> Icons.Default.Star
            "TRI" -> Icons.Default.CheckCircle
            "YEAR" -> Icons.Default.Add
            else -> Icons.Default.Face
        }
    }
}
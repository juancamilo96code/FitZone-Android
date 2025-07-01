package com.vamaju.fitzone.presentation.book_class.model

import com.vamaju.fitzone.domain.classes.model.ClassModel

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class BookingUiState(
    val isLoading: Boolean = true,
    val classId: String = "",
    val classDetails: ClassModel? = null, // Nuevo campo para el resumen de la clase
    val hasActiveSubscription: Boolean = false,
    val activeSubscriptionDetails: String? = null,
    val subscriptionOptions: List<SubscriptionTypeUiModel> = emptyList(),
    val showConfirmationModal: Boolean = false,
    val bookingSuccess: Boolean = false,
    val errorMessage: String? = null
)

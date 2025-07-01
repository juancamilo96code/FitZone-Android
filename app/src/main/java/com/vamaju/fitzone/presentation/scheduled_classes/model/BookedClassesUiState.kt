package com.vamaju.fitzone.presentation.scheduled_classes.model

import com.vamaju.fitzone.domain.classes.model.ClassModel

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class BookedClassesUiState(
    val isLoading: Boolean = false,
    val upcomingClasses: List<ClassModel> = emptyList(),
    val pastClasses: List<ClassModel> = emptyList(),
    val errorMessage: String? = null
)

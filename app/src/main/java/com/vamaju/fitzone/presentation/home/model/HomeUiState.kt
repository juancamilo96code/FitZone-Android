package com.vamaju.fitzone.presentation.home.model

import com.vamaju.fitzone.domain.classes.model.ClassType

/**
 * @author Juan Camilo Collantes Tovar on 29/06/2025
 * **/
data class HomeUiState(
    val isLoading: Boolean = false,
    val classTypes: List<ClassType> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)
package com.vamaju.fitzone.presentation.class_details.model

import com.vamaju.fitzone.domain.classes.model.ClassModel
import com.vamaju.fitzone.domain.classes.model.ClassType
import com.vamaju.fitzone.domain.locations.model.Location
import java.util.Date

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class ClassDetailUiState(
    val isLoading: Boolean = false,
    val classType: ClassType? = null,
    val availableLocations: List<Location> = emptyList(),
    val filteredClasses: List<ClassModel> = emptyList(),
    val selectedDate: Date? = null,
    val selectedLocationId: String? = null,
    val errorMessage: String? = null
)

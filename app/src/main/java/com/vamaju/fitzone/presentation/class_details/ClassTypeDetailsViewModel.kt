package com.vamaju.fitzone.presentation.class_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.classes.usecases.GetClassTypeDetailsUseCase
import com.vamaju.fitzone.domain.classes.usecases.GetFilteredClassesUseCase
import com.vamaju.fitzone.domain.locations.usecases.GetLocationsUseCase
import com.vamaju.fitzone.presentation.class_details.model.ClassDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@HiltViewModel
class ClassTypeDetailsViewModel @Inject constructor(
    private val getClassTypeDetailsUseCase: GetClassTypeDetailsUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getFilteredClassesUseCase: GetFilteredClassesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassDetailUiState(isLoading = true))
    val uiState: StateFlow<ClassDetailUiState> = _uiState.asStateFlow()

    // Los filtros son estados internos que se combinan
    private val _selectedDate = MutableStateFlow<Date?>(null)
    private val _selectedLocationId = MutableStateFlow<String?>(null)

    fun loadInitialData(classTypeId: String) {
        viewModelScope.launch {
            try {
                // Cargar los detalles del ClassType y las ubicaciones en paralelo
                val classType = getClassTypeDetailsUseCase(classTypeId)

                _uiState.value = _uiState.value.copy(classType = classType)

                getLocationsUseCase().collect { locations ->
                    _uiState.value = _uiState.value.copy(
                        availableLocations = locations
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load initial data: ${e.message}"
                )
            }
        }
    }

     fun observeFilteredClasses(classTypeId: String) {
        viewModelScope.launch {
            // Combina los filtros (fecha y ciudad) con el caso de uso para re-ejecutar la consulta
            // solo cuando los filtros cambian. Esto es clave para la eficiencia.
            combine(
                _selectedDate,
                _selectedLocationId
            ) { date, locationId ->
                // Este `combine` dispara una nueva llamada al caso de uso
                // cada vez que `_selectedDate` o `_selectedLocationId` cambian
                getFilteredClassesUseCase(classTypeId, date, locationId)
            }.collect { flowOfClasses ->
                // Recogemos el Flow de clases filtradas
                _uiState.value = _uiState.value.copy(isLoading = true) // Muestra el loading al filtrar
                flowOfClasses.collect { classes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        filteredClasses = classes,
                        errorMessage = null
                    )
                }
            }
        }
    }

    // Funciones para actualizar los filtros desde la UI
    fun onDateSelected(date: Date) {
        _selectedDate.value = date
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun onCitySelected(locationId: String) {
        _selectedLocationId.value = locationId
        _uiState.value = _uiState.value.copy(selectedLocationId = locationId)
    }

    fun clearFilters() {
        _selectedDate.value = null
        _selectedLocationId.value = null
        _uiState.value = _uiState.value.copy(
            selectedDate = null,
            selectedLocationId = null
        )
    }
}
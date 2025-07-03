package com.vamaju.fitzone.presentation.class_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.classes.usecases.GetClassTypeDetailsUseCase
import com.vamaju.fitzone.domain.classes.usecases.GetFilteredClassesUseCase
import com.vamaju.fitzone.domain.locations.usecases.GetLocationsUseCase
import com.vamaju.fitzone.presentation.class_details.model.ClassDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Obtener classTypeId directamente del SavedStateHandle
    private val classTypeId: String = savedStateHandle.get<String>("classTypeId") // <--- ¡Aquí lo obtienes!
        ?: throw IllegalArgumentException("classTypeId argument is required")

    private val _uiState = MutableStateFlow(ClassDetailUiState(isLoading = true))
    val uiState: StateFlow<ClassDetailUiState> = _uiState.asStateFlow()

    // Los filtros son estados internos que se combinan
    private val _selectedDate = MutableStateFlow<Date?>(null)
    private val _selectedLocationId = MutableStateFlow<String?>(null)

    init {
        loadInitialData()
        observeFilteredClasses()
    }

    private fun loadInitialData() {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeFilteredClasses() {
        viewModelScope.launch {
            // Combina los flujos de filtros
            combine(
                _selectedDate,
                _selectedLocationId
            ) { date, locationId ->
                // Este bloque se ejecuta cada vez que _selectedDate o _selectedLocationId cambian.
                // Aquí, el log SÍ se disparará cada vez que cambien los filtros.
                Log.d("ClassTypeDetailsViewModel", "Filters changed: Date=$date, LocationId=$locationId. Fetching filtered classes...")
                // Devuelve el Flow del caso de uso
                getFilteredClassesUseCase(classTypeId, date, locationId)
            }.flatMapLatest { flowOfClasses ->
                    // flatMapLatest es crucial aquí. Cada vez que el combine anterior emite un nuevo Flow,
                    // flatMapLatest cancela la colección del Flow anterior y empieza a recolectar el nuevo.
                    // Los operadores onStart y catch se aplican a CADA NUEVO FLUJO que viene de getFilteredClassesUseCase.
                    flowOfClasses
                        .onStart {
                            // Muestra el loading cuando se inicia una nueva consulta de filtrado
                            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                            Log.d("ClassTypeDetailsViewModel", "Starting new filtered classes query...")
                        }
                        .catch { e ->
                            // Maneja errores específicos de la consulta de filtrado
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Error al filtrar clases: ${e.message}")
                            Log.e("ClassTypeDetailsViewModel", "Error in filtered classes query: ${e.message}")
                        }
                }.collect { classes ->
                    // Recolecta los resultados de las clases filtradas
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        filteredClasses = classes,
                        errorMessage = null
                    )
                    Log.d("ClassTypeDetailsViewModel", "Filtered classes received: ${classes.size} items.")
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
package com.vamaju.fitzone.presentation.scheduled_classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.classes.usecases.GetBookedClassesUseCase
import com.vamaju.fitzone.presentation.scheduled_classes.model.BookedClassesUiState
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
class BookedClassesViewModel @Inject constructor(
    private val getBookedClassesUseCase: GetBookedClassesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookedClassesUiState(isLoading = true))
    val uiState: StateFlow<BookedClassesUiState> = _uiState.asStateFlow()

    init {
        loadBookedClasses()
    }

    fun loadBookedClasses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val groupedClasses = getBookedClassesUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    upcomingClasses = groupedClasses.upcoming,
                    pastClasses = groupedClasses.past,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error cargando clases agendadas: ${e.message}"
                )
            }
        }
    }
}
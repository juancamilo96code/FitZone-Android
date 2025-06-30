package com.vamaju.fitzone.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.fitzone.domain.classes.usecases.GetClassTypesUseCase
import com.vamaju.fitzone.domain.classes.usecases.SearchClassTypesUseCase
import com.vamaju.fitzone.presentation.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClassTypesUseCase: GetClassTypesUseCase,
    private val searchClassTypesUseCase: SearchClassTypesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        observeClassTypes()
    }

    private fun observeClassTypes() {
        viewModelScope.launch {
            combine(
                getClassTypesUseCase(),
                _searchQuery
            ) { allClasses, query ->
                searchClassTypesUseCase(query).map { filteredClasses ->
                    HomeUiState(
                        isLoading = false,
                        classTypes = filteredClasses,
                        searchQuery = query
                    )
                }
            }.collectLatest { flowOfFilteredClasses ->
                flowOfFilteredClasses.collectLatest { uiState ->
                    _uiState.value = uiState
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

}
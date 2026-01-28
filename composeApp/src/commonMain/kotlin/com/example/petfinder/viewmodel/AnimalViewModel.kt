package com.example.petfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.components.resources.Res
import com.example.components.resources.error_unexpected
import com.example.components.resources.msg_unfound
import com.example.domain.model.Animal
import com.example.domain.model.PetType
import com.example.domain.repository.AnimalRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.useCase.GetFavoritesUseCase
import com.example.domain.useCase.ToggleFavoriteUseCase
import com.example.domain.util.AppLogger
import com.example.petfinder.ui.util.UiText
import com.example.petfinder.ui.components.PetFilter // FIXED: New import
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AnimalUiState(
    val isLoading: Boolean = false,
    val animals: List<Animal> = emptyList(),
    val error: UiText? = null,
    val filters: List<String> = PetType.values().map { it.apiValue },
    val selectedFilter: String = PetType.DEFAULT.apiValue,
    val currentQuery: String = "",
    val isAddFilterDialogOpen: Boolean = false
)

class AnimalViewModel(
    private val getAnimalsUseCase: GetAnimalImagesUseCase,
    private val repository: AnimalRepository,
    private val logger: AppLogger,
    getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val TAG = "AnimalViewModel"

    val favorites: StateFlow<List<Animal>> = getFavoritesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(AnimalUiState())
    val uiState: StateFlow<AnimalUiState> = _uiState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(TAG, "Coroutine Error: ${throwable.message}", throwable)
        _uiState.update {
            it.copy(
                isLoading = false,
                error = UiText.Resource(Res.string.error_unexpected)
            )
        }
    }

    init {
        searchInternal(_uiState.value.selectedFilter)
        observeSearchResults()
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
        performSearch()
    }

    fun onAddFilterClicked() {
        _uiState.update { it.copy(isAddFilterDialogOpen = true) }
    }

    fun onDismissAddFilterDialog() {
        _uiState.update { it.copy(isAddFilterDialogOpen = false) }
    }

    fun onApplyFilters(filter: PetFilter) {
        val newType = filter.type
        if (newType.isBlank()) return

        val normalizedType = newType.trim().lowercase()

        _uiState.update { currentState ->
            val newFilters = if (currentState.filters.contains(normalizedType)) {
                currentState.filters
            } else {
                currentState.filters + normalizedType
            }

            currentState.copy(
                filters = newFilters,
                selectedFilter = normalizedType,
                isAddFilterDialogOpen = false
            )
        }
        performSearch()
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.update { it.copy(currentQuery = newQuery) }
    }

    fun performSearch() {
        val state = _uiState.value
        val queryText = state.currentQuery.trim()
        val filter = state.selectedFilter
        val finalQuery = if (queryText.isBlank()) filter else "$filter $queryText"
        searchInternal(finalQuery)
    }

    private fun observeSearchResults() {
        viewModelScope.launch {
            repository.searchResults.collect { animals ->
                _uiState.update { currentState ->
                    if (animals.isEmpty() && !currentState.isLoading) {
                        currentState.copy(
                            isLoading = false,
                            error = UiText.Resource(Res.string.msg_unfound),
                            animals = emptyList()
                        )
                    } else {
                        currentState.copy(
                            isLoading = false,
                            animals = animals,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun searchInternal(query: String) {
        if (query.isBlank()) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch(errorHandler) {
            val result = getAnimalsUseCase(query)

            // FIXED: Using isFailure check to avoid unresolved onFailure extension issues
            if (result.isFailure) {
                val exception = result.exceptionOrNull()
                logger.e(TAG, "Business Rule Violation: ${exception?.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = UiText.Resource(Res.string.error_unexpected),
                        animals = emptyList()
                    )
                }
            }
        }
    }

    fun toggleFavorite(animal: Animal) {
        viewModelScope.launch(errorHandler) {
            toggleFavoriteUseCase(animal)
        }
    }
}

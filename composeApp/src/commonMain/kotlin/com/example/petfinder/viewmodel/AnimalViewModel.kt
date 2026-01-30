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
import com.example.petfinder.ui.components.PetFilter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
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

sealed class AnimalIntent {
    data class FilterSelected(val filter: String) : AnimalIntent()
    data class QueryChanged(val query: String) : AnimalIntent()
    data class ApplyPetFilter(val filter: PetFilter) : AnimalIntent()
    data class ToggleFavorite(val animal: Animal) : AnimalIntent()
    object RefreshSearch : AnimalIntent()
    object OpenFilterDialog : AnimalIntent()
    object CloseFilterDialog : AnimalIntent()
}

class AnimalViewModel(
    private val getAnimalsUseCase: GetAnimalImagesUseCase,
    private val repository: AnimalRepository,
    private val logger: AppLogger,
    getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val TAG = "AnimalViewModel"

    private val _uiState = MutableStateFlow(AnimalUiState())
    val uiState: StateFlow<AnimalUiState> = _uiState.asStateFlow()

    val favorites: StateFlow<List<Animal>> = getFavoritesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(TAG, "MVI State Error: ${throwable.message}", throwable)
        _uiState.update { it.copy(isLoading = false, error = UiText.Resource(Res.string.error_unexpected)) }
    }

    init {
        handleIntent(AnimalIntent.RefreshSearch)
        observeRepositoryResults()
    }

    fun handleIntent(intent: AnimalIntent) {
        when (intent) {
            is AnimalIntent.FilterSelected -> {
                _uiState.update { it.copy(selectedFilter = intent.filter) }
                performSearch()
            }
            is AnimalIntent.QueryChanged -> {
                _uiState.update { it.copy(currentQuery = intent.query) }
            }
            is AnimalIntent.ApplyPetFilter -> applyNewFilter(intent.filter)
            is AnimalIntent.ToggleFavorite -> toggleFavorite(intent.animal)
            is AnimalIntent.OpenFilterDialog -> _uiState.update { it.copy(isAddFilterDialogOpen = true) }
            is AnimalIntent.CloseFilterDialog -> _uiState.update { it.copy(isAddFilterDialogOpen = false) }
            is AnimalIntent.RefreshSearch -> performSearch()
        }
    }

    private fun observeRepositoryResults() {
        viewModelScope.launch(errorHandler) {
            repository.searchResults
                .catch { e ->
                    logger.e(TAG, "Flow Error", e)
                    _uiState.update { it.copy(error = UiText.Resource(Res.string.error_unexpected)) }
                }
                .collect { animals ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            animals = animals,
                            error = if (animals.isEmpty()) UiText.Resource(Res.string.msg_unfound) else null
                        )
                    }
                }
        }
    }

    private fun performSearch() {
        val state = _uiState.value
        val finalQuery = state.currentQuery.trim().let {
            if (it.isBlank()) state.selectedFilter else "${state.selectedFilter} $it"
        }

        if (finalQuery.isBlank()) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch(errorHandler) {
            try {
                val result = getAnimalsUseCase(finalQuery)
                if (result.isFailure) {
                    _uiState.update {
                        it.copy(isLoading = false, error = UiText.Resource(Res.string.error_unexpected))
                    }
                }
            } catch (e: Exception) {
                logger.e(TAG, "Search Exception", e)
                _uiState.update {
                    it.copy(isLoading = false, error = UiText.Resource(Res.string.error_unexpected))
                }
            }
        }
    }

    private fun applyNewFilter(filter: PetFilter) {
        val normalizedType = filter.type.trim().lowercase()
        if (normalizedType.isBlank()) return

        _uiState.update { state ->
            val updatedFilters = if (state.filters.contains(normalizedType)) state.filters else state.filters + normalizedType
            state.copy(
                filters = updatedFilters,
                selectedFilter = normalizedType,
                isAddFilterDialogOpen = false
            )
        }
        performSearch()
    }

    private fun toggleFavorite(animal: Animal) {
        viewModelScope.launch(errorHandler) {
            toggleFavoriteUseCase(animal)
        }
    }
}

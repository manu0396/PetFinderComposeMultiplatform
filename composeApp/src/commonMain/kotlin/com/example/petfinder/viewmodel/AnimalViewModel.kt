package com.example.petfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Animal
import com.example.domain.repository.AnimalRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.useCase.GetFavoritesUseCase
import com.example.domain.useCase.ToggleFavoriteUseCase
import com.example.domain.util.AppLogger
import com.example.petfinder.ui.util.UiText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import petfinder.composeapp.generated.resources.*

sealed class AnimalUiState {
    object Idle : AnimalUiState()
    object Loading : AnimalUiState()
    data class Success(val animals: List<Animal>) : AnimalUiState()
    data class Error(val message: UiText) : AnimalUiState()
}

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
    private val _uiState = MutableStateFlow<AnimalUiState>(AnimalUiState.Idle)
    val uiState: StateFlow<AnimalUiState> = _uiState.asStateFlow()

    var lastQuery: String = ""
        private set

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(TAG, "Coroutine Error: ${throwable.message}", throwable)
        _uiState.value = AnimalUiState.Error(UiText.Resource(Res.string.error_unexpected))
    }

    init {
        observeSearchResults()
    }

    private fun observeSearchResults() {
        viewModelScope.launch {
            repository.searchResults.collect { animals ->
                if (animals.isEmpty() && _uiState.value is AnimalUiState.Loading) {
                    _uiState.value = AnimalUiState.Error(UiText.Resource(Res.string.msg_unfound))
                } else if (animals.isNotEmpty()) {
                    _uiState.value = AnimalUiState.Success(animals)
                }
            }
        }
    }

    fun searchAnimals(query: String) {
        if (query.isBlank()) return

        lastQuery = query
        _uiState.value = AnimalUiState.Loading

        viewModelScope.launch(errorHandler) {
            getAnimalsUseCase(query)
                .onFailure { error ->
                    logger.e(TAG, "Business Rule Violation: ${error.message}")
                    _uiState.value = AnimalUiState.Error(UiText.Resource(Res.string.error_unexpected))
                }
        }
    }

    fun toggleFavorite(animal: Animal) {
        viewModelScope.launch(errorHandler) {
            toggleFavoriteUseCase(animal)
        }
    }
}

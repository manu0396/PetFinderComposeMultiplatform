package com.example.petfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Animal
import com.example.domain.repository.AnimalRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.util.AppLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class AnimalUiState {
    object Idle : AnimalUiState()
    object Loading : AnimalUiState()
    data class Success(val animals: List<Animal>) : AnimalUiState()
    data class Error(val message: String) : AnimalUiState()
}

class AnimalViewModel(
    private val getAnimalsUseCase: GetAnimalImagesUseCase, // Acción (Domain)
    private val repository: AnimalRepository,              // Estado (Data/Domain Bridge)
    private val logger: AppLogger
) : ViewModel() {

    private val TAG = "AnimalViewModel"

    val favorites: StateFlow<List<Animal>> = repository.favorites

    private val _uiState = MutableStateFlow<AnimalUiState>(AnimalUiState.Idle)
    val uiState: StateFlow<AnimalUiState> = _uiState.asStateFlow()

    var lastQuery: String = ""
        private set

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(TAG, "Coroutine Error: ${throwable.message}", throwable)
        _uiState.value = AnimalUiState.Error("Error inesperado en la operación")
    }

    init {
        observeSearchResults()
    }

    private fun observeSearchResults() {
        viewModelScope.launch {
            repository.searchResults.collect { animals ->
                if (animals.isEmpty() && _uiState.value is AnimalUiState.Loading) {
                    _uiState.value = AnimalUiState.Error("No se encontraron animales para esta búsqueda.")
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
                    _uiState.value = AnimalUiState.Error(error.message ?: "Consulta no permitida")
                }
        }
    }

    fun toggleFavorite(animal: Animal) {
        viewModelScope.launch(errorHandler) {
            repository.toggleFavorite(animal)
        }
    }
}

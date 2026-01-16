package com.example.petfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Animal
import com.example.domain.model.DomainError
import com.example.domain.repository.FavoriteRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.util.AppLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AnimalUiState {
    object Loading : AnimalUiState()
    data class Success(val animals: List<Animal>) : AnimalUiState()
    data class Error(val message: String) : AnimalUiState()
}

class AnimalViewModel(
    private val getAnimalsUseCase: GetAnimalImagesUseCase,
    private val favoriteRepository: FavoriteRepository,
    private val logger: AppLogger
    ) : ViewModel() {
    private val TAG = "AnimalViewModel"

    val favorites: StateFlow<List<Animal>> = favoriteRepository.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    private val _state = MutableStateFlow<AnimalUiState>(AnimalUiState.Success(emptyList()))
    val state = _state.asStateFlow()

    var lastQuery: String = ""
        private set

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        println("!!! FATAL_VM: Error en Corrutina: ${throwable.message}")
        throwable.printStackTrace()
    }

    fun toggleFavorite(animal: Animal) {
        viewModelScope.launch(errorHandler) {
            favoriteRepository.toggleFavorite(animal)
        }
    }

    fun searchAnimals(query: String) {
        logger.d(TAG, "Searching animals for query: $query")
        if (query == lastQuery && _state.value is AnimalUiState.Success) {
            val currentList = (_state.value as AnimalUiState.Success).animals
            if (currentList.isNotEmpty()) {
                logger.d(TAG, "Restoring previous search results for: $query")
                return
            }
        }

        lastQuery = query
        _state.value = AnimalUiState.Loading

        viewModelScope.launch(errorHandler) {
            getAnimalsUseCase(query)
                .onSuccess { animals ->
                    logger.d(TAG, "Successfully retrieved ${animals.size} animals")
                    _state.value = AnimalUiState.Success(animals)
                }
                .onFailure { error ->
                    val message = when(error) {
                        is DomainError.Serialization -> "Error al procesar los datos del servidor."
                        is DomainError.Unauthorized -> "Tu sesión ha expirado (API Key inválida)."
                        is DomainError.Network -> "Revisa tu conexión a internet."
                        else -> "Ocurrió un error inesperado."
                    }
                    logger.e(TAG, "$message query:$query", error)
                    _state.value = AnimalUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}



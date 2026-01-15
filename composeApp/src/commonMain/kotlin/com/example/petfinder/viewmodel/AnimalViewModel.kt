package com.example.petfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Animal
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.util.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimalViewModel(
    private val getAnimalsUseCase: GetAnimalImagesUseCase,
    private val logger: AppLogger
    ) : ViewModel() {
    private val _state = MutableStateFlow<List<Animal>>(emptyList())
    val state = _state.asStateFlow()

    fun loadAnimals() {
        viewModelScope.launch {
            try {
                _state.value = getAnimalsUseCase("dogs and cats")
            } catch (e: Exception) {
                logger.e("AnimalViewModel", "Error loading animals", e)
            }
        }
    }
}

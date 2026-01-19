package com.example.domain.useCase

import com.example.domain.model.Animal
import com.example.domain.repository.FavoriteRepository

class ToggleFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(animal: Animal) {
        val exists = repository.isFavorite(animal.id)
        if (exists) {
            repository.removeFavorite(animal.id)
        } else {
            repository.addFavorite(animal)
        }
    }
}

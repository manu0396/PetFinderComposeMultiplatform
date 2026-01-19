package com.example.domain.useCase

import com.example.domain.model.Animal
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

// package com.example.domain.useCase
class GetFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Animal>> = repository.getFavorites()
}

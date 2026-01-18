package com.example.domain.useCase

import com.example.domain.repository.AnimalRepository

class GetAnimalImagesUseCase(
    private val repository: AnimalRepository
) {
    private val allowedAnimals = listOf("dog", "cat", "rabbit", "bird", "hamster")

    // Cambiamos el retorno a Result<Unit>
    suspend operator fun invoke(animalType: String = "animals"): Result<Unit> {
        val query = animalType.lowercase().trim()

        if (query.isEmpty()) return Result.success(Unit)

        if (query !in allowedAnimals) {
            return Result.failure(IllegalArgumentException("Animal not registered"))
        }

        return try {
            repository.searchAnimals(query)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

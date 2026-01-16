package com.example.domain.useCase

import com.example.domain.model.Animal
import com.example.domain.repository.AnimalRepository

class GetAnimalImagesUseCase(
    private val repository: AnimalRepository
) {
    private val allowedAnimals = listOf("dog", "cat", "rabbit", "bird", "hamster")

    suspend operator fun invoke(animalType: String = "animals"): Result<List<Animal>> {
        val query = animalType.lowercase().trim()

        if (query.isEmpty()) return Result.success(emptyList())

        if (query !in allowedAnimals) {
            return Result.failure(IllegalArgumentException("Animal not registered"))
        }

        return try {
            val images = repository.searchAnimals(query)
            Result.success(images)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

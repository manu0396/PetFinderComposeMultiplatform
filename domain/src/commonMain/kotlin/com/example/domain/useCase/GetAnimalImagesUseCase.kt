package com.example.domain.useCase

import com.example.domain.repository.AnimalRepository

class GetAnimalImagesUseCase(
    private val repository: AnimalRepository
) {

    // Cambiamos el retorno a Result<Unit>
    suspend operator fun invoke(animalType: String = "animals"): Result<Unit> {
        val query = animalType.lowercase().trim()

        if (query.isEmpty()) return Result.success(Unit)

        return try {
            repository.searchAnimals(query)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

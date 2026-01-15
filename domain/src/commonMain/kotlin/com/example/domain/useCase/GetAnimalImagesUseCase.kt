package com.example.domain.useCase

import com.example.domain.model.AnimalImage
import com.example.domain.repository.AnimalRepository

class GetAnimalImagesUseCase(
    private val repository: AnimalRepository
) {
    private val allowedAnimals = listOf("dog", "cat", "rabbit", "bird", "hamster")

    suspend operator fun invoke(animalType: String): List<AnimalImage> {
        val query = if (animalType.lowercase() in allowedAnimals) {
            animalType.lowercase()
        } else {
            "dog"
        }

        return repository.getAnimalImages(query)
    }
}

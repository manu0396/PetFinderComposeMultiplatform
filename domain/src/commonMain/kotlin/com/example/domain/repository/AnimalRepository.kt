package com.example.domain.repository

import com.example.domain.model.AnimalImage

interface AnimalRepository {
    suspend fun getAnimalImages(animalType: String): List<AnimalImage>
}

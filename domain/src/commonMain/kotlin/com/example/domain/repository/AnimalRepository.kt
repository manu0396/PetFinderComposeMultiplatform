package com.example.domain.repository

import com.example.domain.model.Animal

interface AnimalRepository {
    suspend fun getAnimalImages(query: String): List<Animal>
}

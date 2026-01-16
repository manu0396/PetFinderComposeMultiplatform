package com.example.data.mapper

import com.example.data.db.FavoriteAnimal
import com.example.domain.model.Animal

fun FavoriteAnimal.toDomain(): Animal = Animal(
    id = this.id,
    imageUrl = this.imageUrl,
    description = this.description,
    name = this.name
)

package com.example.domain.model

data class Animal(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String? = null,
    val tags: List<String> = emptyList()
)

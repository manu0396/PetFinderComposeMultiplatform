package com.example.domain.model

enum class PetType(val apiValue: String) {
    DOG("dog"),
    CAT("cat"),
    BIRD("bird"),
    RABBIT("rabbit");

    fun displayName(): String = apiValue.replaceFirstChar { it.uppercase() }

    companion object {
        val DEFAULT = DOG
    }
}

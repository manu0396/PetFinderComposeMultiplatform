package com.example.petfinder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.example.data.remote

import com.example.data.remote.models.UnsplashResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class UnsplashRemoteDataSource(private val client: HttpClient) {

    suspend fun searchAnimalImages(query: String): UnsplashResponse {
        return client.get("https://api.unsplash.com/search/photos") {
            parameter("query", query)
            parameter("client_id", "TU_KEY")
        }.body<UnsplashResponse>()
    }
}

package com.example.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.data.mapper.toDomain
import com.example.data.remote.UnsplashRemoteDataSource
import com.example.domain.model.Animal
import com.example.domain.repository.AnimalRepository
import com.example.domain.util.AppLogger
import com.example.petfinder.data.db.AnimalDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AnimalRepositoryImpl(
    private val remoteDataSource: UnsplashRemoteDataSource,
    private val db: AnimalDb,
    private val logger: AppLogger,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : AnimalRepository {

    private val queries = db.animalDbQueries

    override val favorites: StateFlow<List<Animal>> = queries.selectAllFavorites()
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map { entities -> entities.map { it.toDomain() } }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchResults = MutableSharedFlow<List<Animal>>(replay = 1)
    override val searchResults: SharedFlow<List<Animal>> = _searchResults.asSharedFlow()

    override suspend fun searchAnimals(query: String) {
        scope.launch {
            try {
                logger.d("AnimalRepo", "Searching: $query")
                val response = remoteDataSource.searchPhotos(query)
                val domainAnimals = response.results.map { result ->
                    Animal(
                        id = result.id,
                        imageUrl = result.urls.regular,
                        description = result.description ?: "No description available",
                        name = result.name ?: "No name available"
                    )
                }
                _searchResults.emit(domainAnimals)
            } catch (e: Exception) {
                logger.e("AnimalRepo", "Search failed", e)
                _searchResults.emit(emptyList())
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun toggleFavorite(animal: Animal) {
        withContext(Dispatchers.IO) {
            val isFav = queries.selectFavoriteById(animal.id).executeAsOneOrNull() != null
            if (isFav) {
                queries.deleteFavorite(animal.id)
            } else {
                queries.insertFavorite(
                    id = animal.id,
                    name = animal.name,
                    description = animal.description,
                    imageUrl = animal.imageUrl,
                    createdAt = Clock.System.now().toEpochMilliseconds()
                )
            }
        }
    }

    override fun isFavorite(id: String): Flow<Boolean> {
        return queries.selectFavoriteById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it != null }
    }
}

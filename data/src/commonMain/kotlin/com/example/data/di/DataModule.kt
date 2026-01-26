package com.example.data.di

import com.example.data.repository.AnimalRepositoryImpl
import com.example.data.repository.FavoriteRepositoryImpl
import com.example.data.repository.FirebaseAuthRepository
import com.example.domain.repository.AnimalRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.FavoriteRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.util.AppLogger
import com.example.petfinder.data.db.AnimalDb
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

expect val dataPlatformModule: Module

val dataModule = module {
    single<AnimalRepository> { AnimalRepositoryImpl(get(), get<AnimalDb>(), get(), scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)) }
    single<AnimalDb> { AnimalDb(driver = get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(db = get<AnimalDb>(), logger = get<AppLogger>()) }
    single<FirebaseAuth> { Firebase.auth }
    single<AuthRepository> { FirebaseAuthRepository(get()) }
    includes(dataPlatformModule)
}
val useCaseModule = module {
    factory { GetAnimalImagesUseCase(get()) }
}

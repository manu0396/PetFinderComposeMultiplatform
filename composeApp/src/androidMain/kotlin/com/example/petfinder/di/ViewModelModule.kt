package com.example.petfinder.di

import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory {
        AnimalViewModel(
            getAnimalsUseCase = get(),
            repository = get(),
            logger = get(),
            getFavoritesUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
}

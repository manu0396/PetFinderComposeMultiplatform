package com.example.domain.module

import com.example.domain.useCase.GetAnimalImagesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAnimalImagesUseCase(get()) }
}

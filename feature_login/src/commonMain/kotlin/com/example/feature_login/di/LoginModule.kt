package com.example.feature_login.di

import com.example.feature_login.domain.LoginUseCase
import com.example.feature_login.presentation.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    factoryOf(::LoginUseCase)
    viewModelOf(::LoginViewModel)
}

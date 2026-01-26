package com.example.session.di

import com.example.session.SessionManager
import org.koin.dsl.module

val sessionModule = module {
    single { SessionManager(authRepository = get()) }
}

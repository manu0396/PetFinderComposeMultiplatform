// domain/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosArm64(); iosSimulatorArm64(); iosX64()

    sourceSets {
        commonMain.dependencies {
            // El dominio no debe tener dependencias de UI, 
            // pero sí puede tener Lifecycle si usas ViewModels en esta capa.
            api(libs.kmp.lifecycle.viewmodel)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.example.petfinder.domain"
    compileSdk = 35
}

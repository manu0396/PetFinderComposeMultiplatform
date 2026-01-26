// domain/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    iosX64()

    sourceSets {
        commonMain.dependencies {
            api(libs.kmp.lifecycle.viewmodel)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.example.petfinder.domain"
    compileSdk = 35
}

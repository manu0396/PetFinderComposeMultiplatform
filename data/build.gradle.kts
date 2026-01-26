// data/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinxSerialization)
}

sqldelight {
    databases {
        create("AnimalDb") {
            packageName.set("com.example.petfinder.data.db")
            generateAsync.set(false)
        }
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    iosArm64(); iosSimulatorArm64(); iosX64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(project(":data-core"))

            // Infraestructura
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            // SQLDelight Base
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
            // KTOR (Red)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.json)

            // KOIN (Inyección)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.coroutines)
            implementation(libs.firebase.auth.kmp)


        }

        androidMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.koin.android)
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
    }
}

android {
    namespace = "com.example.petfinder.data"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

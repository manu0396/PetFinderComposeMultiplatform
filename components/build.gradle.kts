plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform) // Habilita Compose Multiplatform
    alias(libs.plugins.composeCompiler)      // Necesario para Kotlin 2.x
}

kotlin {
    androidTarget {
        compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11) }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework { baseName = "components" }
    }

    sourceSets {
        commonMain.dependencies {
            // Usa el objeto 'compose' proporcionado por el plugin
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
    }
}

android {
    namespace = "com.example.components"
    compileSdk = 36

    // ESTO ES OBLIGATORIO para que el IDE reconozca Compose en Android
    buildFeatures {
        compose = true
    }
}

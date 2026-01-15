plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "featureHome"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))

            implementation(libs.kotlin.stdlib)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime)
        }
    }
}

android {
    namespace = "com.example.feature.home"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}

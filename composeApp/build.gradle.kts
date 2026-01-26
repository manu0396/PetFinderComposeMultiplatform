import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}
val unsplashKey = localProperties.getProperty("unsplash.api.key") ?: ""

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.cocoapods)
}

buildkonfig {
    packageName = "com.example.petfinder"
    defaultConfigs {
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "UNSPLASH_KEY", unsplashKey)
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

    cocoapods {
        summary = "PetFinder KMP App"
        homepage = "https://github.com/example/petfinder"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "ComposeApp"
            isStatic = true
            export(libs.kmp.lifecycle.viewmodel)
            export(libs.kmp.lifecycle.runtime)
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.compose.material.iconsExtended)
            implementation(projects.components)
            implementation(projects.domain)
            implementation(projects.data)
            implementation(projects.session)
            implementation(projects.featureLogin)
            implementation(projects.dataCore)
            implementation(libs.kmp.navigation)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }

        androidMain.dependencies {
            implementation(libs.material)
            implementation(compose.preview)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.example.petfinder"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.example.petfinder.resources"
}
configurations.configureEach {
    // 1. FIX: "ignoreCase = true" es CRÍTICO.
    // Las tareas de gradle a veces usan 'iosArm64' y otras 'IosArm64'.
    val isNativeTarget = name.contains("ios", ignoreCase = true) ||
        name.contains("apple", ignoreCase = true) ||
        name.contains("native", ignoreCase = true)

    if (isNativeTarget) {
        // Exclusión Nuclear: Prohibido terminantemente que entre nada de Android en iOS
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
        exclude(group = "androidx.lifecycle", module = "lifecycle-viewmodel-savedstate-android")
    }

    resolutionStrategy {
        force(libs.kotlinx.coroutines.core)
        force(libs.kotlinx.coroutines.android)
        }
    }

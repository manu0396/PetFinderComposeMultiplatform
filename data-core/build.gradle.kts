// data-core/build.gradle.kts
import java.util.Properties

// 1. Resolver el Unresolved reference leyendo la propiedad del raíz
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}
val unsplashKey = localProperties.getProperty("unsplash.api.key") ?: ""

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    // 2. CORRECCIÓN: Registrar el target de Android
    androidTarget()

    iosArm64()
    iosSimulatorArm64()
    iosX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

// 3. Configuración de BuildKonfig usando la variable resuelta
buildkonfig {
    packageName = "com.example.petfinder.datacore"
    defaultConfigs {
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "UNSPLASH_KEY", unsplashKey)
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "BASE_URL", "https://api.petfinder.com/v2/")
    }
}

android {
    namespace = "com.example.petfinder.datacore"
    compileSdk = 35
    // El bloque defaultConfigs de Android no es el mismo que el de BuildKonfig
    defaultConfig {
        minSdk = 24
    }
}

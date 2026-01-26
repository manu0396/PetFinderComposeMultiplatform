// components/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget()
    iosArm64(); iosSimulatorArm64(); iosX64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            api(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.compose.material.iconsExtended)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.material)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.appcompat)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.example.components.resources"
}

android {
    namespace = "com.example.petfinder.components"
    compileSdk = 35
    defaultConfig {
        val appName = providers.gradleProperty("projectAppName").get()
        manifestPlaceholders["projectName"] = appName
    }
}

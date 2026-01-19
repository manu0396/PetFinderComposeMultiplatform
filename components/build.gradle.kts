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
            implementation(compose.components.resources)
        }
    }
}

android {
    namespace = "com.example.petfinder.components"
    compileSdk = 35
    defaultConfig {
        val appName = providers.gradleProperty("projectAppName").get()
        manifestPlaceholders["projectName"] = appName
    }
}

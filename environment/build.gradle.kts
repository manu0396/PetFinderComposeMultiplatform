import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val isRelease = gradle.startParameter.taskNames.any { it.contains("Release", ignoreCase = true) }
val currentEnv = if (isRelease) "PROD" else "DEV"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}

buildkonfig {
    packageName = "com.example.environment"
    exposeObjectWithName = "BuildKonfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "FLAVOR", "SPAIN")
        buildConfigField(FieldSpec.Type.STRING, "ENV", currentEnv)
        val baseUrl = "https://api.unsplash.com/"
        buildConfigField(FieldSpec.Type.STRING, "BASE_URL", baseUrl)
        buildConfigField(FieldSpec.Type.STRING, "API_KEY", localProperties.getProperty("unsplash.api.key") ?: "")
    }
}

android {
    namespace = "com.example.petfinder.environment"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}

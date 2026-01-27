// build.gradle.kts (ROOT)
plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.cocoapods) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.googleServices) apply false
}
configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "org.jetbrains.kotlinx" && requested.name.startsWith("kotlinx-coroutines")) {
                useVersion("1.10.2")
                because("Force consistent coroutines version across all targets")
            }
        }
    }
}

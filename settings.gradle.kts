rootProject.name = "PetFinder"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
include(":composeApp")
include(":data")
include(":components")
include(":data-core")
include(":androidapp")
include(":domain")

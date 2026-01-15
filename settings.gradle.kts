rootProject.name = "PetFinder"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// 1. Configuración de repositorios para plugins
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// 2. Gestión de dependencias (El catálogo 'libs' se carga automáticamente)
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// 4. Inclusión de módulos de la arquitectura
include(":composeApp")
include(":data")
include(":components")
include(":data-core")
include(":androidapp")
include(":domain")
include(":feature-home")

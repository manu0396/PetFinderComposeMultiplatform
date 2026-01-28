val appName = providers.gradleProperty("projectAppName").getOrElse("PetFinder")
rootProject.name = appName

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/public")
    }
}
include(":composeApp")
include(":data")
include(":components")
include(":data-core")
include(":androidapp")
include(":domain")
include(":feature_login")
include(":session")

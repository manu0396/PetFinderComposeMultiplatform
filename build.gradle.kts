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

/**
 * Task: installGitHooks
 * Purpose: Deploys local repository safeguards.
 * Source: scripts/pre-push
 */
tasks.register<Copy>("installGitHooks") {
    description = "Installs custom git hooks to ensure MVI and Native build integrity."
    group = "verification"

    from(file("scripts/pre-push"))
    into(file("${rootProject.rootDir}/.git/hooks"))

    filePermissions {
        unix("rwxr-xr-x")
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        dependsOn(rootProject.tasks.named("installGitHooks"))
    }
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

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.cocoapods)
}

kotlin {
    androidTarget {
        @Suppress("DEPRECATION")
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "PetFinder KMP App"
        homepage = "https://github.com/example/petfinder"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        specRepos {
            url("https://github.com/CocoaPods/Specs.git")
            url("https://cdn.cocoapods.org")
        }
        framework {
            baseName = "ComposeApp"
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.example.petfinder.shared")
            isStatic = false
            export(libs.kmp.lifecycle.runtime)
            export(libs.kmp.lifecycle.viewmodel)
            export(project(":components"))
            binaryOption("bundleId", "com.example.petfinder.shared")
            linkerOpts("-lsqlite3", "-ObjC")
        }
        extraSpecAttributes["pod_target_xcconfig"] = "{ 'BUILD_LIBRARIES_FOR_DISTRIBUTION' => 'YES' }"

        pod("FirebaseCore") {
            version = "11.6.0"
            packageName = "firebase.core.native"
        }
        pod("FirebaseAuth") {
            version = "11.6.0"
            packageName = "firebase.auth.native"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            api(projects.components)
            implementation(projects.domain)
            implementation(projects.data)
            implementation(projects.dataCore)
            implementation(projects.session)
            implementation(projects.featureLogin)
            implementation(projects.environment)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kmp.navigation)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.firebase.auth.kmp)
            api(libs.kmp.lifecycle.runtime)
            api(libs.kmp.lifecycle.viewmodel)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "com.example.petfinder"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        val appName = project.findProperty("projectAppName")?.toString() ?: "PetFinder"
        manifestPlaceholders["projectName"] = appName
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.named<Delete>("clean") {
    delete(layout.buildDirectory)
}

tasks.register<Copy>("installGitHooks") {
    description = "Installs git hooks from the .github/hooks directory."
    group = "verification"
    from(file(".github/hooks/pre-push"))
    into(file("${rootProject.rootDir}/.git/hooks"))
    filePermissions {
        unix("rwxr-xr-x")
    }
}

configurations.all {
    resolutionStrategy {
        dependencySubstitution {
            substitute(module("com.google.firebase:firebase-auth-ktx"))
                .using(module("com.google.firebase:firebase-auth:${libs.versions.firebase.auth.get()}"))
            substitute(module("com.google.firebase:firebase-common-ktx"))
                .using(module("com.google.firebase:firebase-common:22.1.1"))
        }
        exclude(group = "com.google.firebase", module = "firebase-auth-ktx")
        exclude(group = "com.google.firebase", module = "firebase-common-ktx")
    }
}

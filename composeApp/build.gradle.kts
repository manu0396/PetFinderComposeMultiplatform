plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.buildkonfig)
}
buildkonfig {
    packageName = "com.example.petfinder"
    defaultConfigs {
        // You can add your keys here or pull from local.properties
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "UNSPLASH_KEY", "your_key_here")
    }
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

        pod("FirebaseAuth") { version = "10.29.0" }
        pod("FirebaseCore") { version = "10.29.0" }

        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts("-lsqlite3", "-ObjC")
            export(libs.kmp.lifecycle.runtime)
            export(libs.kmp.lifecycle.viewmodel)
            export(libs.firebase.auth.kmp)
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

            implementation(projects.components)
            implementation(projects.domain)
            implementation(projects.data)
            implementation(projects.dataCore)
            implementation(projects.session)
            implementation(projects.featureLogin)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kmp.navigation)
            implementation(libs.kotlinx.coroutines.core)

            api(libs.kmp.lifecycle.runtime)
            api(libs.kmp.lifecycle.viewmodel)
            api(libs.firebase.auth.kmp)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
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
configurations.all {
    resolutionStrategy {
        dependencySubstitution {
            substitute(module("com.google.firebase:firebase-auth-ktx"))
                .using(module("com.google.firebase:firebase-auth:${libs.versions.firebase.auth.get()}"))

            substitute(module("com.google.firebase:firebase-common-ktx"))
                .using(module("com.google.firebase:firebase-common:22.1.1")) // Stable fallback
        }

        // Prevent the -ktx modules from being resolved at all
        exclude(group = "com.google.firebase", module = "firebase-auth-ktx")
        exclude(group = "com.google.firebase", module = "firebase-common-ktx")
    }
}

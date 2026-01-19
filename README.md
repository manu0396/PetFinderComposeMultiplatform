# Kotlin Multiplatform Project (Android & iOS)

A Kotlin Multiplatform (KMP) project implementing **Clean Architecture**. This repository shares business logic, data mapping, and UI components across Android and iOS while maintaining native performance.

## 🏗 Project Architecture

The project is structured to ensure a strict separation of concerns and modularity:

* **[`/composeApp`](./composeApp/src):** Shared module containing the core logic and UI.
    * **[`commonMain`](./composeApp/src/commonMain/kotlin):** Shared Kotlin code (Business logic, Repositories, ViewModels, and Compose UI).
    * **[`androidMain`](./composeApp/src/androidMain/kotlin):** Android-specific implementations and initialization.
    * **[`iosMain`](./composeApp/src/iosMain/kotlin):** iOS-specific integrations (e.g., native platform APIs).
* **[`/iosApp`](./iosApp/iosApp):** Native iOS entry point. This is where the SwiftUI wrapper resides to launch the shared KMP application.

## 🛠 Tech Stack

* **Language:** Kotlin 2.x and Coroutines for asynchronous operations.
* [cite_start]**UI:** Jetpack Compose / Compose Multiplatform[cite: 14, 34].
* **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture boundaries.
* [cite_start]**Dependency Injection:** Dagger/Hilt or Koin[cite: 14, 34].
* **Build System:** Gradle KTS with Version Catalog (`libs.versions.toml`).

## 🚀 Build and Run

### 🤖 Android
To build and run the development version of the Android app, use the run configuration in your IDE or the terminal:
- **macOS/Linux:**
  ```shell
  ./gradlew :composeApp:assembleDebug

# Kotlin Multiplatform Project (Android & iOS)

[cite_start]A robust Kotlin Multiplatform (KMP) implementation following strict **Clean Architecture** principles. This project is designed for scalability, leveraging shared business logic while maintaining high-performance native entry points for both Android and iOS.

## 🏗 Project Architecture

[cite_start]The project is organized into modular layers to ensure a clear separation of concerns, consistent with banking-grade architecture standards[cite: 8, 34]:

* **[`/composeApp`](./composeApp/src):** Shared UI and core presentation logic.
    * [cite_start]**[`commonMain`](./composeApp/src/commonMain/kotlin):** Houses the Domain and Data layers, utilizing shared repositories and DTOs[cite: 14].
    * [cite_start]**[`iosMain`](./composeApp/src/iosMain/kotlin):** iOS-specific implementations, including integration with Apple-native APIs[cite: 14].
    * **[`androidMain`](./composeApp/src/androidMain/kotlin):** Android-specific configurations and platform-dependent logic.
* **[`/iosApp`](./iosApp/iosApp):** Native iOS entry point. [cite_start]This directory contains the Xcode project and SwiftUI code[cite: 18].

## 🛠 Tech Stack

* [cite_start]**Language:** Kotlin 2.x and Coroutines for asynchronous programming[cite: 14, 15].
* [cite_start]**UI Framework:** Jetpack Compose and Compose Multiplatform[cite: 14, 18, 34].
* [cite_start]**Networking:** Ktor and Retrofit for resilient API consumption[cite: 14, 19].
* [cite_start]**Dependency Injection:** Support for Koin and Dagger-Hilt[cite: 14, 19, 34].
* [cite_start]**Architecture:** MVVM with strict adherence to SOLID principles[cite: 14, 34].

## 🚀 Build and Run

### Android Application
To build and run the development version of the Android app, use your IDE's run configuration or the terminal:

* **macOS/Linux:**
    ```shell
    ./gradlew :composeApp:assembleDebug
    ```
* **Windows:**
    ```shell
    .\gradlew.bat :composeApp:assembleDebug
    ```

### iOS Application
1.  Open the `/iosApp` directory in **Xcode**.
2.  Select your target device or simulator.
3.  Build and run using `Cmd + R`.

---

## 📈 Quality Standards
This project follows professional development workflows:
* [cite_start]**Static Analysis:** Configured for SonarQube[cite: 15, 35].
* [cite_start]**CI/CD:** Compatible with Jenkins and Bitbucket Pipelines[cite: 15, 42].
* [cite_start]**Clean Code:** Focused on readability, maintainability, and modularity[cite: 34, 58].

---
*Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).*

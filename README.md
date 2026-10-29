# SimpleEnergy HMI

An Android-based **Human-Machine Interface (HMI)** application built for Vehicle dashboards using modern Android development practices, **Jetpack Compose**, **Kotlin Coroutines & Flow**, and **MVVM Architecture**.

---
<img width="2856" height="1280" alt="Screenshot_20261029_183453" src="https://github.com/user-attachments/assets/370a839a-f9f2-476d-96ec-c81e6e6a02d6" />
<img width="2856" height="1280" alt="Screenshot_20261029_183516" src="https://github.com/user-attachments/assets/4e30df1c-28f6-44fb-90aa-d3798a93600c" />
---

## 🚀 Features

- Landscape-oriented automotive dashboard.
- Live simulated vehicle speed in km/h.
- Motor ON/OFF status.
- Kill-switch Active/Inactive status.
- Periodic vehicle-state updates using Kotlin Flow.
- Reactive UI state management using StateFlow.
- Invalid-speed validation and stale-data detection.
- Unit tests for state updates and edge cases.


---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Reactive Stream**: `StateFlow` and `SharedFlow` with `collectAsStateWithLifecycle`
- **Asynchronous Processing**: Kotlin Coroutines
- **Unit Testing**: JUnit 4, Kotlinx Coroutines Test, AndroidX Core Testing

---

## 📂 Project Structure

```
SimpleEnergyHMI/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/com/example/simpleenergyhmi/
│   │   │       ├── data/           # Repository & Data Sources
│   │   │       ├── ui/             # Jetpack Compose Screens & Theme
│   │   │       ├── uistate/        # Vehicle UI State Data Classes
│   │   │       └── viewmodel/      # ViewModel & ViewModelFactory
│   │   └── test/                   # Unit Tests & Coroutine Test Rules
│   └── build.gradle.kts
├── gradle/                         # Version Catalog (libs.versions.toml)
├── build.gradle.kts
├── settings.gradle.kts
└── README.md                       # Project documentation
```

---

## Architecture

- The application separates simulated data, state management, and UI rendering.

- **Data model:** VehicleState represents a vehicle reading.
- **Data provider:** VehicleDataProvider emits simulated vehicle states.
- **Repository interface:** VehicleRepository defines the contract for observing vehicle data.
- **Repository implementation:** VehicleRepositoryImpl obtains data from the provider.
- **ViewModel:** VehicleViewModel validates readings, manages UI state, and detects stale data.
- **UI state:** VehicleUiState represents the dashboard state, including unavailable, stale, and error conditions.
- **UI:** HomeScreen renders speed and vehicle-status indicators using Jetpack Compose.
- **Factory:** VehicleViewModelFactory provides the repository dependency to the ViewModel.

---

## Build and Run

To build and run the application, follow these steps:
- Clone or download this repository.
- Open the project in Android Studio.
- Allow Gradle sync to finish.
- Run the app on an Android emulator configured for landscape orientation.
- Confirm that the dashboard displays the simulated scenarios.

---

## Assumptions and Limitations

- Vehicle readings are simulated; no physical vehicle or hardware connection is used.
- The five required scenarios cycle repeatedly for demonstration.
- The stale-data timeout is configured to three seconds.
- This is a demonstration dashboard and is not intended for production vehicle control or safety-critical use.

---

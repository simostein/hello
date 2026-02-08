# E-Tice Lessons Android App

An Android application that allows users to download educational documents from the e-tice.com platform.

## Features

- 📚 Browse and download explicit lessons documents
- 🌐 Bilingual support (Arabic RTL / French LTR)
- 📱 Modern Material Design 3 UI
- ⬇️ Direct document download to device
- 🔄 Dynamic dropdown dependencies

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Clean Architecture
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines

## API Integration

The app integrates with the e-tice.com API:
- Endpoint: `https://www.e-tice.com/lecons-explicites/fetch.php`
- Parameters: level, phase, week, subject

## Building the App

1. Open the project in Android Studio
2. Sync Gradle dependencies
3. Run on an emulator or physical device

## Requirements

- Android SDK 24+
- Android Studio Hedgehog or later
- Internet permission for API calls

## License

This is an educational project.

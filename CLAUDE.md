# CLAUDE.md

This file provides guidance to Claude Code when working with this Android project.

## Project Overview

**browser-launcher** is an Android application written in Kotlin that handles browser launching functionality.

- **Package**: `com.browserlauncher`
- **Min SDK**: 23 (Android 6.0)
- **Target SDK**: 34 (Android 14)
- **Language**: Kotlin 2.1.0
- **Build System**: Gradle 9.2 with Kotlin DSL

## Project Structure

```
browser-launcher/
├── app/
│   ├── build.gradle.kts      # App-level build config
│   ├── proguard-rules.pro    # ProGuard rules for release builds
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/browserlauncher/
│       │   └── MainActivity.kt
│       └── res/
│           ├── layout/       # XML layouts
│           ├── values/       # Strings, colors, themes
│           └── drawable/     # Vector drawables, icons
├── build.gradle.kts          # Root build config (plugins)
├── settings.gradle.kts       # Project settings
└── gradle.properties         # Gradle/Android properties
```

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Clean build
./gradlew clean

# Run lint checks
./gradlew lint

# Run unit tests
./gradlew test

# Install debug APK on connected device
./gradlew installDebug
```

## Dependencies

Core dependencies defined in `app/build.gradle.kts`:

- **androidx.core:core-ktx:1.12.0** - Kotlin extensions for Android core
- **androidx.appcompat:appcompat:1.6.1** - Backward-compatible UI components
- **com.google.android.material:material:1.11.0** - Material Design components
- **org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3** - Kotlin coroutines for async operations

## Development Environment

This project is configured for **Termux on Android** development:
- Uses custom AAPT2 path (`android.aapt2FromMavenOverride`) in gradle.properties
- JVM target: Java 17
- Gradle parallel builds and caching enabled

## Code Conventions

### Kotlin Style
- Follow official Kotlin coding conventions
- Use `camelCase` for functions and variables
- Use `PascalCase` for classes and interfaces
- Prefer expression bodies for simple functions
- Use data classes for simple data holders

### Android Patterns
- Activities extend `AppCompatActivity`
- Use `ViewBinding` or `DataBinding` for view references (when added)
- Handle configuration changes properly
- Follow Material Design guidelines

### Resource Naming
- Layouts: `activity_*.xml`, `fragment_*.xml`, `item_*.xml`
- Strings: Use descriptive keys in snake_case
- Colors: Define in `colors.xml`, reference by name
- Drawables: Use vector drawables when possible

## Security Considerations

- Never hardcode API keys or secrets
- Use Android Keystore for sensitive data
- Validate all intent data from external sources
- Request only necessary permissions
- Use HTTPS for network requests

## Testing

When adding tests:
- Unit tests go in `app/src/test/`
- Instrumentation tests go in `app/src/androidTest/`
- Use JUnit 4/5 for unit tests
- Use Espresso for UI tests

## Common Tasks

### Adding a New Activity
1. Create Kotlin class in `app/src/main/java/com/browserlauncher/`
2. Add layout XML in `app/src/main/res/layout/`
3. Register in `AndroidManifest.xml`

### Adding a New Dependency
1. Add to `dependencies` block in `app/build.gradle.kts`
2. Sync Gradle (`./gradlew --refresh-dependencies`)

### Updating SDK Versions
- Modify `compileSdk`, `minSdk`, `targetSdk` in `app/build.gradle.kts`
- Update Kotlin/AGP versions in root `build.gradle.kts`

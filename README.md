# Nexus Testing

Small Android Java app for smoke-testing `nexus-runtime-release.aar`.

## What it does

- Loads `app/libs/nexus-runtime-release.aar` as a local dependency.
- Initializes `com.androdevsatyam.nexus.Nexus` with Android context.
- Runs sample calculations for plans 714, 717, and 760.
- Prints premium, maturity, surrender, loan, and status values on screen and in logcat under `NexusTesting`.

## Build

```powershell
.\gradlew.bat :app:assembleDebug
```

## Run

Open this folder in Android Studio, install the `app` configuration, and check the screen or logcat.

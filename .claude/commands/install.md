# Install APK via Termux

Build and install the debug APK using Termux tools.

## Steps
1. Build the debug APK with `gradle assembleDebug`
2. Install using Termux: `termux-open app/build/outputs/apk/debug/app-debug.apk`
3. This will open the APK installer on the device
4. Report build and installation status

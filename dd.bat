@echo off
title 🚀 Android Release Builder - Kiri Project
color 0A

:: ==============================
::   ANDROID BUILD AUTOMATION
:: ==============================

echo.
echo ==========================================
echo        🚀 KIRI RELEASE BUILDER
echo ==========================================
echo.

:: Check if gradlew exists
if not exist gradlew.bat (
    color 0C
    echo ❌ ERROR: gradlew.bat not found!
    pause
    exit /b
)

:: Step 1 - Clean project
echo 🧹 Cleaning project...
call gradlew clean
if %errorlevel% neq 0 (
    color 0C
    echo ❌ Clean failed!
    pause
    exit /b
)

:: Step 2 - Bundle Release
color 0B
echo.
echo 📦 Building App Bundle (AAB)...
echo ------------------------------------------
call gradlew bundleRelease
if %errorlevel% neq 0 (
    color 0C
    echo ❌ Bundle build failed!
    pause
    exit /b
)

echo ✅ AAB Generated Successfully!

:: Step 3 - Build APK
color 0E
echo.
echo 📱 Building Release APK...
echo ------------------------------------------
call gradlew assembleRelease
if %errorlevel% neq 0 (
    color 0C
    echo ❌ APK build failed!
    pause
    exit /b
)

echo ✅ APK Generated Successfully!

:: Step 4 - Show output paths
color 0A
echo.
echo ==========================================
echo 🎉 BUILD COMPLETED SUCCESSFULLY!
echo ==========================================
echo.
echo 📦 AAB Location:
echo app\build\outputs\bundle\release\
echo.
echo 📱 APK Location:
echo app\build\outputs\apk\release\
echo.

:: Optional - open folder
choice /m "Open output folder?"
if %errorlevel%==1 (
    explorer app\build\outputs\
)

echo.
echo 🔥 Done! Press any key to exit...
pause >nul
exit
@echo off
title 🚀 Kiri Release Builder
color 0A

:: Try to locate gradlew automatically
set PROJECT_DIR=%~dp0

cd /d "%PROJECT_DIR%"

if not exist gradlew.bat (
    echo 🔍 Searching for gradlew.bat...

    for /r %%i in (gradlew.bat) do (
        set PROJECT_DIR=%%~dpi
        goto found
    )

    echo ❌ ERROR: gradlew.bat not found anywhere!
    pause
    exit /b
)

:found
cd /d "%PROJECT_DIR%"

echo.
echo ==========================================
echo        🚀 KIRI RELEASE BUILDER
echo ==========================================
echo.

echo 📁 Using project path:
echo %PROJECT_DIR%
echo.

:: Clean
echo 🧹 Cleaning project...
call gradlew clean

:: Bundle
echo 📦 Building AAB...
call gradlew bundleRelease

:: APK
echo 📱 Building APK...
call gradlew assembleRelease

echo.
echo ✅ DONE!
pause
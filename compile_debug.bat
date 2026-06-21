# Let's create the polished `compile_debug.bat` file based on the user's setup and requirements.
# It uses standard Windows CMD syntax with the proper -cp bin flags to fix the compilation order issue.

batch_content = """@echo off
REM Script untuk compile AirForce Game di Windows (Debug Mode)
REM Perbaikan: Menggunakan flag -cp bin agar compiler menemukan dependensi antar paket

setlocal enabledelayedexpansion

echo ===========================================
echo   Checking environment and dependencies...
echo ===========================================

REM 1. Cek JDK
javac -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] JDK tidak ditemukan! Pastikan Java Development Kit sudah terinstal dan masuk ke PATH.
    goto CompileFailed
)
echo OK - JDK Found

REM 2. Cek Struktur Folder
if not exist src\\airforce\\utils\\Settings.java (
    echo [ERROR] Folder src\\airforce\\utils\\Settings.java tidak ditemukan!
    goto CompileFailed
)
echo OK - All source files found

REM 3. Bersihkan Folder Bin Lama
if exist bin (
    echo Cleaning old bin folder...
    rmdir /s /q bin
)
mkdir bin
echo OK - bin folder ready
echo.

echo ===========================================
echo   === Step 1: Compiling Settings.java ===
echo ===========================================
javac -encoding UTF-8 -d bin src\\airforce\\utils\\Settings.java
if errorlevel 1 (
    echo [ERROR] Gagal mengompilasi Settings.java
    goto CompileFailed
)
echo OK - Settings.java compiled
echo.

echo ===========================================
echo   === Step 2: Compiling Entity classes ===
echo ===========================================
REM Menambahkan -cp bin agar kelas entity bisa membaca paket airforce.utils
javac -encoding UTF-8 -cp bin -d bin ^
    src\\airforce\\entities\\BulletEnemy.java ^
    src\\airforce\\entities\BulletPlayer.java ^
    src\\airforce\\entities\\EnemyBlue.java ^
    src\\airforce\\entities\\EnemyGreen.java ^
    src\\airforce\\entities\\PlayerPlane.java
if errorlevel 1 (
    echo [ERROR] Gagal mengompilasi Entity classes
    goto CompileFailed
)
echo OK - Entity classes compiled
echo.

echo ===========================================
echo   === Step 3: Compiling Core classes ===
echo ===========================================
REM Menambahkan -cp bin agar kelas core bisa membaca paket airforce.utils dan airforce.entities
javac -encoding UTF-8 -cp bin -d bin ^
    src\\airforce\\core\\GameManager.java ^
    src\\airforce\\core\\Main.java
if errorlevel 1 (
    echo [ERROR] Gagal mengompilasi Core classes
    goto CompileFailed
)
echo OK - Core classes compiled
echo.

REM Check hasil akhir
if exist bin\\airforce\\core\\Main.class (
    echo ===========================================
    echo   === COMPILE SUCCESS ===
    echo ===========================================
    echo.
    echo Run game dengan perintah berikut:
    echo   java -cp bin airforce.core.Main
    echo.
    goto End
) else (
    goto CompileFailed
)

:CompileFailed
echo.
echo ===========================================
echo   === COMPILE FAILED ===
echo ===========================================
echo.
echo Tips Perbaikan:
echo 1. Pastikan encoding semua file .java sudah UTF-8 (bukan UTF-16 / UTF-8 dengan BOM).
echo 2. Cek apakah ada file .java yang typo atau salah penulisan nama variabel/kelas.
echo.
pause
exit /b 1

:End
pause
"""

with open("compile_debug.bat", "w", encoding="utf-8") as f:
    f.write(batch_content)

print("File compile_debug.bat successfully generated.")
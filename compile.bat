@echo off
REM Script untuk compile AirForce Game di Windows
REM Fix: Tambah -cp bin setelah Settings agar compiler tahu classpath

setlocal enabledelayedexpansion

echo === Compiling AirForce Game ===
echo.

REM Hapus bin lama
if exist bin (
    echo Cleaning old bin folder...
    rmdir /s /q bin
)
mkdir bin

REM Compile Settings dulu (tanpa -cp karena tidak ada dependency)
echo Compiling Settings.java...
javac -encoding UTF-8 -d bin src\airforce\utils\Settings.java
if errorlevel 1 goto CompileFailed

REM Compile Entity classes (perlu -cp bin karena import Settings)
echo Compiling Entity classes...
javac -encoding UTF-8 -cp bin -d bin ^
    src\airforce\entities\BulletEnemy.java ^
    src\airforce\entities\BulletPlayer.java ^
    src\airforce\entities\EnemyBlue.java ^
    src\airforce\entities\EnemyGreen.java ^
    src\airforce\entities\PlayerPlane.java
if errorlevel 1 goto CompileFailed

REM Compile Core classes (perlu -cp bin karena import entities dan utils)
echo Compiling Core classes...
javac -encoding UTF-8 -cp bin -d bin ^
    src\airforce\core\GameManager.java ^
    src\airforce\core\Main.java
if errorlevel 1 goto CompileFailed

REM Check hasil
echo.
if exist bin\airforce\core\Main.class (
    echo === COMPILE SUCCESS ===
    echo.
    echo Run game dengan:
    echo   java -cp bin airforce.core.Main
    echo.
    goto End
) else (
    goto CompileFailed
)

:CompileFailed
echo.
echo === COMPILE FAILED ===
echo.
echo Troubleshooting tips:
echo - Pastikan JDK terinstall: java -version
echo - Cek struktur folder: src\airforce\{core,entities,utils,ui}
echo - Pastikan semua file .java ada di folder yang benar
echo.
pause
exit /b 1

:End
pause
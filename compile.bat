#!/bin/bash

# Script untuk compile AirForce Game
# Gunakan: ./compile.sh

echo "=== Compiling AirForce Game ==="

# Hapus bin lama
rm -rf bin
mkdir -p bin

# Compile dengan urutan yang benar
echo "Compiling Settings.java..."
javac -d bin src/airforce/utils/Settings.java

echo "Compiling Entity classes..."
javac -d bin src/airforce/entities/*.java

echo "Compiling Core classes..."
javac -d bin src/airforce/core/*.java

# Check hasil
if [ -f "bin/airforce/core/Main.class" ]; then
    echo ""
    echo "=== COMPILE SUCCESS ==="
    echo "Run game dengan: java -cp bin airforce.core.Main"
else
    echo ""
    echo "=== COMPILE FAILED ==="
    exit 1
fi
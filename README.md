# AirForce Game

Mini game 2D pesawat tembak-menembak (vertical scrolling shooter) menggunakan Java + Java2D.

## Setup

### Prasyarat
- **JDK 11 atau lebih tinggi** (OpenJDK atau Oracle JDK)
- **VS Code** dengan extension:
  - Extension Pack for Java (Microsoft)
  - atau minimal: Language Support for Java (Red Hat)

### Instalasi
1. Clone/download repo ini
2. Buka folder `AirForceGame` di VS Code
3. VS Code akan otomatis detect Java project

## Compile & Run

### Cara 1: VS Code (Recommended)
1. Buka `src/airforce/core/Main.java`
2. Klik tombol **Run** (▶) di atas method `main()`
3. atau gunakan F5 (Debug) / Ctrl+F5 (Run)

### Cara 2: Terminal (Linux/Mac)
```bash
./compile.sh
java -cp bin airforce.core.Main
```

### Cara 3: Terminal (Windows)
```cmd
compile.bat
java -cp bin airforce.core.Main
```

### Cara 4: Manual Compile
```bash
# Compile semua file
javac -d bin src/airforce/utils/Settings.java
javac -d bin src/airforce/entities/*.java
javac -d bin src/airforce/core/*.java

# Run
java -cp bin airforce.core.Main
```

## Controls

- **Arrow Keys** — Gerak pesawat (atas/bawah/kiri/kanan)
- **Auto-fire** — Otomatis menembak (tidak perlu tombol)
- **ENTER** — Mulai game / Kembali ke menu / Restart

## Gameplay

- Musuh muncul dari atas layar
- Tembak musuh untuk mendapatkan skor
- Hindari tembakan musuh untuk pertahankan HP
- Game Over saat HP habis
- Musuh Biru: HP 20, skor +10
- Musuh Hijau: HP 40, skor +20

## File Structure

```
AirForceGame/
├── src/
│   └── airforce/
│       ├── core/
│       │   ├── Main.java           (entry point)
│       │   └── GameManager.java     (game loop & logic)
│       ├── entities/
│       │   ├── PlayerPlane.java     (pesawat pemain)
│       │   ├── EnemyBlue.java       (musuh biru)
│       │   ├── EnemyGreen.java      (musuh hijau)
│       │   ├── BulletPlayer.java    (peluru pemain)
│       │   └── BulletEnemy.java     (peluru musuh)
│       ├── ui/
│       │   ├── MainMenu.java        (TODO: Anggota 3)
│       │   ├── HUD.java             (TODO: Anggota 3)
│       │   └── GameOverScreen.java  (TODO: Anggota 3)
│       └── utils/
│           └── Settings.java         (konstanta global)
├── bin/                             (compiled .class files)
├── .vscode/
│   ├── settings.json               (VS Code config)
│   └── launch.json                 (Run config)
├── compile.sh                      (Linux/Mac compile script)
├── compile.bat                     (Windows compile script)
└── README.md                       (file ini)
```

## Pembagian Tugas

- **Anggota 1** (Laptop Kuat): `Main.java`, `GameManager.java` ✅
- **Anggota 2** (Laptop Kuat): Entity classes (Player, Enemy, Bullet) ✅
- **Anggota 3** (Laptop Kuat): UI classes (MainMenu, HUD, GameOverScreen) — optional
- **Anggota 4** (Laptop Lemah): Asset hunting (sprite, audio)
- **Anggota 5** (Laptop Lemah): `Settings.java`, folder setup, dokumentasi

## Troubleshooting

### Error: "cannot find symbol: class EnemyBlue"
- Pastikan VS Code Language Support for Java sudah installed
- Buka Command Palette (Ctrl+Shift+P) → "Java: Clean Language Server Workspace"
- Reload VS Code (Ctrl+Shift+P → "Reload Window")
- Jika masih error, coba compile manual: `./compile.sh` (Linux/Mac) atau `compile.bat` (Windows)

### Game tidak bisa di-run
- Pastikan JDK sudah terinstall: `java -version`
- Pastikan JAVA_HOME sudah set di environment variable
- Coba compile ulang dengan script: `./compile.sh`

### Performa lag/berat
- Tutup aplikasi lain yang berat
- Jika laptop Pentium/Celeron, gunakan terminal langsung (bukan VS Code dengan debugger)

## License

Proyek akademik untuk tugas Grafika Komputer.
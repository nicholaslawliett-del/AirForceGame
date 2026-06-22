package airforce.core;

import airforce.entities.BulletEnemy;
import airforce.entities.BulletPlayer;
import airforce.entities.EnemyBlue;
import airforce.entities.EnemyGreen;
import airforce.entities.PlayerPlane;
import airforce.utils.Settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Mengatur game loop, state management, input handling, dan collision
 * detection.
 * Anggota 1 (punya laptop kuat) handle file ini.
 * 
 * Tanggung jawab:
 * 1. Update semua entity (player, musuh, peluru)
 * 2. Collision detection: peluru vs musuh, musuh vs player
 * 3. Manage game state (Menu, Playing, GameOver)
 * 4. Input keyboard (Arrow Keys, ENTER)
 * 5. Render semua dengan antialiasing aktif
 */
public class GameManager extends JPanel implements Runnable, KeyListener {

    public enum GameState {
        MENU,
        PLAYING,
        GAME_OVER
    }

    private Thread gameThread;
    private boolean isRunning;
    private GameState currentState;

    // Game entities
    private PlayerPlane playerPlane;
    private List<EnemyBlue> enemiesBlue;
    private List<EnemyGreen> enemiesGreen;

    // Game management
    private int score;
    private long lastSpawnTime;
    private static final int SPAWN_INTERVAL = 1000; // ms

    // Input flags
    private boolean[] keys = new boolean[256];

    public GameManager() {
        setPreferredSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        currentState = GameState.MENU;
        score = 0;
        lastSpawnTime = System.currentTimeMillis();
        enemiesBlue = new ArrayList<>();
        enemiesGreen = new ArrayList<>();
    }

    public void startGame() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / Settings.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        switch (currentState) {
            case MENU:
                updateMenu();
                break;
            case PLAYING:
                updateGameplay();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateMenu() {
        // TODO (Anggota 3): update logic tombol Start/Exit di MainMenu jika perlu
        // Untuk sekarang: tekan ENTER untuk mulai
        if (keys[KeyEvent.VK_ENTER]) {
            startNewGame();
            keys[KeyEvent.VK_ENTER] = false; // Reset key
        }
    }

    private void updateGameplay() {
        // Update input player
        playerPlane.setMovingLeft(keys[KeyEvent.VK_LEFT]);
        playerPlane.setMovingRight(keys[KeyEvent.VK_RIGHT]);
        playerPlane.setMovingUp(keys[KeyEvent.VK_UP]);
        playerPlane.setMovingDown(keys[KeyEvent.VK_DOWN]);

        // Update player
        playerPlane.update();

        // Spawn musuh baru dengan interval
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime >= SPAWN_INTERVAL) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }

        // Update musuh biru
        for (int i = enemiesBlue.size() - 1; i >= 0; i--) {
            EnemyBlue enemy = enemiesBlue.get(i);
            enemy.update();

            // Hapus musuh yang sudah keluar layar
            if (enemy.isOutOfBounds()) {
                enemiesBlue.remove(i);
            }
        }

        // Update musuh hijau
        for (EnemyGreen enemy : enemiesGreen) {
            enemy.update(playerPlane.getPosX() + Settings.PLAYER_SIZE / 2.0);
        }

        // ============ COLLISION DETECTION ============

        // 1. Cek tumbukan peluru pemain dengan musuh biru
        List<BulletPlayer> playerBullets = playerPlane.getBullets();
        for (int i = playerBullets.size() - 1; i >= 0; i--) {
            BulletPlayer bullet = playerBullets.get(i);
            boolean bulletHit = false;

            for (int j = enemiesBlue.size() - 1; j >= 0; j--) {
                EnemyBlue enemy = enemiesBlue.get(j);
                if (enemy.checkCollision(
                        bullet.getPosX(), bullet.getPosY(),
                        bullet.getWidth(), bullet.getHeight())) {
                    // Tumbukan! Musuh ambil damage
                    enemy.takeDamage(Settings.BULLET_PLAYER_DAMAGE);
                    bulletHit = true;

                    if (!enemy.isAlive()) {
                        score += 10; // Bonus skor musuh biru
                    }
                    break;
                }
            }

            if (bulletHit) {
                playerBullets.remove(i);
            }
        }

        // 2. Cek tumbukan peluru pemain dengan musuh hijau
        for (int i = playerBullets.size() - 1; i >= 0; i--) {
            BulletPlayer bullet = playerBullets.get(i);
            boolean bulletHit = false;

            for (EnemyGreen enemy : enemiesGreen) {
                if (enemy.checkCollision(
                        bullet.getPosX(), bullet.getPosY(),
                        bullet.getWidth(), bullet.getHeight())) {
                    // Tumbukan! Musuh ambil damage
                    enemy.takeDamage(Settings.BULLET_PLAYER_DAMAGE);
                    bulletHit = true;

                    if (!enemy.isAlive()) {
                        score += 20; // Bonus skor musuh hijau (lebih tinggi)
                    }
                    break;
                }
            }

            if (bulletHit) {
                playerBullets.remove(i);
            }
        }

        // 3. Cek tumbukan peluru musuh biru dengan player
        for (EnemyBlue enemy : enemiesBlue) {
            List<BulletEnemy> enemyBullets = enemy.getBullets();
            for (int i = enemyBullets.size() - 1; i >= 0; i--) {
                BulletEnemy bullet = enemyBullets.get(i);
                if (playerPlane.checkCollision(
                        bullet.getPosX(), bullet.getPosY(),
                        bullet.getWidth(), bullet.getHeight())) {
                    // Tumbukan! Player ambil damage
                    playerPlane.takeDamage(Settings.BULLET_ENEMY_DAMAGE);
                    enemyBullets.remove(i);
                }
            }
        }

        // 4. Cek tumbukan peluru musuh hijau dengan player
        for (EnemyGreen enemy : enemiesGreen) {
            List<BulletEnemy> enemyBullets = enemy.getBullets();
            for (int i = enemyBullets.size() - 1; i >= 0; i--) {
                BulletEnemy bullet = enemyBullets.get(i);
                if (playerPlane.checkCollision(
                        bullet.getPosX(), bullet.getPosY(),
                        bullet.getWidth(), bullet.getHeight())) {
                    // Tumbukan! Player ambil damage
                    playerPlane.takeDamage(Settings.BULLET_ENEMY_DAMAGE);
                    enemyBullets.remove(i);
                }
            }
        }

        // 5. Cek tumbukan langsung musuh dengan player
        for (EnemyBlue enemy : enemiesBlue) {
            if (enemy.checkCollision(
                    playerPlane.getPosX(), playerPlane.getPosY(),
                    Settings.PLAYER_SIZE, Settings.PLAYER_SIZE)) {
                // Tumbukan! Player ambil damage besar
                playerPlane.takeDamage(10);
            }
        }

        for (EnemyGreen enemy : enemiesGreen) {
            if (enemy.checkCollision(
                    playerPlane.getPosX(), playerPlane.getPosY(),
                    Settings.PLAYER_SIZE, Settings.PLAYER_SIZE)) {
                // Tumbukan! Player ambil damage besar
                playerPlane.takeDamage(10);
            }
        }

        // ============ CHECK GAME OVER ============
        if (!playerPlane.isAlive()) {
            currentState = GameState.GAME_OVER;
        }

        // Hapus musuh yang sudah mati
        enemiesBlue.removeIf(enemy -> !enemy.isAlive());
        enemiesGreen.removeIf(enemy -> !enemy.isAlive());
    }

    private void updateGameOver() {
        // TODO (Anggota 3): update logic tombol restart di GameOverScreen jika perlu
        // Untuk sekarang: tekan ENTER untuk kembali ke menu
        if (keys[KeyEvent.VK_ENTER]) {
            currentState = GameState.MENU;
            keys[KeyEvent.VK_ENTER] = false;
        }
    }

    private void spawnEnemy() {
        // Random spawn musuh (biru atau hijau)
        // 70% musuh biru, 30% musuh hijau
        if (Math.random() < 0.7) {
            // Spawn musuh biru
            double randomX = Math.random() * (Settings.SCREEN_WIDTH - Settings.ENEMY_BLUE_SIZE);
            EnemyBlue enemy = new EnemyBlue(randomX, -Settings.ENEMY_BLUE_SIZE);
            enemiesBlue.add(enemy);
        } else {
            // Spawn musuh hijau (selalu di atas, tetap di posisi yang sama)
            double randomX = Math.random() * (Settings.SCREEN_WIDTH - Settings.ENEMY_GREEN_SIZE);
            EnemyGreen enemy = new EnemyGreen(randomX, 30); // 30px dari atas
            enemiesGreen.add(enemy);
        }
    }

    private void startNewGame() {
        currentState = GameState.PLAYING;
        score = 0;
        playerPlane = new PlayerPlane();
        enemiesBlue.clear();
        enemiesGreen.clear();
        lastSpawnTime = System.currentTimeMillis();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // WAJIB: antialiasing aktif di seluruh proses render (syarat tugas)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        switch (currentState) {
            case MENU:
                drawMenu(g2);
                break;
            case PLAYING:
                drawGameplay(g2);
                break;
            case GAME_OVER:
                drawGameOver(g2);
                break;
        }
        g2.dispose();
    }

    private void drawMenu(Graphics2D g2) {
        // Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        // Judul
        g2.setColor(Color.CYAN);
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.drawString("AIRFORCE SHOOTER", 50, 150);

        // Instruksi
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(20f));
        g2.drawString("Tekan ENTER untuk mulai", 100, 350);

        // Kontrol
        g2.setColor(Color.GRAY);
        g2.setFont(g2.getFont().deriveFont(16f));
        g2.drawString("Arrow Keys: Gerak Pesawat", 80, 450);
        g2.drawString("Auto-fire: Otomatis", 80, 480);

        // TODO (Anggota 3): render menu UI yang lebih bagus (button, background gambar,
        // dll)
    }

    private void drawGameplay(Graphics2D g2) {
        // Background placeholder (TODO: Anggota 3 render background scrolling)
        g2.setColor(new Color(10, 10, 30));
        g2.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        // Bintang background sederhana
        g2.setColor(Color.WHITE);
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * Settings.SCREEN_WIDTH);
            int y = (int) (Math.random() * Settings.SCREEN_HEIGHT);
            g2.fillOval(x, y, 2, 2);
        }

        // Render player
        if (playerPlane != null) {
            playerPlane.render(g2);
        }

        // Render musuh biru
        for (EnemyBlue enemy : enemiesBlue) {
            enemy.render(g2);
        }

        // Render musuh hijau
        for (EnemyGreen enemy : enemiesGreen) {
            enemy.render(g2);
        }

        // TODO (Anggota 3): render HUD (HP & Score) di sini atau via HUD.java
        // Untuk sekarang: HUD sederhana di sini
        renderHUD(g2);
    }

    private void renderHUD(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(18f));

        // HP
        if (playerPlane != null) {
            g2.drawString("HP: " + playerPlane.getCurrentHp(), 20, 30);
        }

        // Score
        g2.drawString("Score: " + score, Settings.SCREEN_WIDTH - 150, 30);

        // TODO (Anggota 3): render lebih bagus dengan background, icon, dll
    }

    private void drawGameOver(Graphics2D g2) {
        // Background gelap
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        // Teks Game Over
        g2.setColor(Color.RED);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawString("GAME OVER", 100, 200);

        // Skor akhir
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(30f));
        g2.drawString("Score: " + score, 130, 300);

        // Instruksi
        g2.setColor(Color.CYAN);
        g2.setFont(g2.getFont().deriveFont(20f));
        g2.drawString("Tekan ENTER untuk kembali ke menu", 40, 450);

        // TODO (Anggota 3): render game over screen yang lebih bagus
    }

    public GameState getState() {
        return currentState;
    }

    // ============ KEY LISTENER ============
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 256) {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 256) {
            keys[e.getKeyCode()] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Tidak digunakan
    }
}
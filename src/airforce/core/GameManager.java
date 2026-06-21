package airforce.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import airforce.utils.Settings;

/**
 * Mengatur game loop dan state management (menu, bermain, game over).
 * Anggota lain menambahkan logic render/update di method drawXxx() dan
 * bagian update() yang sudah ditandai TODO sesuai tanggung jawab masing-masing.
 */
public class GameManager extends JPanel implements Runnable {

    public enum GameState {
        MENU,
        PLAYING,
        GAME_OVER
    }

    private Thread gameThread;
    private boolean isRunning;
    private GameState currentState;

    public GameManager() {
        setPreferredSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        currentState = GameState.MENU;
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
                // TODO (Anggota 3): update logic tombol Start/Exit di MainMenu
                break;
            case PLAYING:
                // TODO (Anggota 2): update PlayerPlane, EnemyBlue/Green, BulletPlayer/Enemy
                // TODO (Anggota 1): integrasi collision detection antar objek
                break;
            case GAME_OVER:
                // TODO (Anggota 3): update logic tombol restart di GameOverScreen
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // WAJIB: antialiasing aktif di seluruh proses render (syarat tugas)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

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
        // TODO (Anggota 3): render MainMenu (background, button start, button exit)
        g2.setColor(Color.WHITE);
        g2.drawString("AIRFORCE SHOOTER - Tekan ENTER untuk mulai", 60, Settings.SCREEN_HEIGHT / 2);
    }

    private void drawGameplay(Graphics2D g2) {
        // TODO (Anggota 2): render PlayerPlane, EnemyBlue, EnemyGreen, BulletPlayer, BulletEnemy
        // TODO (Anggota 3): render HUD (HP & Score)
        g2.setColor(Color.WHITE);
        g2.drawString("Gameplay placeholder", 150, Settings.SCREEN_HEIGHT / 2);
    }

    private void drawGameOver(Graphics2D g2) {
        // TODO (Anggota 3): render GameOverScreen (skor akhir, tombol restart)
        g2.setColor(Color.WHITE);
        g2.drawString("GAME OVER", 180, Settings.SCREEN_HEIGHT / 2);
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getState() {
        return currentState;
    }
}
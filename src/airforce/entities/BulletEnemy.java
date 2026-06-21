package airforce.entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;

import airforce.utils.Settings;

/**
 * Peluru milik EnemyBlue atau EnemyGreen.
 * Bergerak lurus ke bawah dengan kecepatan konstan.
 * Otomatis dihapus kalau keluar dari layar (clipping).
 */
public class BulletEnemy {

    private double posX;
    private double posY;
    private static final int WIDTH = 6;
    private static final int HEIGHT = 12;

    /**
     * Konstruktor.
     * @param startX posisi X awal (biasanya pusat musuh)
     * @param startY posisi Y awal (biasanya ujung bawah musuh)
     */
    public BulletEnemy(double startX, double startY) {
        this.posX = startX - WIDTH / 2.0; // center horizontally
        this.posY = startY;
    }

    /**
     * Update posisi peluru (bergerak ke bawah).
     * Dipanggil setiap frame.
     */
    public void update() {
        posY += Settings.BULLET_ENEMY_SPEED;
    }

    /**
     * Render peluru ke layar.
     * Menggunakan translasi (transformasi) untuk posisi dan warna merah.
     */
    public void render(Graphics2D g2) {
        // WAJIB: demonstrasi transformasi translasi (syarat tugas grafika komputer)
        AffineTransform originalTransform = g2.getTransform();
        g2.translate(posX, posY);

        // Gambar persegi panjang sebagai peluru
        g2.setColor(Color.RED);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        g2.setTransform(originalTransform);
    }

    /**
     * Cek apakah peluru sudah keluar layar (clipping).
     * @return true jika posisi Y sudah melewati batas bawah layar
     */
    public boolean isOutOfBounds() {
        return posY > Settings.SCREEN_HEIGHT;
    }

    // Getter
    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
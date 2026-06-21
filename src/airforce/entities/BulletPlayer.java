package airforce.entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;

import airforce.utils.Settings;

/**
 * Peluru milik PlayerPlane.
 * Bergerak lurus ke atas dengan kecepatan konstan.
 * Otomatis dihapus kalau keluar dari layar (clipping).
 */
public class BulletPlayer {

    private double posX;
    private double posY;
    private static final int WIDTH = 6;
    private static final int HEIGHT = 14;

    /**
     * Konstruktor.
     * @param startX posisi X awal (biasanya pusat pesawat pemain)
     * @param startY posisi Y awal (biasanya ujung atas pesawat pemain)
     */
    public BulletPlayer(double startX, double startY) {
        this.posX = startX - WIDTH / 2.0; // center horizontally
        this.posY = startY;
    }

    /**
     * Update posisi peluru (bergerak ke atas).
     * Dipanggil setiap frame.
     */
    public void update() {
        posY -= Settings.BULLET_PLAYER_SPEED;
    }

    /**
     * Render peluru ke layar.
     * Menggunakan translasi (transformasi) untuk posisi dan warna putih.
     */
    public void render(Graphics2D g2) {
        // WAJIB: demonstrasi transformasi translasi (syarat tugas grafika komputer)
        AffineTransform originalTransform = g2.getTransform();
        g2.translate(posX, posY);

        // Gambar persegi panjang sebagai peluru
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        g2.setTransform(originalTransform);
    }

    /**
     * Cek apakah peluru sudah keluar layar (clipping).
     * @return true jika posisi Y sudah melewati batas atas layar
     */
    public boolean isOutOfBounds() {
        return posY < -HEIGHT;
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
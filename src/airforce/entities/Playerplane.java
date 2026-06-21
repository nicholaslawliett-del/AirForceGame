package airforce.entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import airforce.utils.Settings;

/**
 * Pesawat pemain.
 * Bisa bergerak ke kiri, kanan, atas, bawah.
 * Auto-fire peluru ke atas dengan cooldown.
 * Memiliki HP dan mati kalau HP habis.
 */
public class PlayerPlane {

    private double posX;
    private double posY;
    private int currentHp;
    private List<BulletPlayer> bullets;
    private long lastShotTime;

    // Flag input (diset oleh GameManager sesuai tombol yang ditekan)
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;

    /**
     * Konstruktor.
     * Pesawat dimulai di tengah bawah layar.
     */
    public PlayerPlane() {
        this.posX = Settings.SCREEN_WIDTH / 2.0 - Settings.PLAYER_SIZE / 2.0;
        this.posY = Settings.SCREEN_HEIGHT - Settings.PLAYER_SIZE - 10;
        this.currentHp = Settings.PLAYER_HP;
        this.bullets = new ArrayList<>();
        this.lastShotTime = 0;
    }

    /**
     * Update posisi pesawat berdasarkan input, handle auto-fire dan clipping.
     * Dipanggil setiap frame.
     */
    public void update() {
        // Gerak horizontal
        if (movingLeft) {
            posX -= Settings.PLAYER_SPEED;
        }
        if (movingRight) {
            posX += Settings.PLAYER_SPEED;
        }

        // Gerak vertikal
        if (movingUp) {
            posY -= Settings.PLAYER_SPEED;
        }
        if (movingDown) {
            posY += Settings.PLAYER_SPEED;
        }

        // Clipping: tetap dalam batas layar
        if (posX < 0) {
            posX = 0;
        }
        if (posX + Settings.PLAYER_SIZE > Settings.SCREEN_WIDTH) {
            posX = Settings.SCREEN_WIDTH - Settings.PLAYER_SIZE;
        }
        if (posY < 0) {
            posY = 0;
        }
        if (posY + Settings.PLAYER_SIZE > Settings.SCREEN_HEIGHT) {
            posY = Settings.SCREEN_HEIGHT - Settings.PLAYER_SIZE;
        }

        // Auto-fire: tembak peluru setiap BULLET_PLAYER_COOLDOWN ms
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= Settings.BULLET_PLAYER_COOLDOWN) {
            shoot();
            lastShotTime = currentTime;
        }

        // Update semua peluru dan hapus yang keluar layar
        for (int i = bullets.size() - 1; i >= 0; i--) {
            BulletPlayer bullet = bullets.get(i);
            bullet.update();
            if (bullet.isOutOfBounds()) {
                bullets.remove(i);
            }
        }
    }

    /**
     * Render pesawat ke layar.
     * Menggunakan translasi untuk posisi dan scaling untuk efek.
     */
    public void render(Graphics2D g2) {
        // WAJIB: demonstrasi transformasi translasi dan scaling
        AffineTransform originalTransform = g2.getTransform();
        g2.translate(posX, posY);

        // Gambar pesawat sebagai persegi (placeholder)
        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);

        // Gambar cockpit (detail sederhana)
        g2.setColor(Color.BLACK);
        g2.fillRect(14, 8, 12, 8);

        g2.setTransform(originalTransform);

        // Render semua peluru
        for (BulletPlayer bullet : bullets) {
            bullet.render(g2);
        }
    }

    /**
     * Menembakkan peluru dari posisi pesawat.
     * Peluru keluar dari tengah atas pesawat.
     */
    private void shoot() {
        double bulletX = posX + Settings.PLAYER_SIZE / 2.0;
        double bulletY = posY;
        bullets.add(new BulletPlayer(bulletX, bulletY));
    }

    /**
     * Mengambil damage dari musuh.
     * HP berkurang.
     */
    public void takeDamage(int damage) {
        currentHp -= damage;
        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    /**
     * Cek apakah pesawat masih hidup.
     */
    public boolean isAlive() {
        return currentHp > 0;
    }

    /**
     * Cek bounding box untuk collision detection.
     * Digunakan untuk cek tumbukan dengan peluru musuh.
     */
    public boolean checkCollision(double otherX, double otherY, double otherWidth, double otherHeight) {
        return !(posX + Settings.PLAYER_SIZE < otherX ||
                posX > otherX + otherWidth ||
                posY + Settings.PLAYER_SIZE < otherY ||
                posY > otherY + otherHeight);
    }

    // Setter untuk input (digunakan GameManager)
    public void setMovingLeft(boolean moving) {
        this.movingLeft = moving;
    }

    public void setMovingRight(boolean moving) {
        this.movingRight = moving;
    }

    public void setMovingUp(boolean moving) {
        this.movingUp = moving;
    }

    public void setMovingDown(boolean moving) {
        this.movingDown = moving;
    }

    // Getter
    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public List<BulletPlayer> getBullets() {
        return bullets;
    }
}
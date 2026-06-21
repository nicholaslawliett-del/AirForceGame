package airforce.entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import airforce.utils.Settings;

/**
 * Musuh biru.
 * Bergerak turun dari atas layar dengan kecepatan konstan.
 * Menembakkan peluru lurus ke bawah dengan interval konstan.
 * HP lebih sedikit dibanding EnemyGreen.
 * Hilang otomatis kalau sudah melewati batas bawah layar (effect hyperforce).
 */
public class EnemyBlue {

    private double posX;
    private double posY;
    private int currentHp;
    private List<BulletEnemy> bullets;
    private long lastShotTime;

    /**
     * Konstruktor.
     * @param startX posisi X awal musuh
     * @param startY posisi Y awal musuh (biasanya di atas layar)
     */
    public EnemyBlue(double startX, double startY) {
        this.posX = startX;
        this.posY = startY;
        this.currentHp = Settings.ENEMY_BLUE_HP;
        this.bullets = new ArrayList<>();
        this.lastShotTime = 0;
    }

    /**
     * Update posisi musuh, handle shooting, dan clipping.
     * Dipanggil setiap frame.
     */
    public void update() {
        // Bergerak turun
        posY += Settings.ENEMY_BLUE_SPEED;

        // Tembak dengan interval
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= Settings.ENEMY_BLUE_SHOOT_INTERVAL) {
            shoot();
            lastShotTime = currentTime;
        }

        // Update semua peluru dan hapus yang keluar layar
        for (int i = bullets.size() - 1; i >= 0; i--) {
            BulletEnemy bullet = bullets.get(i);
            bullet.update();
            if (bullet.isOutOfBounds()) {
                bullets.remove(i);
            }
        }
    }

    /**
     * Render musuh ke layar.
     * Menggunakan translasi untuk posisi.
     * Kalau musuh sudah mendekati bawah layar, tampilkan efek hyperforce.
     */
    public void render(Graphics2D g2) {
        // WAJIB: demonstrasi transformasi translasi
        AffineTransform originalTransform = g2.getTransform();
        g2.translate(posX, posY);

        // Gambar musuh biru sebagai persegi (placeholder)
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, Settings.ENEMY_BLUE_SIZE, Settings.ENEMY_BLUE_SIZE);

        // Gambar "mata" musuh (detail sederhana)
        g2.setColor(Color.WHITE);
        g2.fillOval(6, 6, 6, 6);
        g2.fillOval(20, 6, 6, 6);

        g2.setTransform(originalTransform);

        // Efek hyperforce kalau mendekati bawah layar (optional visual)
        if (posY > Settings.SCREEN_HEIGHT - 100) {
            g2.setColor(new Color(0, 255, 255, 100)); // Cyan translucent
            g2.drawOval((int)posX - 20, (int)posY + 20, 70, 50);
        }

        // Render semua peluru
        for (BulletEnemy bullet : bullets) {
            bullet.render(g2);
        }
    }

    /**
     * Menembakkan peluru dari posisi musuh.
     * Peluru keluar dari tengah bawah musuh.
     */
    private void shoot() {
        double bulletX = posX + Settings.ENEMY_BLUE_SIZE / 2.0;
        double bulletY = posY + Settings.ENEMY_BLUE_SIZE;
        bullets.add(new BulletEnemy(bulletX, bulletY));
    }

    /**
     * Mengambil damage dari peluru pemain.
     * HP berkurang.
     */
    public void takeDamage(int damage) {
        currentHp -= damage;
        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    /**
     * Cek apakah musuh masih hidup.
     */
    public boolean isAlive() {
        return currentHp > 0;
    }

    /**
     * Cek apakah musuh sudah keluar layar sepenuhnya (clipping).
     * Musuh dihapus otomatis kalau sudah terlewat bawah layar.
     */
    public boolean isOutOfBounds() {
        return posY > Settings.SCREEN_HEIGHT + Settings.ENEMY_BLUE_SIZE;
    }

    /**
     * Cek bounding box untuk collision detection.
     * Digunakan untuk cek tumbukan dengan peluru pemain atau pesawat pemain.
     */
    public boolean checkCollision(double otherX, double otherY, double otherWidth, double otherHeight) {
        return !(posX + Settings.ENEMY_BLUE_SIZE < otherX ||
                posX > otherX + otherWidth ||
                posY + Settings.ENEMY_BLUE_SIZE < otherY ||
                posY > otherY + otherHeight);
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

    public List<BulletEnemy> getBullets() {
        return bullets;
    }
}
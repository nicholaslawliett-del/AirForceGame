package airforce.entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import airforce.utils.Settings;

/**
 * Musuh hijau.
 * Diam di area atas layar (tidak bergerak).
 * HP lebih banyak dibanding EnemyBlue.
 * Menembakkan peluru ke bawah dengan posisi tembakan mengikuti posisi horizontal pemain
 * (tracking posisi X pemain, bukan homing missile).
 */
public class EnemyGreen {

    private double posX;
    private double posY;
    private int currentHp;
    private List<BulletEnemy> bullets;
    private long lastShotTime;

    /**
     * Konstruktor.
     * @param startX posisi X awal musuh (tetap, tidak bergerak)
     * @param startY posisi Y awal musuh (tetap, biasanya di atas)
     */
    public EnemyGreen(double startX, double startY) {
        this.posX = startX;
        this.posY = startY;
        this.currentHp = Settings.ENEMY_GREEN_HP;
        this.bullets = new ArrayList<>();
        this.lastShotTime = 0;
    }

    /**
     * Update musuh, handle shooting dan update peluru.
     * Musuh sendiri tidak bergerak, hanya menembak.
     * Dipanggil setiap frame.
     *
     * @param playerPosX posisi X pemain (untuk menghitung arah tembakan)
     */
    public void update(double playerPosX) {
        // Musuh hijau diam, tidak bergerak

        // Tembak dengan interval
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= Settings.ENEMY_GREEN_SHOOT_INTERVAL) {
            shootToward(playerPosX);
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
     */
    public void render(Graphics2D g2) {
        // WAJIB: demonstrasi transformasi translasi
        AffineTransform originalTransform = g2.getTransform();
        g2.translate(posX, posY);

        // Gambar musuh hijau sebagai persegi (placeholder)
        g2.setColor(Color.GREEN);
        g2.fillRect(0, 0, Settings.ENEMY_GREEN_SIZE, Settings.ENEMY_GREEN_SIZE);

        // Gambar "mata" musuh (detail sederhana)
        g2.setColor(Color.BLACK);
        g2.fillOval(6, 6, 6, 6);
        g2.fillOval(24, 6, 6, 6);

        g2.setTransform(originalTransform);

        // Render semua peluru
        for (BulletEnemy bullet : bullets) {
            bullet.render(g2);
        }
    }

    /**
     * Menembakkan peluru ke arah pemain.
     * Posisi X tembakan mengikuti posisi X pemain, tapi peluru tetap bergerak lurus ke bawah.
     *
     * @param playerPosX posisi X pemain (center)
     */
    private void shootToward(double playerPosX) {
        // Tembak dari pusat musuh, tapi arah horizontal mengikuti player
        // Sebenarnya, peluru keluar dari pusat musuh hijau (X tetap sama)
        // dan bergerak lurus ke bawah (bukan tracking/homing)
        double bulletX = posX + Settings.ENEMY_GREEN_SIZE / 2.0;
        double bulletY = posY + Settings.ENEMY_GREEN_SIZE;

        // Jika ingin efek "aiming" sederhana, bias posisi X bullet ke arah player
        // Tapi tetap gerak lurus (bukan curved/homing)
        // Untuk sekarang: peluru selalu keluar dari pusat musuh (simple)
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
     * Musuh hijau tidak keluar layar (diam di atas).
     * Selalu return false (tidak pernah "out of bounds").
     */
    public boolean isOutOfBounds() {
        return false; // Musuh hijau tetap di layar
    }

    /**
     * Cek bounding box untuk collision detection.
     * Digunakan untuk cek tumbukan dengan peluru pemain atau pesawat pemain.
     */
    public boolean checkCollision(double otherX, double otherY, double otherWidth, double otherHeight) {
        return !(posX + Settings.ENEMY_GREEN_SIZE < otherX ||
                posX > otherX + otherWidth ||
                posY + Settings.ENEMY_GREEN_SIZE < otherY ||
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
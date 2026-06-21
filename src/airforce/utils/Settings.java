package airforce.utils;

/**
 * Kumpulan konstanta global game.
 * WAJIB: nama konstanta di file ini tidak boleh diubah oleh anggota tim
 * (lihat Kamus_Penamaan_AirForceGame.docx).
 */
public class Settings {

    // Layar
    public static final int SCREEN_WIDTH = 480;
    public static final int SCREEN_HEIGHT = 720;
    public static final int FPS = 60;

    // PlayerPlane
    public static final int PLAYER_HP = 100;
    public static final int PLAYER_SIZE = 40;
    public static final int PLAYER_SPEED = 5;

    // EnemyBlue
    public static final int ENEMY_BLUE_HP = 20;
    public static final int ENEMY_BLUE_SIZE = 32;
    public static final int ENEMY_BLUE_SPEED = 2;
    public static final int ENEMY_BLUE_SHOOT_INTERVAL = 1500; // ms

    // EnemyGreen
    public static final int ENEMY_GREEN_HP = 40;
    public static final int ENEMY_GREEN_SIZE = 36;
    public static final int ENEMY_GREEN_SHOOT_INTERVAL = 2000; // ms

    // BulletPlayer
    public static final int BULLET_PLAYER_DAMAGE = 20;
    public static final int BULLET_PLAYER_SPEED = 8;
    public static final int BULLET_PLAYER_COOLDOWN = 300; // ms

    // BulletEnemy
    public static final int BULLET_ENEMY_DAMAGE = 10;
    public static final int BULLET_ENEMY_SPEED = 5;

    private Settings() {
        // Mencegah instansiasi class ini (hanya dipakai sebagai kumpulan konstanta statis)
    }
}
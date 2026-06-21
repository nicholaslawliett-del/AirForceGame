import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GameManager extends JPanel implements Runnable {
    
    // Mengatur Ukuran Layar Game (800 x 600 piksel)
    final int screenWidth = 800;
    final int screenHeight = 600;
    
    // Jantung Game (Thread & Game Loop)
    Thread gameThread;
    final int FPS = 60; // Game berjalan stabil di 60 Frame Per Second

    // Constructor
    public GameManager() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK); // Layar dasar warna hitam (luar angkasa)
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    // Fungsi untuk memulai thread game
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Logika pembatas FPS agar game tidak berjalan terlalu cepat / lambat
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // 1. UPDATE: Menghitung ulang logika game (posisi pesawat, peluru, musuh)
            update();
            
            // 2. REPAINT: Menggambar ulang tampilan ke layar monitor
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // Ubah ke milidetik

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Tempat untuk memperbarui logika pergerakan game
    public void update() {
        // TODO: Nanti logika pergerakan player dan musuh ditaruh di sini
    }

    // Tempat untuk menggambar komponen visual game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Sementara menggambar teks putih sebagai penanda game jalan
        g2d.setColor(Color.WHITE);
        g2d.drawString("Winger Shooter: Game Loop Berjalan! Siap Diisi Pesawat.", 50, 50);

        g2d.dispose();
    }
}

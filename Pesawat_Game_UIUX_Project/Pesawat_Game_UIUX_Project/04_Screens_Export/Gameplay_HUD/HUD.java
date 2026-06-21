import java.awt.*;
import javax.swing.*;

public class HUD extends JPanel {
    private final JProgressBar enemyHealthBar;
    private int currentMapBackground = 1;

    public HUD() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparan agar render background space kelihatan
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // --- ATAS: HUD INDIKATOR ---
        JPanel topHud = new JPanel(new BorderLayout());
        topHud.setOpaque(false);

        // Kiri & Kanan atas (Ikon / Skor)
        JLabel leftStats = new JLabel("⚡ || 🚀");
        leftStats.setForeground(Color.CYAN);
        leftStats.setFont(new Font("Arial", Font.BOLD, 14));

        JButton settingsBtn = new JButton("⚙");
        settingsBtn.setPreferredSize(new Dimension(30, 30));

        // Tengah atas: Boss/Enemy Health Bar (Bar Hijau di layout gambar)
        enemyHealthBar = new JProgressBar(0, 100);
        enemyHealthBar.setValue(100);
        enemyHealthBar.setForeground(Color.GREEN);
        enemyHealthBar.setBackground(Color.DARK_GRAY);
        enemyHealthBar.setPreferredSize(new Dimension(150, 12));
        
        JPanel centerTop = new JPanel();
        centerTop.setOpaque(false);
        centerTop.add(enemyHealthBar);

        topHud.add(leftStats, BorderLayout.WEST);
        topHud.add(centerTop, BorderLayout.CENTER);
        topHud.add(settingsBtn, BorderLayout.EAST);
        add(topHud, BorderLayout.NORTH);

        // --- BAWAH: UTILITY / CONTROLLER BUTTONS ---
        JPanel bottomHud = new JPanel(new BorderLayout());
        bottomHud.setOpaque(false);

        // Tombol kontrol bawah (4 kotak transparan di mockup gambar)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        controlPanel.setOpaque(false);
        for(int i=0; i<4; i++) {
            JButton ctrlBtn = new JButton("▢");
            ctrlBtn.setFont(new Font("Arial", Font.PLAIN, 10));
            ctrlBtn.setForeground(Color.WHITE);
            ctrlBtn.setBackground(new Color(255, 255, 255, 40)); // Transparan semi murni
            ctrlBtn.setPreferredSize(new Dimension(35, 30));
            ctrlBtn.setFocusable(false);
            controlPanel.add(ctrlBtn);
        }
        bottomHud.add(controlPanel, BorderLayout.CENTER);

        // Tombol aksi kanan bawah
        JPanel rightActions = new JPanel(new GridLayout(2, 1, 0, 5));
        rightActions.setOpaque(false);
        rightActions.add(new JButton("⛯"));
        rightActions.add(new JButton("⚙"));
        bottomHud.add(rightActions, BorderLayout.EAST);

        add(bottomHud, BorderLayout.SOUTH);
    }

    // Method pembantu saat Anggota 1 & 2 ganti map permainan
    public void setMapBackground(int mapId) {
        this.currentMapBackground = mapId;
        // Logic untuk mengganti gambar background antariksa (1-6) saat render berjalan
        repaint();
    }

    public void updateEnemyHealth(int health) {
        enemyHealthBar.setValue(health);
    }

    public int getCurrentMapBackground() {
        return currentMapBackground;
    }
}

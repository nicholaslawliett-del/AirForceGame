package airforce.ui;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameOverScreen extends JPanel {
    private final JLabel finalScoreLabel;

    public GameOverScreen(ActionListener retryAction, ActionListener mainMenuAction) {
        // Menggunakan BoxLayout vertikal agar teks dan tombol tersusun rapi ke bawah
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Warna hitam transparan (Alpha: 200) agar terlihat menyatu dengan background space game
        setBackground(new Color(11, 12, 24, 200)); 
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Spacing atas (push isi ke tengah)
        add(Box.createVerticalGlue());

        // --- TEKS UTAMA: GAME OVER ---
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Impact", Font.ITALIC, 54)); // Font miring menyerupai judul utama
        gameOverLabel.setForeground(new Color(220, 53, 69)); // Warna merah pekat/darah
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(gameOverLabel);

        // Jarak kecil
        add(Box.createRigidArea(new Dimension(0, 10)));

        // --- TEKS SKOR AKHIR ---
        finalScoreLabel = new JLabel("FINAL SCORE: 0");
        finalScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        finalScoreLabel.setForeground(Color.YELLOW); // Warna kuning agar kontras dengan tema gelap
        finalScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(finalScoreLabel);

        // Jarak menuju tombol
        add(Box.createRigidArea(new Dimension(0, 40)));

        // --- TOMBOL 1: TRY AGAIN ---
        JButton retryButton = createStyledButton("TRY AGAIN");
        retryButton.addActionListener(retryAction);
        add(retryButton);

        // Jarak antar tombol
        add(Box.createRigidArea(new Dimension(0, 15)));

        // --- TOMBOL 2: MAIN MENU ---
        JButton menuButton = createStyledButton("MAIN MENU");
        menuButton.addActionListener(mainMenuAction);
        add(menuButton);

        // Spacing bawah (push isi ke tengah)
        add(Box.createVerticalGlue());
    }

    // Method pembantu (helper) untuk menyamakan style tombol dengan MainMenu/MapMenu
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial Black", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 65, 125)); // Biru yang sama dengan MainMenu
        btn.setFocusPainted(false);
        
        // Mengatur ukuran tombol agar seragam dan pas di tengah
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Border cyan tipis seperti tombol di layout gambar
        btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1)); 
        
        return btn;
    }

    /**
     * Method ini dipanggil oleh Anggota 1 (GameManager) tepat sebelum
     * layar GameOver ditarik ke depan, untuk memperbarui skor terakhir user.
     */
    public void setFinalScore(int score) {
        finalScoreLabel.setText("FINAL SCORE: " + score);
    }
}

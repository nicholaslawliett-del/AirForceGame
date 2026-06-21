package airforce.ui;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JPanel {
    
    public MainMenu(ActionListener startAct, ActionListener mapAct, ActionListener exitAct) {
        setLayout(new BorderLayout());
        // Menggunakan background hitam/gelap sesuai tema luar angkasa
        setBackground(new Color(11, 12, 24)); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- ATAS: TOMBOL IKON CORNER ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JButton infoButton = createIconButton("ℹ");
        JButton settingsButton = createIconButton("⚙");
        
        topPanel.add(infoButton, BorderLayout.WEST);
        topPanel.add(settingsButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // --- TENGAH: JUDUL & TOMBOL NAVIGASI ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Judul Game
        JLabel titleLabel = new JLabel("WINGER SHOOTER");
        titleLabel.setFont(new Font("Impact", Font.ITALIC, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subTitleLabel = new JLabel("DUAR DUAR");
        subTitleLabel.setFont(new Font("Impact", Font.ITALIC, 32));
        subTitleLabel.setForeground(Color.WHITE);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(subTitleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Tombol Utama (START, MAP, EXIT)
        JButton startBtn = createMenuButton("START");
        startBtn.addActionListener(startAct);
        
        JButton mapBtn = createMenuButton("MAP");
        mapBtn.addActionListener(mapAct);
        
        JButton exitBtn = createMenuButton("EXIT");
        exitBtn.addActionListener(exitAct);

        centerPanel.add(startBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(mapBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(exitBtn);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        // --- BAWAH & SAMPING: TOMBOL FITUR EKSTRA ---
        // Membuat side panel kiri dan kanan untuk tombol-tombol ikon kecil di layout gambar
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        leftPanel.setOpaque(false);
        for(int i=0; i<4; i++) leftPanel.add(createIconButton("🎮"));
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        rightPanel.setOpaque(false);
        for(int i=0; i<4; i++) rightPanel.add(createIconButton("🔊"));
        add(rightPanel, BorderLayout.EAST);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial Black", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 65, 125)); // Warna biru sesuai mockup
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(160, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
        return btn;
    }

    private JButton createIconButton(String iconText) {
        JButton btn = new JButton(iconText);
        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 65, 125, 180));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(35, 35));
        return btn;
    }
}

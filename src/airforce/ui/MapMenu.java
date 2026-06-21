package airforce.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapMenu extends JPanel {
    
    public MapMenu(ActionListener backAct, MapSelectListener mapSelectAct) {
        setLayout(new BorderLayout());
        setBackground(new Color(11, 12, 24));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Atas: Info & Setting corner
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(new JButton("ℹ"), BorderLayout.WEST);
        topPanel.add(new JButton("⚙"), BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Tengah: Grid 2x3 untuk Pilihan Map
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        for (int i = 1; i <= 6; i++) {
            final int mapId = i;
            // Di game asli, ganti text dengan ImageIcon (Aset dari Anggota 4)
            JButton mapCard = new JButton("MAP " + mapId);
            mapCard.setFont(new Font("Arial Black", Font.BOLD, 14));
            mapCard.setForeground(Color.WHITE);
            mapCard.setBackground(new Color(25, 35, 65));
            mapCard.setFocusPainted(false);
            mapCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            
            // Event ketika map dipilih
            mapCard.addActionListener(e -> mapSelectAct.onMapSelected(mapId));
            gridPanel.add(mapCard);
        }
        add(gridPanel, BorderLayout.CENTER);

        // Bawah: Tombol BACK
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        JButton backBtn = new JButton("BACK");
        backBtn.setFont(new Font("Arial Black", Font.BOLD, 14));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(45, 65, 125));
        backBtn.setPreferredSize(new Dimension(120, 35));
        backBtn.addActionListener(backAct);
        bottomPanel.add(backBtn);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Interface custom untuk mendeteksi nomor map yang diklik
    public interface MapSelectListener {
        void onMapSelected(int mapId);
    }
}
package airforce.ui;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageLoader {
    
    // Method untuk mengambil gambar tombol dari folder 05
    public static ImageIcon getSliceImage(String fileName) {
        String path = "05_Slices_For_Dev/" + fileName;
        return new ImageIcon(path);
    }

    // Contoh penerapan langsung pada JButton di MainMenu/MapMenu
    public static void applyImageToButton(JButton button, String sliceName) {
        ImageIcon icon = getSliceImage(sliceName);
        button.setIcon(icon);
        
        // Menghilangkan border bawaan button agar hanya gambarnya saja yang terlihat
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    }
}
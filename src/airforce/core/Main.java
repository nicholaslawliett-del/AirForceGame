import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // 1. Membuat jendela game
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Winger Shooter Duar Duar");

        // 2. Memanggil dan memasukkan GameManager ke dalam jendela
        GameManager gameManager = new GameManager();
        window.add(gameManager);

        window.pack(); // Menyesuaikan ukuran jendela dengan GameManager (800x600)
        window.setLocationRelativeTo(null); // Jendela otomatis di tengah layar
        window.setVisible(true);

        // 3. Menjalankan Jantung Game (Game Loop)
        gameManager.startGameThread();
    }
}

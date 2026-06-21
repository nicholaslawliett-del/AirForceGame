package airforce.core;

import javax.swing.JFrame;

/**
 * Entry point program. Jalankan file ini untuk membuka game.
 */
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("AirForce Shooter");
        GameManager gameManager = new GameManager();

        window.add(gameManager);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gameManager.startGame();
    }
}
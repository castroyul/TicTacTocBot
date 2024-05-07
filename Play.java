// Test Tic Tac Toe Game
// Dye and Castro Inc.
import java.awt.*;
import javax.swing.*;

public class Play extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;

    public static void main(String [] args) {
        Play g = new Play();
    }
    
    // Constructor 
    public Play() {
        super("Tic Tac Toe By DC Inc.");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        JPanel jp = new TicTacToe();
        add(jp);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);
    }

}

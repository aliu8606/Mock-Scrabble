import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GUIController implements ActionListener{
    private JTextArea gameBoard;
    private JTextField wordEntry;
    private Game currGame;

    public GUIController(Game g) {
        gameBoard = new JTextArea(40, 20);
        wordEntry = new JTextField();
        currGame = g;
    }

    private void setUp() {
        JFrame frame = new JFrame("Welcome to mock-Scrabble!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton getRules = new JButton("Rules");
        JButton getScoreboard = new JButton("Scoreboard");
        JButton playGame = new JButton("Play");

        getRules.addActionListener(this);
        getScoreboard.addActionListener(this);
        playGame.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) (event.getSource());
        String text = button.getText();

        if (text.equals("Rules")) {

        }
    }
}

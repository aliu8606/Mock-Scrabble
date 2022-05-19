import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static java.awt.Color.white;

public class GUIController implements ActionListener{
    private JTextArea gameBoard;
    private JTextField wordEntry;
    private Game currGame;
    private JFrame frame;

    public GUIController(Game g) {
        gameBoard = new JTextArea(20, 60);
        currGame = g;
        frame = new JFrame("Welcome to mock-Scrabble!");

        setUp();
        load();
    }

    private void setUp() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                currGame.saveGame();
                frame.dispose();
                System.exit(0);
            }
        });

        //top panel with game board and data
        JPanel gamePanel = new JPanel();

        gameBoard.setText(currGame.toString());
        gameBoard.setFont(new Font("SansSerif", Font.PLAIN, 20));
        gameBoard.setBorder(BorderFactory.createEmptyBorder());
        gamePanel.add(gameBoard);

        frame.add(gamePanel, BorderLayout.NORTH);

        //middle panel with shuffle-board button
        JPanel shufflePanel = new JPanel();

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(this);
        shuffleButton.setBackground(white);

        shufflePanel.add(shuffleButton);

        frame.add(shufflePanel, BorderLayout.CENTER);

        //bottom panel with playing word structure
        JPanel wordPanel = new JPanel();

        JButton playWord = new JButton("Play");
        playWord.addActionListener(this);
        JLabel entryLabel = new JLabel("Enter a word: ");
        wordEntry = new JTextField(20);

        wordPanel.add(entryLabel);
        wordPanel.add(wordEntry);
        wordPanel.add(playWord);

        frame.add(wordPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void load() {
        gameBoard.setText(currGame.toString());
    }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) (event.getSource());
        String text = button.getText();


        if (text.equals("Shuffle")) {
            currGame.shuffle();
            gameBoard.setText(currGame.toString());
        }
        if (text.equals("Play")) {
            String word = wordEntry.getText();
            gameBoard.setText(currGame.updateBoard(word));
        }
    }
}

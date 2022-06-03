import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.white;

public class ScrabbleGUI implements ActionListener{
    private JEditorPane gameBoard;
    private JTextField playWordEntry;
    private ScrabbleGame currScrabGame;
    private JFrame gameFrame;

    public ScrabbleGUI(ScrabbleGame g) {
        gameBoard = new JEditorPane();
        currScrabGame = g;
        playWordEntry = new JTextField(20);
        gameFrame = new JFrame("Welcome to mock-Scrabble!");

        setUp();
        load();
    }

    private void setUp() {
        gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                currScrabGame.saveGame();
                gameFrame.dispose();
                System.out.println("\nWhew! What a game!");
            }
        });

        //top panel with game board and data
        JPanel gamePanel = new JPanel();

        gameBoard.setText(currScrabGame.toString());
        gameBoard.setPreferredSize(new Dimension(1000, 500));
        gameBoard.setEditable(false);
        gameBoard.setFont(new Font("SansSerif", Font.PLAIN, 20));
        gameBoard.setBackground(Color.getHSBColor((float) 0.079, (float) 0.275, (float) 1));
        CompoundBorder gameBorder = new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(20, 30, 20, 20));
        gameBoard.setBorder(gameBorder);

        gamePanel.add(gameBoard);
        gamePanel.setBackground(Color.getHSBColor((float) 0.077, (float) 0.607, (float) 0.957));
        JPanel empPanel = new JPanel();
        empPanel.setBackground(Color.getHSBColor((float) 0.077, (float) 0.607, (float) 0.957));
        gamePanel.add(empPanel, BorderLayout.CENTER);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        gameFrame.add(gamePanel, BorderLayout.NORTH);

        //middle panel with shuffle-board button
        JPanel shufflePanel = new JPanel();

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(this);
        shuffleButton.setBackground(Color.getHSBColor((float)0.1,(float)0.322, (float)1.0));
        shuffleButton.setPreferredSize(new Dimension(100, 30));

        shufflePanel.add(shuffleButton);
        shufflePanel.add(new JPanel(), BorderLayout.SOUTH);
        shufflePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 25, 20));

        gameFrame.add(shufflePanel, BorderLayout.CENTER);



        //bottom panel with playing word structure
        JPanel wordPanel = new JPanel();

        JButton playWordButton = new JButton("Play");
        playWordButton.addActionListener(this);
        playWordButton.setBackground(white);
        JLabel entryLabel = new JLabel("Enter a word: ");

        wordPanel.add(entryLabel);
        wordPanel.add(playWordEntry);
        wordPanel.add(playWordButton);
        wordPanel.add(new JPanel(), BorderLayout.SOUTH);
        wordPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        gameFrame.add(wordPanel, BorderLayout.SOUTH);

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    public void load() {
        gameBoard.setText(currScrabGame.toString());
    }

    public void actionPerformed(ActionEvent event){
        JButton button = (JButton) (event.getSource());
        String buttonText = button.getText();

        if (buttonText.equals("Shuffle")) {
            currScrabGame.shuffle();
            gameBoard.setText(currScrabGame.toString());
        }
        if (buttonText.equals("Play")) {
            String word = playWordEntry.getText();

            gameBoard.setText(currScrabGame.playWord(word, false));
            Timer timer = new Timer(2000, e -> {
                gameBoard.setText(currScrabGame.botTurn());
            } );
            timer.setRepeats(false);
            timer.start();

        }
    }
}

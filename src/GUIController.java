import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.white;

public class GUIController implements ActionListener{
    private JEditorPane gameBoard;
    private JTextField wordEntry;
    private Game currGame;
    private JFrame frame;

    public GUIController(Game g) {
        gameBoard = new JEditorPane();
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
                System.out.println("\nWhew! What a game!");
            }
        });

        //top panel with game board and data
        JPanel gamePanel = new JPanel();

        gameBoard.setText(currGame.toString());
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

        frame.add(gamePanel, BorderLayout.NORTH);

        //middle panel with shuffle-board button
        JPanel shufflePanel = new JPanel();

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(this);
        shuffleButton.setBackground(Color.getHSBColor((float)0.1,(float)0.322, (float)1.0));
        shuffleButton.setPreferredSize(new Dimension(100, 30));

        shufflePanel.add(shuffleButton);
        shufflePanel.add(new JPanel(), BorderLayout.SOUTH);
        shufflePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 25, 20));

        frame.add(shufflePanel, BorderLayout.CENTER);



        //bottom panel with playing word structure
        JPanel wordPanel = new JPanel();

        JButton playWord = new JButton("Play");
        playWord.addActionListener(this);
        playWord.setBackground(white);
        JLabel entryLabel = new JLabel("Enter a word: ");
        wordEntry = new JTextField(20);

        wordPanel.add(entryLabel);
        wordPanel.add(wordEntry);
        wordPanel.add(playWord);
        wordPanel.add(new JPanel(), BorderLayout.SOUTH);
        wordPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

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

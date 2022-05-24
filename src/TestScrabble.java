import java.util.ArrayList;

public class TestScrabble {
    public static void main(String[] args) {

        ArrayList<Letter> testBoard = new ArrayList<Letter>();
        testBoard.add(new Letter("C", 3));
        testBoard.add(new Letter("A", 1));
        testBoard.add(new Letter("T", 1));
        testBoard.add(new Letter("T", 1));


        ScrabbleGame newGame = new ScrabbleGame("uwu", testBoard);
        ScrabbleGUI newGUI = new ScrabbleGUI(newGame);


        System.out.println(newGame);

        //Valid word, score 8
        System.out.println(newGame.updateBoard("cat"));

        //Invalid word (not in dictionary)
        System.out.println(newGame.updateBoard("tt"));

        //Invalid word (not on board)
        System.out.println(newGame.updateBoard("hi"));

        newGame.saveGame();

        ScrabbleGame newGame2 = new ScrabbleGame("aaaaaaaaaaaaaaaaaaa", testBoard);
        newGame2.saveGame();

        Scoreboard scoreboard = new Scoreboard();
        System.out.println(scoreboard);
    }
}

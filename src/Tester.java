import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {

        ArrayList<Letter> testBoard = new ArrayList<Letter>();
        testBoard.add(new Letter("C", 3));
        testBoard.add(new Letter("A", 1));
        testBoard.add(new Letter("T", 1));
        testBoard.add(new Letter("T", 1));


        Game newGame = new Game("uwu", testBoard);
        GUIController gui = new GUIController(newGame);


        /*System.out.println(newGame);

        //Valid word, score 8
        System.out.println(newGame.updateBoard("cat"));

        //Invalid word (not in dictionary)
        System.out.println(newGame.updateBoard("tt"));

        //Invalid word (not on board)
        System.out.println(newGame.updateBoard("hi"));

        newGame.saveGame();

        Game newGame2 = new Game("aaaaaaaaaaaaaaaaaaa", testBoard);
        newGame2.saveGame();

        Scoreboard scoreboard = new Scoreboard();
        System.out.println(scoreboard); */
    }
}

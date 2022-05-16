import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        ArrayList<Letter> testBoard = new ArrayList<Letter>();
        testBoard.add(new Letter("C", 3));
        testBoard.add(new Letter("A", 1));
        testBoard.add(new Letter("T", 1));

        Game newGame = new Game("uwu", testBoard);
        System.out.println(newGame);
    }
}

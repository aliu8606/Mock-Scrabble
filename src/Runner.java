import java.util.ArrayList;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Game newGame = new Game("uwu");
        System.out.println(newGame);
        newGame.shuffle();
        System.out.println(newGame);

    }
}

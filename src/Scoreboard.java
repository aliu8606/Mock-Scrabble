import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard {
    private ArrayList<ScrabbleGame> pastGames;

    public Scoreboard() {
        try {
            File gameHistory = new File("PrevGames.txt");
            Scanner reader = new Scanner(gameHistory);
            pastGames = new ArrayList<ScrabbleGame>();

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String user = line.substring(0, line.indexOf("|"));
                int score = Integer.parseInt(line.substring(line.indexOf("|") + 1,
                        line.indexOf(",")));
                String mode = line.substring(line.indexOf(",") + 1);
                boolean hard = mode.equals("true");
                pastGames.add(new ScrabbleGame(user, score, hard));
            }
            reader.close();

            Collections.sort(pastGames);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < pastGames.size(); i++) {
            result += (i + 1) + ". " + pastGames.get(i).getUser().getName() +
                    " " + pastGames.get(i).getUser().getScore() + " | Hard mode: " + pastGames.get(i).isHardMode() + "\n";
        }
        return result;
    }

}

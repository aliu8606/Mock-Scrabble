import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard {
    private ArrayList<Game> pastGames;

    public Scoreboard() {
        try {
            File file = new File("PrevGames.txt");
            Scanner reader = new Scanner(file);
            pastGames = new ArrayList<Game>();

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String user = line.substring(0, line.indexOf("|"));
                int score = Integer.parseInt(line.substring(line.indexOf("|") + 1,
                        line.indexOf(",")));
                String mode = line.substring(line.indexOf(",") + 1);
                boolean hard = mode.equals("true");
                pastGames.add(new Game(user, score, hard));
            }
            reader.close();

            Collections.sort(pastGames);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String sb = "";
        for (int i = 0; i < pastGames.size(); i++) {
            sb += (i + 1) + ". " + pastGames.get(i).getUsername() +
                    " " + pastGames.get(i).getScore() + " | Hard mode: " + pastGames.get(i).isHardMode() + "\n";
        }
        return sb;
    }

}

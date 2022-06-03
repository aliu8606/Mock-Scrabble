import java.util.ArrayList;

public class ScrabblePlayer {
    private String name;
    private int score;
    private ArrayList<String> userWords;

    public ScrabblePlayer(String n, int s) {
        name = n;
        score = s;
        userWords = new ArrayList<String>();
    }
    public ScrabblePlayer(String n) {
        name = n;
        score = 0;
        userWords = new ArrayList<String>();
    }

    public ScrabblePlayer() {
        name = "Bot";
        score = 0;
        userWords = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<String> getWords() {
        return userWords;
    }

    public void addScore(int toAdd) {
        score += toAdd;
    }

    public void addWord(String toAdd) {
        userWords.add(toAdd);
    }
}

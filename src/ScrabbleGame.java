import java.io.*;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

public class ScrabbleGame implements Comparable<ScrabbleGame>{
    private ScrabblePlayer user;
    private ScrabblePlayer bot;
    private boolean hardMode;
    private Letter[] letters;
    private ArrayList<Letter> gameBoard;
    private int dictionLen = 2999;

    public ScrabbleGame(String username, boolean diffHard) {
        user = new ScrabblePlayer(username);
        bot = new ScrabblePlayer();

        letters = constructLetters();

        hardMode = diffHard;
        if (hardMode) {
            gameBoard = startBoard(50, 20);
        }
        else {
            gameBoard = startBoard(60, 45);
        }

    }

    //Constructor used for saving
    public ScrabbleGame(String name, int scr, boolean hard) {
        user = new ScrabblePlayer(name, scr);
        hardMode = hard;
    }

    public boolean isHardMode() {
        return hardMode;
    }

    public ScrabblePlayer getUser() {
        return user;
    }

    public void shuffle() {
        Collections.shuffle(gameBoard);
    }

    public String playWord(String word, boolean isBot) {
        //Checks if word is a valid word to play
        if (check(word)) {
            word = word.toUpperCase();
            String[] chars = word.split("");

            for (String chr : chars) {
                for (int i = 0; i < gameBoard.size(); i++) {
                    if (chr.equals(gameBoard.get(i).getLetter())) {
                        //Updating score
                        if (isBot) {
                            bot.addScore(gameBoard.get(i).getPoints());
                        }
                        else {
                            user.addScore(gameBoard.get(i).getPoints());
                        }

                        //Removing Letter from board
                        gameBoard.remove(i);
                        i--;
                        break;
                    }
                }
            }

            //Points are also given for length of the word
            //Plus, word is added to their word lists
            word = word.toLowerCase();
            if (isBot) {
                bot.addScore(chars.length);
                bot.addWord(word);
            }
            else {
                user.addScore(chars.length);
                user.addWord(word);
            }

            return toString();
        }

        if (isBot) {
            return this + "\nBot is thinking...";
        }
        else {
            return this + "\n" + word + " is not a valid word\n";
        }
    }

    public String botTurn() {
        int randLen = (int) (Math.random() * (5 - 2)) + 3;
        ArrayList<String> letters = new ArrayList<>();

        for (int i = 0; i < randLen; i++) {
            letters.add(gameBoard.get(i).getLetter());
        }

        ArrayList<String> possibleWords = new ArrayList<>();
        createWords(possibleWords, letters, 0, letters.size() - 1);

        for (String word : possibleWords) {
            if (check(word)) {
                return playWord(word, true);
            }
        }

        return playWord("n/a", true);
    }

    public void saveGame() {
        try {
            FileWriter writer = new FileWriter("PrevGames.txt", true);
            String gameData = user.getName() + "|" + user.getScore() + "," + hardMode + "\n";
            writer.append(gameData);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String board = "=========================================";
        int count = 0;

        //Formats Letter display
        for (Letter let : gameBoard) {
            if (count % 20 == 0) {
                board += "\n ";
            }
            board += let.getLetter() + " ";
            count++;
        }
        board += "\n=========================================\n";

        //Formats Player Game data
        String data = "Player: " + user.getName() + "\nWordlist: \n";
        for (int i = 0; i < user.getWords().size(); i++) {
            data += (i + 1) + ". " + user.getWords().get(i) + "\n";
        }
        data += "Current Score: " + user.getScore() + "\n\n";

        //Formats Bot Game data
        data += "Bot: " + "\nWordlist: \n";
        for (int i = 0; i < bot.getWords().size(); i++) {
            data += (i + 1) + ". " + bot.getWords().get(i) + "\n";
        }
        data += "Current Score: " + bot.getScore() + "\n";

        return board + data;
    }

    @Override
    public int compareTo(ScrabbleGame other) {
        return other.getUser().getScore() - this.getUser().getScore();
    }

    private void createWords(ArrayList<String> words, ArrayList<String> letters, int index, int end) {
        if (index == end) {
            words.add(String.join(",", letters).replaceAll(",", ""));
            for (int i = 3; i < letters.size(); i++) {
                words.add(String.join(",", letters).replaceAll(",", "").substring(0, i));
            }
        }
        else {
            for (int curr = index; curr <= end; curr++) {
                Collections.swap(letters, index, curr);
                createWords(words, letters, index + 1, end);
                Collections.swap(letters, index, curr);
            }
        }
    }

    private boolean check(String word) {
        word = word.toUpperCase();
        boolean isPresent = false;

        //String array of all the letters in word
        String[] characters = word.split("");

        //Checks if each letter in word is present in gameBoard
        for (String chr : characters) {
            for (Letter let : gameBoard) {
                if (let.getLetter().equals(chr)) {
                    //if yes, it's present
                    isPresent = true;
                    break;
                }
            }
            //if isPresent stayed false, it means that letter is not found, so return false
            if (!isPresent) {
                return false;
            }
            //otherwise, reset for the next loop
            isPresent = false;
        }


            //Check is word is a legitimate English word
            word = word.toLowerCase();
            boolean isValid = false;

            try {
                //opens file and starts reading
                File dictionary = new File
                        ("src/Dictionary");
                Scanner dictionScanner = new Scanner(dictionary);

                while (dictionScanner.hasNextLine()) {

                    //Binary search to find if a line in Dictionary matches target word
                    String currLine = "";
                    int min = 0;
                    int max = dictionLen;
                    int middle;

                    while (min <= max) {
                        middle = (min + max) / 2;
                        try {
                            currLine = Files.readAllLines(Paths.get(dictionary.getAbsolutePath()))
                                    .get(middle).toLowerCase();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }

                        int comparedTo = currLine.compareTo(word);
                        if (comparedTo == 0) {
                            isValid = true;
                            break;
                        } else if (comparedTo < 0) {
                            min = middle + 1;
                        } else {
                            max = middle - 1;
                        }
                    }
                    return isValid;
                }
                dictionScanner.close();
                return false;
            } catch (FileNotFoundException e) {
                System.out.println("Error. Dictionary not found. Was it deleted?");
                return false;
            }

    }

    private Letter[] constructLetters() {
        //this array contains all the letters (uppercase)
        String[] lets = new String[26];
        for (int i = 65; i < 91; i++) {
            String let = (char) i + "";
            lets[i - 65] = let;
        }

        //this array contains all the points respective to the letter
        int[] pts = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3,
                10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

        Letter[] letters = new Letter[26];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new Letter(lets[i], pts[i]);
        }

        return letters;
    }

    /**
     * Returns a randomized list of Letters, which will be used to initialize the gameBoard
     * Takes in numTotal, the total number of Letters to return, and numEasy, which affects
     * how many "easy" Letters will be in the returned list. Lower numEasy will cause a more difficult game, etc.
     * @param numTotal total number of Letters the method should return
     * @param numEasy the number of "easy" Letters this method should include, acts as difficulty
     * @return a list of randomized Letters that will act as the starting gameBoard
     */
    private ArrayList<Letter> startBoard(int numTotal, int numEasy) {
        int countEasy = 0;
        ArrayList<Letter> result = new ArrayList<Letter>();

        for (int i = 0; i < numTotal; i++) {
            int randIdx = (int) (Math.random() * letters.length);

            if (letters[randIdx].getPoints() == 1) {
                if (countEasy < numEasy) {
                    result.add(letters[randIdx]);
                    countEasy++;
                }
                else {
                    i--;
                }
            }
            else {
                result.add(letters[randIdx]);
            }
        }

        return result;
    }
}

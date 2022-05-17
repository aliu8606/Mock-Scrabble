import java.io.*;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

public class Game implements Comparable<Game>{
    private String username;
    private int score;
    private Letter[] letters;
    private ArrayList<String> userWords;
    private ArrayList<Letter> gameBoard;

    public Game(String user) {
        username = user;
        score = 0;
        letters = constructLetters();
        userWords = new ArrayList<String>();

        //maybe add user customization in numTotal and numEasy?
        gameBoard = startBoard(60, 45);
    }

    //Constructor used for testing
    public Game(String user, ArrayList<Letter> board) {
        username = user;
        score = 0;
        letters = constructLetters();
        userWords = new ArrayList<String>();
        gameBoard = board;
    }

    //Constructor used for saving
    public Game(String user, int scr) {
        username = user;
        score = scr;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void shuffle() {
        Collections.shuffle(gameBoard);
    }

    public String updateBoard(String word) {
        if (check(word)) {
            word = word.toUpperCase();
            String[] chars = word.split("");

            for (String chr : chars) {
                for (int i = 0; i < gameBoard.size(); i++) {
                    if (chr.equals(gameBoard.get(i).getLetter())) {
                        //Updating score
                        score += gameBoard.get(i).getPoints();

                        //Removing Letter from board
                        gameBoard.remove(i);
                        i--;
                        break;
                    }
                }
            }

            //Points are also given for length of the word
            score += chars.length;

            //Adds word to userWords
            word = word.toLowerCase();
            userWords.add(word);

            return toString();
        }

        return word + " is not a valid word";
    }

    public void saveGame() {
        try {
            FileWriter writer = new FileWriter("PrevGames.txt", true);
            String gameData = username + "|" + score + "\n";
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
        for (Letter let : gameBoard) {
            if (count % 20 == 0) {
                board += "\n ";
            }
            board += let.getLetter() + " ";
            count++;
        }
        board += "\n=========================================\n";

        String data = "Player: " + username + "\nWordlist: \n";
        for (int i = 0; i < userWords.size(); i++) {
            data += (i + 1) + ". " + userWords.get(i) + "\n";
        }
        data += "Current Score: " + score + "\n";

        return board + data;
    }

    @Override
    public int compareTo(Game other) {
        return other.getScore() - this.getScore();
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
            File dictionary = new File("src/Dictionary");
            Scanner scan = new Scanner(dictionary);

            while(scan.hasNextLine()) {

                //Binary search to find if a line in Dictionary matches target word
                String line = "";
                int bottom = 0;
                int top = 3000; //Total number of lines in Dictionary
                int middle;

                while (bottom <= top){
                    middle = (bottom + top) / 2;
                    try {
                        line = Files.readAllLines(Paths.get("src/Dictionary")).get(middle).toLowerCase();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                        return false;
                    }

                    int compare = line.compareTo(word);
                    if (compare == 0){
                        isValid = true;
                        break;
                    }
                    else if (compare < 0){
                        bottom = middle + 1;
                    }
                    else {
                        top = middle - 1;
                    }
                }
                return isValid;
            }
            scan.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Error. Dictionary not found. Was it deleted?");
            return false;
        }
        return false;
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

        Letter[] tiles = new Letter[26];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Letter(lets[i], pts[i]);
        }

        return tiles;
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

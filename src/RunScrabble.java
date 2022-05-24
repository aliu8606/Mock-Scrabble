import java.util.Scanner;

public class RunScrabble {
    public static void main(String[] args) {
        Scanner scrabbleScanner = new Scanner(System.in);
        String gameMenu = "\nQuit (Q)\n"+ "Access the rules (R)\n" + "See the Scoreboard (S)\n"
                + "Or Start Playing! (P)\n";

        //Welcome screen
        System.out.println("Welcome to mock-Scrabble!");
        System.out.println(gameMenu);

        //Menu system
        String userChoice = scrabbleScanner.nextLine().toUpperCase();
        while (!userChoice.equals("Q")) {
            switch (userChoice) {
                //Printing Rules
                case "R" -> System.out.println("You will be given a set of randomly generated letters.\n" +
                        "From there, you can attempt to make a word with them,\n" +
                        "shuffle the board, or quit the game.\n" +
                        "Some letters will give a higher score.\n" +
                        "Higher points will also be scored for longer words.\n" +
                        "Got any questions? Too bad; go play!\n");
                //Printing Scoreboard
                case "S" -> {
                    Scoreboard newScrabbleScoreboard = new Scoreboard();
                    if (newScrabbleScoreboard.toString().equals("")) {
                        //In the case that there are no previous games...
                        System.out.println("You didn't play any games yet!\n");
                    }
                    else {
                        System.out.println(newScrabbleScoreboard);
                    }
                }
                //Playing a Game
                case "P" -> {
                    //Constructing a Game
                    System.out.print("What would you like your username to be? ");
                    String username = scrabbleScanner.nextLine();
                    System.out.print("\nPlay on hard mode? (Y/N) ");
                    String hardModeStr = scrabbleScanner.nextLine().toUpperCase();
                    boolean hardMode = hardModeStr.equals("Y");
                    ScrabbleGame newScrabbleGame = new ScrabbleGame(username, hardMode);

                    //Opening GUI
                    ScrabbleGUI newGameGUI = new ScrabbleGUI(newScrabbleGame);
                }
                //User did not put in the right command
                default -> System.out.println("Sorry I didn't get that.");
            }
            System.out.println(gameMenu);
            userChoice = scrabbleScanner.nextLine().toUpperCase();
        }
        //Goodbye Message
        System.out.println("See you next time!");
        scrabbleScanner.close();
    }
}

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String menu = "\nQuit (Q)\n"+ "Access the rules (R)\n" + "See the Scoreboard (S)\n"
                + "Or Start Playing! (P)\n";

        //Welcome screen
        System.out.println("Welcome to mock-Scrabble!");
        System.out.println(menu);

        //Menu system
        String choice = input.nextLine().toUpperCase();
        while (!choice.equals("Q")) {
            switch (choice) {
                //Printing Rules
                case "R" -> System.out.println("You will be given a set of randomly generated letters.\n" +
                        "From there, you can attempt to make a word with them,\n" +
                        "shuffle the board, or quit the game.\n" +
                        "Some letters will give a higher score.\n" +
                        "Higher points will also be scored for longer words.\n" +
                        "Got any questions? Too bad; go play!\n");
                //Printing Scoreboard
                case "S" -> {
                    Scoreboard sb = new Scoreboard();
                    if (sb.toString().equals("")) {
                        //In the case that there are no previous games...
                        System.out.println("You didn't play any games yet!\n");
                    }
                    else {
                        System.out.println(sb);
                    }
                }
                //Playing a Game
                case "P" -> {
                    //c String gameMenu = "Would you like to Shuffle (F), Play a word (W), or Quit (X)?\n";

                    //Constructing a Game
                    System.out.print("What would you like your username to be? ");
                    String name = input.nextLine();
                    System.out.print("\nPlay on hard mode? (Y/N) ");
                    String diff = input.nextLine().toUpperCase();
                    boolean hardMode = diff.equals("Y");
                    Game newGame = new Game(name, hardMode);

                    //Opening GUI
                    GUIController newGUI = new GUIController(newGame);

                    /* c
                    System.out.println(newGame);
                    System.out.println(gameMenu);

                    String gameOption = input.nextLine().toUpperCase();

                    while (!gameOption.equals("X")) {
                        if (gameOption.equals("F")) {
                            newGame.shuffle();
                            System.out.println(newGame);
                        } else if (gameOption.equals("W")) {
                            System.out.print("What is your word? ");
                            String word = input.nextLine();

                            System.out.println(newGame.updateBoard(word) + "\n");
                        } else {
                            System.out.println("Sorry I didn't get that.");
                        }
                        System.out.println(gameMenu);
                        gameOption = input.nextLine().toUpperCase();
                    }
                    newGame.saveGame();
                    */
                }
                //User did not put in the right command
                default -> System.out.println("Sorry I didn't get that.");
            }
            System.out.println(menu);
            choice = input.nextLine().toUpperCase();
        }
        //Goodbye Message
        System.out.println("See you next time!");
        input.close();
    }
}

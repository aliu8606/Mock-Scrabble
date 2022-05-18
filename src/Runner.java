import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String menu = "\nQuit (Q)\n"+ "Access the rules (R)\n" + "See the Scoreboard (S)\n"
                + "Or Start Playing! (P)\n";

        System.out.println("Welcome to mock-Scrabble!");
        System.out.println(menu);

        String choice = input.nextLine().toUpperCase();
        while (!choice.equals("Q")) {
            switch (choice) {
                case "R" -> System.out.println("You will be given a set of randomly generated letters.\n" +
                        "From there, you can attempt to make a word with them,\n" +
                        "shuffle the board, or quit the game.\n" +
                        "Some letters will give a higher score.\n" +
                        "Higher points will also be scored for longer words.\n" +
                        "Got any questions? Too bad; go play!\n");
                case "S" -> {
                    Scoreboard sb = new Scoreboard();
                    if (sb.toString().equals("")) {
                        System.out.println("You didn't play any games yet!\n");
                    } else {
                        System.out.println(sb);
                    }
                }
                case "P" -> {
                    String gameMenu = "Would you like to Shuffle (F), Play a word (W), or Quit (X)?\n";
                    System.out.print("What would you like your username to be? ");
                    String name = input.nextLine();
                    Game newGame = new Game(name);
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
                    System.out.println("Whew! What a game!");
                }
                default -> System.out.println("Sorry I didn't get that.");
            }
            System.out.println(menu);
            choice = input.nextLine().toUpperCase();
        }
        System.out.println("See you next time!");
    }
}

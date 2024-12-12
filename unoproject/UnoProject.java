package unoproject;
import java.util.Scanner;

public class UnoProject {
private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to UNO!");
        boolean playAgain = true;

        while (playAgain) {
            playGame();
            playAgain = askPlayAgain();
        }

        System.out.println("Thanks for playing UNO!");
        scanner.close();
    }

    private static void playGame() {
        Game game = new Game();
        
        // Get number of players
        int totalPlayers = getNumberOfPlayers();
        
        // Get number of AI players
        int aiPlayers = getNumberOfAIPlayers(totalPlayers);
        
        // Initialize players
        initializePlayers(game, totalPlayers, aiPlayers);
        
        // Start the game
        game.start();
    }

    private static int getNumberOfPlayers() {
        int players;
        do {
            System.out.println("Enter number of players (2-4):");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
            players = scanner.nextInt();
            if (players < 2 || players > 4) {
                System.out.println("Number of players must be between 2 and 4!");
            }
        } while (players < 2 || players > 4);
        return players;
    }

    private static int getNumberOfAIPlayers(int totalPlayers) {
        int aiPlayers;
        do {
            System.out.println("Enter number of AI players (0-" + (totalPlayers-1) + "):");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
            aiPlayers = scanner.nextInt();
            if (aiPlayers < 0 || aiPlayers >= totalPlayers) {
                System.out.println("Number of AI players must be between 0 and " + (totalPlayers-1) + "!");
            }
        } while (aiPlayers < 0 || aiPlayers >= totalPlayers);
        return aiPlayers;
    }

    private static void initializePlayers(Game game, int totalPlayers, int aiPlayers) {
        int humanPlayers = totalPlayers - aiPlayers;
        scanner.nextLine(); // Clear the buffer
        
        // Initialize human players
        for (int i = 0; i < humanPlayers; i++) {
            System.out.println("Enter name for human player " + (i+1) + ":");
            String name = scanner.nextLine().trim();
            while (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name:");
                name = scanner.nextLine().trim();
            }
            game.addPlayer(new HumanPlayer(name));
        }
        
        // Initialize AI players
        for (int i = 0; i < aiPlayers; i++) {
            game.addPlayer(new AIPlayer("AI-" + (i+1)));
        }
    }

    private static boolean askPlayAgain() {
        while (true) {
            System.out.println("\nWould you like to play again? (yes/no):");
            String response = scanner.next().trim().toLowerCase();
            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            }
            System.out.println("Please answer with 'yes' or 'no'");
        }
    }
}
    
   
    


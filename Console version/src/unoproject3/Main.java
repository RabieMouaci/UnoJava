package unoproject3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask if the user wants to load a saved game
        System.out.println("Do you want to load a saved game? (y/n):");
        String loadChoice = scanner.next().toLowerCase();

        Game game;

        if (loadChoice.equals("y")) {
            System.out.println("Choose a save file to load:");
            System.out.println("1. savegame.ser");
            System.out.println("2. savegame2.ser");
            System.out.println("3. savegame3.ser");

            int saveChoice;
            do {
                System.out.println("Enter 1, 2, or 3 to choose a save file:");
                saveChoice = scanner.nextInt();
            } while (saveChoice < 1 || saveChoice > 3);

            String saveFilePath = switch (saveChoice) {
                case 1 -> "chof win rak hateha nta/savegame.ser";
                case 2 -> "chof win rak hateha nta/resources/savegame2.ser";
                case 3 -> "chof win rak hateha nta/resources/savegame3.ser";
                default -> throw new IllegalStateException("Unexpected value: " + saveChoice);
            };

            try {
                SaveGame savedGame = SaveGame.loadFromFile(saveFilePath);
                game = new Game(
                    savedGame.getPlayers(),
                    savedGame.getDeck(),
                    savedGame.getTable(),
                    savedGame.getTurnManager(),
                    savedGame.isGameWon()
                );
                System.out.println("Game loaded successfully from " + saveFilePath + "!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to load the saved game. Starting a new game...");
                game = createNewGame(scanner);
            }
        } else {
            game = createNewGame(scanner);
        }

        // Start the game
        game.startGame();
    }

    private static Game createNewGame(Scanner scanner) {
        // Ask for the number of players
        System.out.println("Enter the number of players (2, 3, or 4):");
        int numberOfPlayers;
        do {
            numberOfPlayers = scanner.nextInt();
        } while (numberOfPlayers < 2 || numberOfPlayers > 4);

        // Ask for the player types
        List<String> playerTypes = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("Is Player " + i + " a human or AI? Enter 1 for human, 0 for AI:");
            int type;
            do {
                type = scanner.nextInt();
            } while (type != 0 && type != 1);
            playerTypes.add(type == 1 ? "human" : "ai");
        }

        // Initialize the game
        return new Game(numberOfPlayers, playerTypes, "red", "number");
    }
}

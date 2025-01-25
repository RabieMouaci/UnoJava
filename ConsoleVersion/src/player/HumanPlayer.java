package player;

import card.Card;
import game.Game;
import game.SaveGame;
import java.io.IOException;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Card playCard(String tableColor, String tableType, Card topCard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(getName() + "'s Turn");
        System.out.println("Your hand: " + getHand());
        System.out.println("Pick a card by index, 0 to draw a card, or 99 to save and exit:");

        int choice;
        do {
            choice = (scanner.nextInt() - 1);

            // Save and exit logic
            if (choice == 98) {
                saveGame(scanner); // Pass the scanner for file selection
                System.out.println("Game saved. Exiting...");
                System.exit(0); // Exit the game after saving
            }

            // Draw card logic
            if (choice == -1) {
                return null; // Indicate the player chooses to draw a card
            }

            // Play card logic
            if (choice >= 0 && choice < getHand().size()) {
                Card selectedCard = getHand().get(choice);
                if (selectedCard.isPlayable(tableColor, tableType, topCard)) {
                    getHand().remove(selectedCard);
                    return selectedCard; // Return the selected card to be played
                } else {
                    System.out.println("Invalid card selection. Try again.");
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        } while (true);
    }

    private void saveGame(Scanner scanner) {
        // Prompt the user to choose a save file
        System.out.println("Choose a save file to save the game:");
        System.out.println("1. savegame1");
        System.out.println("2. savegame2");
        System.out.println("3. savegame3");

        int saveChoice;
        do {
            System.out.println("Enter 1, 2, or 3 to choose a save file:");
            saveChoice = scanner.nextInt();
        } while (saveChoice < 1 || saveChoice > 3);

        String saveFilePath = switch (saveChoice) {
            case 1 -> "(put ur own path )/src/resources/savegame.ser";
            case 2 -> "(put ur own path )/src/resources/savegame2.ser";
            case 3 -> "(put ur own path )/src/resources/savegame3.ser";
            default -> throw new IllegalStateException("Unexpected value: " + saveChoice);
        };

        // Save the game
        SaveGame saveGame = new SaveGame(
            Game.getPlayers(), // Access the game instance for player data
            Game.getDeck(),
            Game.getTable(),
            Game.getTurnManager(),
            Game.isGameWon()
        );

        try {
            saveGame.saveToFile(saveFilePath);
            System.out.println("Game saved successfully to ");
        } catch (IOException e) {
            System.out.println("Failed to save the game: " + e.getMessage());
        }
    }
}



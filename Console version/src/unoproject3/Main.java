package unoproject3;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for number of players
        System.out.println("Enter the number of players (2, 3, or 4):");
        int numberOfPlayers;
        do {
            numberOfPlayers = scanner.nextInt();
        } while (numberOfPlayers < 2 || numberOfPlayers > 4);

        // Ask for player types (human or AI)
        List<String> playerTypes = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("Is Player " + i + " a human or AI? Enter 1 for human, 0 for AI:");
            int type;
            do {
                type = scanner.nextInt();
            } while (type != 0 && type != 1);
            playerTypes.add(type == 1 ? "human" : "ai");
        }

        // Initialize game
        Game game = new Game(numberOfPlayers, playerTypes, "red", "number");
        game.startGame();
    }
}



package unoproject2;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UnoProject2 {
    public static void main(String[] args) {
      
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to UNO!");
        System.out.print("Enter number of players (2-4): ");
        int numPlayers = scanner.nextInt();
        
        if (numPlayers < 2 || numPlayers > 4) {
            System.out.println("Invalid number of players. Game supports 2-4 players.");
            return;
        }
        
        List<String> playerTypes = new ArrayList<>();
        
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter player " + (i + 1) + " type (human/ai): ");
            String type = scanner.next().toLowerCase();
            
            if (!type.equals("human") && !type.equals("ai")) {
                System.out.println("Invalid player type. Please use 'human' or 'ai'.");
                return;
            }
            playerTypes.add(type);
        }
        
        Game game = new Game(numPlayers, playerTypes);
        game.startGame();
        
        scanner.close();
    }
}

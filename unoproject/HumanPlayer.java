package unoproject;
import java.util.*;
// HumanPlayer.java
public class HumanPlayer extends Player {
    private Scanner scanner;
    
    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }
    
    @Override
    public Card playTurn(Card topCard) {
        displayHand();
        if (!hasValidMove(topCard)) {
            System.out.println("No valid moves available. You must draw a card.");
            return null;
        }
        
        return selectCard(topCard);
    }
    
    private void displayHand() {
        System.out.println("\nYour cards:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.get(i));
        }
    }
    
    private Card selectCard(Card topCard) {
        while (true) {
            System.out.println("Select card (0-" + (hand.size()-1) + ") or -1 to draw:");
            int choice = scanner.nextInt();
            
            if (choice == -1) return null;
            
            if (choice >= 0 && choice < hand.size()) {
                Card selectedCard = hand.get(choice);
                if (selectedCard.isPlayable(topCard)) {
                    return hand.remove(choice);
                } else {
                    System.out.println("Invalid move! Card must match color or number.");
                }
            }
        }
    }
}
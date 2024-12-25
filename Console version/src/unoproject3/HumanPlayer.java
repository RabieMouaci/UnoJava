
package unoproject3;
import java.util.Scanner;


public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

     @Override
    public Card playCard(String tableColor, String tableType, Card topCard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(name +" Turn");
        System.out.println("Your hand: " + hand);
        System.out.println("Pick a card by index  or 0 to draw a card:");

        int choice;
        do {
            choice =( scanner.nextInt()-1);
            if (choice == -1) {
                return null; // Indicate drawing a card
            } else if (choice >= 0 && choice < hand.size()) {
                Card selectedCard = hand.get(choice);
                if (selectedCard.isPlayable(tableColor, tableType, topCard)) {
                    hand.remove(selectedCard);
                    return selectedCard;
                } else {
                    System.out.println("Invalid card selection. Try again.");
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        } while (true);
    }
}


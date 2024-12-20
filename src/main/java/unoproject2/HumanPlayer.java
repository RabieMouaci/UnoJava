
package unoproject2;
import java.util.*;

class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

   @Override
public Card playCard(Card topCard) {
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    scanner.close();
    
    if (choice == -1) {
        return null;
    }
    
    Card chosenCard = getHand().get(choice);
    removeCard(chosenCard);
    
    return chosenCard;

}
}


package unoproject2;
import java.util.*;

public abstract class Player implements PlayerAction {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    @Override
    public boolean hasPlayableCard(Card topCard) {
        for (Card card : hand) {
            if (card.isPlayable(topCard)) {
                return true;
            }
        }
        return false;
    }
}

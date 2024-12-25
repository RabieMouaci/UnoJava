
package unoproject3;
import java.util.ArrayList;
import java.util.List;


public abstract class Player {
    protected String name;
    protected List<Card> hand;

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

    public abstract Card playCard(String tableColor, String tableType, Card topCard);
}

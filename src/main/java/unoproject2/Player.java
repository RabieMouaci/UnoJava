package unoproject2;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
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

    public void drawCard(Card c) {
        hand.add(c);
    }

    public void removeCard(Card c) {
        hand.remove(c);
    }

    // Let each player define how they pick a card to play
    public abstract Card playCard(Card topCard);
}
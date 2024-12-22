package unoproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        String[] colors = {"red", "blue", "green", "yellow"};

        // Minimal: just put some NumberCards
        for (String color : colors) {
            for (int i = 0; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
            }
            // Add a skip or reverse to demonstrate special
            cards.add(new SpecialCard(color, "skip"));
            cards.add(new SpecialCard(color, "reverse"));
        }
        // Add a couple wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard("wild"));
            cards.add(new WildCard("wild_draw4"));
        }
    }

    private void shuffleDeck() {
        Collections.shuffle(cards, new Random());
    }

    public Card drawCard() {
        if (cards.isEmpty()) throw new IllegalStateException("Deck is empty!");
        return cards.remove(0);
    }

    public List<Card> getCards() {
        return cards;
    }
}
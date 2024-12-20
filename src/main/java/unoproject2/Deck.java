
package unoproject2;
import java.util.*;
public class Deck {
    protected List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        String[] colors = {"red", "blue", "green", "yellow"};

        for (String color : colors) {
            cards.add(new NumberCard(color, 0)); // One zero per color
            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
                cards.add(new NumberCard(color, i)); // Two of each number
            }
        }

        for (String color : colors) {
            for (int i = 0; i < 2; i++) {
                cards.add(new SpecialCard(color, "reverse"));
                cards.add(new SpecialCard(color, "skip"));
                cards.add(new SpecialCard(color, "draw2"));
            }
        }

        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard("wild"));
            cards.add(new WildCard("wild_draw4"));
        }
    }

    private void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty!");
        }
        return cards.remove(0);
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        return "Deck contains " + cards.size() + " cards.";
    }
}

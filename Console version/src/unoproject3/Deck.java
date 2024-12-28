package unoproject3;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck implements Serializable{
    private List<Card> cards;
    private static final long serialVersionUID = 1L;
    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public void initializeDeck() {
        String[] colors = {"red", "blue", "green", "yellow"};

       
        for (String color : colors) {
            cards.add(new NumberCard(color, 0)); // One 0 card per color
            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
                cards.add(new NumberCard(color, i)); // Two cards for 1-9
            }
        }

        // Add Special Colored Cards (Skip, Reverse, Draw2)
        for (String color : colors) {
            for (int i = 0; i < 2; i++) {
                cards.add(new SkipCard(color));
                cards.add(new ReverseCard(color));
                cards.add(new Draw2Card(color));
            }
        }

        // Add Wild Cards
        for (int i = 0; i < 4; i++) {
            cards.add(new NormalWildCard());
            cards.add(new WildDraw4Card());
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
    public List<Card> getCards() {
        return cards;
    }
}

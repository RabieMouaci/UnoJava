package unoproject;
// Deck.java
import java.util.*;

public class Deck {
    private List<Card> cards;
    private List<Card> discardPile;
    
    public Deck() {
        cards = new ArrayList<>();
        discardPile = new ArrayList<>();
        initializeDeck();
    }
    
    private void initializeDeck() {
        String[] colors = {"RED", "BLUE", "GREEN", "YELLOW"};
        
        // Add number cards
        for (String color : colors) {
            // One zero per color
            cards.add(new NumberCard(color, 0));
            
            // Two of each number 1-9 per color
            for (int num = 1; num <= 9; num++) {
                cards.add(new NumberCard(color, num));
                cards.add(new NumberCard(color, num));
            }
            
            // Two of each action card per color
            for (int i = 0; i < 2; i++) {
                cards.add(new ActionCard(color, "SKIP"));
                cards.add(new ActionCard(color, "REVERSE"));
                cards.add(new ActionCard(color, "DRAW_TWO"));
            }
        }
        
        // Add wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard(false));  // Regular wild
            cards.add(new WildCard(true));   // Wild Draw Four
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public Card drawCard() {
        if (cards.isEmpty()) {
            reshuffleDiscardPile();
        }
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }
    
    public Card drawInitialCard() {
        Card card;
        do {
            card = drawCard();
        } while (!(card instanceof NumberCard));
        return card;
    }
    
    public void addToDiscardPile(Card card) {
        if (card != null) {
            discardPile.add(card);
        }
    }
    
    private void reshuffleDiscardPile() {
        if (discardPile.isEmpty()) return;
        
        // Keep the top card
        Card topCard = discardPile.remove(discardPile.size() - 1);
        
        // Shuffle the rest back into the deck
        cards.addAll(discardPile);
        discardPile.clear();
        shuffle();
        
        // Put the top card back
        discardPile.add(topCard);
    }
    
    public int getRemainingCards() {
        return cards.size();
    }
}
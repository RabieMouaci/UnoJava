package unoproject;
import java.util.*;
import java.util.stream.Collectors;
// Player.java (Abstract Class)
public abstract class Player {
    protected List<Card> hand;
    protected String name;
    protected boolean hasCalledUno;
    
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hasCalledUno = false;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHandSize() {
        return hand.size();
    }
     public List<Card> getHand() {
        return hand;
    }
     public List<Card> getValidMoves(Card topCard) {
        return hand.stream()
            .filter(card -> card.isPlayable(topCard))
            .collect(Collectors.toList());
    }
 
    public void addCard(Card card) {
        hand.add(card);
        hasCalledUno = false;
    }
      // Add getter method
    public boolean hasCalledUno() {
        return hasCalledUno;
    }
    public boolean hasValidMove(Card topCard) {
        return hand.stream().anyMatch(card -> card.isPlayable(topCard));
    }
    
    public abstract Card playTurn(Card topCard);
    
    public void callUno() {
        if (hand.size() == 2) {
            hasCalledUno = true;
        }
    }
}


package unoproject;
import java.util.*;
import java.util.stream.Collectors;
// AIPlayer.java
public class AIPlayer extends Player {
    public AIPlayer(String name) {
        super(name);
    }
    
    @Override
    public Card playTurn(Card topCard) {
        List<Card> playableCards = hand.stream()
            .filter(card -> card.isPlayable(topCard))
            .collect(Collectors.toList());
            
        if (playableCards.isEmpty()) {
            return null;
        }
        
        int randomIndex = new Random().nextInt(playableCards.size());
        Card selectedCard = playableCards.get(randomIndex);
        hand.remove(selectedCard);
        return selectedCard;
    }
}
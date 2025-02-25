package player;
import card.Card;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class BotPlayer extends Player {
    
    public BotPlayer(String name) {
        super(name);
    }

    @Override
public Card playCard(String tableColor, String tableType, Card topCard) {
    List<Card> playableCards = new ArrayList<>();
    for (Card card : getHand()) {
        if (card.isPlayable(tableColor, tableType, topCard)) {
            playableCards.add(card);
        }
    }

    if (!playableCards.isEmpty()) {
        Card chosenCard = playableCards.get(new Random().nextInt(playableCards.size()));
        getHand().remove(chosenCard);
        return chosenCard; 
    }

    return null; // No playable card, bot needs to draw
}
}



package unoproject2;
import java.util.*;

public class AIPlayer extends Player {
    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public Card playCard(Card topCard) {
        List<Card> playableCards = new ArrayList<>();
        for (Card card : getHand()) {
            if (card.isPlayable(topCard)) {
                playableCards.add(card);
            }
        }

        if (!playableCards.isEmpty()) {
            Card chosenCard = playableCards.get(new Random().nextInt(playableCards.size()));
            removeCard(chosenCard);
            return chosenCard;
        }

        return null; // No valid card, requires drawing a card
    }
}


package unoproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIPlayer extends Player {
    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public Card playCard(Card topCard) {
        List<Card> playable = new ArrayList<>();
        for (Card c : getHand()) {
            if (c.isPlayable(topCard)) {
                playable.add(c);
            }
        }
        if (!playable.isEmpty()) {
            // pick random playable
            Card chosen = playable.get(new Random().nextInt(playable.size()));
            return chosen;
        }
        // if no card playable, return null (which means draw in the Game logic)
        return null;
    }
}
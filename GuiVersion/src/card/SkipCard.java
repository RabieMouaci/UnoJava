package card;

import interfaces.SkipNextPlayer;
import game.TurnManager;
import card.ColoredCard;
import card.Card;


public class SkipCard extends ColoredCard implements SkipNextPlayer {
   
    public SkipCard(String color) {
        super(color, "skip");
    }

    @Override
    public void skipNextPlayer(TurnManager turnManager) {
        turnManager.moveToNextPlayer();
    }
    @Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return this.getColor().equals(tableColor) || "skip".equals(tableType);
    }
    @Override
public String toString() {
    return getColor() + " " + getType();
}

}


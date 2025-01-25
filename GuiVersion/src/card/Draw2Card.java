package card;

import game.Deck;
import interfaces.DrawNextPlayer;
import interfaces.SkipNextPlayer;
import game.TurnManager;
import player.Player;

public class Draw2Card extends ColoredCard implements DrawNextPlayer, SkipNextPlayer{

    public Draw2Card(String color) {
        super(color, "draw2");
    }

    @Override
    public void drawNextPlayer(Player nextPlayer, Deck deck) {
        nextPlayer.drawCard(deck.drawCard());
        nextPlayer.drawCard(deck.drawCard());
    }

    @Override
    public void skipNextPlayer(TurnManager turnManager) {
        turnManager.moveToNextPlayer();
    }
    @Override
public String toString() {
    return getColor() + " " + getType();
}
@Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return this.getColor().equals(tableColor) || "draw2".equals(tableType);
    }
}


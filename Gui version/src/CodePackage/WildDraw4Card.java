package CodePackage;


public class WildDraw4Card extends WildCard implements DrawNextPlayer, SkipNextPlayer {
    private static final long serialVersionUID = 1L;
    public WildDraw4Card() {
        super("wild_draw4");
    }

    @Override
    public void drawNextPlayer(Player nextPlayer, Deck deck) {
        for (int i = 0; i < 4; i++) {
            nextPlayer.drawCard(deck.drawCard());
        }
    }

    @Override
    public void skipNextPlayer(TurnManager turnManager) {
        turnManager.moveToNextPlayer();
    }
     @Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return true; // WildDraw4 is always playable
    }
    @Override
public String toString() {
    if (getChosenColor() == null) {
        return getType() + " (Choose Color)";
    }
    return getType() + " (" + getChosenColor() + ")";
}

}


package unoproject;
import java.util.*;
// WildCard.java
public class WildCard extends Card {
    private boolean isDrawFour;
    
    public WildCard(boolean isDrawFour) {
        super("BLACK");
        this.isDrawFour = isDrawFour;
    }
    public boolean isDrawFour() {
        return isDrawFour;
    }
    @Override
    public boolean isPlayable(Card topCard) {
        return true; // Wild cards can always be played
    }
    
     
    @Override
    public void executeAction(GameInterface game) {
        if (isDrawFour) {
            game.addToDrawStack(4);
        }
    }
    
    @Override
    public String toString() {
        return "WILD" + (isDrawFour ? " DRAW FOUR" : "");
    }
}
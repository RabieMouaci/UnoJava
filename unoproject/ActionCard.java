package unoproject;
import java.util.*;
// ActionCard.java
public class ActionCard extends Card {
    private String actionType; // SKIP, REVERSE, DRAW_TWO
    
    public ActionCard(String color, String actionType) {
        super(color);
        this.actionType = actionType;
    }
    public String getActionType() {
        return actionType;
    }
    @Override
    public boolean isPlayable(Card topCard) {
        return this.color.equals(topCard.getColor()) || 
               (topCard instanceof ActionCard && 
                this.actionType.equals(((ActionCard) topCard).getActionType()));
    }
    
     @Override
    public void executeAction(GameInterface game) {
        switch(actionType) {
            case "SKIP":
                game.skipNextPlayer();
                break;
            case "REVERSE":
                game.reverseDirection();
                break;
            case "DRAW_TWO":
                game.addToDrawStack(2);
                break;
        }
    }
    
    @Override
    public String toString() {
        return color + " " + actionType;
    }
}
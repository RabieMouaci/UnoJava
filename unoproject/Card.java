package unoproject;
import java.util.*;

public abstract class Card {
    protected String color;
    
    public Card(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public abstract boolean isPlayable(Card topCard);
    public abstract void executeAction(GameInterface game);
    public abstract String toString();
}

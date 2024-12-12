package unoproject;
import java.util.*;
// NumberCard.java
public class NumberCard extends Card {
    private int number;
    
    public NumberCard(String color, int number) {
        super(color);
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    
    @Override
    public boolean isPlayable(Card topCard) {
        if (topCard instanceof NumberCard) {
            return this.color.equals(topCard.getColor()) || 
                   this.number == ((NumberCard) topCard).getNumber();
        }
        return this.color.equals(topCard.getColor());
    }
    
    
      @Override
    public void executeAction(GameInterface game) {
        game.nextTurn();
    }

    
    @Override
    public String toString() {
        return color + " " + number;
    }
}

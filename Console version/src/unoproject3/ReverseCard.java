
package unoproject3;


public class ReverseCard extends ColoredCard {
    public ReverseCard(String color) {
        super(color, "reverse");
    }

    public void reverseDirection(TurnManager turnManager) {
        turnManager.reverseDirection();
    }
     @Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return this.color.equals(tableColor) || "reverse".equals(tableType);
    }
    @Override
public String toString() {
    return getColor() + " " + getType();
}

}




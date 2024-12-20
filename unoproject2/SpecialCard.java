package unoproject2;

public class SpecialCard extends Card {
    public SpecialCard(String color, String type) {
        super(color, type, -1); // Special cards have no numerical value
    }

    @Override
public boolean isPlayable(Card topCard) {
    // Can play if same color or same action type (skip, reverse, draw2)
    return this.getColor().equals(topCard.getColor()) || 
           this.getType().equals(topCard.getType());
}
}
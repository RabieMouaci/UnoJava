package unoproject2;

public class SpecialCard extends Card {
    public SpecialCard(String color, String type) {
        super(color, type, -1);
    }

    @Override
    public boolean isPlayable(Card topCard) {
        // same color or same type
        return this.getColor().equals(topCard.getColor()) || this.getType().equals(topCard.getType());
    }
}
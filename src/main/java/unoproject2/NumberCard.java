package unoproject2;

class NumberCard extends Card {
    public NumberCard(String color, int value) {
        super(color, "number", value);
    }

    @Override
    public boolean isPlayable(Card topCard) {
        // Minimal logic: same color or same value
        return this.getColor().equals(topCard.getColor()) || this.getValue() == topCard.getValue();
    }
}
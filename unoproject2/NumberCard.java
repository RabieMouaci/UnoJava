package unoproject2;


class NumberCard extends Card {
    public NumberCard(String color, int value) {
        super(color, "number", value);
    }

    @Override
    public boolean isPlayable(Card topCard) {
        // Handle wild cards with chosen color
        if (topCard instanceof WildCard) {
            WildCard wildTopCard = (WildCard) topCard;
            return this.getColor().equals(wildTopCard.getChosenColor());
        }
        // Normal number card matching
        return this.getColor().equals(topCard.getColor()) || 
               this.getValue() == topCard.getValue();
    }
}


package unoproject2;

public class WildCard extends Card {
    private String chosenColor; // The color chosen after playing the card

    public WildCard(String type) {
        super("wild", type, -1); // Wild cards have no specific color initially
    }

    public String getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(String chosenColor) {
        this.chosenColor = chosenColor;
    }

    @Override
public boolean isPlayable(Card topCard) {
  
    if (this.getChosenColor() != null) {
        return this.getChosenColor().equals(topCard.getColor());
    }

    return true;
}

     @Override
public String toString() {
    if (chosenColor != null) {
        return getType() + " (" + chosenColor + ")";
    }
    return getType();
}
}

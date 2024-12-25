package unoproject3;


public abstract class WildCard extends Card {
    protected String chosenColor;

    public WildCard(String type) {
        super(type);
    }

    public void setChosenColor(String chosenColor) {
        this.chosenColor = chosenColor;
    }

    public String getChosenColor() {
        return chosenColor;
    }

     @Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return true; // Wild cards are always playable
    }
}


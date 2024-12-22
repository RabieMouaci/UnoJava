package unoproject2;

public class WildCard extends Card {
    private String chosenColor; // assigned after play

    public WildCard(String type) {
        super("wild", type, -1);
    }

    public String getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(String chosenColor) {
        this.chosenColor = chosenColor;
    }

    @Override
    public boolean isPlayable(Card topCard) {
        // wild is always playable
        return true;
    }

    @Override
    public String toString() {
        if (chosenColor != null) {
            return getType() + "(" + chosenColor + ")";
        }
        return getType();
    }
}
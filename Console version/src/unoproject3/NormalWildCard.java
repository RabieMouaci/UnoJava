
package unoproject3;



public class NormalWildCard extends WildCard {

    public NormalWildCard() {
        super("wild");
    }

    public void changeColor(String newColor) {
        this.chosenColor = newColor;
    }
    @Override
public String toString() {
    if (chosenColor == null) {
        return getType() + " (Choose Color)";
    }
    return getType() + " (" + chosenColor + ")";
}
@Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return true; // Wild cards are always playable
    }
}


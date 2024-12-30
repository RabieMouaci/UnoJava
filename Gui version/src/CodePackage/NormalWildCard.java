package CodePackage;


public class NormalWildCard extends WildCard {

    public NormalWildCard() {
        super("wild");
    }

    public void changeColor(String newColor) {
      setChosenColor(newColor);
    }
    @Override
public String toString() {
    if (getChosenColor() == null) {
        return getType() + " (Choose Color)";
    }
    return getType() + " (" + getChosenColor() + ")";
}
@Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return true; // Wild cards are always playable
    }
}


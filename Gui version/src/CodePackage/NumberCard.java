package CodePackage;


public class NumberCard extends ColoredCard{
    private int number;

    public NumberCard(String color, int number) {
        super(color, "number");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        if (topCard instanceof NumberCard) {
            NumberCard topNumberCard = (NumberCard) topCard;
            return this.getColor().equals(tableColor) || this.number == topNumberCard.getNumber();
        }
        return this.getColor().equals(tableColor);
    }
    @Override
public String toString() {
    return getColor() + " " + getType() + " " + getNumber();
}

}


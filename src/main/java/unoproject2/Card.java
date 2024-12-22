
package unoproject2;


public abstract class Card {
    private String color;
    private String type;  // e.g., "number", "skip", "reverse", "draw2", "wild", "wild_draw4"
    private int value;    // used for numbers

    public Card(String color, String type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public abstract boolean isPlayable(Card topCard);

    @Override
    public String toString() {
        if (type.equals("number")) return color + " " + value;
        return color + " " + type;
    }
}



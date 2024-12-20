
package unoproject2;


public abstract class Card {
    private String color; // red, blue, green, yellow, or "wild"
    private String type; // "number", "skip", "reverse", "draw2", "wild", "wild_draw4"
    private int value;   // For number cards only

    public Card(String color, String type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public abstract boolean isPlayable(Card topCard);

    @Override
    public String toString() {
        if (type.equals("number")) {
            return color + " " + value;
        }
        return color + " " + type;
    }
}

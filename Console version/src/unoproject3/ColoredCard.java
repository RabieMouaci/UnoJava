package unoproject3;

public abstract class ColoredCard extends Card {
    private String color;
    

    public ColoredCard(String color, String type) {
        super(type);
        this.color = color;
    }
 
    public String getColor() {
        return color;
    }

    @Override
    public boolean isPlayable(String tableColor, String tableType, Card topCard) {
        return this.color.equals(tableColor) || this.getType().equals(tableType);
    }
}


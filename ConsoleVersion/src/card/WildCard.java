package card;


import java.io.Serializable;

public abstract class WildCard extends Card implements Serializable{
    private static final long serialVersionUID = 1L;
    private String chosenColor;

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


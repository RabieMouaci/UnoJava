package card;
import java.io.Serializable;

public abstract class Card implements Serializable{
    private String type;
    private static final long serialVersionUID = 1L;

    public Card(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

  
    public abstract boolean isPlayable(String tableColor, String tableType, Card topCard);

    @Override
public abstract String toString();

}


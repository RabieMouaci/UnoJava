
package unoproject3;
import java.io.Serializable;


public abstract class Card {
    protected String type;

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


package unoproject3;

import java.io.Serializable;


public class Table implements Serializable{
    private static final long serialVersionUID = 1L;
    private String tableColor;
    private String tableType;
    private Card topCard;

    public Table(String initialColor, String initialType) {
        this.tableColor = initialColor;
        this.tableType = initialType;
    }

    public String getTableColor() {
        return tableColor;
    }

    public void setTableColor(String tableColor) {
        this.tableColor = tableColor;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }
}
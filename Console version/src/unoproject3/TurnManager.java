
package unoproject3;


public class TurnManager {
    private int currentPlayerIndex;
    private int direction;
    private int totalPlayers;

    public TurnManager(int totalPlayers) {
        this.totalPlayers = totalPlayers;
        this.currentPlayerIndex = 0;
        this.direction = 1;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + direction + totalPlayers) % totalPlayers;
    }

    public void reverseDirection() {
        direction *= -1;
    }
}



package unoproject2;

public class TurnManager {
    private int currentPlayerIndex;
    private boolean gameDirection; 
    private int totalPlayers;

    public TurnManager(int totalPlayers) {
        this.totalPlayers = totalPlayers;
        this.currentPlayerIndex = 0;
        this.gameDirection = true;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void reverseDirection() {
        gameDirection = !gameDirection;
    }

    public void moveToNextPlayer() {
        if (gameDirection) {
            currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + totalPlayers) % totalPlayers;
        }
    }
}

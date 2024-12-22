package unoproject2;

public class TurnManager {
    private int currentPlayerIndex;
    private int direction;  // 1 for normal, -1 for reverse
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
        System.out.println("[TurnManager] moveToNextPlayer -> " + currentPlayerIndex);
    }

    public void reverseDirection() {
        direction *= -1;
        System.out.println("[TurnManager] reverseDirection -> " + (direction == 1 ? "forward" : "reverse"));
    }
}
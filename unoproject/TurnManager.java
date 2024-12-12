package unoproject;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
// TurnManager.java
public class TurnManager {
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean isClockwise;
    
    public TurnManager(List<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.isClockwise = true;
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    public void nextTurn() {
        if (isClockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
    }
    
    public void skipPlayer() {
        nextTurn(); // Skip current player
    }
    
    public void reverseDirection() {
        isClockwise = !isClockwise;
    }
    
    public boolean isClockwise() {
        return isClockwise;
    }
    
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    
    public Player getNextPlayer() {
        int nextIndex;
        if (isClockwise) {
            nextIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            nextIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
        return players.get(nextIndex);
    }
}
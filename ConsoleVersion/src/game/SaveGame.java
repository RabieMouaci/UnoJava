package game;
import player.Player;
import java.io.*;
import java.util.List;

public class SaveGame implements Serializable {
    private List<Player> players;
    private Deck deck;
    private Table table;
    private TurnManager turnManager;
    private boolean gameWon;

    public SaveGame(List<Player> players, Deck deck, Table table, TurnManager turnManager, boolean gameWon) {
        this.players = players;
        this.deck = deck;
        this.table = table;
        this.turnManager = turnManager;
        this.gameWon = gameWon;
    }
    //durring saving time (in humanplayer class
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }
    //during loading (in main class)
    public static SaveGame loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (SaveGame) ois.readObject();
        }
    }

    // Getters
    public List<Player> getPlayers() { return players; }
    public Deck getDeck() { return deck; }
    public Table getTable() { return table; }
    public TurnManager getTurnManager() { return turnManager; }
    public boolean isGameWon() { return gameWon; }
}


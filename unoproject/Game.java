package unoproject;
// Game.java
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
public class Game implements GameInterface {
    private List<Player> players;
    private Deck deck;
    private Card topCard;
    private TurnManager turnManager;
    private int drawStack;
    private boolean isGameOver;
    private Scanner scanner;

    public Game() {
        players = new ArrayList<>();
        deck = new Deck();
        drawStack = 0;
        isGameOver = false;
        scanner = new Scanner(System.in);
    }

    

    private void initializeGame() {
        deck.shuffle();
        dealInitialCards();
        turnManager = new TurnManager(players);  // Initialize TurnManager with players
        topCard = deck.drawInitialCard();
        deck.addToDiscardPile(topCard);
    }
 public void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
        }
    }

    public void start() {
        if (!canStart()) {
            System.out.println("Cannot start game - need 2-4 players");
            return;
        }

        initializeGame();
        gameLoop();
    }

    private boolean canStart() {
        return players.size() >= 2 && players.size() <= 4;
    }
    private void dealInitialCards() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }
    }

    private void gameLoop() {
        while (!isGameOver) {
            Player currentPlayer = turnManager.getCurrentPlayer();
            displayGameState(currentPlayer);
            handlePlayerTurn(currentPlayer);
            
            if (currentPlayer.getHandSize() == 0) {
                announceWinner(currentPlayer);
                isGameOver = true;
            }
        }
    }

    private void displayGameState(Player currentPlayer) {
        System.out.println("\n====================");
        System.out.println("Top Card: " + topCard);
        System.out.println("Current Player: " + currentPlayer.getName());
        if (drawStack > 0) {
            System.out.println("Draw Stack: +" + drawStack);
        }
        System.out.println("Direction: " + (turnManager.isClockwise() ? "Clockwise" : "Counter-clockwise"));
        System.out.println("====================\n");
    }

    private void handlePlayerTurn(Player currentPlayer) {
        // Handle draw stack first
        if (drawStack > 0) {
            handleDrawStack(currentPlayer);
            return;
        }

        Card playedCard = currentPlayer.playTurn(topCard);
        
        if (playedCard == null) {
            // Player draws a card
            Card drawnCard = deck.drawCard();
            currentPlayer.addCard(drawnCard);
            System.out.println(currentPlayer.getName() + " drew a card");
            turnManager.nextTurn();
        } else {
            // Player plays a card
            handlePlayedCard(playedCard, currentPlayer);
        }
    }

    private void handleDrawStack(Player currentPlayer) {
        // Check if player can play a stacking card
        boolean canStack = currentPlayer.getHand().stream()
            .anyMatch(card -> (card instanceof ActionCard && 
                             ((ActionCard)card).getActionType().equals("DRAW_TWO")) ||
                            (card instanceof WildCard && ((WildCard)card).isDrawFour()));

        if (!canStack) {
            // Player must draw cards
            for (int i = 0; i < drawStack; i++) {
                currentPlayer.addCard(deck.drawCard());
            }
            System.out.println(currentPlayer.getName() + " drew " + drawStack + " cards");
            drawStack = 0;
            turnManager.nextTurn();
        } else {
            // Player can choose to stack or draw
            Card playedCard = currentPlayer.playTurn(topCard);
            if (playedCard == null) {
                // Player chose to draw
                for (int i = 0; i < drawStack; i++) {
                    currentPlayer.addCard(deck.drawCard());
                }
                System.out.println(currentPlayer.getName() + " drew " + drawStack + " cards");
                drawStack = 0;
                turnManager.nextTurn();
            } else {
                handlePlayedCard(playedCard, currentPlayer);
            }
        }
    }

    private void handlePlayedCard(Card playedCard, Player currentPlayer) {
        System.out.println(currentPlayer.getName() + " played: " + playedCard);
        
        // Handle Wild Cards
        if (playedCard instanceof WildCard) {
            handleWildCard(playedCard, currentPlayer);
        }
        
        topCard = playedCard;
        deck.addToDiscardPile(playedCard);
        playedCard.executeAction(this);
        
        // Check UNO
        if (currentPlayer.getHandSize() == 1 && !currentPlayer.hasCalledUno()) {
            System.out.println(currentPlayer.getName() + " forgot to call UNO! Drawing 2 cards.");
            currentPlayer.addCard(deck.drawCard());
            currentPlayer.addCard(deck.drawCard());
        }
    }

    private void handleWildCard(Card wildCard, Player currentPlayer) {
        String color;
        if (currentPlayer instanceof HumanPlayer) {
            color = getColorChoice();
        } else {
            // AI randomly chooses color
            String[] colors = {"RED", "BLUE", "GREEN", "YELLOW"};
            color = colors[new Random().nextInt(colors.length)];
        }
        wildCard.setColor(color);
        System.out.println("Color changed to: " + color);
    }

    private String getColorChoice() {
        while (true) {
            System.out.println("Choose color (RED/BLUE/GREEN/YELLOW):");
            String color = scanner.next().toUpperCase();
            if (color.matches("RED|BLUE|GREEN|YELLOW")) {
                return color;
            }
            System.out.println("Invalid color! Please choose again.");
        }
    }

    public void skipNextPlayer() {
        turnManager.skipPlayer();
    }

    public void reverseDirection() {
        turnManager.reverseDirection();
    }

    public void addToDrawStack(int cards) {
        drawStack += cards;
    }

    private void announceWinner(Player winner) {
        System.out.println("\n======================");
        System.out.println("🎉 " + winner.getName() + " WINS! 🎉");
        System.out.println("======================");
    }

    public void nextTurn() {
        turnManager.nextTurn();
    }
}
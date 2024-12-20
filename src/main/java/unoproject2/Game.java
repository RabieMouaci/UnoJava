package unoproject2;

import java.util.*;
import javax.swing.*;

public class Game {
    private Deck deck;
    private List<Player> players;
    private Card topCard;
    private TurnManager turnManager;
    private UnoGameGUI gui;

    public Game(int numPlayers, List<String> playerTypes) {
        deck = new Deck();
        players = new ArrayList<>();
        initializePlayers(numPlayers, playerTypes);
        distributeCards();
        initializeTopCard();
        turnManager = new TurnManager(numPlayers);

        // Initialize GUI in a separate thread
        SwingUtilities.invokeLater(() -> {
            gui = new UnoGameGUI();
            gui.setVisible(true);
            updateGUI(); // Update GUI after it's visible
        });
    }

    private void initializePlayers(int numPlayers, List<String> playerTypes) {
        for (int i = 0; i < numPlayers; i++) {
            if (playerTypes.get(i).equals("human")) {
                players.add(new HumanPlayer("Player " + (i + 1)));
            } else {
                players.add(new AIPlayer("AI " + (i + 1)));
            }
        }
    }

    private void distributeCards() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck.drawCard());
            }
        }
    }

    private void initializeTopCard() {
        do {
            topCard = deck.drawCard();
            if (!(topCard instanceof NumberCard)) {
                deck.cards.add(topCard);
                Collections.shuffle(deck.cards);
            }
        } while (!(topCard instanceof NumberCard));
    }

    public void startGame() {
        boolean gameWon = false;
        
        while (!gameWon) {
            Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
            
            // Update GUI for the current player's hand
            if (currentPlayer instanceof HumanPlayer) {
                gui.updatePlayerHand(currentPlayer.getHand());
            }

            Card playedCard = currentPlayer.playCard(topCard);

            if (playedCard == null) { // No playable card
                currentPlayer.drawCard(deck.drawCard());
                turnManager.moveToNextPlayer();
                updateGUI();
                continue;
            }

            topCard = playedCard;
            handleCardEffect(playedCard);
            updateGUI();

            if (currentPlayer.getHand().isEmpty()) { // Current player wins
                gui.showGameOver(currentPlayer.getName());
                gameWon = true;
            } else {
                turnManager.moveToNextPlayer();
            }
        }

        // Optionally dispose of the GUI after the game ends
        gui.dispose(); 
    }

    private void handleCardEffect(Card card) {
        if (card instanceof SpecialCard) {
            switch (card.getType()) {
                case "reverse":
                    turnManager.reverseDirection();
                    gui.showMessage("Game direction reversed!");
                    break;
                case "skip":
                    turnManager.moveToNextPlayer();
                    gui.showMessage("Next player skipped!");
                    break;
                case "draw2":
                    applyDrawCards(2);
                    break;
            }
        } else if (card instanceof WildCard) {
            WildCard wildCard = (WildCard) card;
            if (wildCard.getType().equals("wild_draw4")) {
                applyDrawCards(4);
            }

            Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
            String[] colors = {"Red", "Blue", "Green", "Yellow"};
            
            if (currentPlayer instanceof HumanPlayer) {
                int choice = gui.showColorChooser(colors);
                wildCard.setChosenColor(colors[choice].toLowerCase());
            } else { // AI chooses a random color
                wildCard.setChosenColor(colors[new Random().nextInt(colors.length)].toLowerCase());
            }
            
            gui.showMessage("Color changed to: " + wildCard.getChosenColor());
        }
    }

    private void applyDrawCards(int numCards) {
        turnManager.moveToNextPlayer();
        Player nextPlayer = players.get(turnManager.getCurrentPlayerIndex());

        gui.showMessage(nextPlayer.getName() + " must draw " + numCards + " cards.");
        for (int i = 0; i < numCards; i++) {
            nextPlayer.drawCard(deck.drawCard());
        }

        turnManager.moveToNextPlayer();
        updateGUI();
    }

    private void updateGUI() {
        Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
        
        // Ensure GUI is initialized before updating
        if (gui != null) { 
            gui.updateTopCard(getCardImagePath(topCard));
            gui.updateGameStatus(currentPlayer.getName() + "'s turn");
        }
    }

    private String getCardImagePath(Card card) {
        if (card instanceof WildCard) {
            WildCard wildCard = (WildCard) card;
            if (wildCard.getChosenColor() != null) {
                return "/resources/images/" + card.getType() + "_" + wildCard.getChosenColor() + ".jpg";
            }
            return "/resources/images/" + card.getType() + ".jpg";
        }
        
        return "/resources/images/" + card.getColor() + "_" + card.getType() +
               (card instanceof NumberCard ? card.getValue() : "") + ".jpg";
    }
}

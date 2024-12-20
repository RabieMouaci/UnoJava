package unoproject2;

import java.util.*;
import java.util.List;
import javax.swing.*;


public class Game {
    private Deck deck;
    private List<Player> players;
    private Card topCard;
    private TurnManager turnManager;
    private GameStateDisplay gameStateDisplay;
    private UnoGameGUI gui;

    public Game(int numPlayers, List<String> playerTypes) {
        deck = new Deck();
        players = new ArrayList<>();
        initializePlayers(numPlayers, playerTypes);
        distributeCards();
        initializeTopCard();
        turnManager = new TurnManager(numPlayers);
        gameStateDisplay = new GameStateDisplay();
        
        SwingUtilities.invokeLater(() -> {
            gui = new UnoGameGUI();
            gui.setVisible(true);
            updateGUI();
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

    private void updateGUI() {
        Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
        gui.updateTopCard(getCardImagePath(topCard));
        if (currentPlayer instanceof HumanPlayer) {
            gui.updatePlayerHand(currentPlayer.getHand());
        }
        gui.updateGameStatus(currentPlayer.getName() + "'s turn");
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

    public void startGame() {
        boolean gameWon = false;
        while (!gameWon) {
            Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
            updateGUI();

            Card playedCard = currentPlayer.playCard(topCard);
            
            if (playedCard == null) {
                System.out.println(currentPlayer.getName() + " draws a card.");
                currentPlayer.drawCard(deck.drawCard());
                turnManager.moveToNextPlayer();
                updateGUI();
                continue;
            }
            
            topCard = playedCard;
            System.out.println(currentPlayer.getName() + " played: " + playedCard);
            handleCardEffect(playedCard);
            updateGUI();

            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getName() + " has won the game!");
                gui.showGameOver(currentPlayer.getName());
                gameWon = true;
            } else {
                turnManager.moveToNextPlayer();
            }
        }
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
            if (currentPlayer instanceof HumanPlayer) {
                String[] options = {"Red", "Blue", "Green", "Yellow"};
                int choice = gui.showColorChooser(options);
                String chosenColor = options[choice].toLowerCase();
                wildCard.setChosenColor(chosenColor);
            } else {
                String[] colors = {"red", "blue", "green", "yellow"};
                wildCard.setChosenColor(colors[new Random().nextInt(colors.length)]);
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
}

package unoproject2;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class Game {
    private Deck deck;
    private List<Player> players;
    private Card topCard;
    private TurnManager turnManager;
    private UnoGameGUI gui;
    private boolean gameWon;

    public Game(int numPlayers, List<String> playerTypes, UnoGameGUI gui) {
        System.out.println("[Game] Constructor -> " + numPlayers + " players.");
        this.gui = gui;

        deck = new Deck();
        players = new java.util.ArrayList<>();
        initializePlayers(numPlayers, playerTypes);

        distributeCards();

        // We do NOT call updateTopCard here (to avoid NullPointer).
        // Just pick a top card internally.
        initializeTopCard();

        turnManager = new TurnManager(numPlayers);
        gameWon = false;

        System.out.println("[Game] Constructor done.");
    }

    private void initializePlayers(int numPlayers, List<String> playerTypes) {
        for (int i = 0; i < numPlayers; i++) {
            String type = playerTypes.get(i);
            if (type.equals("human")) {
                players.add(new HumanPlayer("Player " + (i + 1)));
            } else {
                players.add(new AIPlayer("AI " + (i + 1)));
            }
        }
    }

    private void distributeCards() {
        System.out.println("[Game] distributeCards()");
        for (Player p : players) {
            for (int i = 0; i < 5; i++) {
                p.drawCard(deck.drawCard());
            }
        }
    }

    private void initializeTopCard() {
        System.out.println("[Game] initializeTopCard()");
        do {
            topCard = deck.drawCard();
            if (!(topCard instanceof NumberCard)) {
                deck.getCards().add(topCard);
                java.util.Collections.shuffle(deck.getCards());
            }
        } while (!(topCard instanceof NumberCard));
        // DO NOT CALL gui.updateTopCard() here -> topCardLabel doesn't exist yet
        System.out.println("[Game] topCard chosen: " + topCard.toString());
    }

    public void startGame() {
        System.out.println("[Game] startGame() called.  Proceeding to first turn...");

        // Now it's safe to update the top card label
        gui.updateTopCard(getCardImagePath(topCard));

        proceedToNextTurn();
        System.out.println("[Game] startGame() done.");
    }

    private void proceedToNextTurn() {
        if (gameWon) return;

        Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
        System.out.println("[Game] proceedToNextTurn -> " + currentPlayer.getName());
        gui.updateCurrentPlayer(currentPlayer);

        if (currentPlayer instanceof HumanPlayer) {
            gui.enableHumanPlayerActions(true);
        } else {
            gui.enableHumanPlayerActions(false);
            SwingWorker<Card, Void> aiWorker = new SwingWorker<>() {
                @Override
                protected Card doInBackground() throws Exception {
                    // AI "thinking" time
                    Thread.sleep(1000);
                    return currentPlayer.playCard(topCard);
                }

                @Override
                protected void done() {
                    try {
                        Card played = get();
                        if (played != null) {
                            System.out.println("[Game] AI played: " + played);
                            currentPlayer.removeCard(played);
                            topCard = played;
                            gui.updateTopCard(getCardImagePath(topCard));
                            handleCardEffect(played, currentPlayer);

                            if (currentPlayer.getHand().isEmpty()) {
                                gui.showGameOver(currentPlayer.getName());
                                gameWon = true;
                                return;
                            }
                        } else {
                            // AI draws
                            Card c = deck.drawCard();
                            currentPlayer.drawCard(c);
                            System.out.println("[Game] AI drew: " + c);
                            gui.showMessage(currentPlayer.getName() + " (AI) drew a card.");
                        }
                        turnManager.moveToNextPlayer();
                        proceedToNextTurn();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        gui.showMessage("Error in AI turn.");
                    }
                }
            };
            aiWorker.execute();
        }
    }

    public void humanPlayerPlayedCard(int cardIndex) {
        Player current = players.get(turnManager.getCurrentPlayerIndex());
        if (!(current instanceof HumanPlayer)) {
            System.out.println("[Game] It's not a human player's turn.");
            return;
        }
        System.out.println("[Game] humanPlayerPlayedCard -> index " + cardIndex);
        if (cardIndex < 0 || cardIndex >= current.getHand().size()) {
            gui.showMessage("Invalid card selection!");
            return;
        }
        Card chosen = current.getHand().get(cardIndex);
        if (!chosen.isPlayable(topCard)) {
            gui.showMessage("That card is not playable!");
            return;
        }
        current.removeCard(chosen);
        topCard = chosen;
        gui.updateTopCard(getCardImagePath(topCard));
        gui.updatePlayerHand(current.getHand());

        handleCardEffect(chosen, current);
        if (current.getHand().isEmpty()) {
            gui.showGameOver(current.getName());
            gameWon = true;
            return;
        }
        turnManager.moveToNextPlayer();
        proceedToNextTurn();
    }

    public void humanPlayerDrawCard() {
        Player current = players.get(turnManager.getCurrentPlayerIndex());
        if (!(current instanceof HumanPlayer)) {
            System.out.println("[Game] Not a human player's turn for draw!");
            return;
        }
        Card c = deck.drawCard();
        current.drawCard(c);
        gui.updatePlayerHand(current.getHand());
        gui.showMessage(current.getName() + " drew a card.");

        turnManager.moveToNextPlayer();
        proceedToNextTurn();
    }

    private void handleCardEffect(Card card, Player player) {
        // Example handling:
        if (card instanceof SpecialCard) {
            switch (card.getType()) {
                case "skip":
                    gui.showMessage("Skip played! Next player is skipped.");
                    turnManager.moveToNextPlayer();
                    break;
                case "reverse":
                    gui.showMessage("Reverse played! Direction reversed.");
                    turnManager.reverseDirection();
                    break;
                case "draw2":
                    applyDrawCards(2);
                    break;
                default:
                    break;
            }
        } else if (card instanceof WildCard) {
            WildCard w = (WildCard) card;
            if (w.getType().equals("wild_draw4")) {
                applyDrawCards(4);
            }
            if (player instanceof HumanPlayer) {
                String[] colors = {"red", "blue", "green", "yellow"};
                int choice = gui.showColorChooser(colors);
                if (choice >= 0) {
                    w.setChosenColor(colors[choice]);
                } else {
                    w.setChosenColor("red");
                }
            } else {
                String[] colors = {"red", "blue", "green", "yellow"};
                w.setChosenColor(colors[new java.util.Random().nextInt(colors.length)]);
            }
            gui.showMessage(player.getName() + " changed color to " + w.getChosenColor());
        }
    }

    private void applyDrawCards(int howMany) {
        turnManager.moveToNextPlayer();
        Player nextP = players.get(turnManager.getCurrentPlayerIndex());
        gui.showMessage(nextP.getName() + " must draw " + howMany + " cards!");

        for (int i = 0; i < howMany; i++) {
            nextP.drawCard(deck.drawCard());
        }
        gui.updatePlayerHand(nextP.getHand());

        turnManager.moveToNextPlayer();
    }

    // For immediate top-card retrieval if you want the GUI to display it
    public Card getTopCard() {
        return topCard;
    }

    private String getCardImagePath(Card card) {
        // Example logic
        if (card instanceof WildCard) {
            WildCard w = (WildCard) card;
            if (w.getChosenColor() != null) {
                return "/images/" + w.getType() + "_" + w.getChosenColor() + ".jpg";
            }
            return "/images/" + w.getType() + ".jpg";
        } else if (card instanceof SpecialCard) {
            return "/images/" + card.getColor() + "_" + card.getType() + ".jpg";
        } else if (card instanceof NumberCard) {
            return "/images/cards/" + card.getColor() + "_" + card.getValue() + ".jpg";
        }
        return "/images/uno.jpg"; // fallback
    }
}

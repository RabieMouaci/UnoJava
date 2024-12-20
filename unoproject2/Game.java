
package unoproject2;
import java.util.*;
public class Game {
    private Deck deck;
    private List<Player> players;
    private Card topCard;
    private TurnManager turnManager;
    private GameStateDisplay gameStateDisplay;

    public Game(int numPlayers, List<String> playerTypes) {
    deck = new Deck();
    players = new ArrayList<>();
    initializePlayers(numPlayers, playerTypes);
    distributeCards();
    initializeTopCard();  // Replace topCard = deck.drawCard();
    turnManager = new TurnManager(numPlayers);
    gameStateDisplay = new GameStateDisplay();
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
            for (int i =0; i < 7; i++) { // Each player gets 7 cards
                player.drawCard(deck.drawCard());
            }
        }
    }
     private void initializeTopCard() {
    // Keep drawing until we get a number card
    do {
        topCard = deck.drawCard();
        if (!(topCard instanceof NumberCard)) {
            // Put the non-number card back into the deck
            deck.cards.add(topCard);
            Collections.shuffle(deck.cards);
        }
    } while (!(topCard instanceof NumberCard));
}

      public void startGame() {
    boolean gameWon = false;
    while (!gameWon) {
        Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
        System.out.println("Current top card: " + topCard);
        
        if (currentPlayer instanceof HumanPlayer) {
            // Display hand only once for human players
            System.out.println(currentPlayer.getName() + "'s turn.");
            System.out.println("Your hand: ");
            List<Card> hand = currentPlayer.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println(i + ": " + hand.get(i));
            }
            System.out.println("Top card: " + topCard);
        } else {
            System.out.println("AI " + currentPlayer.getName() + "'s turn.");
        }

        Card playedCard = currentPlayer.playCard(topCard);
        
        if (playedCard == null) {
            System.out.println(currentPlayer.getName() + " draws a card.");
            currentPlayer.drawCard(deck.drawCard());
            turnManager.moveToNextPlayer();
            continue;
        }
        
        topCard = playedCard;
        System.out.println(currentPlayer.getName() + " played: " + playedCard);
        handleCardEffect(playedCard);

        if (currentPlayer.getHand().isEmpty()) {
            System.out.println(currentPlayer.getName() + " has won the game!");
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
                System.out.println("Game direction reversed!");
                break;
            case "skip":
                turnManager.moveToNextPlayer();
                System.out.println("Next player skipped!");
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
        System.out.println("Choose a color:");
        System.out.println("1: Red");
        System.out.println("2: Blue");
        System.out.println("3: Green");
        System.out.println("4: Yellow");
        
        Scanner scanner = new Scanner(System.in);
        int colorChoice = scanner.nextInt();
        
        String chosenColor;
        switch (colorChoice) {
            case 1: chosenColor = "red"; break;
            case 2: chosenColor = "blue"; break;
            case 3: chosenColor = "green"; break;
            case 4: chosenColor = "yellow"; break;
            default: 
                chosenColor = "red";
                System.out.println("Invalid choice. Defaulting to red.");
        }
        wildCard.setChosenColor(chosenColor);
    } else {
        String[] colors = {"red", "blue", "green", "yellow"};
        wildCard.setChosenColor(colors[new Random().nextInt(colors.length)]);
    }
    System.out.println("Color changed to: " + wildCard.getChosenColor());
}
}

    private void applyDrawCards(int numCards) {
    // Get the next player who needs to draw cards
    turnManager.moveToNextPlayer();
    Player nextPlayer = players.get(turnManager.getCurrentPlayerIndex());
    
    System.out.println(nextPlayer.getName() + " must draw " + numCards + " cards.");
    for (int i = 0; i < numCards; i++) {
        nextPlayer.drawCard(deck.drawCard());
    }
    
    // Skip the player who drew cards by moving to the next player
    turnManager.moveToNextPlayer();
}
}

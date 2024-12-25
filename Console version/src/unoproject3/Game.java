package unoproject3;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


public class Game {
    private Deck deck;
    private List<Player> players;
    private TurnManager turnManager;
    private Table table;
    private boolean gameWon;

    public Game(int numberOfPlayers, List<String> playerTypes, String initialTableColor, String initialTableType) {
        this.deck = new Deck();
        this.players = new ArrayList<>();
        this.turnManager = new TurnManager(numberOfPlayers);
        this.table = new Table(initialTableColor, initialTableType);
        this.gameWon = false;

        for (int i = 0; i < numberOfPlayers; i++) {
            if (playerTypes.get(i).equalsIgnoreCase("human")) {
                players.add(new HumanPlayer("Player " + (i + 1)));
            } else {
                players.add(new BotPlayer("AI Player " + (i + 1)));
            }
        }

        distributeInitialCards();
        initializeTopCard();
    }

    private void distributeInitialCards() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck.drawCard());
            }
        }
    }

    private void initializeTopCard() {//need to be colored
        do {
            Card card = deck.drawCard();
            if (card instanceof ColoredCard) {
                table.setTopCard(card);
                table.setTableColor(((ColoredCard) card).getColor());
                table.setTableType(card.getType());
                break;
            } else {
                deck.getCards().add(card);
                deck.shuffleDeck();
            }
        } while (true);
    }

    public void startGame() {
        while (!gameWon) {
            Player currentPlayer = players.get(turnManager.getCurrentPlayerIndex());
            System.out.println("Table Color: " + table.getTableColor());
            System.out.println("Table Type: " + table.getTableType());
            System.out.println("Top Card: " + table.getTopCard());

            Card playedCard = currentPlayer.playCard(table.getTableColor(), table.getTableType(), table.getTopCard());

            if (playedCard != null) {
                table.setTopCard(playedCard);
                table.setTableColor(playedCard instanceof ColoredCard ? ((ColoredCard) playedCard).getColor() : table.getTableColor());
                table.setTableType(playedCard.getType());
                handleCardEffect(playedCard, currentPlayer);

                if (currentPlayer.getHand().isEmpty()) {
                    System.out.println(currentPlayer.getName() + " wins the game!");
                    gameWon = true;
                    return;
                }
            } else {
                Card drawnCard = deck.drawCard();
                currentPlayer.drawCard(drawnCard);
                System.out.println(currentPlayer.getName() + " drew a card.");
            }

            turnManager.moveToNextPlayer();
        }
    }

    private void handleCardEffect(Card card, Player player) {
        if (card instanceof SkipNextPlayer) {
            ((SkipNextPlayer) card).skipNextPlayer(turnManager);
            System.out.println(player.getName() + " played Skip! Skipping next player.");
        }

        if (card instanceof DrawNextPlayer) {
            Player nextPlayer = players.get(turnManager.getCurrentPlayerIndex());
            ((DrawNextPlayer) card).drawNextPlayer(nextPlayer, deck);
            System.out.println(player.getName() + " played Draw! Next player draws cards.");
        }

        if (card instanceof ReverseCard) {
            ((ReverseCard) card).reverseDirection(turnManager);
            System.out.println(player.getName() + " played Reverse! Reversing turn order.");
        }

        if (card instanceof WildCard) {
            if (card instanceof NormalWildCard) {
                String newColor = humanChooseColor(player);
                ((NormalWildCard) card).changeColor(newColor);
                table.setTableColor(newColor);
                table.setTableType("wild");
                System.out.println(player.getName() + " played Wild! Changing color to " + newColor + ".");
            } else if (card instanceof WildDraw4Card) {
                String newColor = humanChooseColor(player);
                ((WildDraw4Card) card).setChosenColor(newColor);
                table.setTableColor(newColor);
                table.setTableType("wild_draw4");
                Player nextPlayer = players.get(turnManager.getCurrentPlayerIndex());
                ((WildDraw4Card) card).drawNextPlayer(nextPlayer, deck);
                System.out.println(player.getName() + " played Wild Draw 4! Changing color to " + newColor + " and forcing next player to draw 4 cards.");
            }
        }
    }

    private String humanChooseColor(Player player) {
        if (player instanceof HumanPlayer) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(player.getName() + ", choose a color: (1) Red, (2) Blue, (3) Green, (4) Yellow:");
            int choice;
            do {
                choice = scanner.nextInt();
            } while (choice < 1 || choice > 4);
            return switch (choice) {
                case 1 -> "red";
                case 2 -> "blue";
                case 3 -> "green";
                case 4 -> "yellow";
                default -> "red"; // makach li rah yaghlet ms en sais jamais
            };
        } else {
            // Bots choose a random color
            String[] colors = {"red", "blue", "green", "yellow"};
            return colors[new Random().nextInt(colors.length)];
        }
    }
}

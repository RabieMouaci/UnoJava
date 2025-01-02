
package CodePackage;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class GameGUI  {
    private Game game;
    private JFrame frame;
    private JLabel currentPlayerLabel;
    private JPanel tablePanel, handPanel, logPanel;
    private JTextArea logArea;
    private JTable playerInfoTable;
    private DefaultTableModel tableModel;
    private JLabel topCardImageLabel; 

    public GameGUI(Game game) {
        this.game = game;
    }

    public void showGameScreen() {
        initialize();
        updateGameScreen();
    }

    private void initialize() {
        // Main Frame
        frame = new JFrame("UNO Game with Background");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);

        // Set background panel as content pane
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/resources/images/background.jpg");//background path
        backgroundPanel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundPanel);

        // Current Player Label
        currentPlayerLabel = new JLabel("Current Player: ");
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(currentPlayerLabel, BorderLayout.NORTH);

        // Main Center Panel for Table and Log
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setOpaque(false);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Table Panel
        tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Table"));
        tablePanel.setOpaque(false);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // Label for Top Card Image
        topCardImageLabel = new JLabel();
        topCardImageLabel.setPreferredSize(new Dimension(100, 150));
        tablePanel.add(topCardImageLabel);

        // Log Panel
        logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Game Log"));
        logPanel.setOpaque(false);

        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        // Player Info Table (Card Counts)
        tableModel = new DefaultTableModel(new String[]{"Player", "Cards"}, 0);
        playerInfoTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(playerInfoTable);
        tableScrollPane.setPreferredSize(new Dimension(200, 100));
        logPanel.add(tableScrollPane, BorderLayout.SOUTH);

        centerPanel.add(logPanel, BorderLayout.EAST);

        // Hand Panel
        handPanel = new JPanel();
        handPanel.setBorder(BorderFactory.createTitledBorder("Your Hand"));
        handPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        handPanel.setPreferredSize(new Dimension(frame.getWidth(), 200));
        handPanel.setOpaque(false);
        frame.add(handPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateGameScreen() {
    // Get the current player
    Player currentPlayer = Game.getPlayers().get(Game.getTurnManager().getCurrentPlayerIndex());
    currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());

    // Check if its a BotPlayer's turn
    if (currentPlayer instanceof BotPlayer) {
        playBotTurn((BotPlayer) currentPlayer);
        return; // Avoid updating the hand panel for bot players
    }

    // Update Top Card Image
    Card topCard = Game.getTable().getTopCard();
    String topCardImagePath = getCardImagePath(topCard);
    setCardImage(topCardImageLabel, topCardImagePath);

    // Update Hand for Human Player
    handPanel.removeAll();
    for (Card card : currentPlayer.getHand()) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());

        JLabel cardImageLabel = new JLabel();
        String cardImagePath = getCardImagePath(card);
        setCardImage(cardImageLabel, cardImagePath);

        JButton cardButton = new JButton(card.toString());
        cardButton.addActionListener(e -> playCard(card));

        cardPanel.add(cardImageLabel, BorderLayout.CENTER);
        cardPanel.add(cardButton, BorderLayout.SOUTH);
        handPanel.add(cardPanel);
    }

    // Add Draw Card Button
    JButton drawCardButton = new JButton("Draw Card");
    drawCardButton.addActionListener(e -> drawCard());
    handPanel.add(drawCardButton);
    
    // Add Draw Card Button
    JButton SaveButton = new JButton("Save Game");
    SaveButton.addActionListener(e -> saveGame());
    handPanel.add(SaveButton);
    // Update Player Info Table
    updatePlayerInfoTable();

    frame.revalidate();
    frame.repaint();
    }


    private String getCardImagePath(Card card) {
    String cardFileName;

    if (card instanceof NumberCard) {
        
        NumberCard numberCard = (NumberCard) card;
        cardFileName = numberCard.getColor() + "_" + numberCard.getNumber() + ".jpg"; 
    } else if (card instanceof ColoredCard) {
        
        ColoredCard coloredCard = (ColoredCard) card;
        cardFileName = coloredCard.getColor() + "_" + coloredCard.getType() + ".jpg"; 
    } else if (card.getType().equals("wild") || card.getType().equals("wild_draw4")) {
        // Handle wild cards
        cardFileName = card.getType() + ".jpg"; 
    } else {
        throw new IllegalArgumentException("Unknown card type: " + card);
    }

    return "src/resources/images/cards/" + cardFileName;
}

    private void playBotTurn(BotPlayer botPlayer) {
    SwingUtilities.invokeLater(() -> {
        try {
            Thread.sleep(1000); 

            // Bot attempts to play a card
            for (Card card : botPlayer.getHand()) {
                if (card.isPlayable(Game.getTable().getTableColor(), game.getTable().getTableType(), game.getTable().getTopCard())) {
                    playCard(card); 
                    return;
                }
            }

            
            Card drawnCard = Game.getDeck().drawCard();
            botPlayer.drawCard(drawnCard);
            log(botPlayer.getName() + " drew a card.");
            Game.getTurnManager().moveToNextPlayer();
            updateGameScreen();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });
}

    private void setCardImage(JLabel label, String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 150, Image.SCALE_SMOOTH)); // Resize image
            label.setIcon(icon);
        } catch (Exception e) {
            label.setText("Image not found");
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private void playCard(Card card) {
    Player currentPlayer = Game.getPlayers().get(Game.getTurnManager().getCurrentPlayerIndex());

    if (card.isPlayable(Game.getTable().getTableColor(), Game.getTable().getTableType(), Game.getTable().getTopCard())) {
       
        if (card instanceof WildCard) {
            String chosenColor = promptForColor(currentPlayer);
            ((WildCard) card).setChosenColor(chosenColor);
            Game.getTable().setTableColor(chosenColor);
        } else {
            
            if (card instanceof ColoredCard) {
                Game.getTable().setTableColor(((ColoredCard) card).getColor());
            }
        }

        // Update the top card and table type
        Game.getTable().setTopCard(card);
        Game.getTable().setTableType(card.getType());
        currentPlayer.removeCard(card);
        log(currentPlayer.getName() + " played " + card);

        
        handleCardEffect(card, currentPlayer);

        if (currentPlayer.getHand().isEmpty()) {
            log(currentPlayer.getName() + " wins!");
            JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " wins!");
            frame.dispose();
            return;
        }

       
        Game.getTurnManager().moveToNextPlayer();
        updateGameScreen();
        } else {
            log("Invalid move! Try again.");
        }
         }


    private void drawCard() {
        Player currentPlayer = Game.getPlayers().get(Game.getTurnManager().getCurrentPlayerIndex());
        Card drawnCard = Game.getDeck().drawCard();
        currentPlayer.drawCard(drawnCard);
        log(currentPlayer.getName() + " drew " + drawnCard);
        Game.getTurnManager().moveToNextPlayer();
        updateGameScreen();
    }

    private void updatePlayerInfoTable() {
    tableModel.setRowCount(0); 
    for (Player player : Game.getPlayers()) {
        if (player instanceof BotPlayer) {
            tableModel.addRow(new Object[]{player.getName(), player.getHand().size() + " cards (hidden)"});
        } else {
            tableModel.addRow(new Object[]{player.getName(), player.getHand().size()});
        }
    }
}

    private String promptForColor(Player player) {
        if (player instanceof HumanPlayer) {
            String[] colors = {"Red", "Blue", "Green", "Yellow"};
            String chosenColor = (String) JOptionPane.showInputDialog(
                frame,
                player.getName() + ", choose a color for the Wild Card:",
                "Color Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                colors,
                colors[0]
            );
            return chosenColor != null ? chosenColor.toLowerCase() : "red"; 
        }
        String[] colors = {"red", "blue", "green", "yellow"};
        return colors[new java.util.Random().nextInt(colors.length)];
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }
    private void handleCardEffect(Card card, Player player) {
    if (card instanceof SkipCard) {
        Game.getTurnManager().moveToNextPlayer(); 
        log(player.getName() + " played Skip! Skipping the next player.");
    }

    if (card instanceof ReverseCard) {
        Game.getTurnManager().reverseDirection(); 
        log(player.getName() + " played Reverse! Reversing the turn order.");
    }

    if (card instanceof Draw2Card) {
        
        Game.getTurnManager().moveToNextPlayer();
        Player nextPlayer = Game.getPlayers().get(Game.getTurnManager().getCurrentPlayerIndex());

       
        for (int i = 0; i < 2; i++) {
            Card drawnCard = Game.getDeck().drawCard(); 
            nextPlayer.drawCard(drawnCard); 
        }
        log(player.getName() + " played Draw 2! Forcing " + nextPlayer.getName() + " to draw 2 cards.");
    }

    if (card instanceof WildDraw4Card) {
       
        Game.getTurnManager().moveToNextPlayer();
        Player nextPlayer = Game.getPlayers().get(Game.getTurnManager().getCurrentPlayerIndex());

        // Make the next player draw 4 cards
        for (int i = 0; i < 4; i++) {
            Card drawnCard = Game.getDeck().drawCard(); 
            nextPlayer.drawCard(drawnCard); 
        }
        log(player.getName() + " played Wild Draw 4! Forcing " + nextPlayer.getName() + " to draw 4 cards.");
    }
}
    private void saveGame() {
    
    String[] saveFiles = {"savegame.ser", "savegame2.ser", "savegame3.ser"};
    
    // choose file
    String selectedFile = (String) JOptionPane.showInputDialog(
            frame,
            "Choose a save file:",
            "Save Game",
            JOptionPane.QUESTION_MESSAGE,
            null,
            saveFiles,
            saveFiles[0]
    );

    if (selectedFile != null) {
        try {
            // save game
            String saveFilePath = "ur own path /Unoproject5/src/resources/" 
                                  + selectedFile;

           
            SaveGame dataToSave = new SaveGame(
                    Game.getPlayers(),
                    Game.getDeck(),
                    Game.getTable(),
                    Game.getTurnManager(),
                    Game.isGameWon()
            );

            
            dataToSave.saveToFile(saveFilePath);

            JOptionPane.showMessageDialog(frame, 
                    "Game saved successfully to " + selectedFile);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, 
                    "Failed to save the game. Please try again.\n" + e.getMessage());
        }
    }
}




}

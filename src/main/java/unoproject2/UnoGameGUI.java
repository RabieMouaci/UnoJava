package unoproject2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * UnoGameGUI - Fixed-size backgrounds and no runtime scaling
 */
public class UnoGameGUI extends JFrame {

    // For drawing the background with no scaling
    private JPanel contentPanel;
    private String currentBackground = "/images/background.jpg";  // initial background path
    private Image cachedBackground;  // holds the loaded background image

    // Screen 1 controls
    private JLabel screen1Label;
    private JComboBox<String> playerCountCombo;
    private JButton nextButton;
    private int numPlayers = 2;

    // Screen 2 controls
    private JLabel screen2Label;
    private JComboBox<String>[] playerTypeSelectors;
    private JButton startGameButton;

    // Main game screen (screen 3) controls
    private JLabel topCardLabel;
    private JPanel playerHandPanel;
    private JButton deckButton;
    private JLabel statusLabel;

    // Reference to the Game logic
    private Game game;

    public UnoGameGUI() {
        System.out.println("[UnoGameGUI] Constructor called.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Match your background image size:
        setSize(782, 559);
        setLocationRelativeTo(null);

        // Load the initial background image once:
        loadBackgroundImage(currentBackground);

        // Main content panel that draws the cachedBackground (no scaling)
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // If background fails to load, use a green fallback
                if (cachedBackground != null) {
                    g.drawImage(cachedBackground, 0, 0, null);
                } else {
                    setBackground(Color.GREEN);
                }
            }
        };
        contentPanel.setLayout(null);
        add(contentPanel);

        // Show Screen 1 by default
        showScreen1();
    }

    /**
     * Loads the background image from the given path into cachedBackground.
     */
    private void loadBackgroundImage(String path) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            cachedBackground = icon.getImage(); // no scaling
        } catch (Exception e) {
            cachedBackground = null;
            System.err.println("[UnoGameGUI] Failed to load background: " + path);
            e.printStackTrace();
        }
    }

    // ================================================
    //  SCREEN 1: Choose Number of Players
    // ================================================
    private void showScreen1() {
        System.out.println("[UnoGameGUI] showScreen1()");

        // We want background.jpg for screen 1
        currentBackground = "/images/background.jpg";
        loadBackgroundImage(currentBackground);

        contentPanel.removeAll();

        screen1Label = new JLabel("Number of Players:");
        screen1Label.setFont(new Font("Arial", Font.BOLD, 18));
        screen1Label.setBounds(50, 50, 200, 30);
        contentPanel.add(screen1Label);

        String[] options = {"2", "3", "4"};
        playerCountCombo = new JComboBox<>(options);
        playerCountCombo.setBounds(50, 100, 80, 30);
        contentPanel.add(playerCountCombo);

        nextButton = new JButton("Next");
        nextButton.setBounds(50, 150, 100, 30);
        nextButton.addActionListener(e -> {
            String selected = (String) playerCountCombo.getSelectedItem();
            numPlayers = Integer.parseInt(selected);
            System.out.println("[UnoGameGUI] Next button -> numPlayers=" + numPlayers);
            showScreen2_PlayerTypes();
        });
        contentPanel.add(nextButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ================================================
    //  SCREEN 2: Choose Player Types (Human/AI)
    // ================================================
    private void showScreen2_PlayerTypes() {
        System.out.println("[UnoGameGUI] showScreen2_PlayerTypes()");

        // Still background.jpg for screen 2
        currentBackground = "/images/background.jpg";
        loadBackgroundImage(currentBackground);

        contentPanel.removeAll();

        screen2Label = new JLabel("Select Player Types:");
        screen2Label.setFont(new Font("Arial", Font.BOLD, 18));
        screen2Label.setBounds(50, 50, 200, 30);
        contentPanel.add(screen2Label);

        playerTypeSelectors = new JComboBox[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            JLabel plabel = new JLabel("Player " + (i + 1) + ":");
            plabel.setBounds(50, 100 + (i * 40), 100, 30);
            contentPanel.add(plabel);

            JComboBox<String> combo = new JComboBox<>(new String[]{"Human", "AI"});
            combo.setBounds(150, 100 + (i * 40), 100, 30);
            contentPanel.add(combo);

            playerTypeSelectors[i] = combo;
        }

        startGameButton = new JButton("Start Game");
        startGameButton.setBounds(50, 100 + (numPlayers * 50), 120, 30);
        startGameButton.addActionListener(e -> {
            System.out.println("Start Game button pressed.");
            String[] playerTypes = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                playerTypes[i] = playerTypeSelectors[i].getSelectedItem().toString().toLowerCase();
                System.out.println("Player " + (i+1) + " type: " + playerTypes[i]);
            }
            initializeGame(numPlayers, playerTypes);
            System.out.println("initializeGame() called.");
        });
        contentPanel.add(startGameButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ================================================
    //  INITIALIZE THE GAME + SWITCH TO SCREEN 3
    // ================================================
    public void initializeGame(int numPlayers, String[] playerTypes) {
        System.out.println("[UnoGameGUI] initializeGame() with " + numPlayers + " players.");

        // 1) Create the Game instance
        game = new Game(numPlayers, List.of(playerTypes), this);

        // 2) Show the main game screen (Screen 3)
        showMainGameScreen();

        // 3) Now it's safe to start the game logic
        game.startGame();
        System.out.println("[UnoGameGUI] game.startGame() finished.");
    }

    // ================================================
    //  SCREEN 3: Main Game Screen
    // ================================================
    private void showMainGameScreen() {
        System.out.println("[UnoGameGUI] showMainGameScreen()");

        // Use background2 for screen 3
        currentBackground = "/images/background2.jpg";
        loadBackgroundImage(currentBackground);

        contentPanel.removeAll();

        topCardLabel = new JLabel("Top Card:");
        topCardLabel.setBounds(50, 50, 300, 30);
        contentPanel.add(topCardLabel);

        playerHandPanel = new JPanel(new FlowLayout());
        playerHandPanel.setBounds(50, 100, 600, 120);
        playerHandPanel.setOpaque(false);
        contentPanel.add(playerHandPanel);

        deckButton = new JButton("Deck");
        deckButton.setBounds(50, 250, 100, 40);
        deckButton.addActionListener(e -> {
            if (game != null) {
                System.out.println("[UnoGameGUI] Deck button clicked -> human draw card");
                game.humanPlayerDrawCard();
            }
        });
        contentPanel.add(deckButton);

        statusLabel = new JLabel("Game Status");
        statusLabel.setBounds(50, 320, 400, 30);
        contentPanel.add(statusLabel);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ================================================
    //  METHODS CALLED BY THE GAME CLASS
    // ================================================
    public void updateTopCard(String imagePath) {
        System.out.println("[UnoGameGUI] updateTopCard(" + imagePath + ")");
        if (topCardLabel == null) {
            // If this happens, you're calling updateTopCard before showMainGameScreen
            System.err.println("[UnoGameGUI] topCardLabel is null, can't update card image!");
            return;
        }
        try {
            // Load the card image without scaling
            ImageIcon cardIcon = new ImageIcon(getClass().getResource(imagePath));
            topCardLabel.setIcon(cardIcon);
            topCardLabel.setText(""); 
        } catch (Exception e) {
            System.err.println("[UnoGameGUI] Could not load top card image: " + imagePath);
            e.printStackTrace();
            topCardLabel.setText("Card Image Not Found");
        }
    }

    public void updateGameStatus(String message) {
        System.out.println("[UnoGameGUI] updateGameStatus -> " + message);
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    public void updatePlayerHand(List<Card> hand) {
        System.out.println("[UnoGameGUI] updatePlayerHand with " + hand.size() + " cards");
        if (playerHandPanel == null) {
            System.err.println("[UnoGameGUI] playerHandPanel is null, UI not ready yet?");
            return;
        }
        playerHandPanel.removeAll();
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            JLabel cardLabel = new JLabel(c.toString());
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            final int cardIndex = i;
            cardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    System.out.println("[UnoGameGUI] Card " + cardIndex + " clicked -> human play card");
                    game.humanPlayerPlayedCard(cardIndex);
                }
            });
            playerHandPanel.add(cardLabel);
        }
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    public void showMessage(String message) {
        System.out.println("[UnoGameGUI] showMessage -> " + message);
        JOptionPane.showMessageDialog(
            this,
            message,
            "UNO",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public int showColorChooser(String[] colors) {
        System.out.println("[UnoGameGUI] showColorChooser() -> " + String.join(", ", colors));
        return JOptionPane.showOptionDialog(
            this,
            "Choose a color:",
            "Color Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            colors,
            colors[0]
        );
    }

    public void showGameOver(String winner) {
        System.out.println("[UnoGameGUI] showGameOver -> winner: " + winner);
        JOptionPane.showMessageDialog(
            this,
            winner + " has won the game!",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
        );
        System.exit(0);
    }

    public void updateCurrentPlayer(Player player) {
        System.out.println("[UnoGameGUI] updateCurrentPlayer -> " + player.getName());
        updateGameStatus(player.getName() + "'s turn");
        updatePlayerHand(player.getHand());
    }

    public void enableHumanPlayerActions(boolean enable) {
        System.out.println("[UnoGameGUI] enableHumanPlayerActions(" + enable + ")");
        if (deckButton != null) deckButton.setEnabled(enable);
    }
}

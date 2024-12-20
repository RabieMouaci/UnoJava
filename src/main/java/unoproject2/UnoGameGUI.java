package unoproject2;

import java.awt.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class UnoGameGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel playerHandPanel;
    private JPanel topCardPanel;
    private JLabel topCardLabel;
    private JLabel statusLabel;
    private boolean isGameStarted = false;

    public UnoGameGUI() {
        initializeFrame();
        if (!isGameStarted) {
            initializeMainMenu();
        } else {
            initializeGameComponents();
            layoutGameComponents();
        }
    }

    private void initializeFrame() {
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initializeMainMenu() {
        mainPanel = createMainPanel();
        mainPanel.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        showPlayerSelectionScreen();
    }

    private void initializeComponents() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bg = ImageIO.read(getClass().getResource("resources/images/background.jpg"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    setBackground(new Color(0, 100, 0)); // Fallback color
                    e.printStackTrace();
                }
            }
        };
    }
    private void initializeGameComponents() {
        mainPanel = createMainPanel();
        mainPanel.setLayout(new BorderLayout());

        topCardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topCardPanel.setOpaque(false);
        topCardLabel = new JLabel();
        topCardPanel.add(topCardLabel);

        statusLabel = new JLabel("Welcome to UNO!");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        playerHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerHandPanel.setOpaque(false);
    }

    private void layoutGameComponents() {
        mainPanel.add(statusLabel, BorderLayout.NORTH);
        mainPanel.add(topCardPanel, BorderLayout.CENTER);
        mainPanel.add(playerHandPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private JPanel createMainPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bg = ImageIO.read(getClass().getResource("resources/images/background.jpg"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    setBackground(new Color(0, 100, 0));
                    e.printStackTrace();
                }
            }
        };
    }

    private void showPlayerSelectionScreen() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(2, 1));

        JLabel label = new JLabel("Choose Number of Players", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        contentPanel.add(label);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        for (int i = 2; i <= 4; i++) {
            addPlayerButton(buttonPanel, i + " Players", i);
        }

        contentPanel.add(buttonPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showPlayerTypeSelectionScreen(int numPlayers) {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(numPlayers + 2, 1));

        JLabel label = new JLabel("Select Player Types", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        contentPanel.add(label);

        JComboBox<String>[] playerTypeSelectors = new JComboBox[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            playerPanel.setOpaque(false);

            JLabel playerLabel = new JLabel("Player " + (i + 1));
            playerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            playerLabel.setForeground(Color.WHITE);

            playerTypeSelectors[i] = new JComboBox<>(new String[]{"Human", "AI"});
            playerTypeSelectors[i].setFont(new Font("Arial", Font.PLAIN, 18));

            playerPanel.add(playerLabel);
            playerPanel.add(playerTypeSelectors[i]);
            contentPanel.add(playerPanel);
        }

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.addActionListener(e -> {
            String[] playerTypes = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                playerTypes[i] = playerTypeSelectors[i].getSelectedItem().toString().toLowerCase();
            }
            startGame(numPlayers, playerTypes);
        });
        contentPanel.add(startButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Game interface methods
    public void updateTopCard(String imagePath) {
        try {
            ImageIcon cardIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = cardIcon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            topCardLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            topCardLabel.setText("Card Image Not Found");
            e.printStackTrace();
        }
    }

    public void updatePlayerHand(List<Card> hand) {
        playerHandPanel.removeAll();
        for (Card card : hand) {
            playerHandPanel.add(createCardLabel(card));
        }
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    private void addPlayerButton(JPanel panel, String text, int numPlayers) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(e -> showPlayerTypeSelectionScreen(numPlayers));
        panel.add(button);
    }

    private void startGame(int numPlayers, String[] playerTypes) {
        isGameStarted = true;
        dispose();
        SwingUtilities.invokeLater(() -> {
            new Game(numPlayers, List.of(playerTypes)).startGame();
        });
    }

    // Utility methods for game display
    private JLabel createCardLabel(Card card) {
        String imagePath = "/images/" + getCardImageName(card);
        JLabel cardLabel = new JLabel();
        try {
            ImageIcon cardIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = cardIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            cardLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            cardLabel.setText(card.toString());
            cardLabel.setBackground(Color.WHITE);
            cardLabel.setOpaque(true);
        }
        cardLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return cardLabel;
    }

    private String getCardImageName(Card card) {
        if (card instanceof WildCard) {
            return card.getType() + ".jpg";
        }
        return card.getColor() + "_" + card.getType() + 
               (card instanceof NumberCard ? card.getValue() : "") + ".jpg";
    }

    // Additional game interface methods
    public void updateGameStatus(String message) {
        statusLabel.setText(message);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public int showColorChooser(String[] colors) {
        return JOptionPane.showOptionDialog(this, "Choose a color:", "Color Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);
    }

    public void showGameOver(String winner) {
        JOptionPane.showMessageDialog(this, winner + " has won the game!", 
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}

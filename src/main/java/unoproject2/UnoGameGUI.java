package unoproject2;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class UnoGameGUI extends JFrame {
    private JPanel contentPanel;
    private JLabel topCardLabel;
    private JPanel playerHandPanel;
    private JLabel statusLabel;
    private int numPlayers;
    private JComboBox<String>[] playerTypeSelectors;
    private boolean isGameStarted;

    public UnoGameGUI() {
        // Initialize components
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("/background.png"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel);

        // Set default size for the window
        setSize(800, 600);

        // Other initialization code...
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.addActionListener(e -> {
            String[] playerTypes = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                playerTypes[i] = playerTypeSelectors[i].getSelectedItem().toString().toLowerCase();
            }
            startGame(numPlayers, playerTypes);
        });
        contentPanel.add(startButton, BorderLayout.SOUTH);

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

    private void showPlayerTypeSelectionScreen(int numPlayers) {
        this.numPlayers = numPlayers;
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(numPlayers + 1, 2));

        playerTypeSelectors = new JComboBox[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            JLabel label = new JLabel("Player " + (i + 1) + " Type:");
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            contentPanel.add(label);

            playerTypeSelectors[i] = new JComboBox<>(new String[]{"Human", "AI"});
            playerTypeSelectors[i].setFont(new Font("Arial", Font.PLAIN, 18));
            contentPanel.add(playerTypeSelectors[i]);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnoGameGUI gui = new UnoGameGUI();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(800, 600);
            gui.setVisible(true);
        });
    }
}

package unoproject2;

import java.awt.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class UnoGameGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel playerHandPanel;
    private JPanel topCardPanel;
    private JLabel topCardLabel;
    private JLabel statusLabel;
    
    public UnoGameGUI() {
        initializeFrame();
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeFrame() {
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bg = ImageIO.read(getClass().getResource("/resources/images/background.jpg"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    setBackground(new Color(0, 100, 0));
                    e.printStackTrace();
                }
            }
        };
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
    
    private void layoutComponents() {
        mainPanel.add(statusLabel, BorderLayout.NORTH);
        mainPanel.add(topCardPanel, BorderLayout.CENTER);
        mainPanel.add(playerHandPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

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
            JLabel cardLabel = createCardLabel(card);
            playerHandPanel.add(cardLabel);
        }
        
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    public void updateGameStatus(String message) {
        statusLabel.setText(message);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public int showColorChooser(String[] colors) {
        return JOptionPane.showOptionDialog(this,
            "Choose a color:",
            "Color Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            colors,
            colors[0]);
    }

    public void showGameOver(String winner) {
        JOptionPane.showMessageDialog(this,
            winner + " has won the game!",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private JLabel createCardLabel(Card card) {
        String imagePath = "/resources/images/" + getCardImageName(card);
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
}

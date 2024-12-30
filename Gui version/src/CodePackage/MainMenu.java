package CodePackage;
import javax.swing.*;
import java.awt.*;

public class MainMenu {
    public void showMenu() {
        JFrame frame = new JFrame("UNO Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("UNO Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Start New Game Button
        JButton newGameButton = new JButton("Start New Game");
        newGameButton.addActionListener(e -> {
            frame.dispose();
            new NewGameSetup().showSetup();
        });
        buttonPanel.add(newGameButton);

        // Load Game Button
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> {
            frame.dispose();
            loadSavedGame();  
        });
        buttonPanel.add(loadGameButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadSavedGame() {
       
        GameLoader loader = new GameLoader();
        Game loadedGame = loader.loadGame();  
        if (loadedGame != null) {
            JOptionPane.showMessageDialog(null, "Game loaded successfully!");
            new GameGUI(loadedGame).showGameScreen();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to load the saved game.");
        }
    }
}




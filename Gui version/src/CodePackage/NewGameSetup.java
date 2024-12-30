
package CodePackage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



public class NewGameSetup {
    public void showSetup() {
        JFrame frame = new JFrame("UNO Game Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JLabel setupLabel = new JLabel("Setup New Game", JLabel.CENTER);
        frame.add(setupLabel, BorderLayout.NORTH);

        JPanel setupPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Dynamic for 2-4 players
        List<JComboBox<String>> playerTypeSelectors = new ArrayList<>();

        // Player Selection Options
        setupPanel.add(new JLabel("Number of Players:"));
        String[] playerCounts = {"2", "3", "4"};
        JComboBox<String> playerCountDropdown = new JComboBox<>(playerCounts);
        setupPanel.add(playerCountDropdown);

        // Dynamically Add Player Type Selectors
        for (int i = 1; i <= 4; i++) {
            JLabel playerLabel = new JLabel("Player " + i + ":");
            setupPanel.add(playerLabel);

            JComboBox<String> playerTypeDropdown = new JComboBox<>(new String[]{"Human", "AI"});
            playerTypeDropdown.setEnabled(i <= 2); // Default to 2 players
            setupPanel.add(playerTypeDropdown);

            playerTypeSelectors.add(playerTypeDropdown);
        }

        // Enable/Disable Player Selectors Based on Player Count
        playerCountDropdown.addActionListener(e -> {
            int selectedCount = Integer.parseInt((String) playerCountDropdown.getSelectedItem());
            for (int i = 0; i < 4; i++) {
                playerTypeSelectors.get(i).setEnabled(i < selectedCount);
            }
        });

        frame.add(setupPanel, BorderLayout.CENTER);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            int numberOfPlayers = Integer.parseInt((String) playerCountDropdown.getSelectedItem());
            List<String> playerTypes = new ArrayList<>();

            for (int i = 0; i < numberOfPlayers; i++) {
                playerTypes.add((String) playerTypeSelectors.get(i).getSelectedItem());
            }

            Game game = new Game(numberOfPlayers, playerTypes, "red", "number");
            frame.dispose();
            new GameGUI(game).showGameScreen();
        });

        frame.add(startButton, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}




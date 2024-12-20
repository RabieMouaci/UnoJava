package unoproject2;

import javax.swing.*;



public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Start the game GUI
            new UnoGameGUI().setVisible(true);
        });
    }
}


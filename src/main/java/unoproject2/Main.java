package unoproject2;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Optional: catch any uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in thread " + thread.getName() + ": " + throwable);
            throwable.printStackTrace();
        });

        SwingUtilities.invokeLater(() -> {
            System.out.println("Launching UnoGameGUI...");
            UnoGameGUI gui = new UnoGameGUI();
            gui.setVisible(true);
        });
    }
}

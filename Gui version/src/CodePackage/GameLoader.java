package CodePackage;
import java.io.IOException;
import javax.swing.*;

public class GameLoader {
    public Game loadGame() {
           String[] saveFiles = {"savegame.ser", "savegame2.ser", "savegame3.ser"};
           String selectedFile = (String) JOptionPane.showInputDialog(
                   null,
                   "Choose a save file to load:",
                   "Load Game",
                   JOptionPane.QUESTION_MESSAGE,
                   null,
                   saveFiles,
                   saveFiles[0]
           );

           if (selectedFile == null) {
               return null;
           }

           String saveFilePath = "put ur own path    /Unoproject3/src/resources/" + selectedFile;

           try {
               // Load the saved game data
               SaveGame savedGame = SaveGame.loadFromFile(saveFilePath);

               // Reconstruct the actual Game instance from SaveGame
               Game loadedGame = new Game(
                       savedGame.getPlayers(),
                       savedGame.getDeck(),
                       savedGame.getTable(),
                       savedGame.getTurnManager(),
                       savedGame.isGameWon()
               );
               Game.instance = loadedGame;  

               return loadedGame;
                  //using exception for error
           } catch (IOException | ClassNotFoundException e) {
               e.printStackTrace();
               return null;
           }
       }
}




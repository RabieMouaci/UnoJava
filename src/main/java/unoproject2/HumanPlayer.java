package unoproject2;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Card playCard(Card topCard) {
        // In this example, the GUI calls game.humanPlayerPlayedCard(index) instead of using this
        // We'll return null to indicate "no card auto-play"
        return null;
    }
}

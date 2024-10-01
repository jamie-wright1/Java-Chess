package chess;

public class AIPlayer extends Player {
    //Black by default
    public AIPlayer() {
        super("Bot", false);
    }

    public AIPlayer(boolean isWhite) {
        super("Bot", isWhite);
    }

    public void findBestMove() {

    }
}

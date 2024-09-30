package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Controller {
    private BoardView boardView;
    private Board boardModel;
    private Game game;

    int turn;

    public Controller(BoardView boardView, Board boardModel, Player playerOne, Player playerTwo, Game game) {
        this.boardView = boardView;
        this.boardModel = boardModel;
        this.game = game;


        int i = 0;
        for (SquareView squareView: boardView.getSquares()) {
            Square square = boardModel.getSquare(i);
            squareView.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.squareClicked(square);
                }
            });
            i++;
        }
    }
}

package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Controller {
    private BoardView board;
    private Board boardModel;
    private PropertyChangeSupport propertyChangeSupport;

    public Controller(BoardView board, Board boardModel) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        this.board = board;
        this.boardModel = boardModel;

        int i = 0;
        for (SquareView square: board.getSquares()) {
            int finalI = i;
            square.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boardModel.squareClicked(finalI);
                }
            });
            i++;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}

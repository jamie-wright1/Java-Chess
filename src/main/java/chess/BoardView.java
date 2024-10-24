package chess;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class BoardView extends JPanel implements PropertyChangeListener {
    private ArrayList<SquareView> squares;

    public BoardView() {
        squares = new ArrayList<>();
        setLayout(null);
        setPreferredSize(new Dimension(600, 600));
        setLayout(new GridLayout(8, 8));

        setUpDisplay();
    }

    public void setUpDisplay() {
        for (int i = 0; i < 64; i++) {
            add(new JButton());
        }
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                SquareView square = new SquareView(row, column, 0);

                remove(56 - row * 8 + column);
                add(square, 56 - row * 8 + column);
                squares.add(square);
            }

        }
    }

    public ArrayList<SquareView> getSquares() {
        return squares;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("boardUpdated".equals(evt.getPropertyName())) {
            ArrayList<Integer> oldList = (ArrayList<Integer>) evt.getOldValue();
            ArrayList<Integer> newList = (ArrayList<Integer>) evt.getNewValue();

            for (int i = 0; i < newList.size(); i++) {
                if (oldList.get(i) != newList.get(i)) {
                    squares.get(i).setIcon(newList.get(i));
                }
            }

            repaint();
        }

        if ("turnOver".equals(evt.getPropertyName()) || "unHighlight".equals(evt.getPropertyName())) {
            unHighlightBoard();
        }

        if ("newHighlights".equals(evt.getPropertyName())) {
            ArrayList<Integer> newList = (ArrayList<Integer>) evt.getNewValue();

            for (Integer index : newList) {
                squares.get(index).highlight();
            }
        }

        if ("gameStarted".equals(evt.getPropertyName())) {
            unHighlightBoard();
        }

        updateUI();
    }

    public void unHighlightBoard() {
        for (SquareView square : squares) {
            square.unHighlight();
        }
    }
}

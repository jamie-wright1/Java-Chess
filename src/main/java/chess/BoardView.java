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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        int oldValue = (Integer) evt.getOldValue();
        int newValue = (Integer) evt.getNewValue();

        if ("valueChanged".equals(propertyName)) {
            newValue = newValue - 100;
            squares.get(oldValue).setIcon(newValue);
        }
        else if ("highlightSquare".equals(propertyName)) {
            if (newValue == -1) {
                squares.get(oldValue).setBackground(Color.WHITE);
            } else {
                if ((oldValue % 8 + oldValue / 8) % 2 == 0) {
                    squares.get(oldValue).setBackground(Color.LIGHT_GRAY);
                }
                else {
                    squares.get(oldValue).setBackground(Color.GRAY);
                }
            }
        }
    }

    public ArrayList<SquareView> getSquares() {
        return squares;
    }
}

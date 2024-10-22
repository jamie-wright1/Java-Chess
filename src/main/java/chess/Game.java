package chess;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Game extends JFrame implements PropertyChangeListener {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private enum GameMode {Versus, AI};
    private GameMode mode;

    private int turnNumber;

    private BoardView boardView;
    private TopBarView topBar;
    private Board board;
    private Controller controller;
    private Player playerOne;
    private Player playerTwo;
    private AIPlayer ai;

    public Game() {
        propertyChangeSupport = new PropertyChangeSupport(this);

        mode = GameMode.Versus;

        boardView = new BoardView();
        topBar = new TopBarView();
        board = new Board();

        //Default is versus mode
        playerOne = new Player(getName(), true, board);
        playerTwo = new Player(getName(), false, board);
        ai = null;

        controller = new Controller(boardView, board, this, topBar);

        //boardView and game "listen" to the board
        board.addPropertyChangeListener(boardView);
        board.addPropertyChangeListener(this);

        this.addPropertyChangeListener(topBar);

        setUpGame();

        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        add(boardView, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void setUpGame() {
        String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

        board.fenToBoard(startFen);
        turnNumber = 1;
        mode = GameMode.Versus;
    }

    public void setUpAIGame() {
        String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

        if (ai == null) {
            ai = new AIPlayer(board, false);
        }

        board.fenToBoard(startFen);
        turnNumber = 1;
        mode = GameMode.AI;
    }

    public String getName() {
        return "defaultName";
    }

    public void squareClicked(Square square) {
        if (turnNumber % 2 == 1) {
            playerOne.squareClicked(square);
        } else {
            playerTwo.squareClicked(square);
        }

        return;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("turnOver".equals(evt.getPropertyName())) {
            //Checking the color that did not just move
            boolean isWhite = (turnNumber % 2 == 0);

            if (board.checkForCheck(isWhite)) {
                board.getKing(isWhite).setCastleable(false);
            }

            //if (checkForCheck(isWhite)) {
                if (board.checkForMate(isWhite)) {
                    propertyChangeSupport.firePropertyChange("gameOver", evt.getOldValue(), evt.getNewValue());
                    this.remove(boardView);
                }

           // }

            turnNumber++;

            if (mode == GameMode.valueOf("AI") && turnNumber % 2 == 0) {
                ai.turn(board);
            }
        }

        this.repaint();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
package chess;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Game extends JFrame implements PropertyChangeListener {
    private static Game game;

    private String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    private int turnNumber;

    private BoardView boardView;
    private TopBarView topBar;

    private Board board;
    private Controller controller;

    Player playerOne;
    Player playerTwo;

    //Singleton game storing data for a game of chess
    private Game() {
        boardView = new BoardView();
        topBar = new TopBarView();
        board = Board.getInstance();
        playerOne = new Player(getName(), true);
        playerTwo = new Player(getName(), false);

        controller = new Controller(boardView, board, playerOne, playerTwo, this);

        //boardView and game "listen" to the board
        board.addPropertyChangeListener(boardView);
        board.addPropertyChangeListener(this);

        board.fenToBoard(startFen);

        turnNumber = 1;

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

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void runGame() {

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

            if (checkForCheck(isWhite)) {
                if (checkForMate(isWhite)) {
                    this.remove(boardView);
                }

            }

            turnNumber++;
        }
    }

    public boolean checkForCheck(boolean checkForWhite) {
        ArrayList<Integer> colorSquares = board.getColor(!checkForWhite);

        for (Integer square: colorSquares) {
            if (board.getSquare(square).getPiece().isChecking()) {
                return true;
            }
        }

        return false;
    }

    public boolean checkForMate(boolean checkForWhite) {
        ArrayList<Integer> colorSquares = board.getColor(checkForWhite);
        ArrayList<Integer> totalPossibleMoves = new ArrayList<>();

        for (Integer square: colorSquares) {
            totalPossibleMoves.addAll(board.getSquare(square).getPiece().findMoves());
        }

        return totalPossibleMoves.isEmpty();
    }
}
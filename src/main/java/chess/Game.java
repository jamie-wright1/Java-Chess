package chess;

import chess.Database.SQLConnection;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Game extends JFrame implements PropertyChangeListener {
    ArrayList<String> moves;
    String fen_string;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private enum GameMode {Versus, AI};
    private GameMode mode;

    private int turnNumber;

    private BoardView boardView;
    private AccountPageView userPage;
    private LeaderboardView leaderboardView;

    private CardLayout screens;
    private JPanel homeContainer;

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
        userPage = new AccountPageView();
        leaderboardView = new LeaderboardView();
        screens = new CardLayout();
        homeContainer = new JPanel(screens);
        homeContainer.add(boardView, "board");
        homeContainer.add(userPage, "user");
        homeContainer.add(leaderboardView, "leaderboard");

        topBar = new TopBarView();
        board = new Board();

        //Default is versus mode
        playerOne = new Player(getName(), true, board);
        playerOne.name = "";
        playerTwo = new Player(getName(), false, board);
        playerTwo.name = "";
        ai = null;

        fen_string = "placeholder_fen";
        moves = new ArrayList<>();

        controller = new Controller(boardView, board, this, topBar, screens, homeContainer, userPage, leaderboardView);

        //boardView and game "listen" to the board
        board.addPropertyChangeListener(boardView);
        board.addPropertyChangeListener(this);

        this.addPropertyChangeListener(boardView);
        this.addPropertyChangeListener(topBar);

        String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        board.fenToBoard(startFen);

        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        add(homeContainer, BorderLayout.CENTER);
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
        controller.activateBoard();
        moves.clear();

        propertyChangeSupport.firePropertyChange("gameStarted", null, mode);
    }

    public void setUpAIGame() {
        String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

        if (ai == null) {
            ai = new AIPlayer(board, false);
        }

        board.fenToBoard(startFen);
        turnNumber = 1;
        mode = GameMode.AI;
        controller.activateBoard();
        moves.clear();

        propertyChangeSupport.firePropertyChange("gameStarted", null, mode);
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

            if (board.noPossibleMoves(isWhite)) {
                if (board.checkForCheck(isWhite)) {
                    propertyChangeSupport.firePropertyChange("gameOver", evt.getOldValue(), evt.getNewValue());
                    int kingIndex = board.getKing(isWhite).getLocation();
                    propertyChangeSupport.firePropertyChange("newHighlights", evt.getOldValue(), new ArrayList(Arrays.asList(kingIndex)));
                } else {
                    propertyChangeSupport.firePropertyChange("stalemate", evt.getOldValue(), evt.getNewValue());
                }

                controller.freezeBoard();

                if (mode == GameMode.Versus) {
                    try {
                        if (isWhite) {
                            SQLConnection.getInstance().insertGame(playerOne.name, playerTwo.name, moves, SQLConnection.RESULT.LOSS);
                        } else {
                            SQLConnection.getInstance().insertGame(playerOne.name, playerTwo.name, moves, SQLConnection.RESULT.WIN);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            turnNumber++;

            if (mode == GameMode.valueOf("AI") && turnNumber % 2 == 0) {
                ai.turn(board);
            }
        }

        if ("moveMade".equals(evt.getPropertyName())) {
            ArrayList<Integer> oldList = (ArrayList<Integer>) evt.getOldValue();
            ArrayList<Integer> newList = (ArrayList<Integer>) evt.getNewValue();

            for (int i = 0; i < 64; i++) {
                if (oldList.get(i) != newList.get(i) && newList.get(i) != 0) {
                    String newMove = "";
                    switch (newList.get(i)) {
                        case 9:
                        case 17:
                            newMove = "P";
                            break;
                        case 10:
                        case 18:
                            newMove = "N";
                            break;
                        case 11:
                        case 19:
                            newMove = "R";
                            break;
                        case 12:
                        case 20:
                            newMove = "P";
                            break;
                        case 13:
                        case 21:
                            newMove = "P";
                            break;
                        case 14:
                        case 22:
                            newMove = "P";
                            break;
                    }

                    newMove = newMove + " " + board.getSquareNum(i);
                    moves.add(newMove);
                }
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

    public Player getPlayerOne() { return playerOne; }
    public Player getPlayerTwo() { return playerTwo; }
}
package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controller {
    private BoardView boardView;
    TopBarView topBarView;
    private Board boardModel;
    private Game game;
    private boolean boardActive;

    public Controller(BoardView boardView, Board boardModel, Game game, TopBarView topBarView) {
        this.boardView = boardView;
        this.boardModel = boardModel;
        this.game = game;
        this.topBarView = topBarView;
        boardActive = true;

        setUpViews();
    }

    public void setUpViews() {
        setUpSquares();

        setUpButtons();
    }

    public void setUpSquares() {
        int i = 0;
        for (SquareView squareView: boardView.getSquares()) {
            final int finalI = i;
            squareView.addActionListener(new ActionListener() {
                Square square = boardModel.getSquare(finalI);
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (boardActive) {
                        game.squareClicked(square);
                    }
                }
            });
            i++;
        }
    }

    public void setUpButtons() {
        JButton VSMode = topBarView.getVSMode();

        VSMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardActive = true;
                game.setUpGame();
            }
        });

        JButton AIMode = topBarView.getAIMode();

        AIMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardActive = true;
                game.setUpAIGame();
            }
        });

        /*JButton button3 = topBarView.getButton3();

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });*/
    }

    public void activateBoard() { boardActive = true; }

    public void freezeBoard() { boardActive = false; }
}

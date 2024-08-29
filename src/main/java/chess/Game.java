package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Game extends JFrame {
    private String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    private BoardView board;
    private Board boardModel;
    private Controller controller;

    Player player1;
    Player player2;

    public Game() {
        board = new BoardView();
        boardModel = new Board();
        controller = new Controller(board, boardModel);

        player1 = new Player(getName());
        player2 = new Player(getName());

        boardModel.addPropertyChangeListener(board);
        boardModel.fenToBoard(startFen);

        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void runGame() {

    }

    public String getName() {
        return "defaultName";
    }

}
package chess;

import chess.Database.SQLConnection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GamePagePanel extends JPanel{
    private JPanel gameInfo;
    private JPanel game;
    private JButton backButton;

    public GamePagePanel() {
        gameInfo = new JPanel();
        game = new JPanel();
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        backButton = new JButton("Back");

        this.setLayout(new BorderLayout());

        this.add(gameInfo, BorderLayout.NORTH);
        this.add(game, BorderLayout.CENTER);
        this.add(backButton, BorderLayout.SOUTH);
    }

    public void loadGame(ArrayList<String> turns, String user, String opponent) throws SQLException, IOException {
        game.removeAll();
        gameInfo.removeAll();

        for (String turn : turns) {
            game.add(new JLabel(turn));
        }

        gameInfo.add(new JLabel("VS. " + opponent + " Record: " + SQLConnection.getInstance().getHeadToHead(user, opponent)));
    }

    public JButton getBackButton() { return backButton; }
}

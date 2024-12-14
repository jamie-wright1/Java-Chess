package chess;

import chess.Database.SQLConnection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaderboardView extends JPanel {
    private JPanel currentLeaderboard;
    private JPanel allTimeLeaderboard;

    public LeaderboardView() {
        currentLeaderboard = new JPanel();
        currentLeaderboard.setLayout(new BoxLayout(currentLeaderboard, BoxLayout.Y_AXIS));

        allTimeLeaderboard = new JPanel();
        allTimeLeaderboard.setLayout(new BoxLayout(allTimeLeaderboard, BoxLayout.Y_AXIS));

        this.setLayout(new BorderLayout());
        this.add(currentLeaderboard, BorderLayout.WEST);
        this.add(allTimeLeaderboard, BorderLayout.EAST);
    }

    public void loadLeaderboard() throws SQLException, IOException {
        ArrayList<String> current = SQLConnection.getInstance().getLeaderboard();
        ArrayList<String> allTime = SQLConnection.getInstance().getAllTimeLeaderboard();

        currentLeaderboard.removeAll();
        currentLeaderboard.add(new JLabel("Current Leaderboard:"));
        for (String leader : current) {
            currentLeaderboard.add(new JLabel(leader));
        }

        allTimeLeaderboard.removeAll();
        allTimeLeaderboard.add(new JLabel("All Time Leaderboard:"));
        for (String leader : allTime) {
            allTimeLeaderboard.add(new JLabel(leader));
        }
    }
}

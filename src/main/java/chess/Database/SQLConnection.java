package chess.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.time.LocalDate;
import java.time.LocalTime;

public final class SQLConnection {
    private static  SQLConnection INSTANCE;

    private String url;
    private Properties props;
    private Connection cn;

    public enum RESULT {
        WIN,
        LOSS,
        DRAW
    }

    private SQLConnection() throws IOException, SQLException {
        // connection info
        url = "jdbc:postgresql://cps-postgresql.gonzaga.edu/jwright9_db";
        props = new Properties();

        FileInputStream in = new FileInputStream("src/main/java/chess/Database/config.properties");
        props.load(in);
        in.close();

        //connect
        cn = DriverManager.getConnection(url, props);
    }

    public static SQLConnection getInstance() throws IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new SQLConnection();
        }
        return INSTANCE;
    }

    public void insertGame(String player_one, String player_two, ArrayList<String> turns, RESULT player_one_result) throws SQLException {
        Date date_played = Date.valueOf(LocalDate.now());
        Time time_played = Time.valueOf(LocalTime.now());
        boolean whiteWon = player_one_result.equals(RESULT.WIN);

        //Get number of games
        String num_games = "SELECT MAX(game_id) AS count FROM game";
        Statement stmt = cn.createStatement();
        ResultSet rs = stmt.executeQuery(num_games);

        rs.next();
        int game_id = rs.getInt("count") + 1;

        stmt.close();
        rs.close();

        String query = "INSERT INTO game VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement st = cn.prepareStatement(query);
        st.setInt(1, game_id);
        st.setDate(2, date_played);
        st.setTime(3, time_played);
        st.setString(4, player_one);
        st.setString(5, player_two);
        st.setBoolean(6, whiteWon);
        st.execute();

        st.close();

        insertTurns(game_id, turns);

        RESULT player_two_result;

        if (player_one_result.equals(RESULT.WIN)) {
            player_two_result = RESULT.LOSS;
        } else if (player_one_result.equals(RESULT.LOSS)){
            player_two_result = RESULT.WIN;
        } else {
            player_two_result = RESULT.DRAW;
        }

        updateElo(player_one, player_one_result);
        updateElo(player_two, player_two_result);
        updateRecord(player_one, player_one_result);
        updateRecord(player_two, player_two_result);
        update_head_to_head(player_one, player_two, player_one_result);
    }

    //Returns true if able to insert and false if already exists
    public boolean createAccount(String username, String name) throws SQLException {
            //Check if already exists
            String q = "SELECT username FROM account WHERE username = ?";
            PreparedStatement st = cn.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return false;
            }

            Date date_created = Date.valueOf(LocalDate.now());

            String query = "INSERT INTO account VALUES (?, ?, ?, 0, 0, '00-00-00')";

            st = cn.prepareStatement(query);

            st.setString(1, username);
            st.setString(2, name);
            st.setDate(3, date_created);

            st.execute();
            st.close();

            return true;
    }

    //returns an array holding all username values
    public ArrayList<String> findAccount(String username) throws SQLException {
        ArrayList<String> acct_info = new ArrayList<>();
        String query = "SELECT * FROM account WHERE username = ?";

        PreparedStatement st = cn.prepareStatement(query);

        st.setString(1, username);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            acct_info.add(rs.getString("name"));
            acct_info.add(String.valueOf(rs.getDate("date_created")));
            acct_info.add(String.valueOf(rs.getInt("elo")));
            acct_info.add(String.valueOf(rs.getInt("peak_elo")));
            acct_info.add(rs.getString("record"));
        }

        st.close();
        rs.close();

        return acct_info;
    }

    public void addFriend(String user_one, String user_two) throws SQLException {
        if (Objects.equals(user_one, user_two)) {
            return;
        }

        //Checks if friendship exists
        String q = "SELECT user_one, user_two " +
                "FROM friendship " +
                "WHERE user_one = ? AND user_two = ? OR " +
                "user_one = ? AND user_two = ?";
        PreparedStatement stmt = cn.prepareStatement(q);
        stmt.setString(1, user_one);
        stmt.setString(2, user_two);
        stmt.setString(3, user_two);
        stmt.setString(4, user_one);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return;
        }

        Date date_friended = Date.valueOf(LocalDate.now());
        String query = "INSERT INTO friendship VALUES (?, ?, ?)";
        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, user_one);
        st.setString(2, user_two);
        st.setDate(3, date_friended);

        st.execute();
        st.close();
    }

    public ArrayList<String> getLeaderboard() throws SQLException {
        ArrayList<String> leaderboard = new ArrayList<>();

        String query = "SELECT username, elo," +
                       "DENSE_RANK() OVER (ORDER BY elo DESC) AS rank " +
                       "FROM account " +
                       "ORDER BY elo DESC " +
                       "LIMIT 10";

        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String rank = String.valueOf(rs.getInt("rank"));
            String username = rs.getString("username");
            String elo = String.valueOf(rs.getInt("elo"));

            leaderboard.add(rank + ". " + username + " Elo: " + elo);
        }

        st.close();
        rs.close();

        return leaderboard;
    }

    public ArrayList<String> getAllTimeLeaderboard() throws SQLException {
        ArrayList<String> leaderboard = new ArrayList<>();

        String query = "SELECT username, peak_elo, " +
                "DENSE_RANK() OVER (ORDER BY peak_elo DESC) AS rank " +
                "FROM account " +
                "ORDER BY peak_elo DESC " +
                "LIMIT 10";

        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String rank = String.valueOf(rs.getInt("rank"));
            String username = rs.getString("username");
            String elo = String.valueOf(rs.getInt("peak_elo"));

            leaderboard.add(rank + ". " + username + " Elo: " + elo);
        }

        st.close();
        rs.close();

        return leaderboard;
    }

    public void removeFriend(String username, String friend) throws SQLException {
        String query = "DELETE FROM friendship WHERE " +
                       "user_one = ? AND user_two = ? OR " +
                       "user_two = ? AND user_one = ?";

        PreparedStatement st = cn.prepareStatement(query);

        st.setString(1, username);
        st.setString(2, friend);
        st.setString(3, username);
        st.setString(4, friend);

        st.execute();
        st.close();
    }

    //Removes account and its friends, messages, games, head-to-heads, turns, and elo changes
    public void removeAccount(String username) throws SQLException {
        //delete messages
        String del_message = "DELETE FROM message WHERE user_one = ? OR user_two = ?";
        PreparedStatement st = cn.prepareStatement(del_message);
        st.setString(1, username);
        st.setString(2, username);
        st.execute();

        //Delete friendships
        String del_friendships = "DELETE FROM friendship WHERE user_one = ? OR user_two = ?";
        st = cn.prepareStatement(del_friendships);
        st.setString(1, username);
        st.setString(2, username);
        st.execute();

        //Removes games, turns, and head_to_heads
        removeGames(username);

        //Delete account
        String delete_acct = "DELETE FROM account WHERE username = ?";
        st = cn.prepareStatement(delete_acct);
        st.setString(1, username);
        st.execute();

        st.close();
    }

    //Removes games, turns and head_to_heads
    public void removeGames(String username) throws SQLException {
        //Find games to delete turns
        ArrayList<Integer> games = new ArrayList<>();
        String find_games = "SELECT game_id FROM game WHERE white_player = ? OR black_player = ?";
        PreparedStatement st = cn.prepareStatement(find_games);
        st.setString(1, username);
        st.setString(2, username);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            games.add(rs.getInt("game_id"));
        }

        //Delete turns
        String del_turns = "DELETE FROM turn WHERE game_id = ?";
        st = cn.prepareStatement(del_turns);

        for (Integer game : games) {
            st.setInt(1, game);
            st.execute();
        }

        rs.close();

        //Delete games
        String del_games = "DELETE FROM game WHERE white_player = ? OR black_player = ?";
        st = cn.prepareStatement(del_games);
        st.setString(1, username);
        st.setString(2, username);
        st.execute();

        //Delete head_to_heads
        String del_head = "DELETE FROM head_to_head WHERE user_one = ? OR user_two = ?";
        st = cn.prepareStatement(del_head);
        st.setString(1, username);
        st.setString(2, username);
        st.execute();

        st.close();
    }

    public void updateElo(String username, RESULT result) throws SQLException {
        int newElo;
        int peak_elo;

        if (result == RESULT.DRAW) {
            return;
        }

        //Get current elo
        String q = "SELECT elo, peak_elo " +
                "FROM account " +
                "WHERE username = ?";
        PreparedStatement stmt = cn.prepareStatement(q);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        rs.next();

        if (result == RESULT.WIN) {
            newElo = rs.getInt("elo") + 30;
        } else {
            newElo = rs.getInt("elo") - 30;
        }
        peak_elo = rs.getInt("peak_elo");

        String query = "UPDATE account SET elo = ? WHERE username = ?";
        PreparedStatement st = cn.prepareStatement(query);
        st.setInt(1, newElo);
        st.setString(2, username);
        st.execute();

        if (newElo > peak_elo) {
            String qry = "UPDATE account SET peak_elo = ? WHERE username = ?";
            PreparedStatement st2 = cn.prepareStatement(qry);
            st2.setInt(1, peak_elo);
            st2.setString(2, username);
            st2.execute();
            st2.close();
        }



        st.close();
        stmt.close();
        rs.close();
    }

    public void updateRecord(String username, RESULT result) throws SQLException {
        //Get record
        String query = "SELECT record FROM account WHERE username = ?";

        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();

        rs.next();
        String record = rs.getString("record");

        String updated_record;

        //Replace the record
        updated_record = updateRecord(result, record);

        String q = "UPDATE account SET record = ? WHERE username = ?";
        st = cn.prepareStatement(q);
        st.setString(1, updated_record);
        st.setString(2, username);
        st.execute();

        st.close();
        rs.close();
    }

    private String updateRecord(RESULT result, String record) {
        String updated_record;
        if (result == RESULT.WIN) {
            String wins = record.substring(0, 1);
            int updated_wins = Integer.parseInt(wins) + 1;

            if (updated_wins > 99) {
                updated_record = record;
            } else {
                wins = String.valueOf(updated_wins);
                updated_record = wins + record.substring(3);
            }
        } else if (result == RESULT.LOSS) {
            String wins = record.substring(3, 4);
            int updated_wins = Integer.parseInt(wins) + 1;

            if (updated_wins > 99) {
                updated_record = record;
            } else {
                wins = String.valueOf(updated_wins);
                updated_record = record.substring(0, 2) + wins + record.substring(5);
            }
        } else {
            String wins = record.substring(7, 8);
            int updated_wins = Integer.parseInt(wins) + 1;

            if (updated_wins > 99) {
                updated_record = record;
            } else {
                wins = String.valueOf(updated_wins);
                updated_record = record.substring(0, 6) + wins;
            }
        }
        return updated_record;
    }

    public void update_head_to_head(String player_one, String player_two, RESULT player_one_result) throws SQLException {
        //Get current record
        String record;
        String updated_record;
        String query;
        PreparedStatement st;

        String q = "SELECT user_one, record FROM head_to_head WHERE user_one = ? AND user_two = ? " +
                    "UNION " +
                    "SELECT user_one, record FROM head_to_head WHERE user_one = ? AND user_two = ?";
        PreparedStatement stmt = cn.prepareStatement(q);
        stmt.setString(1, player_one);
        stmt.setString(2, player_two);
        stmt.setString(3, player_two);
        stmt.setString(4, player_one);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            record = rs.getString("record");
            //Replace the record
            updated_record = updateRecord(player_one_result, record);
            query = "UPDATE head_to_head SET record = ? WHERE user_one = ? OR user_two = ?";
            st = cn.prepareStatement(query);
            st.setString(1, updated_record);
            st.setString(2, player_one);
            st.setString(3, player_one);
        } else {
            updated_record = "00-00-00";
            query = "INSERT INTO head_to_head VALUES(?,?,?)";
            st = cn.prepareStatement(query);
            st.setString(1, player_one);
            st.setString(2, player_two);
            st.setString(3, updated_record);
        }

        st.execute();

        stmt.close();
        rs.close();
        st.close();
    }

    public void insertTurns(int game_id, ArrayList<String> turns) throws SQLException {
        String query = "INSERT INTO turn VALUES(?, ?, ?)";
        PreparedStatement st = cn.prepareStatement(query);

        int i = 1;
        for (String turn : turns) {
            st.setInt(1, game_id);
            st.setInt(2, i);
            st.setString(3, turn);
            st.execute();
            i++;
        }

        st.close();
    }

    //Returns updated message list
    public void sendMessage(String user_one, String user_two, String user_sent, String message) throws SQLException {
        if (user_sent != user_one && user_sent != user_two) {
            return;
        }

        Date date_sent = Date.valueOf(LocalDate.now());
        Time time_sent = Time.valueOf(LocalTime.now());

        String query = "INSERT INTO message VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, user_one);
        st.setString(2, user_two);
        st.setString(3, user_sent);
        st.setString(4, message);
        st.setDate(5, date_sent);
        st.setTime(6, time_sent);

        st.execute();
    }

    public ArrayList<ArrayList<String>> getRecentGames(String username) throws SQLException {
        ArrayList<ArrayList<String>> games = new ArrayList<>();

        String query = "SELECT opponent, date, time, game_id " +
                        "FROM (" +
                            "SELECT black_player AS opponent, date, time, game_id " +
                            "FROM account JOIN game ON (? = white_player) " +
                            "UNION " +
                            "SELECT white_player AS opponent, date, time, game_id " +
                            "FROM account JOIN game ON (? = black_player)) AS games " +
                        "ORDER BY date DESC, time DESC " +
                        "LIMIT 20";

        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, username);
        st.setString(2, username);
        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            ArrayList<String> game = new ArrayList<>();
            game.add(rs.getString("opponent"));
            game.add(String.valueOf(rs.getDate("date")));
            game.add(String.valueOf(rs.getTime("time")));
            game.add(String.valueOf(rs.getInt("game_id")));
            games.add(game);
        }

        st.close();
        rs.close();

        return games;
    }

    public ArrayList<String> getRecentMessages(String user_one, String user_two) throws SQLException {
        ArrayList<String> recentMessages = new ArrayList<>();

        String query = "SELECT user_sent, message, date_sent " +
                       "FROM message " +
                       "WHERE user_one = ? AND user_two = ? OR " +
                       "user_one = ? AND user_two = ? " +
                       "ORDER BY date_sent DESC, time_sent DESC " +
                       "LIMIT 20";

        PreparedStatement st = cn.prepareStatement(query);

        st.setString(1, user_one);
        st.setString(2, user_two);
        st.setString(3, user_two);
        st.setString(4, user_one);

        ResultSet messages = st.executeQuery();

        while (messages.next()) {
            recentMessages.add(messages.getString("user_sent") + ", " + messages.getDate("date_sent") + ": " + messages.getString("message"));
        }

        st.close();
        messages.close();

        return recentMessages;
    }

    public ArrayList<ArrayList<String>> getFriends(String username) throws SQLException {
        ArrayList<ArrayList<String>> friends = new ArrayList<>();
        String query = "SELECT user_two AS friend, date_friended " +
                        "FROM friendship " +
                        "WHERE user_one = ? " +
                        "UNION " +
                        "SELECT user_one AS friend, date_friended " +
                        "FROM friendship " +
                        "WHERE user_two = ? " +
                        "ORDER BY date_friended DESC " +
                        "LIMIT 20";

        PreparedStatement st = cn.prepareStatement(query);

        st.setString(1, username);
        st.setString(2, username);
        ResultSet rs = st.executeQuery();


        while (rs.next()) {
            ArrayList<String> friend = new ArrayList<>();
            friend.add(rs.getString("friend"));
            friend.add(rs.getDate("date_friended").toString());
            friends.add(friend);
        }

        st.close();
        rs.close();

        return friends;
    }

    public ArrayList<String> getFriend(String user, String friend) throws SQLException {
        ArrayList<String> friendInfo = new ArrayList<>();

        String query = "SELECT date_friended " +
                        "FROM friendship " +
                        "WHERE user_one = ? AND user_two = ? OR " +
                        "user_one = ? AND user_two = ?";
        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, user);
        st.setString(2, friend);
        st.setString(3, friend);
        st.setString(4, user);
        ResultSet rs = st.executeQuery();
        rs.next();
        friendInfo.add(rs.getString("date_friended"));

        String q = "SELECT record " +
                    "FROM head_to_head " +
                    "WHERE user_one = ? AND user_two = ? OR " +
                    "user_one = ? AND user_two = ?";
        PreparedStatement stmt = cn.prepareStatement(q);
        stmt.setString(1, user);
        stmt.setString(2, friend);
        stmt.setString(3, friend);
        stmt.setString(4, user);
        ResultSet rs2 = stmt.executeQuery();

        if(rs2.next()) {
            friendInfo.add(rs2.getString("record"));
        } else {
            friendInfo.add("00-00-00");
        }

        return friendInfo;
    }

    public int getAverageGameLength(String username) throws SQLException {
        int average_length;

        //Uses a subquery to find number of turns per game
        String query = "SELECT AVG(turns) AS turns " +
                       "FROM " +
                            //subquery
                            "(SELECT COUNT(*) AS turns " +
                            "FROM account JOIN game ON (username = white_player OR username = black_player) " +
                            "JOIN turn USING (game_id) " +
                            "WHERE username = ? " +
                            "GROUP BY game_id) AS turn_count";

        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, username);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            average_length = rs.getInt("turns");
        } else {
            average_length = 0;
        }

        rs.close();
        st.close();

        return average_length;
    }

    public ArrayList<String> getTurns(int game_id) throws SQLException  {
        ArrayList<String> turns = new ArrayList<>();

        String query = "SELECT move, turn_number " +
                        "FROM turn JOIN game USING (game_id) " +
                        "WHERE game_id = ? " +
                        "ORDER by turn_number ASC";

        PreparedStatement st = cn.prepareStatement(query);
        st.setInt(1, game_id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            turns.add(String.valueOf(rs.getInt("turn_number")) + ". " + rs.getString("move"));
        }

        st.close();
        rs.close();

        return turns;
    }

    public String getHeadToHead(String user, String opponent) throws SQLException {
        String query = "SELECT record " +
                        "From head_to_head " +
                        "WHERE user_one = ? AND user_two = ? " +
                        "OR user_one = ? AND user_two = ?";

        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, user);
        st.setString(2, opponent);
        st.setString(3, opponent);
        st.setString(4, opponent);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return rs.getString("record");
        } else {
            return "00-00-00";
        }
    }
}
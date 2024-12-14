package chess;

import chess.Database.SQLConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountPageView extends JPanel {
    private JPanel userInfoPanel;

    private CardLayout cardLayout;

    private JPanel gameContainer;
    private JPanel gamePanel;
    private GamePagePanel gamePage;
    private ArrayList<JButton> gameButtons;
    private ArrayList<Integer> game_ids;
    private ArrayList<String> opponents;
    private JTextField addFriend;
    private JLabel num_games;

    private JPanel friendContainer;
    private JPanel friendPanel;
    private FriendPagePanel friendPage;
    private ArrayList<JButton> friendButtons;
    public ArrayList<String> friends;
    private JLabel num_friends;

    private JLabel user_info;
    private JPanel listedInfo;
    private JLabel name;
    private JLabel elo;
    private JLabel peakElo;
    private JLabel date_created;

    private JButton removeAccount;

    private String activeUser;

    public AccountPageView() {
        userInfoPanel = new JPanel(new FlowLayout());
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePage = new GamePagePanel();

        friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));
        friendPage = new FriendPagePanel();

        cardLayout = new CardLayout();
        friendContainer = new JPanel(cardLayout);
        friendContainer.add(friendPanel, "friendPanel");
        friendContainer.add(friendPage, "friendPage");
        gameContainer = new JPanel(cardLayout);
        gameContainer.add(gamePanel, "gamePanel");
        gameContainer.add(gamePage, "gamePage");

        cardLayout.show(gameContainer, "gamePanel");
        cardLayout.show(friendContainer, "friendPanel");

        listedInfo = new JPanel();
        listedInfo.setLayout(new BoxLayout(listedInfo, BoxLayout.Y_AXIS));

        user_info = new JLabel("User Info");

        name = new JLabel("Name");
        elo = new JLabel("Elo");
        peakElo = new JLabel("Peak Elo");
        date_created = new JLabel("Date Created");

        friendButtons = new ArrayList<>();
        friends = new ArrayList<>();
        gameButtons = new ArrayList<>();
        game_ids = new ArrayList<>();
        opponents = new ArrayList<>();

        gamePanel.add(new JLabel("GAMES"));
        num_games = new JLabel();
        gamePanel.add(num_games);

        friendPanel.add(new JLabel("FRIENDS"));
        num_friends = new JLabel();
        friendPanel.add(num_friends);



        for (int i = 0; i < 10; i ++) {
            friendButtons.add(new JButton());
            friendButtons.get(i).setVisible(false);
            friendPanel.add(friendButtons.get(i));
            gameButtons.add(new JButton());
            gameButtons.get(i).setVisible(false);
            gamePanel.add(gameButtons.get(i));
        }

        addFriend = new JTextField("Add Friend");
        friendPanel.add(addFriend);


        listedInfo.add(name);
        listedInfo.add(elo);
        listedInfo.add(peakElo);
        listedInfo.add(date_created);

        userInfoPanel.add(user_info);
        userInfoPanel.add(listedInfo);

        removeAccount = new JButton("Remove Account");

        this.setLayout(new BorderLayout());
        this.add(userInfoPanel, BorderLayout.NORTH);
        this.add(gameContainer, BorderLayout.WEST);
        this.add(friendContainer, BorderLayout.EAST);
        this.add(removeAccount, BorderLayout.SOUTH);
        setUpActionListeners();
    }

    //Returns true if load is successful, else false
    public boolean loadAccount(String username) throws SQLException, IOException {
        ArrayList<String> account_info = SQLConnection.getInstance().findAccount(username);

        if (account_info.isEmpty()) {
            return false;
        }

        activeUser = username;

        name.setText("Name: " + account_info.get(0));
        date_created.setText("Account created: " + account_info.get(1));
        elo.setText("Current Elo: " + account_info.get(2));
        peakElo.setText("Peak Elo: " + account_info.get(3));
        user_info.setText(username + ", " + account_info.get(4));

        loadGames();
        loadFriends();

        cardLayout.show(gameContainer, "gamePanel");
        cardLayout.show(friendContainer, "friendPanel");

        return true;
    }

    public void loadGames() throws SQLException, IOException {
        ArrayList<ArrayList<String>> games = SQLConnection.getInstance().getRecentGames(activeUser);
        String avgGameLength = String.valueOf(SQLConnection.getInstance().getAverageGameLength(activeUser));

        for (JButton button : gameButtons) {
            button.setVisible(false);
        }

        game_ids.clear();
        opponents.clear();

        for (int i = 0; i < games.size(); i++) {
            ArrayList<String> game = games.get(i);
            gameButtons.get(i).setText("vs. " + game.get(0) + " " + game.get(1) + " " + game.get(2));
            gameButtons.get(i).setVisible(true);
            game_ids.add(Integer.valueOf(game.get(3)));
            opponents.add(game.get(0));
        }

        if (games.isEmpty()) {
            num_games.setText("No games found");
        } else {
            num_games.setText(games.size() + " games found  (avg length: " + avgGameLength + " turns)");
        }
    }

    public void loadTurns(int game_id, String opponent) throws SQLException, IOException {
        ArrayList<String> turns = SQLConnection.getInstance().getTurns(game_id);
        gamePage.loadGame(turns, activeUser, opponent);
        cardLayout.show(gameContainer, "gamePage");
    }

    public void loadFriends() throws SQLException, IOException {
        ArrayList<ArrayList<String>> friend_data = SQLConnection.getInstance().getFriends(activeUser);

        for (JButton button : friendButtons) {
            button.setVisible(false);
        }

        friends.clear();

        for (int i = 0; i < friend_data.size(); i++) {
            ArrayList<String> friend = friend_data.get(i);
            friendButtons.get(i).setText(friend.get(0) + " " + friend.get(1));
            friendButtons.get(i).setVisible(true);
            friends.add(friend.get(0));
        }

        if (friend_data.isEmpty()) {
            num_friends.setText("No friends found");
        } else {
            num_friends.setText(friend_data.size() + " friends found");
        }
    }

    public void loadFriend(String friend) throws SQLException, IOException {
        friendPage.loadFriend(activeUser, friend);
        cardLayout.show(friendContainer, "friendPage");
    }

    public void setUpActionListeners() {
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = addFriend.getText();
                try {
                    if (!SQLConnection.getInstance().findAccount(user).isEmpty()) {
                        SQLConnection.getInstance().addFriend(activeUser, user);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        int i = 0;
        for (JButton button : friendButtons) {
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        loadFriend(friends.get(finalI));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            i++;
        }

        i = 0;
        for (JButton button : gameButtons) {
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        loadTurns(game_ids.get(finalI), opponents.get(finalI));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            i++;
        }

        friendPage.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(friendContainer, "friendPanel");
            }
        });

        friendPage.getSendMessageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = friendPage.getSendMessageButton().getText();
                try {
                    SQLConnection.getInstance().sendMessage(activeUser, friendPage.friend, activeUser, message);
                    cardLayout.show(friendContainer, "friendPanel");
                    loadFriend(friendPage.friend);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        friendPage.getRemoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLConnection.getInstance().removeFriend(activeUser, friendPage.friend);
                    loadAccount(activeUser);
                    cardLayout.show(friendContainer, "friendPanel");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        gamePage.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(gameContainer, "gamePanel");
            }
        });

        this.addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLConnection.getInstance().addFriend(activeUser, addFriend.getText());
                    loadAccount(activeUser);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JButton getRemoveAccount() { return removeAccount; }
}

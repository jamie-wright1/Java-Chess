package chess;

import chess.Database.SQLConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendPagePanel extends JPanel {
    JButton removeFriendButton;
    JPanel friendInfo;
    JPanel messages;
    JPanel bottomPanel;
    JButton backButton;
    JTextField sendMessageButton;

    String friend;
    String activeUser;

    public FriendPagePanel() {
        messages = new JPanel();
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        friendInfo = new JPanel();
        removeFriendButton = new JButton("Remove As Friend");
        bottomPanel = new JPanel(new FlowLayout());
        backButton = new JButton("Back");
        sendMessageButton = new JTextField("Send Message");

        this.setLayout(new BorderLayout());
        this.add(friendInfo, BorderLayout.NORTH);
        this.add(messages, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.add(sendMessageButton);
        bottomPanel.add(backButton);
        bottomPanel.add(removeFriendButton);

        friend = "";
        activeUser = "";
    }

    public void loadFriend(String user, String friend) throws SQLException, IOException {
        this.activeUser = user;
        this.friend = friend;

        ArrayList<String> getInfo = SQLConnection.getInstance().getFriend(activeUser, friend);
        ArrayList<String> recentMessages = SQLConnection.getInstance().getRecentMessages(user, friend);

        friendInfo.removeAll();
        messages.removeAll();
        for (String message : recentMessages) {
            messages.add(new JLabel(message));
        }

        friendInfo.add(new JLabel(friend + "Head-To-Head Record :" + getInfo.get(1)));
    }

    public JButton getRemoveButton() {
        return removeFriendButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSendMessageButton() { return sendMessageButton; }

}

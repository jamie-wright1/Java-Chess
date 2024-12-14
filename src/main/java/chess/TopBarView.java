package chess;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TopBarView extends JPanel implements PropertyChangeListener {
    JButton VSMode = new JButton("VS Mode");
    JButton AIMode = new JButton("AI Mode");
    JButton login = new JButton("Login");
    JButton createAccount  = new JButton("Create Account");
    JTextField enterUsername = new JTextField("Enter Username");
    JButton leaderboards = new JButton("Leaderboards");
    JButton account = new JButton("Account");

    boolean onFirstPlayer = true;

    //JButton button3 = new JButton("button3");

    JTextField gameText = new JTextField("Enter Player One");

    public TopBarView() {
        setLayout(new GridLayout(2, 4));

        this.add(enterUsername);
        this.add(login);
        login.setEnabled(false);
        this.add(createAccount);
        createAccount.setEnabled(false);
        this.add(account);
        account.setEnabled(false);

        this.add(VSMode);
        this.add(AIMode);
        VSMode.setEnabled(false);
        AIMode.setEnabled(false);

        //this.add(button3);
        this.add(gameText);
        this.add(leaderboards);
    }

    public JButton getAccount() { return account; }

    public JButton getVSMode() { return VSMode; }

    public JButton getAIMode() { return AIMode; }

    public JTextField getEnterUsername() { return enterUsername; }

    public JTextField getGameText() { return gameText; }

    public JButton getLogin() { return login; }

    public JButton getCreateAccount() { return createAccount; }

    public JButton getLeaderboard() { return leaderboards; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("gameOver".equals(evt.getPropertyName())) {
            gameText.setText("Checkmate!");
        }
        if ("stalemate".equals(evt.getPropertyName())) {
            gameText.setText("Stalemate!");
        }
        if ("gameStarted".equals(evt.getPropertyName())) {
            gameText.setText(null);
        }

        updateUI();
    }
}

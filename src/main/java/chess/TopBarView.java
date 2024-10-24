package chess;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TopBarView extends JPanel implements PropertyChangeListener {
    JButton VSMode = new JButton("VS Mode");
    JButton AIMode = new JButton("AI Mode");
    //JButton button3 = new JButton("button3");

    JTextArea textField = new JTextArea("");

    public TopBarView() {
        setLayout(new FlowLayout());
        this.add(VSMode);
        this.add(AIMode);
        //this.add(button3);
        this.add(textField);

        textField.setBackground(Color.WHITE);
        textField.setEditable(false);
    }

    public JButton getVSMode() { return VSMode; }

    public JButton getAIMode() { return AIMode; }

    //public JButton getButton3() { return button3; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("gameOver".equals(evt.getPropertyName())) {
            textField.setText("Checkmate!");
        }
        if ("stalemate".equals(evt.getPropertyName())) {
            textField.setText("Stalemate!");
        }
        if ("gameStarted".equals(evt.getPropertyName())) {
            textField.setText(null);
        }

        updateUI();
    }
}

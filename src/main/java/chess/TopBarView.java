package chess;

import javax.swing.*;
import java.awt.*;

public class TopBarView extends JPanel {
    JButton VSMode = new JButton("VS Mode");
    JButton AIMode = new JButton("AI Mode");
    JButton button3 = new JButton("button3");

    public TopBarView() {
        setLayout(new FlowLayout());
        this.add(VSMode);
        this.add(AIMode);
        this.add(button3);
    }

}

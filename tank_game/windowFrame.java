package tank_game;

import java.awt.*;
import javax.swing.*;

public class windowFrame extends Canvas {

    public windowFrame(int width, int height){
        JFrame frame = new JFrame();
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
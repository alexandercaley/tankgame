package tank_game;

import java.awt.*;
import javax.swing.*;

public class windowFrame extends Canvas {

    public windowFrame(){}

    public void makeFrame(){
        JFrame frame = new JFrame();
        Canvas canvas = new windowFrame();
        canvas.setSize(1280, 900);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
}
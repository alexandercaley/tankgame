package tank_game;

import java.awt.*;
import javax.swing.*;

public class windowFrame extends Canvas {

    public windowFrame(String title, int width, int height, tankGame game){
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        game.start();
    }
}
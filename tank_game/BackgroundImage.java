package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BackgroundImage extends JPanel{

    private int width;
    private int height;
    private BufferedImage background;

    public BackgroundImage(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void initialize(BufferedImage imagePath){
        background = imagePath;
    }
}
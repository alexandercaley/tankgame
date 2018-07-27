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

    public void render(Graphics display){
        int tileWidth = background.getWidth(this);
        int tileHeight = background.getHeight(this);
        int x = width / tileWidth;
        int y = height / tileHeight;
        for (int i = -1; i <= y; i++) {
            for (int j = 0; j <= x; j++) {
                display.drawImage(background, j * tileWidth, i * tileHeight, tileWidth, tileHeight, this);
            }
        }
    }
}
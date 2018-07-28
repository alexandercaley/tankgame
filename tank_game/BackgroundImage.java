package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BackgroundImage extends JPanel{

    private int width;
    private int height;
    private BufferedImage background;

    public BackgroundImage(int width, int height, BufferedImage background){
        this.width = width;
        this.height = height;
        this.background = background;
    }

    public void render(Graphics display){
        int x = this.width / this.background.getWidth(this);
        int y = this.height / this.background.getHeight(this);
        for (int i = -1; i <= y; i++) {
            for (int j = 0; j <= x; j++) {
                display.drawImage(
                        this.background,
                        j * this.background.getWidth(this),
                        i * this.background.getHeight(this),
                        this.background.getWidth(this),
                        this.background.getHeight(this),
                        this
                );
            }
        }
    }
}
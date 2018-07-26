package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BreakableWalls extends Walls{

    //time for walls to respawn
    private int respawn = 60;

    public BreakableWalls(BufferedImage image, int x, int y){
        super(image, x, y);
        setTangible(true);
        setVisible(true);
    }

    public void render(Graphics graphics, ImageObserver observer){
        if (isVisible() == false){
            this.respawn--;
            if(this.respawn <= 0){
                this.respawn = 60;
                setVisible(true);
                setTangible(true);
            }
        }
        else{
            super.render(graphics, observer);
        }
    }
}

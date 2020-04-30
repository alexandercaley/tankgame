package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Walls {
    protected boolean visible;
    protected boolean tangible;
    private int x;
    private int y;
    private BufferedImage image;

    public Walls(BufferedImage image, int x, int y){
        this.x = x;
        this.y = y;
        this.image = image;
        this.visible = true;
        this.tangible = true;
    }

    public void render(Graphics graphics, ImageObserver observer){
        graphics.drawImage(image, x, y, observer);
    }

    public void setTangible(boolean tangible) {
        this.tangible = tangible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public BufferedImage getImage(){
        return image;
    }

    public boolean isVisible() {
        return visible;
    }
}

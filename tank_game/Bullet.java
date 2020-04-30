package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Bullet {
    private int angle;
    private int x;
    private int y;
    private BufferedImage bullet;
    private boolean visible;

    public Bullet(BufferedImage image, int x, int y, int angle){
        this.x = x;
        this.y = y;
        this.bullet = image;
        this.angle = angle;
        this.visible = true;
    }

    public void draw(Graphics graphic, ImageObserver observer){
        graphic.drawImage(this.bullet, this.x, this.y, observer);
    }

    public void update(){
        x += Math.cos(Math.toRadians(angle)) * 12;
        y -= Math.sin(Math.toRadians(angle)) * 12;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public BufferedImage getImage(){
        return this.bullet;
    }

    public boolean isVisible() {
        return visible;
    }
}

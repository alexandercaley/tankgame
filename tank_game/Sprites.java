package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Sprites {
    protected TankGame game;
    private BufferedImage image;
    private BufferedImage[] images;
    private String type;
    protected int x;
    protected int y;
    protected int item;
    protected int totalItems;

    public Sprites(BufferedImage image, int x, int y, int item, int totalItems, String type){
        game = TankGame.getGame();
        this.image = image;
        this.x = x;
        this.y = y;
        this.item = item;
        this.totalItems = totalItems;
        this.type = type;
        this.images = new BufferedImage[100];
        for (int i = 0; i < this.totalItems; i++){
            this.images[i] = this.image.getSubimage(i * (this.image.getWidth() / this.totalItems),
                    0, (this.image.getWidth() / this.totalItems), this.image.getHeight());
        }
    }

    public void render(Graphics graphic, ImageObserver observer){
        graphic.drawImage(images[item], x, y, observer);
    }


    public BufferedImage getImage() {
        return this.images[this.item];
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }

    public String getType(){
        return type;
    }
}

package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ImageLoader {
    protected TankGame game;
    private BufferedImage image;
    private BufferedImage[] images = new BufferedImage[100];
    protected int speed = 6;
    private String type;
    boolean visible = true;
    protected int x;
    protected int y;
    protected int item = 0;
    protected int totalItems;

    public ImageLoader(BufferedImage image, int x, int y, int item, int totalItems, String type){
        this.totalItems = totalItems;
        this.item = item;
        this.type = type;
        init(image, x, y);
    }

    private void init(BufferedImage image, int x, int y){
        game = TankGame.getGame();
        this.x = x;
        this.y = y;
        this.image = image;
        int width = image.getWidth()/totalItems;
        int height = image.getHeight();
        for (int i = 0; i < totalItems; i++){
            images[i] = image.getSubimage(i * width, 0, width, height);
        }
    }

    public void render(Graphics graphic, ImageObserver observer){
        graphic.drawImage(images[item], x, y, observer);
    }


    public BufferedImage getImage() {
        return image;
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
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public String getType(){
        return type;
    }
}

package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends ImageLoader{

    protected int vx;
    protected int vy;
    protected int initItem;
    public ArrayList<Bullet> bullets;
    private int angle;
    private final int rotation = 6;
    protected boolean[] keys = new boolean[4];
    private boolean powerup = false;
    private int health = 100;
    private int lives = 3;


    public Tank(BufferedImage image, int vx, int vy, int item, int totalItems){
        super(image, vx, vy, item, totalItems, "tank");
        bullets = new ArrayList<>();
        angle = rotation * item;
        this.vx = vx;
        this.vy = vy;
        this.initItem = item;
        for (int i = 0; i < 2; i++){
            keys[i] = false;
        }
    }

    public void move(){
        if (keys[0]) rotateLeft();
        if (keys[1]) moveBackward();
        if (keys[2]) rotateRight();
        if (keys[3]) moveForward();
    }

    public void rotateLeft(){
        item++;
        if (item > 59) {
            item = 0;
        }
        angle = rotation * item;
    }

    public void rotateRight(){
        item--;
        if (item < 0){
            item = 59;
        }
        angle = rotation * item;
    }

    public void moveForward(){
        x += (speed * Math.cos(Math.toRadians(angle)));
        y -= (speed * Math.sin(Math.toRadians(angle)));
    }

    public void moveBackward(){
        x -= (speed * Math.cos(Math.toRadians(angle)));
        y += (speed * Math.sin(Math.toRadians(angle)));
    }

    public Rectangle returnBounds(){
        return new Rectangle(x, y, 54, 54);
    }

    public ArrayList getBullets(){
        return bullets;
    }

    public void shoot(){
        bullets.add(new Bullet(game.loadImages().get("bullet"),
                x + getImage().getWidth()/2,
                y + getImage().getHeight()/2, angle));
        if (powerup){
            bullets.add(new Bullet(game.loadImages().get("bullet"),
                    x + getImage().getWidth()/4,
                    y + getImage().getHeight()/4, angle));
        }
    }

    //checks tank lives and respawns tank or ends game
    public void respawn(){

    }

    public void collisions(Tank t1){
        Rectangle t = returnBounds();
        Rectangle tRec = t1.returnBounds();

        //check powerups and tank
        for (int i = 0; i < game.objects.size(); i++){
            if (game.objects.get(i).getType().equals("powerUp")){
                Rectangle powers = game.objects.get(i).getBounds();
                if (t.intersects(powers)){
                    powerup = true;
                    game.objects.remove(i);
                }
            }
        }
        //check bullets and tanks
        for (int i = 0; i < bullets.size(); i++){
            boolean delete = false;
            Rectangle bullet = new Rectangle(bullets.get(i).getX(),bullets.get(i).getY(),
                    bullets.get(i).getImage().getWidth(), bullets.get(i).getImage().getHeight());
            //for bullet intersect tank
            if (bullet.intersects(tRec)){
                delete = true;
                t1.health = t1.health - 20;
                if (t1.health <= 0){
                    t1.respawn();
                }
            }

            //check bullets and breakable walls
            for (int j = 0; j < game.temporaryWalls.size(); j++){
                Rectangle breakablewall = new Rectangle(game.temporaryWalls.get(j).getX(),
                        game.temporaryWalls.get(j).getY(),
                        game.temporaryWalls.get(j).getImage().getWidth(),
                        game.temporaryWalls.get(j).getImage().getHeight());
                if (bullet.intersects(breakablewall) && game.temporaryWalls.get(j).isVisible()){
                    game.temporaryWalls.get(j).setVisible(false);
                    game.temporaryWalls.get(j).setTangible(false);
                    delete = true;
                }
            }

            //check bullets and unbreakable walls
            for(int j = 0; j < game.objects.size(); j++){
                if (game.objects.get(i).getType().equals("wall")){
                    Rectangle wall = new Rectangle(game.objects.get(j).getX(), game.objects.get(j).getY(),
                            game.objects.get(j).getImage().getWidth(), game.objects.get(j).getImage().getHeight());
                    if (bullet.intersects(wall)){
                        delete = true;
                    }
                }
            }
            if (delete == true){
                bullets.remove(i);
            }
        }
    }
}

package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends Sprites {

    private boolean show;
    public ArrayList<Bullet> bullets;
    private int angle;
    private final int rotation;
    protected boolean[] keys;
    private boolean powerUp;
    private int health;
    private int lives;
    protected ArrayList<Explosions> explosions;


    public Tank(BufferedImage image, int x, int y, int item, int totalItems){
        super(image, x, y, item, totalItems, "tank");
        this.bullets = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.item = item;
        this.rotation = 6;
        this.angle = this.rotation * this.item;
        this.show = true;
        this.powerUp = false;
        this.lives = 3;
        this.health = 100;
        this.keys = new boolean[4];
        for (int i = 0; i < 4; i++){
            this.keys[i] = false;
        }
    }

    public void move(){
        if (keys[0]) this.rotateLeft();
        if (keys[1]) this.moveBackward();
        if (keys[2]) this.rotateRight();
        if (keys[3]) this.moveForward();
    }

    public void rotateLeft(){
        this.item++;
        if (this.item > 59) {
            this.item = 0;
        }
        this.angle = this.rotation * this.item;
    }

    public void rotateRight(){
        this.item--;
        if (this.item < 0){
            this.item = 59;
        }
        this.angle = this.rotation * this.item;
    }

    public void moveForward(){
        x += (this.getSpeed() * Math.cos(Math.toRadians(this.angle)));
        y -= (this.getSpeed() * Math.sin(Math.toRadians(this.angle)));
    }

    public void moveBackward(){
        x -= (this.getSpeed() * Math.cos(Math.toRadians(this.angle)));
        y += (this.getSpeed() * Math.sin(Math.toRadians(this.angle)));
    }

    public Rectangle returnBounds(){
        return new Rectangle(x, y, 54, 54);
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public void shoot(){
        bullets.add(new Bullet(game.returnImages().get("bullet"),
                x + this.getImage().getWidth()/2,
                y + this.getImage().getHeight()/2, this.angle));
        if (powerUp){
            bullets.add(new Bullet(game.returnImages().get("bullet"),
                    x + this.getImage().getWidth()/4,
                    y + this.getImage().getHeight()/4, this.angle));
        }
    }

    //checks tank lives and respawn tank or ends game
    public void respawn(){
        this.lives--;
        if (this.lives > 1){
            this.health = this.health + 100;
        }
    }

    public void collisions(Tank t1){
        Rectangle t = returnBounds();
        Rectangle tRec = t1.returnBounds();

        //check powerUps and tank
        for (int i = 0; i < game.objects.size(); i++){
            if (game.objects.get(i).getType().equals("powerUp")){
                Rectangle powers = game.objects.get(i).getBounds();
                if (t.intersects(powers)){
                    powerUp = true;
                    game.objects.remove(i);
                }
            }
        }
        //check bullets and tanks
        for (int i = 0; i < bullets.size(); i++){
            boolean remove = false;
            Rectangle bullet = new Rectangle(bullets.get(i).getX(),bullets.get(i).getY(),
                    bullets.get(i).getImage().getWidth(), bullets.get(i).getImage().getHeight());
            //for bullet intersect tank
            if (bullet.intersects(tRec)){
                this.explosions.add(new Explosions(game.returnImages().get("explosion"),
                        (int) bullet.getX() - 16,
                        (int) bullet.getY() - 16, 0, 6));
                remove = true;
                t1.health = t1.health - 20;
                if (t1.health <= 0){
                    t1.respawn();
                }
            }

            //check bullets and breakable walls
            for (int j = 0; j < game.temporaryWalls.size(); j++){
                Rectangle breakableWall = new Rectangle(game.temporaryWalls.get(j).getX(),
                        game.temporaryWalls.get(j).getY(),
                        game.temporaryWalls.get(j).getImage().getWidth(),
                        game.temporaryWalls.get(j).getImage().getHeight());
                if (bullet.intersects(breakableWall) && game.temporaryWalls.get(j).isVisible()){
                    this.explosions.add(new Explosions(game.returnImages().get("explosion"),
                            game.temporaryWalls.get(j).getX(),
                            game.temporaryWalls.get(j).getY(),
                            0, 6));
                    game.temporaryWalls.get(j).setVisible(false);
                    remove = true;
                }
            }

            //check bullets and unbreakable walls
            for(int j = 0; j < game.objects.size(); j++){
                if (game.objects.get(i).getType().equals("wall")){
                    Rectangle wall = new Rectangle(game.objects.get(j).getX(), game.objects.get(j).getY(),
                            game.objects.get(j).getImage().getWidth(), game.objects.get(j).getImage().getHeight());
                    if (bullet.intersects(wall)){
                        remove = true;
                        this.explosions.add(new Explosions(
                                game.returnImages().get("explosion"),
                                (int) wall.getX(),
                                (int) wall.getY(),
                                0, 6)
                        );
                    }
                }
            }
            if (remove == true){
                bullets.remove(i);
            }
        }
    }

    public boolean[] getKeys(){
        return this.keys;
    }

    public boolean isShown(){
        return this.show;
    }

    public int getLives(){
        return this.lives;
    }

    public int getHealth(){
        return this.health;
    }
}

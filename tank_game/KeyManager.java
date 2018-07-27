package tank_game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyManager {
    private int [] p1;
    private int [] p2;
    TankGame game = TankGame.getGame();

    public void move(ArrayList<Tank> tanks){
        p1 = new int [2];
        p2 = new int [2];
        p1[0] = tanks.get(0).getX();
        p1[1] = tanks.get(0).getY();
        p2[0] = tanks.get(1).getX();
        p2[1] = tanks.get(1).getY();
        tanks.get(0).move();
        tanks.get(1).move();
    }

    public void pressed(KeyEvent event, ArrayList<Tank> tanks){
        if (event.getKeyCode() == KeyEvent.VK_A){
            tanks.get(0).keys[0] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_W) {
            tanks.get(0).keys[3] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_D) {
            tanks.get(0).keys[2] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_S) {
            tanks.get(0).keys[1] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_LEFT) {
            tanks.get(1).keys[0] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_UP) {
            tanks.get(1).keys[3] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            tanks.get(1).keys[2] = true;
        }
        if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            tanks.get(1).keys[1] = true;
        }
    }

    public void released(KeyEvent event, ArrayList<Tank> tanks){
        if(event.getKeyCode() == KeyEvent.VK_A) {
            tanks.get(0).keys[0] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_W) {
            tanks.get(0).keys[3] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_D) {
            tanks.get(0).keys[2] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_S) {
            tanks.get(0).keys[1] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_LEFT) {
            tanks.get(1).keys[0] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_UP) {
            tanks.get(1).keys[3] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            tanks.get(1).keys[2] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            tanks.get(1).keys[1] = false;
        }
        if(event.getKeyCode() == KeyEvent.VK_SPACE) {
            tanks.get(0).shoot();
        }
        if(event.getKeyCode() == KeyEvent.VK_PERIOD) {
            tanks.get(1).shoot();
        }
    }

    public void setup(ArrayList<Tank> tanks, ArrayList<ImageLoader> object, ArrayList<BreakableWalls> breakableWalls){
        move(tanks);
        tankCollision(tanks, object, breakableWalls);
    }

    public void tankCollision(ArrayList<Tank> tanks, ArrayList<ImageLoader> object, ArrayList<BreakableWalls> breakableWalls){
        Rectangle tank1 = new Rectangle(tanks.get(0).getX(), tanks.get(0).getY(), 55, 55);
        Rectangle tank2 = new Rectangle(tanks.get(1).getX(), tanks.get(1).getY(), 55, 55);
        if (tank1.intersects(tank2)){
            tanks.get(0).setX(p1[0]);
            tanks.get(0).setX(p1[1]);
            tanks.get(1).setX(p2[0]);
            tanks.get(1).setY(p2[1]);
        }

        for (int i = 0; i < object.size(); i++){
            Rectangle obj = new Rectangle(object.get(i).getX(), object.get(i).getY(),
                    object.get(i).getImage().getWidth()/2, object.get(i).getImage().getHeight()/2);
            if(tank1.intersects(obj)){
                tanks.get(0).setX(p1[0]);
                tanks.get(0).setY(p1[1]);
            }
            if(tank2.intersects(obj)){
                tanks.get(1).setX(p2[0]);
                tanks.get(1).setY(p2[1]);
            }
        }

        for (int i = 0; i < breakableWalls.size(); i++){
            Rectangle wall = new Rectangle(breakableWalls.get(i).getX(), breakableWalls.get(i).getY(),
                    object.get(i).getImage().getWidth(), object.get(i).getImage().getHeight());
            if (tank1.intersects(wall) && game.temporaryWalls.get(i).isVisible()){
                tanks.get(0).setX(p1[0]);
                tanks.get(0).setY(p1[1]);
            }
            if (tank2.intersects(wall) && game.temporaryWalls.get(i).isVisible()){
                tanks.get(1).setX(p2[0]);
                tanks.get(1).setY(p2[1]);
            }
        }

        tanks.get(0).collisions(tanks.get(1));
        if(game.isRunning()){
            game.endRunning();
        }
        tanks.get(1).collisions(tanks.get(0));
        if(game.isRunning()){
            game.endRunning();
        }
    }
}

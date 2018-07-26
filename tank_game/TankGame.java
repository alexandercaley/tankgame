package tank_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TankGame extends JPanel implements Runnable{

    private Thread thread;
    private BackgroundImage bg;
    public static HashMap<String, BufferedImage> imageMap;
    protected ArrayList<Tank> tanks;
    //for breakable walls
    protected ArrayList<BreakableWalls> temporaryWalls;
    protected ArrayList<ImageLoader> objects;
    private static TankGame game = new TankGame();
    private static Map mapLayout;
    Point mapSize;
    private KeyManager keyManager;
    protected KeyController key;
    private boolean running = false;

    public void setMap(){
        imageMap.put("Background", loadImages("/resources/Background.bmp");
        imageMap.put("Wall1", loadImages("/resources/Wall1.gif"));
        imageMap.put("Wall2", loadImages("/resources/Wall2.gif"));
        imageMap.put("Tank1", loadImages("/resources/Tank1.gif"));
        imageMap.put("Tank2", loadImages("/resources/Tank2.gif"));
        imageMap.put("powerUp", loadImages("/resources/Pickup.gif"));
        imageMap.put("bullet", loadImages("/resources/Rocket.gif"));
        imageMap.put("small_explosion", loadImages("/resources/Explosion_small.gif"));
        imageMap.put("large_explosion", loadImages("/resources/Explosion_large.gif"));
    }

    public BufferedImage loadImages(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResource(path));
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Invalid File!");
            System.exit(1);
        }
        return image;
    }

    public void init(){
        setFocusable(true);
        imageMap = new HashMap<String, BufferedImage>();
        setMap();
        tanks = new ArrayList<Tank>();
        objects  = new ArrayList<ImageLoader>();
        temporaryWalls = new ArrayList<>();
        mapLayout = new Map("/resources/map.txt");
        mapLayout.loadmap();
        mapSize = new Point(mapLayout.getWidth() * 32, mapLayout.getHeight() * 32);
        bg = new BackgroundImage(mapSize.x, mapSize.y);
        bg.initialize(imageMap.get("Background"));
        keyManager = new KeyManager();
        key = new KeyController();
        addKeyListener(key);

    }

    public void addTempItems(BufferedImage image, int x, int y){
        temporaryWalls.add(new BreakableWalls(image, x, y));
    }

    public void addTank1(BufferedImage image, int x, int y){
        tanks.add(new Tank(image, x, y, 0, 60));
    }

    public void addTank2(BufferedImage image, int x, int y){
        tanks.add(new Tank(image, x, y, 30, 60));
    }

    public void run(){
        Thread current = Thread.currentThread();
        while (this.thread == current){
            repaint();
            try{
                thread.sleep(20);
            } catch(Exception e){break;}
        }
    }

    public static TankGame getGame(){
        return game;
    }

    public boolean isRunning(){
        return this.running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public static void main(String[] args){

    }
}
package tank_game;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TankGame extends JPanel implements Runnable{

    private Thread thread;
    private Graphics2D graphic2d;
    protected int width;
    protected int height;
    private BufferedImage backg;
    private BufferedImage leftview;
    private BufferedImage rightview;
    private BufferedImage minimap;
    private BackgroundImage bg;
    public static HashMap<String, BufferedImage> imageMap;
    protected ArrayList<Tank> tanks;
    //for breakable walls
    protected ArrayList<BreakableWalls> temporaryWalls;
    protected ArrayList<ImageLoader> objects;
    private static TankGame game = new TankGame();
    private static Map mapLayout;
    private Point mapSize;
    private KeyManager keyManager;
    protected KeyController key;
    private boolean running = false;

    public void setMap(){
        imageMap.put("Background", loadImages("tank_game/resources/Background.bmp"));
        imageMap.put("Wall1", loadImages("tank_game/resources/Wall1.gif"));
        imageMap.put("Wall2", loadImages("tank_game/resources/Wall2.gif"));
        imageMap.put("Tank1", loadImages("tank_game/resources/Tank1.gif"));
        imageMap.put("Tank2", loadImages("tank_game/resources/Tank2.gif"));
        imageMap.put("powerUp", loadImages("tank_game/resources/Pickup.gif"));
        imageMap.put("bullet", loadImages("tank_game/resources/Rocket.gif"));
        imageMap.put("small_explosion", loadImages("tank_game/resources/Explosion_small.gif"));
        imageMap.put("large_explosion", loadImages("tank_game/resources/Explosion_large.gif"));
    }

    public HashMap<String, BufferedImage> loadImages(){
        return imageMap;
    }

    public BufferedImage loadImages(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(new File(path));
        } catch (IOException e){
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
        mapLayout = new Map("tank_game/resources/map.txt");
        mapLayout.loadMap();
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

    public void addObjects(BufferedImage image, int x, int y, int object, int total, String type){
        objects.add(new ImageLoader(image, x, y, object, total, type));
    }

    public void addTank1(BufferedImage image, int x, int y){
        tanks.add(new Tank(image, x, y, 0, 60));
    }

    public void addTank2(BufferedImage image, int x, int y){
        tanks.add(new Tank(image, x, y, 30, 60));
    }

    public void paint(Graphics graphic){
        if (backg == null){
            backg = (BufferedImage) createImage(mapSize.x, mapSize.y);
            graphic2d = backg.createGraphics();
            minimap = backg;
        }
        render();
        Dimension windowSize = getSize();
        int p1x, p1y;
        if (tanks.get(0).getX() - windowSize.width/4 > 0){
            p1x = tanks.get(0).getX() - windowSize.width/4;
        }
        else{
            p1x = 0;
        }

        if (tanks.get(0).getY() - windowSize.width/4 > 0){
            p1y = tanks.get(0).getY();
        }
        else{
            p1y = 0;
        }

        if (p1x > mapSize.x - windowSize.width/2){
            p1x = mapSize.x - windowSize.width/2;
        }
        if (p1y > mapSize.y - windowSize.height){
            p1y = mapSize.y - windowSize.height;
        }

        int p2x, p2y;
        if (tanks.get(1).getX() - windowSize.width/4 > 0){
            p2x = tanks.get(1).getX() - windowSize.width / 4;
        }
        else{
            p2x = 0;
        }
        if (tanks.get(1).getY() - windowSize.height/2 > 0){
            p2y = tanks.get(1).getY() - windowSize.height / 2;
        }
        else{
            p2y = 0;
        }

        if (p2x > mapSize.x - windowSize.width / 2){
            p2x = mapSize.x - windowSize.width / 2;
        }
        if (p2y > mapSize.y - windowSize.height){
            p2x = mapSize.y - windowSize.height;
        }

        leftview = backg.getSubimage(p1x, p1y, windowSize.width/2, windowSize.height);
        rightview = backg.getSubimage(p2x, p2y, windowSize.width/2, windowSize.height);

        graphic.drawImage(leftview, 0, 0, this);
        graphic.drawImage(rightview, windowSize.width / 2, 0, this);
        graphic.drawRect(windowSize.width/2 - 1, 0, 1, windowSize.height);
        graphic.drawImage(minimap, windowSize.width / 2 - 104, 378, 200, 200, this);


    }

    public void render(){
        bg.render(graphic2d);
        keyManager.setup(tanks, objects, temporaryWalls);
        ArrayList<Bullet> b;

        for (int i = 0; i < objects.size(); i++){
            objects.get(i).render(graphic2d, this);
        }
        for (int i = 0; i < temporaryWalls.size(); i++){
            temporaryWalls.get(i).render(graphic2d, this);
        }

        for (int i = 0; i < tanks.size(); i++){
            b = tanks.get(i).getBullets();
            for (Bullet bull : b){
                if (bull.isVisible()){
                    bull.draw(graphic2d, this);
                    bull.update();
                }
            }
            tanks.get(i).render(graphic2d, this);
        }
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

    public void start(){
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
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

    public void endRunning(){
        this.setRunning(false);
        removeKeyListener(key);
        game.init();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Tank Game");
        TankGame game = TankGame.getGame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });
        frame.getContentPane().add("Center", game);
        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        game.setDimensions(800, 600);
        game.init();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.start();
    }
}
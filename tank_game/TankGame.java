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

    private BackgroundImage background;
    private Thread thread;
    private Graphics2D graphic2d;
    private final int width = 800;
    private final int height = 600;
    private BufferedImage bg;
//    private BufferedImage leftView;
//    private BufferedImage rightView;
    private BufferedImage miniMap;
    public static HashMap<String, BufferedImage> imageMap;
    protected ArrayList<Tank> tanks;
    //for breakable walls
    protected ArrayList<BreakableWalls> temporaryWalls;
    protected ArrayList<Sprites> objects;
    private static TankGame game = new TankGame();
    private Point mapSize;
    private KeyManager keyManager;
    protected KeyController key;
    private boolean running;
    private Dimension window;
//    private int tank1Lives, tank2Lives;
//    private int tank1Health, tank2Health;

    private TankGame(){
        setFocusable(true);
        this.tanks = new ArrayList<>();
        this.objects  = new ArrayList<>();
        this.temporaryWalls = new ArrayList<>();
        this.setSize(this.width, this.height);
        this.graphic2d = null;
        this.running = false;
        this.window = this.getSize();
        this.bg = null;
        this.miniMap = null;
    }

    private void init(){
        imageMap = new HashMap<>();
        this.loadImages();
        Map map = new Map("map.txt");
        map.loadMap();
        mapSize = new Point(map.getWidth() * 32, map.getHeight() * 32);
        background = new BackgroundImage(mapSize.x, mapSize.y, imageMap.get("Background"));
        keyManager = new KeyManager();
        key = new KeyController(keyManager, this.tanks);
        this.addKeyListener(key);
    }

//    private void setMap(){
//        imageMap.put("Background", loadImages("tank_game/resources/Background.bmp"));
//        imageMap.put("Wall1", loadImages("tank_game/resources/Wall1.gif"));
//        imageMap.put("Wall2", loadImages("tank_game/resources/Wall2.gif"));
//        imageMap.put("Tank1", loadImages("tank_game/resources/Tank1.gif"));
//        imageMap.put("Tank2", loadImages("tank_game/resources/Tank2.gif"));
//        imageMap.put("powerUp", loadImages("tank_game/resources/Pickup.gif"));
//        imageMap.put("bullet", loadImages("tank_game/resources/Rocket.gif"));
//        imageMap.put("explosion", loadImages("tank_game/resources/explosion.png"));
//    }

    public void loadImages(){
        imageMap.put("Background", loadImages("tank_game/resources/Background.bmp"));
        imageMap.put("Wall1", loadImages("tank_game/resources/Wall1.gif"));
        imageMap.put("Wall2", loadImages("tank_game/resources/Wall2.gif"));
        imageMap.put("Tank1", loadImages("tank_game/resources/TankImage.png"));
        imageMap.put("Tank2", loadImages("tank_game/resources/TankImage.png"));
        imageMap.put("powerUp", loadImages("tank_game/resources/Pickup.png"));
        imageMap.put("bullet", loadImages("tank_game/resources/bullet.png"));
        imageMap.put("explosion", loadImages("tank_game/resources/explosion.png"));
    }

    public HashMap<String, BufferedImage> returnImages(){
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

    public void addBreakableWalls(BufferedImage image, int x, int y){
        this.temporaryWalls.add(new BreakableWalls(image, x, y));
    }

    public void addSprites(BufferedImage image, int x, int y, int object, int total, String type){
        this.objects.add(new Sprites(image, x, y, object, total, type));
    }

    public void addTank1(BufferedImage image, int x, int y){
        this.tanks.add(new Tank(image, x, y, 0, 60));
    }

    public void addTank2(BufferedImage image, int x, int y){
        this.tanks.add(new Tank(image, x, y, 30, 60));
    }

    public void paint(Graphics graphic){
        this.bg = (BufferedImage) createImage(mapSize.x, mapSize.y);
        this.graphic2d = this.bg.createGraphics();
        this.miniMap = this.bg;
        this.render();

        int p1x = this.tanks.get(0).getX() - this.window.width / 4 > 0 ? tanks.get(0).getX() - this.window.width / 4 : 0;
        int p1y = this.tanks.get(0).getY() - this.window.height / 2 > 0 ? tanks.get(0).getY() - this.window.height / 2 : 0;
        int p2x = this.tanks.get(1).getX() - this.window.width / 4 > 0 ? tanks.get(1).getX() - this.window.width / 4 : 0;
        int p2y = this.tanks.get(1).getY() - this.window.height / 2 > 0 ? tanks.get(1).getY() - this.window.height / 2 : 0;

        if (p1x > mapSize.x - this.window.width / 2){
            p1x = mapSize.x - this.window.width / 2;
        }
        if (p1y > mapSize.y - this.window.height){
            p1y = mapSize.y - this.window.height;
        }
        if (p2x > mapSize.x - this.window.width / 2){
            p2x = mapSize.x - this.window.width / 2;
        }
        if (p2y > mapSize.y - this.window.height){
            p2y = mapSize.y - this.window.height;
        }

        BufferedImage leftView = this.bg.getSubimage(p1x, p1y, this.window.width / 2, this.window.height);
        BufferedImage rightView = this.bg.getSubimage(p2x, p2y, this.window.width / 2, this.window.height);

        graphic.drawImage(leftView, 0, 0, this);
        graphic.drawImage(rightView, this.window.width / 2, 0, this);
        graphic.drawRect(this.window.width / 2 - 1, 0, 1, this.window.height);
        graphic.drawImage(this.miniMap, this.window.width / 2 - 104, 378, 200, 200, this);

        if (this.tanks.get(0).getLives() < 0){
            graphic.setColor(Color.blue);
            game.stopRunning();
            graphic.drawString("TANK 2 WINS!", p1x + 30, p1y - 70);
        }
        else if (this.tanks.get(1).getLives() < 0){
            graphic.setColor(Color.blue);
            game.stopRunning();
            graphic.drawString("TANK 1 WINS!", p1x + 30, p1y - 70);
        }

        graphic.setColor(Color.WHITE);
        graphic.drawString("HEALTH: " + this.tanks.get(0).getHealth(), 32, getHeight() - 60);
        graphic.drawString("HEALTH: " + this.tanks.get(1).getHealth(), getWidth() - 150, getHeight() - 60);
        graphic.drawString("LIVES: " + this.tanks.get(0).getLives(), 32, getHeight() - 120);
        graphic.drawString("LIVES: " + this.tanks.get(1).getLives(), getWidth() - 150, getHeight() - 120);
        graphic.dispose();
    }

    public void render(){
        background.render(graphic2d);
        keyManager.setup(tanks, objects, temporaryWalls);

        //walls
        for (Sprites object : this.objects){
            object.render(this.graphic2d, this);
        }
        for (BreakableWalls temporaryWall : this.temporaryWalls){
            temporaryWall.render(this.graphic2d, this);
        }

        //tanks and bullets
        for (Tank tank : this.tanks){
            for (Bullet b : tank.getBullets()){
                if (b.isVisible()){
                    b.draw(this.graphic2d, this);
                    b.update();
                }
            }
            if (tank.isShown()){
                tank.render(this.graphic2d, this);
            }
        }
        //explosions
        for (Tank tank : this.tanks){
            for (int j = 0; j < tank.explosions.size(); j++){
                Explosions explode = tank.explosions.get(j);
                if (!explode.isFinal()){
                    explode.render();
                }
                else{
                    tank.explosions.remove(explode);
                }
            }
        }
    }

    public void run(){
        Thread current = Thread.currentThread();
        while (this.thread == current){
            this.repaint();
            try{
                Thread.sleep(30);
            } catch(InterruptedException e){break;}
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
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

    public void stopRunning(){
        this.setRunning(false);
        removeKeyListener(key);
    }

    public Graphics2D getGraphics2D(){
        return this.graphic2d;
    }

    public static void main(String[] args){
        final TankGame game = TankGame.getGame();
        JFrame frame = new JFrame("Tank Game");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });
        frame.getContentPane().add("Center", game);
        frame.pack();
        frame.setSize(game.width, game.height);
        frame.setLocationRelativeTo(null);
        game.init();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.start();
    }
}
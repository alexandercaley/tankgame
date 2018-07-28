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
    private BufferedImage leftView;
    private BufferedImage rightView;
    private BufferedImage miniMap;
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
    private Dimension window;

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
    }

    private void setMap(){
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

    public void addBreakableWalls(BufferedImage image, int x, int y){
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
        //NEEDS TO BE DONE!!!!!!!
















    }

    public void render(){
        background.render(graphic2d);
        keyManager.setup(tanks, objects, temporaryWalls);

        //walls
        for (ImageLoader object : this.objects){
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
        game.init();
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
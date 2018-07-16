package tank_game;

import java.awt.*;
import java.util.*;

public class tankGame extends Canvas implements Runnable{

    public static String TITLE = "TANK GAME";
    public static int WIDTH = 1280;
    public static int HEIGHT = 900;

    public tankGame(){
        new windowFrame(TITLE, WIDTH, HEIGHT, this);
    }

    public synchronized void start(){

    }

    public void run(){

    }
    public static void main(String[] args){
        new tankGame();
    }
}
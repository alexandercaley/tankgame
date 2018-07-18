package tank_game;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class tankGame extends JPanel implements Runnable{

    public static String title = "Tank Wars";
    public static int width = 1280;
    public static int height = 900;

    public void run(){

    }

    public tankGame(){

    }

    public static void main(String[] args){
        Graphics g2;
        BackgroundImage bg = new BackgroundImage(width, height);
        bg.initialize("/Users/alexandercaley/Documents/School Work/CSC413/csc413-tankgame-zanner11030/tank_game/resources/Background.bmp");
        bg.drawTiles(g2);
    }
}
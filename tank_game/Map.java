package tank_game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Map {

    private int width;
    private int height;
    private String file;
    BufferedReader layer;

    public Map(String filepath){
        String newLine;
        try{
            file = filepath;
            layer = new BufferedReader(new InputStreamReader(getClass().getResource(file).openStream()));
            newLine = layer.readLine();
            width = newLine.length();
            //Each layer will take one unit
            height = 0;
            //get all layers
            while(newLine != null){
                height++;
                newLine = layer.readLine();
            }
            layer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        TankGame game = TankGame.getGame();
        String newLine;
        try{
            layer = new BufferedReader(new InputStreamReader(TankGame.class.getResource(file).openStream()));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

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
            newLine = layer.readLine();
            width = newLine.length();
            height = 0;
            while(newLine != null){
                for (int i = 0, n = newLine.length(); i < n; i++){
                    char c = newLine.charAt(i);

                    if (c == '1'){
                        game.addObjects(game.loadImages().get("Wall1"), i * 32, height * 32, 0, 1, "wall");
                    }
                    if (c == '2'){
                        game.addTempItems(game.loadImages().get("Wall2"), i * 32, height * 32);
                    }
                    if (c == '3'){
                        game.addTank1(game.loadImages().get("Tank1"), i * 32, height * 32);
                    }
                    if (c == '4'){
                        game.addTank2(game.loadImages().get("Tank2"), i * 32, height * 32);
                    }

                    if (c == '5'){
                        game.addObjects(game.loadImages().get("power"), i * 32, height, 0, 1, "power");
                    }
                }
                height++;
                newLine = layer.readLine();
            }
            layer.close();
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

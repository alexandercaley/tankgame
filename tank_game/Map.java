package tank_game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Map {

    private int width;
    private int height;
    private String file;
    BufferedReader layer;

    public Map(String file){
        try{
            this.file = file;
            this.layer = new BufferedReader(
                    new InputStreamReader(
                            getClass().getResource(this.file).openStream()
                    )
            );
            this.width = this.layer.readLine().length();
            this.height = 0;
            while(this.layer.readLine() != null){
                this.height++;
            }
            this.layer.close();
        }   catch(IOException e){
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
                        game.addSprites(game.returnImages().get("Wall1"), i * 32, this.height * 32, 0, 1, "wall");
                    }
                    if (c == '2'){
                        game.addBreakableWalls(game.returnImages().get("Wall2"), i * 32, this.height * 32);
                    }
                    if (c == '3'){
                        game.addTank1(game.returnImages().get("Tank1"), i * 32, this.height * 32);
                    }
                    if (c == '4'){
                        game.addTank2(game.returnImages().get("Tank2"), i * 32, this.height * 32);
                    }

                    if (c == '5'){
                        game.addSprites(game.returnImages().get("powerUp"), i * 32, this.height * 32, 0, 1, "powerUp");
                    }
                }
                this.height++;
                newLine = layer.readLine();
            }
            this.layer.close();
        } catch(IOException e){
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

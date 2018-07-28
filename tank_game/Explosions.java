package tank_game;

import java.awt.image.BufferedImage;

public class Explosions extends ImageLoader{
    private int end;
    private int current;

    public Explosions(BufferedImage image, int x, int y, int item, int totalItems){
        super(image, x, y, item, totalItems, "explosion");
        this.end = 3;
        this.current = 0;
    }

    public void render(){
        render(game.getGraphics2D(), game);
        this.current++;
        if (this.current > this.end){
            this.current = 0;
            item++;
        }
    }

    public boolean isFinal(){
        return (item >= totalItems);
    }
}

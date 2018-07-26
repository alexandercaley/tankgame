package tank_game;

import java.awt.image.BufferedImage;

public class UnbreakableWalls extends Walls{

    public UnbreakableWalls(BufferedImage image, int x, int y){
        super(image, x, y);
        setTangible(true);
        setVisible(true);
    }
}

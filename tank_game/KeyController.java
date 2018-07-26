package tank_game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyController extends KeyAdapter {

    public void keyPressed(ArrayList<Tank> tanks, KeyManager keyManager, KeyEvent key){
        keyManager.pressed(key, tanks);
    }

    public void keyReleased(ArrayList<Tank> tanks, KeyManager keyManager, KeyEvent key){
        keyManager.released(key, tanks);
    }
}

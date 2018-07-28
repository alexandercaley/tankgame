package tank_game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyController extends KeyAdapter {

    private KeyManager manager;
    private ArrayList<Tank> tanks;

    KeyController(KeyManager manager, ArrayList<Tank> tanks){
        this.manager = manager;
        this.tanks = tanks;
    }

    public void keyPressed(KeyEvent event){
        this.manager.pressed(event, this.tanks);
    }

    public void keyReleased(KeyEvent event){
        this.manager.released(event, this.tanks);
    }
}
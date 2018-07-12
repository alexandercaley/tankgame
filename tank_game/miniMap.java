import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class miniMap extends Canvas {

    public miniMap(){
    }

    public void draw(){
        JFrame frame = new JFrame();
        Canvas canvas = new miniMap();
        canvas.setSize(1000, 1000);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
}
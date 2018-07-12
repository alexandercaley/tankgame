import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class windowFrame extends Canvas {

    public windowFrame(){}

    public void draw(){
        JFrame frame = new JFrame();
        Canvas canvas = new windowFrame();
        canvas.setSize(1000, 1000);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
}
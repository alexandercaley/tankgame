import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class gameFrame extends Canvas {
  public static void main(String[] args) {
        JFrame frame = new JFrame();
        Canvas canvas = new gameFrame();
        canvas.setSize(1000, 1000);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
}

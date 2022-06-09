import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by straigchri on 3/08/17.
 */
public class LanderFrame extends JFrame {

    public LanderFrame() {
        super("Moon Lander");
        LanderCanvas canvas = new LanderCanvas();        // create canvas
        setLayout(new BorderLayout());      // use border layout
        add(canvas, BorderLayout.CENTER);   // add canvas
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();                             // pack components tightly together
        setResizable(false);                // prevent us from being resizeable
        setVisible(true);                   // make sure we are visible!
        new Timer(1, (e)->{
            canvas.landery+=canvas.yVelocity;
            canvas.landerx+=canvas.xVelocity;
            canvas.yVelocity+=0.0001;

            int boxx = canvas.lander.getBounds().x+(int)canvas.landerx;
            int boxy = canvas.lander.getBounds().y+(int)canvas.landery;

            for(int x=boxx; x<canvas.lander.getBounds().width+boxx; x++) {
                for(int y=boxy; y<canvas.lander.getBounds().height+boxy; y++) {
                    if (canvas.ground.contains(x,y)) {
                        if(x>=230 && x+canvas.lander.getBounds().width-10<300){
                            int r = JOptionPane.showConfirmDialog(this, new JLabel("you win"),
                                    "Warning!", JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                        }else {
                            int r = JOptionPane.showConfirmDialog(this, new JLabel("game over"),
                                    "Warning!", JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
            canvas.repaint();
        }).start();

        KeyListener arowKeys = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
                    if(canvas.fuel>0) {
                        canvas.xVelocity += canvas.thrust;
                        canvas.fuel -= 10;
                        canvas.burning = true;
                    }
                } else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
                    if(canvas.fuel>0) {
                        canvas.xVelocity -= canvas.thrust;
                        canvas.fuel -= 10;
                        canvas.burning = true;
                    }
                } else if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP){
                    if(canvas.fuel>0) {
                        canvas.yVelocity-=canvas.thrust;
                        canvas.fuel-=10;
                        canvas.burning=true;
                    }
                }
            }
            public void keyReleased(KeyEvent e) {
                canvas.burning=false;
            }
            public void keyTyped(KeyEvent e) {}
        };
        addKeyListener(arowKeys);
    }

    public static void main(String[] args) {
        new LanderFrame();
    }
}

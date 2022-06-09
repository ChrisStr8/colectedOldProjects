import javax.swing.*;
import java.awt.*;

/**
 * Created by straigchri on 3/08/17.
 */
public class LanderCanvas extends JPanel{
    double xVelocity = 0;
    double yVelocity = 0;
    double landerx = 50;
    double landery = 50;
    double thrust = 0.01;
    double fuel = 1000;
    boolean burning = false;

    int[] groundXS = {0  ,30 ,40 ,100,140,160,180,200,220,230,300,310,330,350,
            360,400,410,435,460,465,500,545,560,575,580,600,600,0};
    int[] groundYS = {500,450,480,510,350,400,395,480,490,480,480,520,515,520,
            515,550,400,350,360,400,410,480,455,465,480,500,600,600};

    int[] landerXS = {11,13,27,29,30,26,37,40,40,30,30,33,24,
            21,24,16,19, 16, 7, 0, 0,10,10, 3,14,10};
    int[] landerYS = { 5, 0,0, 5, 20,20,35,35,40,40,35,35,20,
            20,25,25,20,20,35,35,40,40,35,35,20,20};
    Polygon ground = new Polygon(groundXS, groundYS, groundXS.length);
    Polygon lander = new Polygon(landerXS, landerYS, landerXS.length);

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(600, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.GRAY);
        g.fillPolygon(ground);

        g.setColor(Color.BLUE);
        g.fillRect(230, 481, 70, 5);

        g.setColor(Color.LIGHT_GRAY);
        g.translate((int)landerx, (int)landery);
        g.fillPolygon(lander);

        g.setColor(Color.WHITE);
        g.drawString("Fuel: "+fuel, 0, 0);
        if(burning){
            g.setColor(Color.orange);
            g.fillOval(12, 24, 16, 16);
        }
    }
}

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment 1  
 * Name:christopher straight
 * Usercode:straigchri
 * ID:300363269
 */

//import java.util.*;
import ecs100.*;
import java.awt.Color;

/**
 * Makes a dirty floor (a grey rectangle), and then makes a robot floor
 *  cleaner run around on the floor to clean it all up.
 * The robot will go forward one unit on each step in its current
 *  direction, and erase the "dirt" (by erasing where it was, moving
 *  one step, and redrawing itself).
 * When it is about to take a step, the program must check that
 *  it won't go over edges of the floor, and change its direction
 *  to a new random direction if it would go over the edge.
 *
 * Hints:
 *   - You can use Math.random() to create a random number (between 0.0 and 1.0)
 *
 *   - If the robot needs to move a distance s in the direction d, then it can
 *     work out the amounts to move in the x and y directions respectively using
 *     basic trigonometry:
 *       s * Math.cos(d * Math.PI/180)   and   s * Math.sin(d * Math.PI/180)
 *     (assuming that d is measured in degrees from 0 to 360)
 *
 *   - Write one method with the main loop, and a separate method for
 *     drawing the floor cleaner. It should show the direction the
 *     floor cleaner is heading in some way. 
 */

public class FloorCleaner{
    // Constants:  The edges of the floor and the robot size
    public static final double Left = 50;
    public static final double Right = 550;
    public static final double Top = 50;
    public static final double Bot = 420;
    public static final double Radius = 30;
    public static final double Diam = 2*Radius;

    /** cleanFloor
     *  The main simulation loop,
     *  Draws the floor area
     *  Initialises the position and direction of the floor cleaning robot and draws it
     *  Loops forever
     *    erase the robot from its current position
     *    work out position of one step in the current direction
     *    check if it is hitting a wall or not
     *    then either move it to the new position, or change its direction
     *    redraw
     *    sleep for a short time.
     */
    public void cleanFloor(){
        double x = (Left+Right)/2;
        double y = (Top+Bot)/2;
        double dir = Math.random()*360;
        UI.setColor(Color.gray);
        UI.fillRect(Left, Top, Right-Left, Bot-Top);
        
        draw(x, y, dir);
        UI.sleep(500);
        while(true){
            UI.eraseOval(x-Radius, y-Radius, Diam , Diam);
            double newx = x + Math.cos(dir*Math.PI/180);
            double newy = y + Math.sin(dir*Math.PI/180);
            if (newx-Radius < Left || newx+Radius > Right || newy-Radius < Top || newy+Radius > Bot){
              dir= Math.random()*360;  
            }
            else{
                x = newx;
                y = newy;
            }
            draw(x, y, dir);
            UI.sleep(20);
        
        }
        
    }

    /**
     * Draws the robot as a red circle at the specified position and direction
     */
    public void draw(double x, double y, double dir) {
        UI.setColor(Color.red);
        UI.fillOval(x-Radius, y-Radius, Diam, Diam);
        UI.setColor(Color.blue);
        UI.drawOval(x-Radius, y-Radius, Diam, Diam);
        double xfront = x+Radius*0.8*Math.cos(dir*Math.PI/180);
        double yfront = y+Radius*0.8*Math.sin(dir*Math.PI/180);
        UI.fillOval(xfront-2, yfront-2, 5, 5);
    }

    // Main
    /** Create a new FloorCleaner object and call cleanFloor.   */
    public static void main(String[] arguments){
        FloorCleaner fc =new FloorCleaner();
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0);
        fc.cleanFloor();
    }        

}

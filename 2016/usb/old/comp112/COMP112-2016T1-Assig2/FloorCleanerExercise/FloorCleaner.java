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

/** Makes a dirty floor (a grey rectangle), and then makes a robot floor
 *  cleaner run around on the floor to clean it all up.
 *  The robot will go forward one unit on each step in its current
 *  direction, and erase the "dirt" (by erasing where it was, moving
 *  one step, and redrawing itself).
 *  When it is about to take a step, the program must check that
 *  it won't go over edges of the floor, and change its direction
 *  to a new random direction if it would go over the edge.
 */
public class FloorCleaner{

    // Constants
    public static final double Left = 50;
    public static final double Right = 550;
    public static final double Top = 50;
    public static final double Bot = 420;

    /**
     * cleanFloor is the main simulation loop.
     * draws floor,
     * makes a new robot, and draws it
     * loops forever:
     *  erase robot, move robot, draw robot, sleeping briefly
     */
    public void cleanFloor(){
        // draw the floor
        UI.setColor(Color.gray);
        UI.fillRect(Left, Top, Right-Left, Bot-Top);

        // Make the robot (storing it in a variable)
        // draw it, (by calling the appropriate method on the robot object
        // then repeatedly make it move by calling erase, then move, then draw.
        // Dont forget the sleep!
        /*# YOUR CODE HERE */
        Robot newRobot1 = new Robot((Left+Right)/2, (Top+Bot)/2);
        Robot newRobot2 = new Robot((Left+Right)/2, (Top+Bot)/2);
        UI.sleep(500);
        while(true){
            newRobot1.erase();
            newRobot2.erase();
            newRobot1.move();
            newRobot2.move();
            newRobot1.draw();
            newRobot2.draw();
            UI.sleep(10);
        }
        
    }

    // Main
    /** Create a new FloorCleaner object and call cleanFloor.   */
    public static void main(String[] arguments){
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize((int)Right+50, (int)Bot+50);
        UI.setDivider(0);      // expands the graphics area
        FloorCleaner fc =new FloorCleaner();
        fc.cleanFloor();
    }        

}

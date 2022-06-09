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

public class Robot {
    public static final double Radius = 30;
    public static final double Diam = Radius * 2;

    // Fields for the robot's state 
    /*# YOUR CODE HERE */
    double x = (FloorCleaner.Left+FloorCleaner.Right)/2;
    double y = (FloorCleaner.Top+FloorCleaner.Bot)/2;
    double dir = Math.random()*360;

    //Constructor
    /**
     * Creates a new robot at the specified position and a random direction.
     * remembers the arguments in the robot's fields.
     **/ 
    public Robot (double x, double y){
        /*# YOUR CODE HERE */
        draw();
    }

    /**
     * Move the robot one unit in the current direction.
     * Work out where it should move to.
     * Check if it would be over the edge, if so, don't move it, but just change
     * its direction to a random new direction.
     * otherwise, move it.
     * You wrote all the code for this last week; you just have to modify it a little.
     * Note that you can access the constants in the FloorCleaner class using the
     * class name and the constant name, eg   FloorCleaner.Right.
     */
    public void move(){
        /*# YOUR CODE HERE */
        double newx = x + Math.cos(dir*Math.PI/180);
        double newy = y + Math.sin(dir*Math.PI/180);
        if (newx-Radius < FloorCleaner.Left || newx+Radius > FloorCleaner.Right || newy-Radius < FloorCleaner.Top || newy+Radius > FloorCleaner.Bot){
            dir= Math.random()*360;  
        }
        else{
            x = newx;
            y = newy;
       }
       
       

    }

    /**
     * Draw the robot as a red circle
     */
    public void draw() {
        /*# YOUR CODE HERE */
        UI.setColor(Color.red);
        UI.fillOval(x-Radius, y-Radius, Diam, Diam);
        UI.setColor(Color.blue);
        UI.drawOval(x-Radius, y-Radius, Diam, Diam);
        double xfront = x+Radius*0.8*Math.cos(dir*Math.PI/180);
        double yfront = y+Radius*0.8*Math.sin(dir*Math.PI/180);
        UI.fillOval(xfront-2, yfront-2, 5, 5);

    }

    /**
     * Erase the robot
     */
    public void erase(){
        /*# YOUR CODE HERE */
        UI.eraseOval(x-Radius, y-Radius, Diam , Diam);

    }

}

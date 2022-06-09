// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/**
 * BallDrop is a simple program that lets you drop balls from the top of the window
 * The constructor sets up two buttons and draws the "ground"
 * There is a method to respond to the buttons, and
 * a method that drops a ball, using a loop that repeatedly
 * erases the ball, moves the position of the ball, and redraws it
 * until it gets to the ground.
 */

public class BallDrop{

    //Constants
    public static final double size = 20;  // the size of the ball that gets dropped.
    public static final double ground = 400;  // the size of the ball that gets dropped.

    //Constructor

    /**
     * Construct a new BallDrop object, setting up the GUI (the two buttons)
     */
    public BallDrop(){
        UI.initialise();
        UI.addButton("Drop", this::doDrop);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0);
        UI.drawLine(20, ground, 600, ground);
    }

    /**
     * Drop a ball from a random position along the top of the graphics pane.
     */
    public void doDrop(){
        double x = 50 + Math.random()*500;
        double y = 0;
        double z = 15;
        UI.setColor(Color.blue);
        while (y < ground-size){
            UI.eraseOval(x, y, size, size);
            y = y + 5;
            UI.fillOval(x, y, size, size);
            UI.sleep(z);
            z = z - 0.2;
            
        }
    }

    /**
     * Main method that is called on the class, rather than on an object
     */
    public static void main(String[] arguments){
        BallDrop obj = new BallDrop();
    }        

}

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/** Exercise
 *    The program lets the user draw circles and move them around.
 *    The program has fields to store
 *     - an array of Circle objects
 *     - whether it is currently drawing new circles or moving them around
 *     - the index of the Circle the mouse was last pressed on.
 *    If the program is drawing new circles, then releasing the mouse
 *     on the graphics pane will create a new Circle at that point and add
 *     it to the collection.
 *    If the program is moving circles around, then if the mouse was pressed on
 *     top of a Circle, it will then be moved to the position the mouse is released.
 */

public class Exercise{
    // Fields

    private ArrayList<Circle> circles = new ArrayList<Circle>();   // the collection of circles

    private String currentAction = "Circle";  // what the program should do next
    private Circle currentCircle = null;         // the Circle the mouse was pressed on
    // null if it wasn't pressed on any Circle

    /** Construct a new Exercise object and set up the GUI
     *    - set the mouselistener
     *    - make two buttons - "Circle" and "Move"
     */
    public Exercise(){
        /*# YOUR CODE HERE */
    }

    public void circle(){
        this.currentAction = "Circle";
    }

    public void move(){
        this.currentAction = "Move";
    }

    /** Respond to mouse events
     *  if "pressed", find and record the Circle it was pressed on (if any)
     *    in the currentCircle field, using the findCircle method
     *  if "released", then depends on whether the program is drawing or moving
     *    if drawing then create a new Circle and add to the list
     *    if moving, and the currentCircle is not null,
     *       then move that Circle to a new place.
     *      (by calling the setPos(..) method on the current circle)
     *  After each "released" action, redraw the collection of shapes.
     */
    public void doMouse(String action, double x, double y) {
        if (action.equals("pressed")){
            this.currentCircle = this.findCircle(x, y);
        }
        if (action.equals("released")){
            /*# YOUR CODE HERE */

            this.redrawCircles();
        }
    }

    /** Search through the circles array for a Circle that the point x,y is on
     *   by calling the on(..) method on each circle 
     * If there is such a Circle, return the Circle object.
     * if there is no such circle, return null.
     */
    public Circle findCircle(double x, double y){
        /*# YOUR CODE HERE */

        return null;
    }

    /** Clear the graphics pane,
     *    redraw all the circles, (calling the redraw method on each circle)
     *    repaint the graphics pane.
     */
    public void redrawCircles(){
        /*# YOUR CODE HERE */

    }

    // Main
    public static void main(String[] arguments){
        new Exercise();
    }   

}

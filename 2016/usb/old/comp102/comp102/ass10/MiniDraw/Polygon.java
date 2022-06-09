// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

/** Polygon represents a polygon made of a sequence of straight lines.
 *  Implements the Shape interface.
 *  Has a field to record the colour of the line and two fields to store
 *  lists of the x coordinates and the y coordinates of all the vertices
 */

public class Polygon implements Shape{
    /*# YOUR CODE HERE */
    public double x1;
    public double y1;
    public double width;
    public double height;
    public Color col;

    /** Constructor with explicit values
     *  Arguments are the x and y of the top left corner,
     *  the width and height, and the color. 
     */
    public Polygon(double x, double y, Color col) {
        /*# YOUR CODE HERE */

    }
    
    /** [Completion] Constructor which reads values from a String
     *  that contains the specification of the Rectangle. 
     *  The format of the String is determined by the toString method.
     *     The first 3 integers specify the color;
     *     the following four numbers specify the position and the size.
     */
    public Polygon(String description) {
        /*# YOUR CODE HERE */
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
    }

    /** Returns true if the point (u, v) is on top of the shape */
    public boolean on(double u, double v) {
        /*# YOUR CODE HERE */
        return false;
    }

    /** Changes the position of the shape by dx and dy.
     *  If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy) {
        /*# YOUR CODE HERE */
    }

    /** Draws the rectangle on the graphics pane. 
     *  It draws a black border and fills it with the color of the rectangle.
     */
    public void redraw() {
        /*# YOUR CODE HERE */
    }

    /** [Completion] Changes the width and height of the shape by the
     *  specified amounts.
     *  The amounts may be negative, which means that the shape
     *  should get smaller, at least in that direction.
     *  The shape should never become smaller than 1 pixel in width or height
     *  The center of the shape should remain the same.
     */
    public void resize (double changeWd, double changeHt) {
        /*# YOUR CODE HERE */
    }

    /** Returns a string description of the rectangle in a form suitable for
     *  writing to a file in order to reconstruct the rectangle later
     *  The first word of the string must be Rectangle 
     */
    public String toString() {
        /*# YOUR CODE HERE */
        return ("Polygon "+col.getRed()+" "+col.getGreen()+" "+col.getBlue());
    }
}

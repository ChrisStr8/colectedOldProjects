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

/** Rectangle represents a solid rectangle shape
 *   Implements the Shape interface.
 *   Needs fields to record the position, size, and colour
 */

public class Rectangle implements Shape {
    //fields
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
    public Rectangle(double x, double y, double wd, double ht, Color col) {
        /*# YOUR CODE HERE */
        this.x1 = x;
        this.y1 = y;
        this.width = wd;
        this.height = ht;
        this.col = col;

    }

    /** [Completion] Constructor which reads values from a String
     *  that contains the specification of the Rectangle. 
     *  The format of the String is determined by the toString method.
     *     The first 3 integers specify the color;
     *     the following four numbers specify the position and the size.
     */
    public Rectangle(String description) {
        /*# YOUR CODE HERE */
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x1 = data.nextDouble();
        this.y1 = data.nextDouble();
        this.width = data.nextDouble();
        this.height = data.nextDouble();

    }

    /** Returns true if the point (u, v) is on top of the shape */
    public boolean on(double u, double v) {
        /*# YOUR CODE HERE */
        double right = x1 + width;
        double bot = y1 + height;
        if(u < right && u > x1 && v < bot && v > y1){
            return true;
        }
        else {
            return false;
        }
    }

    /** Changes the position of the shape by dx and dy.
     *  If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy) {
        /*# YOUR CODE HERE */
        x1 += dx;
        y1 += dy;
    }

    /** Draws the rectangle on the graphics pane. 
     *  It draws a black border and fills it with the color of the rectangle.
     */
    public void redraw() {
        /*# YOUR CODE HERE */
        UI.setColor(Color.black);
        UI.fillRect(x1, y1, width, height);
        UI.setColor(col);
        UI.fillRect(x1+1, y1+1, width-2, height-2);
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
        double newWidth = 0;
        double newHeight = 0;
        double rx = 0;
        double ry = 0;
        if (x1 < changeWd){
            newWidth = changeWd - x1;
            rx = x1;
        }else if(x1 > changeWd){
            newWidth = x1 - changeWd;
            rx = changeWd;
        }
        if (y1 < changeHt){
            newHeight = changeHt - y1;
            ry = y1;
        }else if(y1 > changeHt){
            newHeight = y1 - changeHt;
            ry = changeHt;
        }
        this.x1 = rx;
        this.y1 = ry;
         if(newWidth<2){
            newWidth = 2;
        }
        if(newHeight<2){
            newHeight = 2;
        }
        this.width = newWidth;
        this.height = newHeight;
    }

    /** Returns a string description of the rectangle in a form suitable for
     *  writing to a file in order to reconstruct the rectangle later
     *  The first word of the string must be Rectangle 
     */
    public String toString() {
        /*# YOUR CODE HERE */
        return ("Rectangle "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x1+" "+this.y1+" "+this.width+" "+this.height);
    }

}

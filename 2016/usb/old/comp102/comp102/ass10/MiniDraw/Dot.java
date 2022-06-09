// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

/** Dot represents a small circle shape of a fixed size (5 pixels)
 *   Implements the Shape interface.
 *   Needs fields to record the position, and colour
 */

public class Dot implements Shape  {
    /*# YOUR CODE HERE */
    public static int SIZE = 5;
    public double x1;
    public double y1;
    public Color col;
    public Dot (double x, double y, Color col){
        /*# YOUR CODE HERE */
        this.x1 = x;
        this.y1 = y;
        this.col = col;
    }
    
    public Dot(String description) {
        /*# YOUR CODE HERE */
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x1 = data.nextDouble();
        this.y1 = data.nextDouble();
    }
    
    public boolean on(double u, double v){
        // an easy approximation is to pretend it is the enclosing rectangle.
        // It is nicer to do a little bit of geometry and get it right
        /*# YOUR CODE HERE */
        double right = x1 + SIZE;
        double bot = y1 + SIZE;
        if(u < right && u > x1 && v < bot && v > y1){
            return true;
        }
        else {
            return false;
        }
    }
    
    public void moveBy(double dx, double dy){
        /*# YOUR CODE HERE */
        x1 += dx;
        y1 += dy;
    }
    
    public void redraw(){
        /*# YOUR CODE HERE */
        UI.setColor(col);
        UI.fillOval(x1-(0.5*SIZE), y1-(0.5*SIZE), SIZE, SIZE);
    }
    
    public void resize(double changeWd, double changeHt){
        /*# YOUR CODE HERE */
        UI.println("you can not resize a dot");
    }
    
    public String toString(){
        /*# YOUR CODE HERE */
        return ("Dot "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x1+" "+this.y1);
    }
}


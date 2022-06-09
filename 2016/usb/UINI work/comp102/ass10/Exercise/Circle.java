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

/** Circle   */
public class Circle{
    private double xPos;               // position of the center of the circle.
    private double yPos;
    private Color col = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
    private static double radius = 30;  

    /** Construct a new Circle object */
    public Circle(double x, double y){
        this.setPos(x, y);
    }

    /* move the circle to the specified position */
    public void setPos(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    /* returns true if the point x,y is on the circle */
    public boolean on(double x, double y){
        return (Math.hypot(x-this.xPos, y-this.yPos) < this.radius);
    }

    /* Draw the circle at its current position */
    public void redraw(){
        UI.setColor(this.col);
        UI.fillOval(this.xPos-this.radius, this.yPos-this.radius, 2*this.radius, 2*this.radius);
    }

}

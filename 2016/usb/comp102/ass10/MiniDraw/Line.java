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

/** Line represents a straight line.
 *   Implements the Shape interface.
 *   Needs fields to record the two ends of the line and the colour of the line.
 *   May have additional fields if you want.
 */

public class Line implements Shape{
    //fields
    private double x1;  // one end
    private double y1;
    private double x2;  // the other end
    private double y2;
    private Color col;  // the colour of the line

    /** Constructor with explicit values
     *  Arguments are the position (x1,y1) of one end and the position (x2,y2) of the other end,
     *  and the color. 
     */
    public Line (double x1, double y1, double x2, double y2, Color col){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.col = col;
    }

    /** Constructor which reads values from a String
     *  that contains the specification of the Line.
     *  The format of the String is determined by the toString method.
     *    The first 3 integers specify the color;
     *    the following four numbers specify the positions of the two ends.
     */
    public Line (String description){
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x1 = data.nextDouble();
        this.y1 = data.nextDouble();
        this.x2 = data.nextDouble();
        this.y2 = data.nextDouble();
    }

    /** Returns true if the point (u, v) is on top of the shape
     *  The geometry of this is more tricky than for rectangles
     *  or dots!
     */
    public boolean on(double u, double v){
        double threshold = 3;
        // first check if it is past the ends of the line...
        if (u < Math.min(this.x1,this.x2)-threshold ||     
        u > Math.max(this.x1,this.x2)+threshold ||
        v < Math.min(this.y1,this.y2)-threshold ||
        v > Math.max(this.y1,this.y2)+threshold) {
            return false;
        }
        // then check the distance from the point to the line
        double wd = this.x2-this.x1;
        double ht = this.y2-this.y1;
        return (Math.abs(((v-this.y1)*wd - (u-this.x1)*ht)/Math.hypot(wd, ht)) <= threshold);
        // distance of a point from a line, from linear algebra
    }

    /** Changes the position of the shape by dx and dy.
     *  If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy){
        this.x1 += dx;
        this.y1 += dy;
        this.x2 += dx;
        this.y2 += dy;
    }

    /** Draws the line on the graphics pane.
     */
    public void redraw(){
        UI.setColor(this.col);
        UI.drawLine(this.x1, this.y1, this.x2, this.y2);
    }

    /** Changes the width and height of the shape by the
     *  specified amounts.
     *  The amounts may be negative, which means that the shape
     *  should get smaller, at least in that direction.
     *  The shape should never become smaller than 1 pixel in width or height
     *  The center of the shape should remain the same.
     */
    public void resize(double changeWd, double changeHt){
        if (this.x1 < this.x2){
            this.x1 = this.x1 - changeWd/2;
            this.x2 = this.x2 + changeWd/2;
        }
        else{
            this.x1 = this.x1 + changeWd/2;
            this.x2 = this.x2 - changeWd/2;
        }
        if (this.y1 < this.y2){
            this.y1 = this.y1 - changeHt/2;
            this.y2 = this.y2 + changeHt/2;
        }
        else{
            this.y1 = this.y1 + changeHt/2;
            this.y2 = this.y2 - changeHt/2;
        }
    }

    /** Returns a string description of the line in a form suitable for
     *  writing to a file in order to reconstruct the line later
     *  The first word of the string must be Line 
     */
    public String toString(){
        return ("Line "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x1+" "+this.y1+" "+this.x2+" "+this.y2);
    }

}

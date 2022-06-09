// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;

/** The interface for all Shape objects
Provides five methods 
 */

public interface Shape{

    /** Returns true if the point (x, y) is on top of the shape */
    public boolean on(double x, double y);

    /** Changes the position of the shape by dx and dy.
     *  If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy);

    /** Draws the shape on the graphics pane. */
    public void redraw();

    /** Changes the width and height of the shape by the
     *  specified amounts.
     *  The amounts may be negative, which means that the shape
     *  should get smaller, at least in that direction.
     *  The shape should never become smaller than 1 pixel in width or height
     *  The center of the shape should remain the same.
     */
    public void resize(double changeWd, double changeHt);


    /** Returns a string description of the shape in a form suitable for
     *  writing to a file in order to reconstruct the shape later
     */
    public String toString();

}

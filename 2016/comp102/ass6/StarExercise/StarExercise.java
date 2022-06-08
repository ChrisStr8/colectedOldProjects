// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 6
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

/** Lets the user draw "stars" - lots of lines from a center point.
 * After clicking the "Set Center" button, the user can set (or reset)
 *  the center of a new star using the mouse.
 *  (Note, nothing happens directly in response to the button -
 *   the program just remembers that the user is about to change the center point.)
 * After clicking the "Set Tips" button, the user can click anywhere to
 *  add a new tip to the star:
 *  the program will draw a line from the current center to the new point.
 *  (Note, nothing happens directly in response to the button - the program just
 *   remembers that the user is about to set the positions of tips of the star.)
 * Note that neither of the buttons will draw anything directly - they
 *  just record whether user is about to choose the center or a tip.
 * The user can click the "Clear" button to clear the graphics pane or
 *  click the "Set Color" button to change the color
 *  (using the JColorChooser.showDialog(...) method).
 */
public class StarExercise{

    //three fields to record current state:
    //  position of current center, and
    //  whether the user is currently choosing the center or a point
    /*# YOUR CODE HERE */

    /** Set up the user interface: set mouse listener and buttons.
     *  "Set Color" and "Clear" cause an immediate action (changing the color of the UI or
     *  clearing the pane).
     *  "Set Center" and "Set Tips" do not draw anything. They only record what state
     *  the user is now in - choosing a center point or not.
     */
    public StarExercise(){
        /*# YOUR CODE HERE */

    }


    /** Respond to mouse released events.
    If currently choosing a center, then remember the position and draw a small
    circle at the point.
    Othewise, draw a line from the current center to this point
     */
    public void doMouse(String action, double x, double y){
        if (action.equals("released")){
            /*# YOUR CODE HERE */
        }
    }

    public static void main(String[] args){
        new StarExercise();
    }        

}

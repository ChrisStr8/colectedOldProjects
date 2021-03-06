// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 5
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/** ImageExercise
 *  Draw Images made of constructed colours.
 */

// Hint:         The Color class has a constructor with three parameters that are
// the red, green, and blue components of the Color, eg new Color(20, 200, 100);
// The arguments must be integers between 0 and 255.
// You can make random integers between 0 and 255 using
// int red = (int)(Math.random()*256);

public class ImageExercise{
    /** Draw an image made of squares of random colours
     *  left and top, are the position of the top-left corner
     *  rows and cols specify the number of rows and colums of squares
     *  size specifies the size of the individual squares.
     */
    public void drawRandomImage(int rows, int cols){
        double left=20;
        double top=20;
        double size = 10;

        /*# YOUR CODE HERE */

    }

    /** Draw an image that is a smooth gradient from red at the
     *  top to green at the bottom.
     */
    public void drawGradient(int rows, int cols){
        UI.clearGraphics();
        double left=20;
        double top=20;
        double size = 2;

        /*# YOUR CODE HERE */

    }

    /** ---------- The code below is already written for you ---------- **/

    /** Constructor: set up user interface */
    public ImageExercise(){
        UI.addButton("Clear", UI::clearGraphics);
        UI.addButton("Random Image", this::doDrawRandom);
        UI.addButton("Gradient Image", this::doDrawGradient);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0);
    }        

    public void doDrawRandom() {
        this.drawRandomImage(20, 20);
    }

    public void doDrawGradient() {
        this.drawGradient(200, 200);
    }


}

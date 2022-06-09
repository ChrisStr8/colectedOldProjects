// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Exercise for COMP112 Assignment 2
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;

/**
 * The GUIExercise program is a very simple program that lets the user place 
 *  images on the graphics pane using the mouse.
 * It has a Clear button, and a text field to enter the name of the image
 * The Clear button should clear the graphics pane.
 * If the user enters a name into the text field, it should store the value
 *  in a field.
 * When the user releases the mouse at any point on the canvas, the program
 *  should draw the image that the user specified at that point (note, if
 *  there is no image file of the given name, it may not draw anything).
 */ 

public class GUIExercise{

    //field to record the name of the image.
    //initial value should be "bubble.jpg"
    /*# YOUR CODE HERE */
    String name = "";
    public void addInput(){
        name = UIFileChooser.open();
        
        setImageName(name);
    }
    // Constructor
    /** Construct a new GUIExercise object and set up the GUI:
     *  - set the mouse listener
     *  - add a button
     *  - add a text field
     */
    public GUIExercise(){
        /*# YOUR CODE HERE */
        UI.initialise();
        UI.setMouseListener(this::doMouse);
        UI.addButton("Input File", this::addInput);
        UI.addButton("Quit", UI::quit);
    }

    /** 
     * Respond to the mouse:
     * This program only does something on a released action - it draws 
     * the current image at the position the mouse was released
     */
    public void doMouse(String action, double x, double y) {
        /*# YOUR CODE HERE */
        UI.drawImage(name, x, y);
        
    }

    /** 
     * Stores the name of the image in the field.
     */
    public void setImageName(String value) {
        /*# YOUR CODE HERE */
        UI.print(value);
        
    }

    // Main: call the constructor, which will set up the interface,
    // then prints some user instructions
    public static void main(String[] arguments){
        GUIExercise  ge = new GUIExercise();
        UI.println("Enter an image name in the text field");
        UI.println("eg, camel.jpg, london.jpg, bubble.jpg, eggs.jpg, or flower.jpg");
        UI.println("Click the mouse in the graphics pane to paste the image");
    }        

}

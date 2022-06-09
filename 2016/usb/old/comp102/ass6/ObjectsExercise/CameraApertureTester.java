// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 6
 * Name: pondy
 */

import ecs100.*;
import java.awt.Color;

/**
 *  This program contains methods for testing CameraAperture objects.
 *  It is all written for you, but you need to read it to see what the
 *  CameraAperture class should do
 */

public class CameraApertureTester{

    /** 
     * Makes two CameraAperture objects and at random chooses a camera
     *   and makes it open or close
     */
    public void testCameraAperture(){
        UI.clearPanes();
        UI.println("Should make two cameras:");
        UI.println("one opens, then closes, then opens again");
        UI.println("one closes, then opens, then closes again");
        UI.setColor(Color.black);
        CameraAperture cam1 = new CameraAperture(150, 300); 
        CameraAperture cam2 = new CameraAperture(400, 200);
        int steps = 0;
        while (steps < 10){
            cam1.open();
            cam2.close();
            UI.sleep(200);
            steps++;   //(shorthand for steps = steps + 1;
        }
        while (steps >0){
            cam1.close();
            cam2.open();
            UI.sleep(200);
            steps--;   //(shorthand for steps = steps - 1;
        }
        while (steps < 10){
            cam1.open();
            cam2.close();
            UI.sleep(200);
            steps++;   //(shorthand for steps = steps + 1;
        }

    }

    /** Constructor to set up the user interface */
    public CameraApertureTester(){
        UI.addButton("Test Camera", this::testCameraAperture);
        UI.addButton("Quit", UI::quit);
    }

    /** Calls the constructor */
    public static void main(String[] args){
        CameraApertureTester obj = new CameraApertureTester();
    }
}

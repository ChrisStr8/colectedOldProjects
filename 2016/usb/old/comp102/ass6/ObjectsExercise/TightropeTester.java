// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 6
 * Name: pondy
 */

import ecs100.*;
import java.awt.Color;

/**
 *  This program contains methods for testing Tightrope objects.
 *  It is all written for you, but you need to read it to see what the
 *  Tightrope class should do
 */

public class TightropeTester{

    /** Makes two Tightrope objects and raises the tightropes */
    public void testTightrope(){
        UI.clearPanes();
        UI.println("Should draw  some ground");
        UI.println("then two tightropes (narrow and wide)");
        UI.println("which go up and down randomly ");
        UI.setColor(new Color(0,100,0)); 
        UI.fillRect(0,400,600,10);  // the ground
        Tightrope training = new Tightrope(110, 100);  // horizontal center and width
        Tightrope perform = new Tightrope(400, 220);   // horizontal center and width
        int steps = 0;
        while (steps < 40){
            // lift a random end of the training tightrope by a random amount
            double amount = Math.random()*10-3;
            if (Math.random() <0.5){
                training.raiseLeft(amount);
            }
            else{
                training.raiseRight(amount);
            }
            // lift a random end of the perform tightrope by a random amount
            amount = Math.random()*15-5;
            if (Math.random() <0.5){
                perform.raiseLeft(amount);
            }
            else{
                perform.raiseRight(amount);
            }

            //pause
            UI.sleep(60);
            steps++;   //(shorthand for steps = steps + 1;
        }
    }

    /** Constructor to set up the user interface */
    public TightropeTester(){
        UI.setWindowSize(900,500);
        UI.addButton("Test Tightrop", this::testTightrope);
        UI.addButton("Quit", UI::quit);
    }

    /** Calls the constructor */
    public static void main(String[] args){
        TightropeTester obj = new TightropeTester();
    }
}

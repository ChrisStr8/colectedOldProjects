// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 6
 * Name: pondy
 */

import ecs100.*;
import java.awt.Color;

/**
 *  This program contains methods for testing Surprise objects.
 *  It is all written for you, but you need to read it to see what the
 *  Surprise class should do
 */

public class SurpriseTester{

    public void testSurprise(){
        UI.clearText();
        UI.println("Should say 'boo' to pumpkin, squash, pumpkin,");
        UI.println("then to elephant, squash, pumpkin");
        UI.println("then to ant, squash, elephant");
        UI.println();

        Surprise s1 = new Surprise("Pumpkin");
        Surprise s2 = new Surprise("Squash");
        s1.sayBoo();
        s2.sayBoo();
        s1.sayBoo();
        Surprise s3 = new Surprise("Elephant");
        s3.sayBoo();
        s2.sayBoo();
        s1.sayBoo();
        //change the value in s1 to a new object  
        s1 = new Surprise("Ant");
        s1.sayBoo();
        s2.sayBoo();
        s3.sayBoo();
    }        

    /** Constructor to set up the user interface */
    public SurpriseTester(){
        UI.addButton("Test Surprise", this::testSurprise);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);
    }

    /** Calls the constructor */
    public static void main(String[] args){
        SurpriseTester obj = new SurpriseTester();
    }
}

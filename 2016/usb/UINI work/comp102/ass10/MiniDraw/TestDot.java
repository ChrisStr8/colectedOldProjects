// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

public class TestDot{

    // for testing the Dot class.  Run this method. It should display two (or three) dots.
    // click on and off them  to see if your on is working correctly
    // click in the left margin to exit.
    public static void main(String[] args){

        Dot d1 = new Dot( 100, 100, Color.blue);
        Dot d2 = new Dot( 200, 300, Color.red);
        d1.redraw();
        d2.redraw();
        UI.repaintGraphics();  // to redisplay them 

        UI.println("Testing on()");
        if (d1.on(10, 10)){
            UI.println("Error: (10,10) should not be on the blue dot");
        }
        if (!d1.on(100, 100)){
            UI.println("Error: (100,100) should be on the blue dot");
        }
        if (d2.on(203, 297)){
            UI.println("Error: (203,297) should not be on the red dot");
        }
        if (!d2.on(199, 301)){
            UI.println("Error: (199,301) should be on the red dot");
        }

        UI.println("Now blue should move right down\nred should move left down");
        UI.askBoolean("Ready?");
        d1.moveBy(10, 10);
        d2.moveBy(-26, 2);
        d1.redraw();
        d2.redraw();
        UI.repaintGraphics();  // to redisplay them 

        UI.println("Make new green dot from String");
        UI.askBoolean("Ready?");
        try{
            Dot d3 = new Dot("0 255 0 300 400");  //a green dot from a String
            d3.redraw();
            UI.repaintGraphics(); 
        }
        catch(Exception e){UI.println("Can't construct from a string yet");}
        UI.println("Done");
    }
}

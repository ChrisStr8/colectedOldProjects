// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 10
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

public class TestOval{

    // For testing the Oval class.  Run this method. It should display two ovals
    // then test on(), move the blue oval, resize the red one,
    // and finally test the second constructor by displaying a yellow oval
    public static void main(String[] args){

        Oval o1 = new Oval( 100, 100, 200, 200, Color.blue);
        Oval o2 = new Oval( 400, 200, 50, 50, Color.red);
        o1.redraw();
        o2.redraw();
        UI.repaintGraphics(); // to redisplay them 

        UI.println("Testing on()");
        if (o1.on(10, 10)){
            UI.println("Error: (10,10) should not be on the blue oval");
        }
        if (!o1.on(150, 190)){
            UI.println("Error: (150,190) should be on the blue oval");
        }
        if (o2.on(401, 201)){
            UI.println("Error: (401,201) should not really be on the red oval");
        }
        if (!o2.on(401, 225)){
            UI.println("Error: (401,225) should be just on the red oval");
        }

        UI.println("Blue should move and red should be resized");
        UI.askBoolean("Ready?");
        o1.moveBy(20, 20);
        o2.resize(-20, -20);
        o1.redraw();
        o2.redraw();
        UI.repaintGraphics();  // to redisplay them 

        UI.println("Make new yellow oval from String");
        UI.askBoolean("Ready?");

        try{
            //a yellow oval from a String
            Oval o3 = new Oval("255 255 0 300 400 20 20");  
            o3.redraw();
            UI.repaintGraphics(); 
        }
        catch(Exception e){UI.println("Can't construct from a string yet");}
        UI.println("Done");
    }
}

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 10
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

public class TestRectangle{

    // For testing the Rectangle class.  Run this method. It should display two rectangles
    // then test on(), move the blue rectangle, resize the red one,
    // and finally test the second constructor by displaying a yellow rectangle
    public static void main(String[] args) {
        Rectangle r1 = new Rectangle(100, 100, 200, 200, Color.blue);
        Rectangle r2 = new Rectangle(400, 200, 50, 50, Color.red);
        r1.redraw();
        r2.redraw();
        UI.repaintGraphics();                             // to redisplay them 

        UI.println("Testing on()");
        if (r1.on(10, 10)) {
            UI.println("Error: (10,10) should not be on the blue rectangle");
        }
        if (!r1.on(150, 190)) {
            UI.println("Error: (150,190) should be on the blue rectangle");
        }
        if (r2.on(451, 251)) {
            UI.println("Error: (451,251) should not be on the red rectangle");
        }
        if (!r2.on(401, 225)) {
            UI.println("Error: (401,225) should be just on the red rectangle");
        }
        UI.println("Blue should now move and red should be resized");
        UI.askBoolean("Ready?");
        r1.moveBy(20, 20);
        r2.resize( - 20,  - 20);
        r1.redraw();
        r2.redraw();
        UI.repaintGraphics();                             // to redisplay them 

        UI.println("Make new yellow rectangle from String");
        UI.askBoolean("Ready?");

        try {
            //a yellow rectangle from a String
            Rectangle r3 = new Rectangle("255 255 0 300 400 20 20");
            r3.redraw();
            UI.repaintGraphics();
        }
        catch(Exception e){UI.println("Can't construct from a string yet");}
        UI.println("Done");
    }

}

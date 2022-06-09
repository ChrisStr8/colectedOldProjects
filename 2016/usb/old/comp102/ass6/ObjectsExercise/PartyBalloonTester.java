// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 6
 * Name: pondy
 */

import ecs100.*;
import java.awt.Color;

/**
 *  This program contains methods for testing PartyBalloon objects.
 *  It is all written for you, but you need to read it to see what the
 *  PartyBalloon class should do
 */

public class PartyBalloonTester{

    /** Makes a pair of PartyBalloon objects and makes them fly into the sky */
    public void testPartyBalloon(){
        UI.clearPanes();
        UI.println("Should make two PartyBalloon objects");
        UI.println("of random colours at random positions");
        UI.println("which then fly into the sky");
        UI.setColor(Color.black);
        UI.drawLine(0,400, 500, 400);
        int count = 0;
        // make a random strong colour (random Hue, fully Saturated, maximum Brightness)
        Color colr = Color.getHSBColor((float)Math.random(),1,1);
        // choose a random x position
        double x = Math.random()*500;
        // make and draw a new PartyBalloon object.
        PartyBalloon myBalloon = new PartyBalloon(x, 370, colr);
        myBalloon.draw();

        // repeat for a second PartyBalloon.
        colr = Color.getHSBColor((float)Math.random(),1,1);
        x = Math.random()*500;
        PartyBalloon yourBalloon = new PartyBalloon(x, 370, colr);
        yourBalloon.draw();

        // repeatedly move and redraw the balloons.
        int steps = 0;
        while (steps < 18){
            UI.sleep(200);
            UI.eraseRect(0,0,700,400);

            //move the balloons
            myBalloon.riseLeft();
            yourBalloon.riseRight();

            //redraw the balloons
            myBalloon.draw();
            yourBalloon.draw();
            steps = steps + 1;
        }
    }

    /** Constructor to set up the user interface */
    public PartyBalloonTester(){
        UI.addButton("Test Party Ballons", this::testPartyBalloon);
        UI.addButton("Quit", UI::quit);
    }

    /** Calls the constructor */
    public static void main(String[] args){
        PartyBalloonTester obj = new PartyBalloonTester();
    }
}


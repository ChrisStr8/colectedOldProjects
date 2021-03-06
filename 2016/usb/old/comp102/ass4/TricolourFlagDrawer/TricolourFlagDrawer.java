// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 3
 * Name:
 * Usercode:
 * ID:
 */


import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

/** TricolourFlagDrawer: draws a series of tricolour flags */
public class TricolourFlagDrawer{

    public static final double width = 200;
    public static final double height = 133;

    /**
     * asks user for a position and three colours, then calls the 
     * drawThreeColourFlagCore method, passing the appropriate arguments
     *
     * CORE
     */
    public void testCore(){
        double left = UI.askDouble("left of flag");
        double top = UI.askDouble("top of flag");
        UI.println("Now choose the colours");
        Color stripe1 = JColorChooser.showDialog(null, "First Stripe", Color.white);
        Color stripe2 = JColorChooser.showDialog(null, "Second Stripe", Color.white);
        Color stripe3 = JColorChooser.showDialog(null, "Third Stripe", Color.white);
        this.drawThreeColourFlagCore(left, top, stripe1, stripe2, stripe3 );
    }

    /**
     * draws a three colour flag consisting of three vertical equal-width stripes
     * at the given position
     *
     * CORE
     */
    public void drawThreeColourFlagCore(double left, double top, Color stripe1, Color stripe2, Color stripe3){
        /*# YOUR CODE HERE */
        double stripe2Left = left + 10;
        double stripe3Left = left + 20;
        UI.setColor(Color.black);
        UI.drawRect(left-1, top-1, 31, 21);
        
        UI.setColor(stripe1);
        UI.fillRect(left, top, 10, 20);
        
        UI.setColor(stripe2);
        UI.fillRect(stripe2Left, top, 10, 20);
        
        UI.setColor(stripe3);
        UI.fillRect(stripe3Left, top, 10, 20);

    }

    /**
     * draws multiple flag made up of three equal size stripes by calling the
     * drawThreeColourFlagCompletion method, passing the appropriate arguments
     *
     * COMPLETION
     */
    public void testCompletion(){
        double left=100;
        double top=20;
        this.drawThreeColourFlagCompletion(true, 20, 50, Color.black, Color.yellow, Color.red);               // Belgium
        this.drawThreeColourFlagCompletion(false, 250, 100, Color.black, Color.red, Color.yellow);            // Germany
        this.drawThreeColourFlagCompletion(true, 140, 430, Color.blue, Color.white, Color.red);               // France
        this.drawThreeColourFlagCompletion(false, 470, 30, Color.red, Color.white, Color.blue);               // The Netherlands
        this.drawThreeColourFlagCompletion(false, 50, 250, Color.white, Color.blue, Color.red);               // Russia
        this.drawThreeColourFlagCompletion(true, 290, 270, Color.red, Color.yellow, Color.green.darker());    // Guinea
    }

    /**
     * draws a three colour flag consisting of three equal-size stripes
     * at the given position
     * The stripes can be either vertical or horizontal
     *
     * COMPLETION
     */
    public void drawThreeColourFlagCompletion(Boolean orientation, double left, double top, Color stripe1, Color stripe2, Color stripe3){
        /*# YOUR CODE HERE */
        double stripe2Left = left + 10;
        double stripe3Left = left + 20;
        
        double stripe2Top = top + 7;
        double stripe3Top = top + 14;
        if (orientation == true){
            UI.setColor(Color.black);
            UI.drawRect(left-1, top-1, 31, 21);
        
            UI.setColor(stripe1);
            UI.fillRect(left, top, 10, 20);
        
            UI.setColor(stripe2);
            UI.fillRect(stripe2Left, top, 10, 20);
        
            UI.setColor(stripe3);
            UI.fillRect(stripe3Left, top, 10, 20);
        }else if (orientation == false){
            UI.setColor(Color.black);
            UI.drawRect(left-1, top-1, 31, 22);
        
            UI.setColor(stripe1);
            UI.fillRect(left, top, 30, 7);
        
            UI.setColor(stripe2);
            UI.fillRect(left, stripe2Top, 30, 7);
        
            UI.setColor(stripe3);
            UI.fillRect(left, stripe3Top, 30, 7);
        }
    }


    /** ---------- The code below is already written for you ---------- **/

    /** Constructor: set up user interface */
    public TricolourFlagDrawer(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Core", this::testCore );
        UI.addButton("Completion", this::testCompletion );
        UI.addButton("Quit", UI::quit );
    }


}

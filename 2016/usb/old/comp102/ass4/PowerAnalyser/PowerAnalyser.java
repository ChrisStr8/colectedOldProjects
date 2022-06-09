// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 4 
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** The program contains two methods for analysing the readings of the level of power usage in a house over the course of a day.
 *  There are several things about the power usage level that a user may be interested in: 
 *    The maximum and the minimum power usage level at any point during the day.
 *    The average power usage level during the day.
 *
 *  The program has two methods.  They both read a sequence of levels from
 *  the user (through the terminal window).
 *  One prints a report about the levels; the other plots a graph.
 */
public class PowerAnalyser{

    /**
     * analyseLevels reads a sequence of levels from the user  and prints out
     *    maximum, minimum, and average level.
     *    
     * The sequence is terminated by any word (non-number) such as "done" or "end".
     * All the levels are integers between 0 and 8000. 
     * The method will need variables to keep track of various quantities, all of which 
     * need to be initialised to an appropriate value.
     * It will need a loop to keep reading the levels until there isn't an integer next.
     * [Core]
     *   - There is guaranteed to be at least one level,
     *   - Print the maximum, minimum, and average level
     *   - Hint, keep track of the sum of the levels using a variable of type double
     */
    public void analyseLevels() {
        UI.clearText();

        // Initialise variables
        // Prompt for input
        // Loop, reading numbers and updating variables
        // Compute and print out the analysis

        /*# YOUR CODE HERE */
        double averagelevel = 0;
        double totallevels = 0;
        double numlevels = 0;
        double minlevel = Integer.MAX_VALUE;
        double maxlevel = Integer.MIN_VALUE;
        
        UI.clearText();
        UI.clearGraphics();
        String fname = UIFileChooser.open("Power levels file");
        if (fname != null){
            try {
                Scanner scan = new Scanner(new File(fname));
                
                while(scan.hasNextInt()){
                    double token = Double.parseDouble(scan.next());
                    totallevels += token;
                    numlevels += 1;
                    if(token < minlevel){
                        minlevel = token;
                    }else if(token > maxlevel){
                        maxlevel = token;
                    }
                }
                averagelevel = totallevels/numlevels;
                
                UI.println("Maximum: "+maxlevel);
                UI.println("Minimum: "+minlevel);
                UI.println("Average: "+averagelevel);
            }catch(IOException e){UI.println("File reading failed" + e);}
        }
        

        UI.nextLine(); // to clear out the input
    }

    /**
     * Reads a sequence of levels (integers) from the user (using Scanner
     * and the terminal window) and plots a bar graph of them, using narrow 
     * rectangles whose heights are equal to the level.
     * The sequence is terminated by any word (non-number) such as "done" or "end".
     * The method may assume that there are at most 24 numbers.
     * The method will need a loop to keep reading the levels until there isn't a number next.
     *  Each time round the loop, it needs to read the next level and work out where
     *  to draw the rectangle for the bar. 
     * [Completion]
     *   - The method should work even if there were no readings for the day.
     *   - Any level greater than 8000 should be plotted as if it were just 8000, putting an
     *         asterisk ("*") above it to show that it has been cut off.
     *   - Draws a horizontal line for the x-axis (or baseline) without any labels.
     * [Challenge:] 
     *   - The graph should also have labels on the axes, roughly every 50 pixels.
     *   - Make the method ask for a maximum level first, then scale the y-axis and values 
     *     so that the largest numbers just fit on the graph.
     *   - The numbers on the y axis should reflect the scaling.
     */
    public void plotLevels() {
        UI.clearText();
        UI.clearGraphics();
        //  Initialise variables and prompt for input
        // Loop, reading numbers and drawing bars

        /*# YOUR CODE HERE */
        double averagelevel = 0;
        double totallevels = 0;
        double numlevels = 0;
        double minlevel = Integer.MAX_VALUE;
        double maxlevel = Integer.MIN_VALUE;
        double graphheight = 0;
        double graphy = 20;
        
        UI.clearText();
        UI.clearGraphics();
        String fname = UIFileChooser.open("Power levels file");
        UI.drawLine(20, 400, 400, 400);
        if (fname != null){
            try {
                Scanner scan = new Scanner(new File(fname));
                
                while(scan.hasNextInt()){
                    double token = Double.parseDouble(scan.next());
                    graphheight = token/20;
                    double graphx = 400 - graphheight;
                    UI.fillRect(graphy, graphx, 20, graphheight);
                    graphy += 30;
                    totallevels += token;
                    numlevels += 1;
                    if(token < minlevel){
                        minlevel = token;
                    }else if(token > maxlevel){
                        maxlevel = token;
                    }
                }
                averagelevel = totallevels/numlevels;
                
                UI.println("Maximum: "+maxlevel);
                UI.println("Minimum: "+minlevel);
                UI.println("Average: "+averagelevel);
            }catch(IOException e){UI.println("File reading failed" + e);}
        }

        UI.nextLine(); // to clear out the input
        UI.println("Done");
    }

    /** ---------- The code below is already written for you ---------- **/
    /** Constructor: set up user interface */
    public PowerAnalyser(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Analyse Levels", this::analyseLevels );
        UI.addButton("Plot Levels Completion", this::plotLevels );
        UI.addButton("Quit", UI::quit );
    }


}

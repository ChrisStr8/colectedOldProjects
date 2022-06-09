// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/** Exercise
 * Read lines/tokens from a file and prints them out, one per line
 * Read numbers from a file and add them up, first number says how many numbers there are.
 */

public class Exercise{

    /** Construct a new Exercise object */
    public Exercise(){
        UI.addButton("print lines (text.txt)",this::printLines);
        UI.addButton("print tokens (text.txt)", this::printTokens);
        UI.addButton("print numbers only (text.txt)", this::printNumbers);
        UI.addButton("add up numbers (numbers.txt)", this::addUpNumbers);
        UI.addButton("add up bad numbers", this::addUpBadNumbers);
        UI.addButton("draw names (names.txt)", this::drawNames);
        UI.addButton("quit", UI::quit);
    }

    public void printLines(){
        UI.clearText();
        printFileByLine("text.txt");
    }

    /**
     * Print out a file, one line at a time
     */
    public void printFileByLine(String fname){
        /*# YOUR CODE HERE */

    }

    public void printTokens(){
        UI.clearText();
        printFileByToken("text.txt");
    }

    /**
     * Print out a file, one token at a time, one on each line.
     */
    public void printFileByToken(String fname){
        /*# YOUR CODE HERE */

    }

    public void printNumbers(){
        UI.clearText();
        printNumbersOnly("text.txt");
    }

    /**
     * Print all (and only) the numbers in a file, skipping over non-numbers.
     * Assumes that numbers are separated by space from surrounding tokens.
     */
    public void printNumbersOnly(String fname){
        /*# YOUR CODE HERE */

    }

    public void addUpNumbers(){
        UI.clearText();
        UI.println("total = " + addUpFile("numbers.txt"));
    }

    public void addUpBadNumbers(){
        UI.clearText();
        UI.println("badnumbers1.txt: " + addUpFile("badnumbers1.txt"));
        UI.println("badnumbers2.txt: " + addUpFile("badnumbers2.txt"));
        UI.println("badnumbers3.txt: " + addUpFile("badnumbers3.txt"));
    }

    /**
     * Add up a list of numbers in a file, where the first
     * token in the file is the count of the numbers in the list.
     * Checks for badly constructed files - reporting if the count
     * was too high, or if there was a non number in the file.
     * in either case, it returns null. 
     * If the file is properly constructed, it returns the total.
     * (Note that the return type is Double, not double:
     * Double is an object containing a double. Java will
     * automatically convert between double and Double in most cases.
     */
    public double addUpFile(String fname){
        /*# YOUR CODE HERE */

    }

    /**
     * Read a file called "names.txt" containing  of names and positions,
     * and draw them on the graphics pane.
     * Each line of the file has two numbers (x and y) and one word name,
     * followed by three integers specifying the color to draw the name.
     * For each line, it sets the color, then draws the name at the position.
     * If the three colour integers are in r, g, and b, then you can set the colour
     * using  UI.setColor(new Color(r, g, b)));
     */
    public void drawNames(){
        /*# YOUR CODE HERE */

    }



    public static void main(String[] arguments){
        Exercise ex = new Exercise();
    }        

}

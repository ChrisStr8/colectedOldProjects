// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 5
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Color;

/**
 * Exercises for programs that read data from files.
 */

public class FilesExercise{

    /**
     *  This method lets you read the contents of a file.
     *        It should
     *  - make a Scanner wrapped around a new File
     *  - then read each line of the file and print it to the UI text pane
     */
    public void printFile(){
        String fileName = UIFileChooser.open("Choose a file");
        UI.println("Printing contents of "+ fileName);
        UI.println("--------------------------------------");
        try {
            /*# YOUR CODE HERE */

        }
        catch(IOException e){UI.println("File reading failed");}
        UI.println("--------------------------------------");
    }


    /**
     * Read numbers from a file called "numbers.txt" which contains only numbers
     * adding them up, and printing the total at the end. (The total should be 2174)
     */
    public void sumNumbers(){
        /*# YOUR CODE HERE */

    }

    /**
     * Read a file called "circle-positions.txt" containing pairs of numbers,
     *  and draw a circle of radius 20 at each position.
     *        eg, if a line of the file has 120 350 on it, then draw a circle centered at
     *        (120, 350). You can make them all the same colour or random colours
     */
    public void drawCircles(){
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

    /**
     * Read a file called "text.txt" and print out just the first token (word) on each line.
     */
    public void firstWords(){
        /*# YOUR CODE HERE */

    }

    /**
     * Read words from a file called "text.txt" and print out the longest word.
     */
    public void longestWord(){
        /*# YOUR CODE HERE */

    }

    /**
     * Read coursecodes and course sizes (number of students) from file called
     * "course-counts.txt" and count how many courses have more than 100 students
     */
    public void largeCourses(){
        /*# YOUR CODE HERE */

    }

    /**
     * Read a file called "order.txt", consisting of lines with:
     *  item  count  unit-price,
     *  and then print total cost of order
     */
    public void totalOrder(){
        /*# YOUR CODE HERE */

    }

    /** ---------- The code below is already written for you ---------- **/

    /** Constructor: set up user interface */
    public FilesExercise(){
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("print file", this::printFile );
        UI.addButton("sum numbers", this::sumNumbers );
        UI.addButton("draw circles", this::drawCircles );
        UI.addButton("draw names", this::drawNames );
        UI.addButton("large courses", this::largeCourses );
        UI.addButton("first words", this::firstWords );
        UI.addButton("longest word", this::longestWord );
        UI.addButton("total of order", this::totalOrder );
        UI.addButton("Quit", UI::quit );
    }        


}

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for Assignment 4
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** LoopsExercise is a collection of small methods with while loops
 */
public class LoopsExercise{

    /* Add up all the integers from 1 to n (using a loop) and prints the sum.
     *    For example, if the argument is 10, it should add up
     *    1+2+3+4+5+6+7+8+9+10 and print out the sum: 55
     */
    public void sumOneToN(int max){
        /*# YOUR CODE HERE */

    }

    /* Tell the user to enter a sequence of numbers, ending with the word 'done'
     * then read all the numbers in a loop, printing out the square of each number as it goes.
     * eg, if they enter 4 11 20 9 done
     * it should print   16 121 400 81    (either on one line or on separate lines)
     */
    public void squareNumbers(){
        /*# YOUR CODE HERE */

        UI.next(); // Read and throw away the 'done' 
    }

    /* Tell the user to enter a sentence, followed by the token #
     * then read all the tokens (words) and print out the number of
     * words in the sentence.
     * eg, if they enter
     * I am happy today #
     * it should print out
     * Sentence has 4 words
     * You should use a "while(true)" loop with a break, and the UI.next() method
     */
    public void countWords(){
        /*# YOUR CODE HERE */

    }

    /* Asks user for an integer, then draws that many blue bubbles
     * in a vertical line.
     */
    public void drawBubbles(int n){
        /*# YOUR CODE HERE */

    }

    /* Tell the user to enter a sequence of numbers, ending with the word 'done'
     * then read all the numbers in a loop, adding them up as it goes.
     * Then print the sum 
     */
    public void sumNumbers(){
        /*# YOUR CODE HERE */
        
        UI.next(); // Read and throw away the 'done' 
    }

    /** ---------- The code below is already written for you ---------- **/
    
    /** Constructor: set up user interface */
    public LoopsExercise(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("sumOneToN", () -> this.sumOneToN(UI.askInt("Enter argument for sumOneToN(int max)")));
        UI.addButton("squareNumbers", this::squareNumbers);
        UI.addButton("countWords", this::countWords);
        UI.addButton("drawBubbles", () -> this.drawBubbles(UI.askInt("Enter int argument for drawBubbles(int n)")));
        UI.addButton("sumNumbers", this::sumNumbers);
        UI.addButton("Quit", UI::quit );
    }


}

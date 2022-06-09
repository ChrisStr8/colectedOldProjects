// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for Assignment 6
 * Name:christopher straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;

/** A Surprise object remembers the word it was given when it was constructed
 *   and has one method that says "Boo " followed by the word.
 *   It is a totally useless class!!!
 */
public class Surprise{

    //field
    /*# YOUR CODE HERE */
    public String supriseword;

    /** Constructor: 
     * Stores the word it is passed in the field
     */
    public Surprise(String wd){
        /*# YOUR CODE HERE */
        supriseword = wd;
    }

    /** sayBoo method
     * Prints out "Boo " followed by the stored word
     */
    public void sayBoo(){
        /*# YOUR CODE HERE */
        UI.println("Boo "+supriseword);
                
    }        
}

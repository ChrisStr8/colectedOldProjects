// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Make-up Assignment
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.util.Scanner;
import java.io.*;

/** Displays an animated cartoon of two characters from a script in a file.
 *  The first two lines of the file are the names (imagefile names) and
 *  initial positions of the two characters.
 *  Each remaining line in the file contains the name of a character,
 *  followed by a command: lookLeft, lookRight, smile, frown, move, speak
 *  If the command is "walk", it is followed by a distance to walk
 *  If the command is "speak", it is followed by the text to say.
 */
public class CartoonAnimator{

    /** animateFromFile opens a script file, and reads the names and intial positions
     *  of the two characters from the first two lines of the file.
     *  It then creates two CartoonCharacter objects, using the names as the
     *  imageNames and places them on the window.
     *  It then has a loop to read each instruction from the script file:
     *  Each instruction starts with the name of the character, followed by
     *  a command:  lookLeft, lookRight, smile, frown, walk, or speak
     *  If the command is walk, it must be followed by a single integer
     *  (the distance to walk).
     *  If the command is speak, it must be followed by some words to say.
     *  The method then calls the command on the correct CartoonCharacter object.
     *  The loop will exit when there are no more instructions.
     */
    public void animateFromFile(){
        String fname = UIFileChooser.open();
        try{
            // open the script file 
            // read the names, image files, and intial positions of the two characters
            // and create the two CartoonCharacter objects.
            // Then loop through the instructions
            /*# YOUR CODE HERE */
            Scanner sc = new Scanner(new File(fname));
            String c1Name = sc.next();
            double c1x = Double.parseDouble(sc.next());
            double c1y = Double.parseDouble(sc.next());
            String c1Colour = sc.next();
            CartoonCharacter c1 = new CartoonCharacter(c1x, c1y, c1Colour); 
            
            String c2Name = sc.next();
            double c2x = Double.parseDouble(sc.next());
            double c2y = Double.parseDouble(sc.next());
            String c2Colour = sc.next();
            CartoonCharacter c2 = new CartoonCharacter(c2x, c2y, c2Colour);
            while(sc.hasNext()){
                String cName = sc.next();
                String cInstruction = sc.next();
                String cData = sc.nextLine();
                if(cName.equals(c1Name)){
                    if(cInstruction.equals("lookLeft")){
                        c1.lookLeft();
                    }else if(cInstruction.equals("lookRight")){
                        c1.lookRight();
                    }else if(cInstruction.equals("smile")){
                        c1.smile();
                    }else if(cInstruction.equals("frown")){
                        c1.frown();
                    }else if(cInstruction.equals("walk")){
                        c1.walk(Double.parseDouble(cData));
                    }else if(cInstruction.equals("speak")){
                        c1.speak(cData);
                    }else if(cInstruction.equals("think")){
                        c1.think(cData);
                    }
                }else if(cName.equals(c2Name)){
                    if(cInstruction.equals("lookLeft")){
                        c2.lookLeft();
                    }else if(cInstruction.equals("lookRight")){
                        c2.lookRight();
                    }else if(cInstruction.equals("smile")){
                        c2.smile();
                    }else if(cInstruction.equals("frown")){
                        c2.frown();
                    }else if(cInstruction.equals("walk")){
                        c2.walk(Double.parseDouble(cData));
                    }else if(cInstruction.equals("speak")){
                        c2.speak(cData);
                    }else if(cInstruction.equals("think")){
                        c2.think(cData);
                    }
                }
            }
            sc.close();
        } catch(IOException e){UI.println("File reading failed: "+e);}
    }

    /** ---------- The code below is already written for you ---------- **/

    /** Constructor: set up user interface */
    public CartoonAnimator(){
        UI.initialise();
        UI.addButton("Animate", this::animateFromFile);
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Quit", UI::quit );
        UI.setDivider(0);       // Expand the graphics area
    }

    public static void main(String[] arguments){
        CartoonAnimator animator = new CartoonAnimator();
    }        

}

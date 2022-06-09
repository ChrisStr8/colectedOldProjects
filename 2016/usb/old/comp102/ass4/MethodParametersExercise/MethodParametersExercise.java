// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for Assignment 3, COMP102 
 * Name:
 * Usercode:
 * ID:
 */


import ecs100.*;
import java.awt.Color;

/** The MethodParametersExercise class has several methods that could be
greatly simplified by creating new methods and calling them several times.

- nameTags() prints out a series of nametags for workshop participants.
Each nametag has the participant's name and workshop ID number.
It would be better if it called a drawNameTag method five times. 

- bottleSong() prints out the words to a traditional repetitive song.
It would be better if it called a bottleSongVerse method 9 times.

- signalZeroFlag() draws the flag used by the US navy to signal the
value "zero", which is a white square with five small blue crosses,
laid out like the dots on the "5" side of ordinary dice.
It would be better if it called a drawCross method five times. 

 */

public class MethodParametersExercise {

    /** Prints out a series of nametags for workshop participants.
     *  Each nametag has the participant's name and workshop ID number.
     */
    public void nameTags(){
        UI.println();
        UI.println("+-------------------------------+");
        UI.println("| The Programming Workshop 2016 |");
        UI.println("|                               |");
        UI.println("|  HELLO, my name is            |");
        UI.println("|                               |");
        UI.printf( "|   %20s        |\n", "John Smith");
        UI.println("|                               |");
        UI.printf( "| # %3d                         |\n", 325);
        UI.println("+-------------------------------+");
        UI.println();
        UI.println("+-------------------------------+");
        UI.println("| The Programming Workshop 2016 |");
        UI.println("|                               |");
        UI.println("|  HELLO, my name is            |");
        UI.println("|                               |");
        UI.printf( "|   %20s        |\n", "Jason Strickland");
        UI.println("|                               |");
        UI.printf( "| # %3d                         |\n", 218);
        UI.println("+-------------------------------+");
        UI.println();
        UI.println("+-------------------------------+");
        UI.println("| The Programming Workshop 2016 |");
        UI.println("|                               |");
        UI.println("|  HELLO, my name is            |");
        UI.println("|                               |");
        UI.printf( "|   %20s        |\n", "Jane Salisor");
        UI.println("|                               |");
        UI.printf( "| # %3d                         |\n", 15);
        UI.println("+-------------------------------+");
        UI.println();
        UI.println("+-------------------------------+");
        UI.println("| The Programming Workshop 2016 |");
        UI.println("|                               |");
        UI.println("|  HELLO, my name is            |");
        UI.println("|                               |");
        UI.printf( "|   %20s        |\n", "Jie Song");
        UI.println("|                               |");
        UI.printf( "| # %3d                         |\n", 123);
        UI.println("+-------------------------------+");
        UI.println();
        UI.println("+-------------------------------+");
        UI.println("| The Programming Workshop 2016 |");
        UI.println("|                               |");
        UI.println("|  HELLO, my name is            |");
        UI.println("|                               |");
        UI.printf( "|   %20s        |\n", "Julia Sargeant");
        UI.println("|                               |");
        UI.printf( "| # %3d                         |\n", 248);
        UI.println("+-------------------------------+");
        UI.println();
    }

    /** Prints out a series of nametags for workshop participants.
     *  Each nametag has the participant's name and workshop ID number.
     */
    public void nameTagsFixed(){
        this.printNameTag(/*# YOUR CODE HERE */ );
        this.printNameTag(/*# YOUR CODE HERE */ );
        this.printNameTag(/*# YOUR CODE HERE */ );
        this.printNameTag(/*# YOUR CODE HERE */ );
        this.printNameTag(/*# YOUR CODE HERE */ );
        UI.println();
    }

    /** Prints a nametag
     *  A nametag has the participant's name and workshop ID number.
     */
    public void printNameTag(/*# YOUR CODE HERE */ ){
        /*# YOUR CODE HERE */

    }

    /** Prints out the words to a traditional repetitive song. */
    public void bottleSong(){
        UI.println(10 + " green bottles, hanging on the wall,");
        UI.println(10 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (10-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(9 + " green bottles, hanging on the wall,");
        UI.println(9 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (9-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(8 + " green bottles, hanging on the wall,");
        UI.println(8 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (8-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(7 + " green bottles, hanging on the wall,");
        UI.println(7 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (7-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(6 + " green bottles, hanging on the wall,");
        UI.println(6 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (6-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(5 + " green bottles, hanging on the wall,");
        UI.println(5 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (5-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(4 + " green bottles, hanging on the wall,");
        UI.println(4 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (4-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(3 + " green bottles, hanging on the wall,");
        UI.println(3 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (3-1) + " green bottles, hanging on the wall,");
        UI.println();

        UI.println(2 + " green bottles, hanging on the wall,");
        UI.println(2 + " green bottles, hanging on the wall,");
        UI.println("And if one green bottle, should accidently fall,");
        UI.println("There'd be "+ (2-1) + " green bottles, hanging on the wall,");
        UI.println();

    }

    public void bottleSongFixed(){
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); // prints verse 10 bottles...
        this.bottleSongVerse(/*# YOUR CODE HERE */ );  // prints verse 9 bottles..
        this.bottleSongVerse(/*# YOUR CODE HERE */ );  // prints verse 8 bottles...
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); 
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); 
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); 
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); 
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); 
        this.bottleSongVerse(/*# YOUR CODE HERE */ ); 
        UI.println();
        UI.println(); 
    }

    /** bottleSongVerse should be passed the number of bottles remaining */
    public void bottleSongVerse(/*# YOUR CODE HERE */ ){
        /*# YOUR CODE HERE */

    }

    /** Draws the flag used by the US navy to signal the value "zero":
     *    a white square with five little blue crosses on it (laid out like the
     *    dots on the "5" side of ordinary dice).
     *    See http://www.navy.mil/navydata/communications/flags/num0.gif
     */
    public void signalZeroFlag(){
        // Variables
        double flagSize = UI.askDouble("Size of flag:");
        double flagLeft = 200;
        double flagTop = 100;
        double centerX = flagLeft + 0.5 * flagSize;
        double centerY = flagTop + 0.5 * flagSize;
        double offset = 0.3 * flagSize;
        double crossRad = 0.08 * flagSize;
        double crossThick = 0.05 * flagSize;

        //flag background
        UI.setColor(Color.white);
        UI.fillRect(flagLeft, flagTop, flagSize, flagSize);
        UI.setColor(Color.black);
        UI.drawRect(flagLeft, flagTop, flagSize, flagSize);

        //first cross
        double left  = (centerX) - crossRad;
        double right = (centerX) + crossRad;
        double top   = (centerY) - crossRad;
        double bot   = (centerY) + crossRad;
        UI.setLineWidth(crossThick);
        UI.setColor(Color.blue);
        UI.drawLine(left, centerY, right, centerY);
        UI.drawLine(centerX, top, centerX, bot);

        //top left cross
        left  = (centerX - offset) - crossRad;
        right = (centerX - offset) + crossRad;
        top   = (centerY - offset) - crossRad;
        bot   = (centerY - offset) + crossRad;
        UI.setLineWidth(crossThick);
        UI.setColor(Color.blue);
        UI.drawLine(left, (centerY - offset), right, (centerY - offset));
        UI.drawLine((centerX - offset), top, (centerX - offset), bot);

        //top right cross
        left  = (centerX + offset) - crossRad;
        right = (centerX + offset) + crossRad;
        top   = (centerY - offset) - crossRad;
        bot   = (centerY - offset) + crossRad;
        UI.setLineWidth(crossThick);
        UI.setColor(Color.blue);
        UI.drawLine(left, (centerY - offset), right, (centerY - offset));
        UI.drawLine((centerX + offset), top, (centerX + offset), bot);

        //bottom left cross
        left  = (centerX - offset) - crossRad;
        right = (centerX - offset) + crossRad;
        top   = (centerY + offset) - crossRad;
        bot   = (centerY + offset) + crossRad;
        UI.setLineWidth(crossThick);
        UI.setColor(Color.blue);
        UI.drawLine(left, (centerY + offset), right, (centerY + offset));
        UI.drawLine((centerX - offset), top, (centerX - offset), bot);

        //bottom right cross
        left  = (centerX + offset) - crossRad;
        right = (centerX + offset) + crossRad;
        top   = (centerY + offset) - crossRad;
        bot   = (centerY + offset) + crossRad;
        UI.setLineWidth(crossThick);
        UI.setColor(Color.blue);
        UI.drawLine(left, (centerY + offset), right, (centerY + offset));
        UI.drawLine((centerX + offset), top, (centerX + offset), bot);
    }

    /** Draws the flag used by the US navy to signal the value "zero":
     *    a white square with five little blue crosses on it (laid out like the
     *    dots on the "5" side of ordinary dice).
     *    See http://www.navy.mil/navydata/communications/flags/num0.gif
     */
    public void signalZeroFlagFixed(){
        double flagSize = UI.askDouble("Size of flag:");
        double left = 200;
        double top = 100;
        double centerX = left+flagSize/2;
        double centerY = top+flagSize/2;
        double offset = 0.3*flagSize;
        double crossRad = 0.08*flagSize;
        double crossThick = 0.05*flagSize;

        //flag background
        UI.setColor(Color.white);
        UI.fillRect(left, top, flagSize, flagSize);
        UI.setColor(Color.black);
        UI.drawRect(left, top, flagSize, flagSize);

        //draw crosses
        this.drawCross(/*# YOUR CODE HERE */ );
        this.drawCross(/*# YOUR CODE HERE */ );
        this.drawCross(/*# YOUR CODE HERE */ );
        this.drawCross(/*# YOUR CODE HERE */ );
        this.drawCross(/*# YOUR CODE HERE */ );
    }

    /** drawCross should be passed the position of the centre of the cross and its size */
    public void drawCross(/*# YOUR CODE HERE */ ){
        /*# YOUR CODE HERE */

    }

    /** ---------- The code below is already written for you ---------- **/

    /** Constructor to set up the user interface */
    public MethodParametersExercise(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("NameTags", this::nameTagsFixed );
        UI.addButton("BottleSong", this::bottleSongFixed );
        UI.addButton("SignalFlag", this::signalZeroFlagFixed );
        UI.addButton("Quit", UI::quit );
    }


}

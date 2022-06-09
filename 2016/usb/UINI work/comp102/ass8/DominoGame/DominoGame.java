// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 8
 * Name:Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;


/**
 *  Lets a player play a simple Solitaire dominoes game.
 *  Dominoes are rectangular tiles with two numbers from 0 to 6 on
 *  them (shown with dots).
 *  The player has a "hand" which can contain up to six dominoes.
 *  They can reorder the dominoes in their hand, they can place dominoes
 *  from their hand onto the table, and they can pick up more dominoes from a bag
 *  to fill the gaps in their "hand".
 *  The core and completion do not involve any of the matching and scoring
 *  of real dominoes games. 
 *
 *  PROGRAM DESIGN
 *  The dominoes are represented by objects of the Domino class.
 *  The Domino constructor will construct a new, random domino.
 *  Dominos have a draw(double x, double y) method that will draw the
 *  Domino on the graphics pane at the specified position.
 *  
 *  The program has two key fields:
 *    hand:  an array that can hold 6 Dominos. 
 *    table: an arrayList of the Dominos that have been placed on the table.
 *    
 *  The hand should be displayed near the top of the Graphics pane with a
 *   rectangular border and each domino drawn at its place in the hand.
 *  Empty spaces in the hand should be represented by nulls and displayed as empty.
 *
 *  The user can select a position on the hand using the mouse.
 *  The selected domino (or empty space) should be highlighted with
 *  a border around it.
 *  
 *  The user can use the "Left" or "Right" button to move the selected domino
 *  (or the space) to the left or the right, in which case the domino is
 *  swapped with the contents of the adjacent position in the hand.
 *  If the selected position contains a domino, the user
 *  can use the "Place" button to move the selected domino to the table.
 *  
 *  If there are any empty positions on the hand, the user can use the
 *  "Pickup" button to get a new (random) domino which will be added to
 *  the hand at the leftmost empty position.
 *
 *  The table is represented by an ArrayList of dominos.
 *  At the beginning of the game the table should be empty.
 *  Dominos should be added to the end of the table.
 *  The table should be displayed in rows at the top of the graphics pane.
 */

public class DominoGame{
    public static final int NUM_HAND = 6;    // Number of dominos in hand

    // Fields
    private Domino[] hand;            // the hand (fixed size array of Dominos)
    private ArrayList<Domino> table;  // the table (variable sized list of Dominos)

    private int selectedPos = 0;      //  selected position in the hand.

    // (You shouldn't add any more fields for core or completion)

    // constants for the layout
    public static final int HAND_LEFT = 60; // x-position of the leftmost Domino in the hand
    public static final int HAND_TOP = 5;   // y-Position of all the Dominos in the hand 
    public static final int DOMINO_SPACING = 54; 
    //spacing is the distance from left side of Domino to left side of next domino
    public static final int DOMINO_HEIGHT = 100;

    public static final int TABLE_LEFT = 10;                
    public static final int TABLE_TOP = 120; 
    
    public boolean draging = false;
    public double dragedX = 0;
    public double dragedY = 0;

    /**  Constructor:
     * Initialise the hand field to have an array that will hold NUM_HAND Dominos
     * Initialise the table field to have an ArrayList of Dominos,
     * set up the GUI (buttons and mouse)
     *  restart the game
     */
    public DominoGame(){
        /*# YOUR CODE HERE */
        this.hand = new Domino[NUM_HAND];
        this.table = new ArrayList<Domino>();
        UI.initialise();
        UI.addButton("Pickup", this::pickup);
        UI.addButton("Place", this::placeDomino);
        UI.addButton("Flip", this::flipDomino);
        UI.addButton("Left", this::moveLeft);
        UI.addButton("Right", this::moveRight);
        UI.addButton("Suggstion", this::suggestDomino);
        UI.addButton("Restart", this::restart);
        UI.addButton("Quit", UI::quit);
        UI.setMouseListener(this::doMouse);
        UI.setMouseMotionListener(this::doMouse);
        this.restart();
        this.redraw();
    }

    /**
     * Restart the game:
     *  set the table to be empty,
     *  set the hand to have no dominos
     */
    public void restart(){
        /*# YOUR CODE HERE */
        this.table.clear();
        this.hand = new Domino[NUM_HAND];
        this.redraw();
    }
    

    /**
     * If there is at least one empty position on the hand, then
     * create a new random domino and put it into the first empty position on the hand.
     * (needs to search along the array for an empty position.)
     */
    public void pickup(){
        /*# YOUR CODE HERE */
        for(int n=0;n<hand.length;n++){
            if(this.hand[n]==null){
                this.hand[n] = new Domino();
                break;
            }
        }
        this.redraw();
    }

    /**
     * Draws the outline of the hand,
     * draws all the Dominos in the hand,
     * highlights the selected position in some way
     */
    public void drawHand(){
        /*# YOUR CODE HERE */
        UI.setColor(Color.red);
        UI.drawRect(HAND_LEFT-5, HAND_TOP, HAND_LEFT + DOMINO_SPACING * 5, DOMINO_HEIGHT);
        try{
            for(int n=0;n<hand.length;n++){
                if(this.hand[n]!=null && n!=selectedPos){
                    this.hand[n].draw(HAND_LEFT + DOMINO_SPACING*n, HAND_TOP);
                }else if(n==selectedPos){
                    if(draging==true){
                        this.hand[selectedPos].draw(dragedX, dragedY);
                    }else if(draging==false){
                        this.hand[selectedPos].draw(HAND_LEFT + DOMINO_SPACING*selectedPos, HAND_TOP);
                    }
                }
            }
        }catch(NullPointerException e){}
        UI.setColor(Color.blue);
        UI.drawRect(HAND_LEFT + DOMINO_SPACING*selectedPos, HAND_TOP, 50, DOMINO_HEIGHT);
        UI.clearText();
        UI.println("selected "+(this.selectedPos+1));
    }
   
    
    /**
     * Move domino from selected position on hand (if there is domino there) to the table
     * The selectedPos field contains the index of the selected domino.
     */
    public void placeDomino(){
        /*# YOUR CODE HERE */
        if(this.hand[selectedPos]== null){
            UI.println("selection "+(this.selectedPos+1)+" is empty");
        }else if(checkLegal()){
            this.table.add(this.hand[selectedPos]);
            this.hand[selectedPos] = null;
        }else if(checkLegal()==false){
            UI.println("Ilegal move");
        }
        this.redraw();
    }

    /**
     * Draws the list of Dominos on the table, 10 to a row
     * Note, has to wrap around to a new row when it gets to the
     * edge of the table
     */
    public void drawTable(){
        /*# YOUR CODE HERE */
        int rownum = 0;
        int top = HAND_LEFT + DOMINO_HEIGHT;
        int left = DOMINO_SPACING;
        try{
            for(int n=0;n<this.table.size();n++){
                Domino d = table.get(n);
                d.draw(left + DOMINO_SPACING*rownum, top);
                rownum += 1;
                if(rownum==10){
                    top += DOMINO_HEIGHT+4;
                    left = DOMINO_SPACING;
                    rownum = 0;
                }
            }
        }catch(IndexOutOfBoundsException e){}
    }
    
    /**
     * If there is a domino at the selected position in the hand, 
     * flip it over.
     */
    public void flipDomino(){
        /*# YOUR CODE HERE */
        if(this.hand[selectedPos]!= null){
            this.hand[selectedPos].flip();
        }
        this.redraw();
    }

    /**
     * Swap the contents of the selected position on hand with the
     * position on its left (if there is such a position)
     * and also decrement the selected position to follow the domino 
     */
    public void moveLeft(){
        /*# YOUR CODE HERE */
        if(selectedPos-1>=0){
            Domino leftD = this.hand[selectedPos-1];
            Domino d = this.hand[selectedPos];
            this.hand[selectedPos-1] = d;
            this.hand[selectedPos] = leftD;
            this.selectedPos -= 1;
        }
        this.redraw();
    }

    /**
     * Swap the contents of the selected position on hand with the
     *  position on its right (if there is such a position)
     *  and also increment the selected position to follow the domino 
     */
    public void moveRight(){
        /*# YOUR CODE HERE */
        if(selectedPos+1<=NUM_HAND-1){
            Domino rightD = this.hand[selectedPos+1];
            Domino d = this.hand[selectedPos];
            this.hand[selectedPos+1] = d;
            this.hand[selectedPos] = rightD;
            this.selectedPos += 1;
        }
        this.redraw();
    }

    /**
     * If the table is empty, only a double can be suggested.
     * If the table is not empty, see if one domino has a number that matches one of the 
     *    numbers of the last domino.
     */
    public void suggestDomino(){
        /*# YOUR CODE HERE */
        int nullnum = 0;
        try{
            Domino d = table.get(table.size()-1);
            for(int n=0;n<hand.length;n++){
                if(this.hand[n]==null){
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    nullnum += 1;
                }else if(this.hand[n].getTop()==d.getTop()){
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("Use domino "+(n+1));
                    break;
                }else if(this.hand[n].getBottom()==d.getBottom()){
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("Use domino "+(n+1));
                    break;
                }else if(this.hand[n].getTop()==d.getBottom()){
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("Flip domino "+(n+1)+" and use it");
                    break;
                }else if(this.hand[n].getBottom()==d.getTop()){
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("Flip domino "+(n+1)+" and use it");
                    break;
                }else{
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("There are no possible moves");
                }
            }
            if(nullnum==NUM_HAND){
                UI.println("The hand is empty"); 
            }
        }catch(IndexOutOfBoundsException e){
            for(int n=0;n<hand.length;n++){   
                if(this.hand[n]==null){
                    nullnum += 1;
                }else if(this.hand[n].getTop()==this.hand[n].getBottom()){
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("Use domino "+(n+1));
                    break;
                }else{
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    UI.println("There are no possible moves");
                }
            }
            if(nullnum==NUM_HAND){
                    UI.println("The hand is empty"); 
                }
        }
    }
    
    public Boolean checkLegal(){
        try{
            Domino d = table.get(table.size()-1);
            if(this.hand[selectedPos].getTop()==d.getTop()){
                return true;
            }else if(this.hand[selectedPos].getBottom()==d.getBottom()){
                return true;
            }else if(this.hand[selectedPos].getTop()==d.getBottom()){
                return true;
            }else if(this.hand[selectedPos].getBottom()==d.getTop()){
                return true;
            }else{
                return false;
            }
        }catch(IndexOutOfBoundsException e){  
            if(this.hand[selectedPos].getTop()==this.hand[selectedPos].getBottom()){
                return true;
            }else{
                return false;
            }
        }
    }

    /** ---------- The code below is already written for you ---------- **/
    
    /** Allows the user to select a position in the hand using the mouse.
     * If the mouse is released over the hand, then sets  selectedPos
     * to be the index into the hand array.
     * Redraws the hand and table */
    public void doMouse(String action, double x, double y){
        if (action.equals("dragged")){
            this.dragedX = x;
            this.dragedY = y;
            this.draging = true;
            this.redraw();
        }
        if (action.equals("pressed")){
                if (y >= HAND_TOP && y <= HAND_TOP+DOMINO_HEIGHT && 
                x >= HAND_LEFT && x <= HAND_LEFT + NUM_HAND*DOMINO_SPACING) {
                    this.selectedPos = (int) ((x-HAND_LEFT)/DOMINO_SPACING);
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    this.redraw();
                }
            }
        if (action.equals("released")){
            if (y >= HAND_TOP && y <= HAND_TOP+DOMINO_HEIGHT && 
            x >= HAND_LEFT && x <= HAND_LEFT + NUM_HAND*DOMINO_SPACING && draging==true) {
                this.draging = false;
                Domino D = this.hand[selectedPos];
                this.hand[selectedPos] = null;
                int lastPos = this.selectedPos;
                this.selectedPos = (int) ((x-HAND_LEFT)/DOMINO_SPACING);
                if(this.hand[selectedPos]!=null && selectedPos>lastPos){
                    int pos = selectedPos-1;
                    int n = 1;
                    for(int i=selectedPos;i<NUM_HAND;i++){
                        Domino rightD = this.hand[pos+n];
                        Domino d = this.hand[pos];
                        this.hand[pos+n] = d;
                        this.hand[pos] = rightD;
                        if(selectedPos+n==NUM_HAND){
                            pos = 0;
                            n = 0;
                        }else{
                            n += 1;
                        }
                    }
                    this.hand[selectedPos] = D;
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    this.redraw();
                }else if(this.hand[selectedPos]!=null && selectedPos<lastPos){
                    int pos = selectedPos;
                    int n = 1;
                    for(int i=0;i<NUM_HAND;i++){
                        if(pos==0){
                            pos = NUM_HAND-1;
                            n = 0;
                        }
                        Domino rightD = this.hand[pos-n];
                        Domino d = this.hand[pos];
                        this.hand[pos-n] = d;
                        this.hand[pos] = rightD;
                        if(pos-n==0){
                            pos = NUM_HAND-1;
                            n = 0;
                        }else{
                            n += 1;
                        }
                    }
                    this.hand[selectedPos] = D;
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    this.redraw();
                }else if(this.hand[selectedPos]==null){
                    this.hand[selectedPos] = D;
                    UI.clearText();
                    UI.println("selected "+(this.selectedPos+1));
                    this.redraw();
                }
            }
        }
    }

    /**
     *  Redraw the table and the hand.
     *  To work with the code above, this needs to use the constants:
     *   - DOMINO_SPACING, HAND_HEIGHT, HAND_LEFT, HAND_TOP, TABLE_LEFT, TABLE_TOP
     *   See the descriptions where these fields are defined.
     *  Needs to clear the graphics pane,
     *  then draw the hand with all its dominos, 
     *  then outline the selected position on the hand
     *  then draw the rows of dominos on the table.
     */
    public void redraw(){
        UI.clearGraphics();
        this.drawHand();
        this.drawTable();
    }


    public static void main(String[] args){
        DominoGame obj = new DominoGame();
    }   

}

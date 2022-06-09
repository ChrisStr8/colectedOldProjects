// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment
 * Name:christopher straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/**
 *  The bag is represented by a list of tiles.
 *  The bag should be initialised to have the standard distribution of tiles:
 *
 *  2 blank tiles                                      0 points
 *  12xE, 9xA, 9xI, 8xO, 6xN, 6xR, 6xT, 4xL, 4xS, 4xU  1 point 
 *  4xD, 3xG                                           2 points
 *  2xB, 2xC, 2xM, 2xP                                 3 points
 *  2xF, 2xH, 2xV, 2xW, 2xY                            4 points
 *  1xK                                                5 points
 *  1xJ, 1xX                                           8 points
 *  1xQ, 1xZ                                           10 points
 */

public class Bag{
    /*# YOUR CODE HERE */
    public ArrayList tiles = new ArrayList();

    public Bag(){
        /*# YOUR CODE HERE */
        
    }

    /** Reset the bag to contain all 100 tiles */
    public void reset(){
        tiles = new ArrayList<Tile>();
        addTiles(2, "blank", 0);
        addTiles(12, "E", 1);
        addTiles(9, "A", 1);
        addTiles(9, "I", 1);
        addTiles(8, "O", 1);
        addTiles(6, "N", 1);
        addTiles(6, "R", 1);
        addTiles(6, "T", 1);
        addTiles(4, "L", 1);
        addTiles(4, "S", 1);
        addTiles(4, "U", 1);
        addTiles(4, "D", 2);
        addTiles(3, "G", 2);
        addTiles(2, "B", 3);
        addTiles(2, "C", 3);
        addTiles(2, "M", 3);
        addTiles(2, "P", 3);
        addTiles(2, "F", 4);
        addTiles(2, "H", 4);
        addTiles(2, "V", 4);
        addTiles(2, "W", 4);
        addTiles(2, "Y", 4);
        addTiles(1, "K", 5);
        addTiles(1, "J", 8);
        addTiles(1, "X", 8);
        addTiles(1, "Q", 10);
        addTiles(1, "Z", 10);
    }

    /** Add the specified number of tiles of the given name and score to the bag */
    private void addTiles(int count, String name, int score){
        /*# YOUR CODE HERE */

    }

    /** Is the bag empty yet?    */
    public boolean isEmpty(){
        /*# YOUR CODE HERE */
        
        if (tiles.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /** Remove and return a random tile from the bag, unless it is empty  */
    public Tile pick(){
        /*# YOUR CODE HERE */

    }

}

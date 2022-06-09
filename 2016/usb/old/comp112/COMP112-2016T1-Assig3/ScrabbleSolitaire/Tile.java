// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment
 * Name:christopher straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/**
 *  Tile
 *  Represents a single tile.
 *  Needs at least the name of the image file for drawing itself
 *  and the value of the tile.  It could store the letter on the tile also,
 *  though this is not used in this version of the game.
 *
 * Needs a
 *  - constructor
 *  - draw method, to draw the tile at a position x,y
 *  - method to return the value of the tile.
 */

public class Tile{
    public static final int Size = 40;             // width and height of the tiles

    private String letter;
    private int value;
    private String image;

    /** Construct a new Tile object */
    public Tile(String name, int val){
        letter = name;
        image = "tiles/"+name+".jpg";
        value = val;
    }

    /**
     * Return the value of the tile
     */
    public int value(){
        return value;
    }

    /**
     * Draw the tile
     */
    public void draw(double x, double y){
        UI.drawImage(image, x, y);
    }

    /* equals method:  tiles are equal if they have the same letter */
    public boolean equals(Object other){
        if (this==other) { return true; }
        if (! (other instanceof Tile) ) { return false; }
        Tile t = (Tile) other;
        return (this.letter.equals(t.letter));
    }

}

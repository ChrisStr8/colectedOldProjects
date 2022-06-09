import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/* Name: Chrstopher Straight
 * Usercode:straigchri
 * ID:300363269
 */ 

/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item{
    
    private String name;
    private double weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, double itemWeight){
        // initialise instance variables
        name = itemName;
        weight = itemWeight;
    }
    
    /**
     * returns the weight of the item
     */
    public double getWeight(){
        return weight;
    }
    
    public String getName(){
        return name;
    }
}

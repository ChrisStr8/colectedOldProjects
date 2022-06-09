import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/* Name: Chrstopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

/**
 * Write a description of class Trap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trap{
    private String name;
    private boolean isActive;
    private ArrayList<Item> itemsForTrap = new ArrayList<>();

    /**
     * Constructor for objects of class Trap
     */
    public Trap(String trapName, ArrayList<Item> itemsForTrap){
        // initialise instance variables
        name = trapName;
        itemsForTrap = itemsForTrap;
        isActive = true;
    }

    /**
     *
     */
    public boolean disable(ArrayList<Item> pack){
        if (itemsForTrap.equals(pack)){
            isActive = false;
            return true;
        }
        return false;
    }
    
    /**
     *
     */
    public boolean isActive(){
        return isActive;
    }
    
    /**
     *
     */
    public String getName(){
        return name;
    }
    
    public void getItems(){
        UI.println("The items required for the trap are:");
        for (Item item : itemsForTrap){
            UI.println(item.getName());
        }
    }
}

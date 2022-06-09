import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/* Name: Chrstopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int playerHealth;
    private ArrayList<Item> pack = new ArrayList<>();
    private double packWeight = 0;
    private boolean hasDataCache = false;

    /**
     * Constructor for objects of class Player
     */
    public Player(){
        // initialise instance variables
        playerHealth = 100;
    }

    /**
     * 
     */
    public void damagePlayer(int damage){
        playerHealth-=damage;
    }
    
    /**
     * 
     */
    public void healPlayer(int healing){
        playerHealth+=healing;
    }
    
    /**
     * 
     */
    public int getHealth(){
        return playerHealth;
    }
    
    /**
     * 
     */
    public double packWeight(){
        return packWeight;
    }
    
    /**
     * 
     */
    public void listItems(){
        UI.println("you are carying "+ packWeight+"kg");
        for (Item item : pack){
            UI.println(item.getName()+" "+item.getWeight());
        }
    }
    
    /**
     * 
     */
    public void addItem(Item item){
        packWeight += item.getWeight();
        pack.add(item);
    }
    
    /**
     * 
     */
    public void removeItem(Item item){
        pack.remove(item);
    }
    
    public boolean hasTorch(){
        for (Item item : pack){
            if (item.getName().equals("torch")){
                return true;
            }
        }
        return false;
    }
    
    public void getCache(){
        hasDataCache = true;
    }
    
    public boolean hasDataCache(){
        return hasDataCache;
    }
    
    public ArrayList<Item> getPack(){
        return pack;
    }
}

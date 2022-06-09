import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/* Name: Chrstopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

/**
 * Write a description of class Pod here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pod
{
    // instance variables - replace the example below with your own
    public String name;
    private boolean hasRecoveryKit;
    private boolean recoveryKitUsed;
    private boolean hasDataCache;
    private Pod portalA;
    private Pod portalB;
    private Pod portalC;
    private Trap trap;
    private Item item;

    /**
     * Constructor for objects of class Pod
     */
    public Pod(String podName, boolean hasRecoveryKit, boolean hasDataCache){
        // initialise instance variables
        name = podName;
        hasRecoveryKit = hasRecoveryKit;
        if (hasRecoveryKit){recoveryKitUsed=false;}
        hasDataCache = hasDataCache;
    }

    /**
     * links a portal to the given pod
     */
    public void addPortalTo(Pod pod){
        if (portalA==null){portalA = pod;}
        else if (portalB==null){portalB = pod;}
        else if (portalC==null){portalC = pod;}
    }
    
    /**
     * 
     */
    public Trap getTrap(){
        return trap;
    }
    
    /**
     * 
     */
    public void setTrap(Trap newTrap){
        trap = newTrap;
    }
    
    /**
     * 
     */
    public void addItem(Item newItem){
        item = newItem;
    }
    
    public Item getItem(){
        return item;
    }
    
    public Item removeItem(){
        Item i = item;
        item = null;
        return i;
    }
    
    /**
     * 
     */
    public String getDescription(){
        String descrtpition = ("Pod Name : "+name+" \n");
        if (trap!=null){
            descrtpition+=("Trap: "+trap.getName()+"\n"+"the trap is ");
            if (trap.isActive()){descrtpition+="active";}
            else {descrtpition+="disabled";}
        }
        return descrtpition;
    }
    
    /**
     * 
     */
    public Pod goPortal(int num){
        if (num==0){return portalA;}
        if (num==1){return portalB;}
        if (num==2){return portalC;}
        return null;
    }
    
    /**
     * 
     */
    public void resetRecoveryKit(){
        recoveryKitUsed=false;
    }
    
    /**
     * 
     */
    public boolean hasDataCache(){
        return hasDataCache;
    }
    
    public void removeCache(){
        hasDataCache=false;
    }
    
    /**
     * 
     */
    public boolean hasRecoveryKit(){
        return hasRecoveryKit;
    }
    
    /**
     * 
     */
    public boolean recoveryKitUsed(){
        return recoveryKitUsed;
    }
    
    public void useRecoveryKit(){
        recoveryKitUsed = true;
    }
}

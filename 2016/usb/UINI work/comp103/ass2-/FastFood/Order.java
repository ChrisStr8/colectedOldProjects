// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 Assignment 2
 * Name: Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ecs100.UI;

/** 
 * This class is provided as a bad example.
 * Don't do this at home!
 */
public class Order {

    /** the items that are wanted for the order */
    private boolean wantsFish;
    private boolean wantsChips;
    private boolean wantsBurger;

    /** the items that have been added and are ready in the order */
    private boolean hasFish;
    private boolean hasChips;
    private boolean hasBurger;
    
    private int numFish=1;
    private int numChips=1;
    private int numBurger=1;
    
    private int numFishLeft=1;
    private int numChipsLeft=1;
    private int numBurgerLeft=1;

    public Order() {
        wantsFish = Math.random() > 0.5;
        wantsChips = Math.random() > 0.5;
        wantsBurger = Math.random() > 0.5;

        if (!wantsFish && !wantsChips && !wantsBurger) {
            int choice = (int)(Math.random() * 3);
            if (choice==0){
                wantsFish = true;
                int num = (int)(Math.random() * 10);
                this.numFish += num;
                this.numFishLeft += num;
            } else if (choice==1){
                wantsChips = true;
                int num = (int)(Math.random() * 10);
                this.numChips += num;
                this.numChipsLeft += num;
            } else if (choice==2) {
                wantsBurger = true;
                int num = (int)(Math.random() * 10);
                this.numBurger += num;
                this.numBurgerLeft += num;
            }
        }
    }

    /** 
     *  The order is ready as long as every item that is
     *  wanted is also ready.
     */
    public boolean isReady() {
        if (wantsFish && !hasFish) return false;
        if (wantsChips && !hasChips) return false;
        if (wantsBurger && !hasBurger) return false;
        return true;
    }

    /** 
     *  If the item is wanted but not already in the order,
     *  then put it in the order and return true, to say it was successful.
     *  If the item not wanted, or is already in the order,
     *  then return false to say it failed.
     */
    public boolean addItemToOrder(String item){
        if (item.equals("Fish")) {
            if (wantsFish && !hasFish) {
                this.numFishLeft -= 1;
                if (this.numFishLeft==0){
                    hasFish = true;
                }
                return true;
            }
        }
        else if (item.equals("Chips")){
            if (wantsChips && !hasChips) {
                this.numChipsLeft -=1;
                if (this.numChipsLeft==0){
                    hasChips = true;
                }
                return true;
            }
        }
        else if (item.equals("Burger")){
            if (wantsBurger && !hasBurger) {
                this.numBurgerLeft -= 1;
                if (this.numBurgerLeft==0){
                    hasBurger = true;
                }
                return true;
            }
        }
        return false;
    }

    /** 
     *  Computes and returns the price of an order.
     *  [CORE]: Uses constants: 2.50 for fish, 1.50 for chips, 5.00 for burger
     *  to add up the prices of each item
     *  [COMPLETION]: Uses a map of prices to look up prices
     */
    public double getPrice() {
        double price = 0;
        if (wantsFish) price += (double)(FastFood.prices.get("Fish"))*this.numFish;
        if (wantsChips) price += (double)(FastFood.prices.get("Chips"))*this.numChips;
        if (wantsBurger) price += (double)(FastFood.prices.get("Burger"))*this.numBurger;
        return price;
    }

    public void draw(int y) {
        if (wantsFish) {
            UI.drawImage("Fish-grey.png", 10, y);
            UI.drawString(""+numFishLeft,10, y+40);
        }
        if (wantsChips){
            UI.drawImage("Chips-grey.png", 50, y);
            UI.drawString(""+numChipsLeft,50, y+40);
        }
        if (wantsBurger) {
            UI.drawImage("Burger-grey.png", 90, y);
            UI.drawString(""+numBurgerLeft,90,y+40);
        }

        if (hasFish) UI.drawImage("Fish.png", 10, y);
        if (hasChips) UI.drawImage("Chips.png", 50, y);
        if (hasBurger) UI.drawImage("Burger.png", 90, y);
    }
}

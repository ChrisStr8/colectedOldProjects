// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 6
 * Name: pondy
 */

import ecs100.*;
import java.awt.Color;

/**
 *  This program contains methods for testing FlatAccount objects.
 *  It is all written for you, but you need to read it to see what the
 *  FlatAccount class should do
 */

public class FlatAccountTester{

    /** Makes two FlatAccount objects, and lets user record expenses and contributions */
    public void testFlatAccount(){
        UI.clearText();
        UI.println("Creates accounts for two flats");
        UI.println("Asks user for transactions on random flats:");
        UI.println("either type of expense, and amount");
        UI.println("or an amount to contribute to account");
        UI.println("Prints balance after each transaction.");
        UI.println();
        FlatAccount us = new FlatAccount("161 Kelburn Pde");
        FlatAccount them = new FlatAccount("72 Salamanca Rd");
        us.printBalances();
        them.printBalances();

        int tests = 0;
        while (tests<=6 & UI.askBoolean("Do more? (y/n): ") ){
            // choose a flat:
            FlatAccount flat;
            if (Math.random()<0.5) { flat = us; }
            else { flat = them;}

            //  expense or contribute
            if (Math.random() < 0.5){
                String category = UI.askString("Type of expense for " + flat.getName() + " ('rent' or 'food')");
                double amt = UI.askDouble("Amount:");
                flat.expense(category, amt);
            }
            else {
                double amt = UI.askDouble("Amount to contribute to " + flat.getName() + ":");
                flat.contribute(amt);
            }
            UI.println("-------------------------");
            flat.printBalances();
            tests++;
        }            
    }   

    /** Constructor to set up the user interface */
    public FlatAccountTester(){
        UI.addButton("Test Flat Account", this::testFlatAccount);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);
    }

    /** Calls the constructor */
    public static void main(String[] args){
        FlatAccountTester obj = new FlatAccountTester();
    }
}

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Example Code for COMP112
 */

import ecs100.*;
import java.util.Scanner;
import java.io.*;

/**
 * Two example methods that read and write data from files.
 */

public class Example{

    /**
     * This method prints out each token in a file, one per line
     * But highlights integers by putting **  **  around them
     */
    public void readFile(String fileName){
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()){
                if (scan.hasNextInt()){
                    int num = scan.nextInt();
                    UI.println("** " + num + " **");
                }
                else {   
                    String token = scan.next();
                    UI.println(token);
                }
            }
            scan.close();
        } catch(IOException e){UI.println("File reading failed" + e);}
    }

    /**
     * This method creates a file containing a list of 100 random numbers
     */
    public void makeFile(String fileName){
        try {
            PrintStream out = new PrintStream(new File(fileName));
            for (int i=0; i<100; i++){
                int num = (int)(Math.random()* 10000);
                out.println(num);
            }
            out.close();
        } catch(IOException e){UI.println("File reading failed");}
        UI.println("Written numbers to "+ fileName);
    }

    /** For testing */
    public static void main(String[] args){
        Example ex = new Example();
        ex.readFile(UIFileChooser.open("Choose a file to read"));
        ex.makeFile(UIFileChooser.open("Specify file to write random numbers to"));
    }        
}

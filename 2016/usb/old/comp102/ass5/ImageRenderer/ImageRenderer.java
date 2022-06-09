// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 5
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** Renders plain ppm images onto the graphics panel
 *  ppm images are the simplest possible colour image format.
 */

public class ImageRenderer{
    public static final int top = 20;   // top edge of the image
    public static final int left = 20;  // left edge of the image
    public static final int pixelSize = 2;  

    /** Core:
     * Renders a ppm image file.
     * Asks for the name of the file, then calls renderImageHelper.
     */
    public void renderImageCore(){
        /*# YOUR CODE HERE */
        String fname = UIFileChooser.open();
        if (fname != null){
                try {
                Scanner sc = new Scanner(new File(fname));
                String token = sc.nextLine();
                this.renderImageHelper(sc);
            }catch(IOException e){UI.println("File reading failed" + e);}
        }
    }

    /** Core:
     * Renders a ppm image file.
     * Renders the image at position (left, top).
     * Each pixel of the image  is rendered by a square of size pixelSize
     * Assumes that
     * - the colour depth is 255,
     * - there is just one image in the file (not "animated"), and
     * - there are no comments in the file.
     * The first four tokens are "P3", number of columns, number of rows, 255
     * The remaining tokens are the pixel values (red, green, blue for each pixel)
     */
    public void renderImageHelper(Scanner sc){
        /*# YOUR CODE HERE */
        UI.clearText();
        UI.clearGraphics();
        //this.checkComment(sc);
        int colnum = Integer.parseInt(sc.next());
        UI.println("colums: "+colnum);
        //this.checkComment(sc);
        int rownum = Integer.parseInt(sc.next());
        UI.println("rows: "+rownum);
        //this.checkComment(sc);
        int sacle = Integer.parseInt(sc.next());
        //this.checkComment(sc);
        int pixelTop = top;
        while(sc.hasNextInt()){
            for (int row = 0; row < rownum; row++){
                pixelTop += pixelSize;
                int pixelLeft = left;
                for (int col = 0; col < colnum; col++){ 
                    //this.checkComment(sc);
                    int r = Integer.parseInt(sc.next());
                    //this.checkComment(sc);
                    int g = Integer.parseInt(sc.next());
                    //this.checkComment(sc);
                    int b = Integer.parseInt(sc.next());
                    UI.setColor(new Color(r,g,b));
                    UI.fillRect(pixelLeft, pixelTop, pixelSize, pixelSize); 
                    pixelLeft+= pixelSize;
                }
            }
        }
    }

    /** Completion
     * Renders a ppm image file which may be animated (multiple images in the file)
     * Asks for the name of the file, then renders the image at position (left, top).
     * Each pixel of the image  is rendered by a square of size pixelSize
     * Renders each image in the file in turn with 200 mSec delay.
     * Repeats the sequence 3 times.
     */
    public void renderAnimatedImage(){
        /*# YOUR CODE HERE */
        String fname = UIFileChooser.open();
        if (fname != null){
                try {
                Scanner sc = new Scanner(new File(fname));
                while(sc.hasNext()){
                    String token = sc.nextLine();
                    switch(token){
                        case "P3":
                        this.renderImageHelper(sc);
                        try {
                            Thread.sleep(200);
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        break;
                    }
                }
            }catch(IOException e){UI.println("File reading failed" + e);}
        }
    }
    
    public void checkComment(Scanner sc){
        try{
             if(sc.hasNext("#.*")){
                String comment = sc.nextLine();
                UI.println(comment);
            }
        }catch(NumberFormatException e){UI.println("File reading failed" + e);}
    }


    /** ---------- The code below is already written for you ---------- **/

    // Constructor
    public ImageRenderer() {
        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Render (core)", this::renderImageCore );
        UI.addButton("Render (compl)", this::renderAnimatedImage );
        UI.addButton("Quit", UI::quit );
        UI.setWindowSize(850, 700);
        UI.setDivider(0.0);
    }


}

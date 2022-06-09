// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 4
 * Name:Christopher Straight
 * Usercode:srtaigchri
 * ID:300363269
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** Renders pnm images (pbm, pgm, or ppm) onto the graphics panel
ppm images are the simplest possible colour image format.
 */

public class ImageRenderer{
    public static final int top = 20;   // top edge of the image
    public static final int left = 20;  // left edge of the image
    public static final int pixelSize = 2;  
    public static final String pbm = "P1";
    public static final String pgm = "P2";
    public static final String ppm = "P3";

    public ImageRenderer(){
        UI.initialise();
        UI.addButton("Render", this::renderAFile);
        UI.addButton("Quit", UI::quit);
    }

    public void renderAFile(){
        UI.clearText();
        UI.clearGraphics();
        String fname = UIFileChooser.open("Image file to render");
        if (fname != null){
            this.renderImage(fname);
        }
    }

    /**
     * Renders a pnm image file with the given name.
     * Renders the image at position (left, top).
     * Each pixel of the image is rendered by a square of size pixelSize
     * The first three tokens (other than comments) are
     *    the magic number (P1, P2, or P3),
     *    number of columns, (integer)
     *    number of rows,  (integer)
     * ppm and pgm files then have 
     *    colour depth  (integer: range of possible color values)
     * The remaining tokens are the pixel values
     *  (0 or 1 for pbm, single integer for pgm; red, green, and blue integers for ppm)
     * There may be comments anywhere in the file, which start with # and go to the end of the line. Comments should be ignored.
     * The image may be "animated", in which case the file contains a sequence of images
     * (ideally, but not necessarily, the same type and size), which should be rendered
     * in sequence.
     * This method should read the magic number then call the appropriate method for rendering the rest of the image
     */                                
    public void renderImage(String fname){
        /*# YOUR CODE HERE */
        try {
            Scanner scan = new Scanner(new File(fname));
            String token = scan.nextLine();
            UI.println(token);
            switch(token){
                case pbm:
                UI.println("pbm");
                this.renderpbm(fname, scan);
                break;
                case pgm:
                UI.println("pgm");
                this.renderpgm(fname, scan);
                break;
                case ppm:
                UI.println("ppm");
                this.renderppm(fname, scan);
                break;
            }
        }catch(IOException e){UI.println("File reading failed" + e);}
    }

    public void renderpbm(String fname, Scanner scan){
        UI.setColor(Color.black);
        int colnum = Integer.parseInt(scan.next());
        int rownum = Integer.parseInt(scan.next());
        int row = 0;
        int col = 0;
        UI.println("colnum: "+colnum);
        UI.println("rownum: "+rownum);
        int pixelTop = top;
        while(scan.hasNext()){
            try{
                for(row = 0; row < rownum; row++){
                    
                    pixelTop += pixelSize;
                    int pixelLeft = left;
                    for(col = 0; col < colnum; col++){
                        String colour = scan.next();
                        UI.println(colour);
                        switch(colour){
                            case "1":
                            UI.setColor(Color.black);
                            UI.fillRect(pixelLeft, pixelTop, pixelSize, pixelSize); 
                            pixelLeft+= pixelSize;
                            break;
                            case "0":
                            pixelTop += pixelSize;
                            break;
                        }
                    }
                }
            }catch(NoSuchElementException e){UI.println("File reading ended" + e);}
        }
    }

    public void renderpgm(String fname, Scanner scan){
        UI.setColor(Color.black);
        int colnum = Integer.parseInt(scan.next());
        int rownum = Integer.parseInt(scan.next());
        int row = 0;
        int col = 0;
        UI.println("colnum: "+colnum);
        UI.println("rownum: "+rownum);
        int pixelTop = top;
        while(scan.hasNext()){
            try{
                for(row = 0; row < rownum; row++){
                    int pixelLeft = left;
                    for(col = 0; col < colnum; col++){
                        String colour = scan.next();
                        UI.println(colour);
                        UI.fillRect(pixelLeft, pixelTop, pixelSize, pixelSize); 
                        pixelLeft+= pixelSize;
                        pixelTop += pixelSize;
                        }
                    pixelTop += pixelSize;
                    }
            }catch(NoSuchElementException e){UI.println("File reading ended" + e);}
        }
    }

    public void renderppm(String fname, Scanner scan){
        int colnum = Integer.parseInt(scan.next());
        int rownum = Integer.parseInt(scan.next());
        int cScale = Integer.parseInt(scan.next());
        int row = 0;
        int col = 0;
        UI.println("colnum: "+colnum);
        UI.println("rownum: "+rownum);
        int pixelTop = top;
        while(scan.hasNext()){
            try{
                for(row = 0; row < rownum; row++){
                    pixelTop += pixelSize;
                    int pixelLeft = left;
                    for(col = 0; col < colnum; col++){
                        String colour = scan.next();
                        UI.println(colour);
                        //UI.setColor(Color(int r, int g, int b, int a));
                        UI.fillRect(pixelLeft, pixelTop, pixelSize, pixelSize); 
                        UI.println("works");
                        pixelLeft+= pixelSize;
                        }
                    }
                }catch(NoSuchElementException e){UI.println("File reading ended" + e);}
        }
    }    
   
    public static void main(String[] args){
        ImageRenderer im = new ImageRenderer();
        im.renderImage("image-bee.ppm");   // this is useful for testing.
    }
} 




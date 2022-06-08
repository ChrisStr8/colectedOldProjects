 // This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 6
 * Name:Christopher Straight
 * Usercode:strachri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

public class MiniPaint{

    // fields to remember:
    //  - the shape that will be drawn when the mouse is next released.
    //  - whether the shape should be filled or not
    //  - the position the mouse was pressed, 
    //  - the name of the image file
    /*# YOUR CODE HERE */
    public String shape;
    public String iname;
    public String painta;
    public boolean filled = true;
    public double mousex;
    public double mousey;
    public double width;
    public double height;
    public double rx;
    public double ry;
    

    //Constructor
    /** Sets up the user interface - mouselistener and buttons */
    public MiniPaint(){
        /*# YOUR CODE HERE */
        UI.setMouseListener(this::doMouse);
        UI.addButton("Quit", UI::quit);
        UI.addButton("Line", this::doLine);
        UI.addButton("Rect", this::doRect);
        UI.addButton("Oval", this::doOval);
        UI.addButton("Image", this::doImage);
        UI.addButton("Eraser", this::doEraser);
        UI.addButton("Colour", this::doColour);
        UI.addButton("Fil/NoFill", this::doFill);
        UI.addButton("Clear", UI::clearGraphics);
        UI.println("Filling");
        UI.fillRect(0, 0, 10, 10);
    }


    /**
     * Respond to mouse events
     * When pressed, remember the position.
     * When released, draw the current shape using the pressed position
     *  and the released position.
     * Uses the value in the field to determine which kind of shape to draw.
     * Although you could do all the drawing in this method,
     *  it may be better to call some helper methods for the more
     *  complex actions (and then define the helper methods!)
     */
    public void doMouse(String action, double x, double y) {
        /*# YOUR CODE HERE */
        try{
            if (action.equals("pressed")){
                this.mousex = -1;
                this.mousey = -1;
                if (painta.equals("line")){
                    this.drawALine(x, y);
                }else {
                    this.drawAShape(x, y);
                }
            }else if (action.equals("released")){
                if (painta.equals("line")){
                    this.drawALine(x, y);
                }else {
                    this.drawAShape(x, y);
                }
            }
        }catch(NullPointerException e){UI.println("Select an action");}
        }
        
        
    public void doLine(){
        this.painta = "line";
    }
    
    public void doRect(){
        this.painta = "rect";
    }
    
    public void doOval(){
        this.painta = "oval";
    }
    
    public void doImage(){
        this.iname = UIFileChooser.open();
        this.painta = "image";
    }
    
    public void doEraser(){
        this.painta = "eraser";
    }
    
    public void doColour(){
        Color fillc = JColorChooser.showDialog(null, "Fill Colour", Color.black);
        UI.setColor(fillc);
        UI.fillRect(0, 0, 10, 10);
    }
    
    public void doFill(){
        if (filled == true){
            filled = false;
            UI.println("No Fill");
        } else if (filled == false){
            filled = true;
            UI.println("Filling");
        }
    }
    
    public void drawAShape(double x, double y){
        // draws a Rectangle between the mouse pressed and mouse released points.
        this.width = 0;
        this.height = 0;
        this.rx = x;
        this.ry = y;
        if (mousex == -1 && mousey == -1){
            this.mousex = x;
            this.mousey = y;
        }else{
            if (mousex < x){
                this.width = x - mousex;
                rx = mousex;
            }else if(mousex > x){
                this.width = mousex - x;
            }
            if (mousey < y){
                this.height = y - mousey;
                ry = mousey;
            }else if(mousey > y){
                this.height = mousey - y;
            }
            
            if(painta.equals("rect")){
                this.drawrect(rx, ry, width, height);
            }else if(painta.equals("oval")){
                this.drawoval(rx, ry, width, height);
            }else if(painta.equals("image")){
                this.drawAnImage(rx, ry, width, height);
            } else if(painta.equals("eraser")){
                this.erase(rx, ry, width, height);
            }
            
            this.mousex = -1;
            this.mousey = -1;
        }
    }

    public void drawALine(double x, double y){
        if (mousex == -1 && mousey == -1){
            this.mousex = x;
            this.mousey = y;
        }else{
            UI.drawLine(mousex, mousey, x, y);
            this.mousex = -1;
            this.mousey = -1;
        }
    }
    
    public void drawAnImage(double x, double y, double width, double height){
        // draws an image at the mouse released point.
        UI.drawImage(iname, x, y, width, height);
    }
     
    public void drawrect(double x, double y, double width, double height){
        if (filled == true){
            UI.fillRect(x, y, width, height);
        } else if (filled == false){
            UI.drawRect(x, y, width, height);
        }
    }
    
    public void drawoval(double x, double y, double width, double height){
        if (filled == true){
            UI.fillOval(x, y, width, height);
        } else if (filled == false){
            UI.drawOval(x, y, width, height);
        }
    }
    
    public void erase(double x, double y, double width, double height){
        UI.eraseRect(x, y, width, height);
    }
    /*# YOUR CODE HERE */

    // Main:  constructs a new MiniPaint object
    public static void main(String[] arguments){
        new MiniPaint();
    }        

}

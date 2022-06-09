/* Code for COMP 103 Assignment 1
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;

public class Spiral
{
    // instance variables - replace the example below with your own
    private int[][] image;
    private static int cellSize = 40;
    public static int size = 10;
    public int h;
    public int w;
    public boolean line;

    /**
     * Constructor for objects of class Spiral
     */
    public Spiral(){
        // initialise instance variables
        UI.initialise();
        UI.addButton("Line", this::line);
        UI.addButton("Square", this::square);       
        UI.addButton("Spiral", this::spiral);
        UI.addButton("Clear", this::clear);
        UI.addButton("Quit", UI::quit ); 
        UI.setFontSize(10);
    }
    
    public void line(){
        this.line = true;
        this.image = null;
        this.image = new int[1][size];
        this.h = 1;
        this.w = size;
        int row = 0;
        for (int col=0; col < size; col++){
            this.image[row][col] = col;
        }
        this.display();
    }
    
    public void square(){
        this.line = false;
        this.image = null;
        this.image = new int[size][size];
        this.h = size;
        this.w = size;
        int num = 0;
        for (int row=0; row < size; row++){
            for (int col=0; col < size; col++){
                this.image[row][col] = num;
                num += 1; 
            }
        }
        this.display();
    }
    
    public void spiral(){
        this.line = false;
        this.image = null;
        this.image = new int[size][size];
        this.h = size;
        this.w = size;
        int num = 0;
        int row = 0;
        int col = 0;
        int loop = 0;
        for (int length=size; length > -1; length-=1){
            for (col=col; col < length; col++){
                this.image[row][col] = num;
                num += 1; 
            }
            col-=1;
            for (row+=1; row < length; row++){
                this.image[row][col] = num;
                num += 1;
            }
            row-=1;
            for (col-=1; col > -1+loop; col-=1){
                this.image[row][col] = num;
                num += 1; 
            }
            col+=1;
            for (row-=1; row > 1+loop; row-=1){
                this.image[row][col] = num;
                num += 1; 
            }
            loop += 1;
        }
        this.display();
    }
   
    public void clear(){
        UI.clearGraphics();
    }
    
    public void display(){
        for(int row=0; row< h; row++){
            int y = row * this.cellSize;
            for(int col=0; col< w; col++){
                //UI.println(image[row][col]);
                int x = col * this.cellSize;
                if (line == true){
                    UI.setColor(new Color(25*this.image[row][col])); 
                }else{ 
                    UI.setColor(new Color(2*this.image[row][col])); 
                }
                UI.fillRect(x, y, this.cellSize, this.cellSize);
                UI.setColor(Color.white);
                UI.drawString(String.valueOf(this.image[row][col]+1), x+15, y+20);
            }
        }
    }
}

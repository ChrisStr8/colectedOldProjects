// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 4, 
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;

/** PatternsDrawer
Draw various patterns. */

public class PatternsDrawer{

    public static final double boardLeft = 50;   // Top left corner of the board
    public static final double boardTop = 50;
    public static final double boardSize = 300;  // The size of the board on the window

    /** Draw a square grid board with white squares.
     *  Asks the user for the number of squares on each side
     *
     * CORE
     */
    public void drawGridBoard(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        /*# YOUR CODE HERE */
        int bsize = (int) boardSize;
        int cellSize = bsize/num;
        
        double y = boardTop;
        for (int row = 0; row < num; row++){
            double x = boardLeft;
            for (int col = 0; col < num; col++){                
                UI.drawRect(x, y, cellSize, cellSize);
                x += cellSize;
            }
            y += cellSize;
        }
    }

    /** Illusion
     * a pattern that makes dark circles appear in the intersections
     * when you look at it. 
     **/
    public void drawIllusion(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        /*# YOUR CODE HERE */
        int bsize = (int) boardSize;
        int cellSize = bsize/num;
        
        int colnum = num;
        double y = boardTop;
        for (int row = 0; row < num; row++){
            double x = boardLeft;
            for (int col = 0; col < colnum; col++){                
                UI.fillRect(x, y, cellSize, cellSize);
                x += cellSize;
                x += cellSize/4;
            }
            y += cellSize;
            y += cellSize/4;
            colnum -= 1;
        }
    }

    /** Draw a checkered board with alternating black and white squares
     *    Asks the user for the number of squares on each side
     *
     * COMPLETION
     */
    public void drawCheckersBoard(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        /*# YOUR CODE HERE */
        int bsize = (int) boardSize;
        int cellSize = bsize/num;
        boolean coloured = false;
        
        double y = boardTop;
        for (int row = 0; row < num; row++){
            double x = boardLeft;
            if (coloured == false){
                    coloured = true;
                }else if(coloured == true){
                    coloured = false;
                }
            for (int col = 0; col < num; col++){  
                if (coloured == false){
                    UI.drawRect(x, y, cellSize, cellSize);
                    coloured = true;
                }else if(coloured == true){
                    UI.fillRect(x, y, cellSize, cellSize);
                    coloured = false;
                }
                x += cellSize;
            }
            y += cellSize;
        }
    }

    /** Draw a board made of concentric circles, 2 pixel apart
     *  Asks the user for the number of squares on each side
     */
    public void drawConcentricBoard(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        /*# YOUR CODE HERE */
        int bsize = (int) boardSize;
        int cellSize = bsize/num;
        
        double y = boardTop;
        for (int row = 0; row < num; row++){
            double x = boardLeft;
            for (int col = 0; col < num; col++){  
                int ovalsize = cellSize;
                double ovalx = x;
                double ovaly = y;
                for(int circles = 0; circles < cellSize; circles++){
                    UI.drawOval(ovalx, ovaly, ovalsize, ovalsize);
                    ovalsize -= 1;
                    ovalx -= 2;
                    ovaly += 2;
                    
                }
                x += cellSize;
            }
            y += cellSize;
        }
        }
    

    /** ---------- The code below is already written for you ---------- **/

    public PatternsDrawer(){
        UI.initialise();
        UI.addButton("Clear",UI::clearPanes);
        UI.addButton("Core: Grid", this::drawGridBoard);
        UI.addButton("Core: Illusion", this::drawIllusion);
        UI.addButton("Completion: Checkers", this::drawCheckersBoard);
        UI.addButton("Challenge: Concentric", this::drawConcentricBoard);
        UI.addButton("Quit",UI::quit);
    }   


}


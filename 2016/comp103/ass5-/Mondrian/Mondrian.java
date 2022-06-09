// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 5
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;

/** 
 * Mondrian Painting Generator
 * 
 * Generates Mondrian Paintings by recursivley dividing frames into
 * sub-frames and filling them with the typical Mondrian colours. 
 * 
 */
public class Mondrian {
    // constants

    static final int MondrianLineWidth = 12;    // width of the black separating lines
    static final int MondrianSideMinimum = 48;  // minimum side below which no more splitting takes place

    // Mondrian Colours
    static final Color mondrianBlack   = new Color(25,23,26);
    static final Color mondrianWhite   = new Color(229,227,228);
    static final Color mondrianYellow  = new Color(255,221,10);
    static final Color mondrianRed     = new Color(223, 12, 29);
    static final Color mondrianBlue    = new Color(8,56,138);

    // fields
    static int level = 2;   // how deeply sub-Mondrians may be nested
    static int chance = 45; // chance to create a sub-Mondrian in %

    static List<Color> mondrianColours = new ArrayList<Color>(); // collection to pick Mondrian colours from

    // create UI and initialise Mondrian colours
    public Mondrian() {
        UI.setWindowSize(900,600);
        UI.setDivider(0.1); // leave some space for printing level and chance values

        // create buttons
        UI.addButton("Create", this::drawMondrian);
        UI.addButton("Level+", this::increaseLevel);
        UI.addButton("Level-", this::decreaseLevel);
        UI.addButton("More", this::increaseChance);
        UI.addButton("Less", this::decreaseChance);

        // make white three times more likely to occur than the other colours
        mondrianColours.add(mondrianWhite);
        mondrianColours.add(mondrianWhite);
        mondrianColours.add(mondrianWhite);
        mondrianColours.add(mondrianYellow);
        mondrianColours.add(mondrianRed);
        mondrianColours.add(mondrianBlue);

        drawMondrian();
    }

    /**
     * Increases the level, up to a maximum of 9.
     */
    public void increaseLevel() {
        if (level < 9)
            level++;

        drawMondrian(); 
    }

    /**
     * Decreases the level, up to a minimum of 0.
     */
    public void decreaseLevel() {
        if (level > 0)
            level--;

        drawMondrian();
    }

    /**
     * Increases the likelihood of creating a sub-Mondrian
     * up to a maximum of 1.
     */
    public void increaseChance() {
        if (chance <= 95)
            chance += 5;    // increase by 5%

        drawMondrian();
    }

    /**
     * Decreases the likelihood of creating a sub-Mondrian
     * up to a minimum of 0.
     */
    public void decreaseChance() {
        if (chance >= 5)
            chance -= 5;  // decrease by 5%

        drawMondrian();
    }

    /**
     * @return a random Mondrian colour.
     */
    Color randomMondrianColour() {
        return mondrianColours.get((int)(mondrianColours.size()*Math.random()));
    }

    /** 
     * Draws a Mondrian within a frame specified by
     * the coordinates (x1, y1) and (x2, y2).
     * 
     * Always fills the specified frame with one (random) Mondrian colour.
     * 
     * Generates a split point within the frame to potentially 
     * draw further sub-Mondrians. The split point is within a subframe that 
     * is centred in the original frame with margins extending from 25%-75%
     * of the original frame. 
     * 
     * Only draws the dividing black lines and further sub-Mondrians, if all of the following hold:
     *    1. the currentLevel is not zero yet. 
     *    2. the filled rectangle has no sides that are smaller than MondrianSideMinimum.
     *    3. the chance of creating a sub-Mondrian is > 0%.
     * 
     */
    public void drawMondrian(int x1, int y1, int x2, int y2, int currentLevel) {

        // draw the colour patch that may or may not become divided
        UI.setColor(randomMondrianColour());
        UI.fillRect(x1+MondrianLineWidth/2, y1+MondrianLineWidth/2,    // do not paint over already painted lines
                       x2-x1-MondrianLineWidth, y2-y1-MondrianLineWidth);
        
        /*# YOUR CODE HERE */
        Random r = new Random();
        int xa = (int)((x2-x1)*0.25);
        int ya = (int)((y2-y1)*0.25);
        
        int newX1 = x1+xa;
        int newY1 = y1+ya;
        int newX2 = x2-xa;
        int newY2 = y2-ya;
        
        int splitX = r.nextInt((newX2 - newX1) + 1) + newX1;
        int splitY = r.nextInt((newY2 - newY1) + 1) + newY1;
        
        UI.setColor(mondrianBlack);
        UI.drawLine(splitX, y1, splitX, y2);
        UI.drawLine(x1, splitY, x2, splitY);
        if (currentLevel>0 && y2-y1>MondrianSideMinimum && x2-x1>MondrianSideMinimum && chance>0){
            int frame1 = r.nextInt((100 - 1) + 1) + 1;
            int frame2 = r.nextInt((100 - 1) + 1) + 1;
            int frame3 = r.nextInt((100 - 1) + 1) + 1;
            int frame4 = r.nextInt((100 - 1) + 1) + 1;
            if(frame1<=chance){
                int newLevel1 = currentLevel-1;
                drawMondrian(x1, y1, splitX, splitY, newLevel1);
            }
            if(frame2<=chance){
                int newLevel2 = currentLevel-1;
                drawMondrian(x1, splitY, splitX, y2, newLevel2);
            }
            if(frame3<=chance){
                int newLevel3 = currentLevel-1;
                drawMondrian(splitX, y1, x2, splitY, newLevel3);
            }
            if(frame4<=chance){
                int newLevel4 = currentLevel-1;
                drawMondrian(splitX, splitY, x2, y2, newLevel4);
            }
        }
    }

    /**
     * Paints a Mondrian painting by drawing the outer frame and
     * calling drawMondrian.
     * 
     */
    public void drawMondrian() {
        int margin = 20;    // margin to canvas edges

        // get canvas size
        int width = UI.getCanvasWidth() - margin;
        int height = UI.getCanvasHeight();

        UI.clearGraphics();
        UI.setLineWidth(MondrianLineWidth);

        UI.clearText();
        UI.print("Level: " + level + "\n");
        UI.print("Chance: " + chance + "%");

        // calculate coordinates of the first, largest Mondrian patch
        int x1 = margin;
        int y1 = margin;
        int x2 = margin + (width - margin);
        int y2 = margin + (height - 2*margin);

        // draw the first, largest Mondrian patch and all smaller ones
        drawMondrian(x1, y1, x2, y2, level);

        // draw the frame last to cover unfinished areas
        UI.setColor(mondrianBlack);
        UI.drawRect(x1, y1, x2-x1, y2-y1);
    }

    // Create a new masterpiece
    public static void main(String[] arguments) {
        new Mondrian();
    }   
}

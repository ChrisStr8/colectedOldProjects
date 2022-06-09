// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 3 
 * Name: pondy
 */

import ecs100.*;

/**
 * A CartoonFigure object is a cartoon figure, displayed on the screen
 * that can 
 *   move around,
 *   change the direction the figure is facing,
 *   change its emotion (smiling or frowning)
 *   speak or "think" a phrase
 */

public class CartoonFigure {

    /* Fields representing the state of a CartoonFigure */
    private String imageName = "yellow";
    private double figureX = -100;   // top left corner of figure
    private double figureY = -100;
    private String direction = "right";
    private String emotion = "smile";

    /* Fields containing dimensions of CartoonFigures */

    private int figureHeight = 100;
    private int figureWidth = 70;

    private int wordsWidth = 150;
    private int wordsHeight = 35;

    /** Constructor requires the coordinates (left, top) of where it should be placed,
    and the directory name where the images are,
    For example
    new CartoonFigure(100, 50, "blue");
     */
    public CartoonFigure(double x, double y, String name ){
        this.figureX = x;
        this.figureY = y;
        this.imageName=name;
        this.draw();
    }

    /** makes the CartoonFigure turn to the left */
    public void lookLeft() {
        this.erase();
        this.direction = "left";
        this.draw();
    }

    /** makes the CartoonFigure turn to the right */
    public void lookRight() {
        this.erase();
        this.direction = "right";
        this.draw();
    }

    /** makes the CartoonFigure smile */
    public void smile() {
        this.erase();
        this.emotion = "smile";
        this.draw();
    }

    /** makes the CartoonFigure frown */
    public void frown() {
        this.erase();
        this.emotion = "frown";
        this.draw();
    }

    /** move the CartoonFigure in the direction it is facing */
    public void walk(double dist) {
        this.erase();
        if (this.direction.equals("right")){
            this.figureX = this.figureX + dist;
        }
        else{
            this.figureX = this.figureX - dist;
        }
        this.draw();
    }

    /** makes the CartoonFigure say something in a speech box */
    public void speak(String words) {
        double boxX = this.figureX;
        double boxY = this.figureY - this.wordsHeight - 20;

        if (this.direction.equals("right"))
            boxX += 15 ;
        else
            boxX +=  this.figureWidth  - 15 - this.wordsWidth;

        UI.eraseRect(boxX, boxY, this.wordsWidth, this.wordsHeight);
        UI.drawRect(boxX, boxY, this.wordsWidth, this.wordsHeight);
        UI.drawString(words, boxX + 5, boxY + this.wordsHeight/2 + 3);

        UI.sleep(1000);

        UI.eraseRect(boxX, boxY, this.wordsWidth+1, this.wordsHeight+1);
    }

    /** makes the CartoonFigure think something in a speech bubble */
    public void think(String words) {
        double bubbleX = this.figureX;
        double bubbleY = this.figureY - this.wordsHeight - 2;
        double circleX = this.figureX;
        double circleY = this.figureY;

        if (this.direction.equals("right")){
            bubbleX += 15 ;
            circleX += this.figureWidth + 20;
        }
        else{
            bubbleX +=  this.figureWidth  - 15 - this.wordsWidth;
            circleX -= 30;
        }

        UI.eraseOval(bubbleX, bubbleY, this.wordsWidth, this.wordsHeight);
        UI.drawOval(bubbleX, bubbleY, this.wordsWidth, this.wordsHeight);
        UI.drawString(words, bubbleX + 12, bubbleY + this.wordsHeight/2 + 3);

        UI.drawOval(circleX, circleY, 10, 10);

        UI.sleep(1000);

        UI.eraseRect(bubbleX, bubbleY, this.wordsWidth+1, this.wordsHeight+1);
        UI.eraseOval(circleX, circleY, 10, 10);
    }

    /** Helper method that erases the CartoonFigure 
     * All the public methods that change the figure call erase first */

    private void erase() {
        UI.eraseRect(this.figureX, this.figureY, this.figureWidth+1, this.figureHeight+1);
    }

    /** Helper method that draws the CartoonFigure
     * All the public methods that change the figure call draw.
     */

    private void draw(){
        String fname = null;
        fname = this.imageName + "/" + this.direction + "-" + this.emotion + ".png"; 
        UI.drawImage(fname, this.figureX, this.figureY, this.figureWidth, this.figureHeight);
        UI.sleep(500);
    }

}


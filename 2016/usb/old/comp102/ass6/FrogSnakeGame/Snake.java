// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;

/** The snake is created at a random position with a random direction.
 * The constructor does not have any parameters.
 * It can move
 *  - makes it go forward one step in its current direction.
 *  - if outside arena boundaries, makes it go backward one step, and then turn to a new (random)
 *         direction.
 *  The walls of the arena are determined by the constants:
 *    FrogSnakeGame.TopWall, FrogSnakeGame.BotWall, FrogSnakeGame.LeftWall and FrogSnakeGame.RightWall
 * It can report its current position (x and y) with the
 *  getX() and getY() methods.
 *  draw() will make it draw itself,
 *  erase() will make it erase itself
 */

public class Snake{
    /*# YOUR CODE HERE */
    public double sleft;
    public double stop;
    public String sdir;
    
    /**
     * Turn right
     */
    public void turnRight(){
        /*# YOUR CODE HERE */
        this.sdir = "right";
    }

    /**
     * Turn left
     */
    public void turnLeft(){
        /*# YOUR CODE HERE */
        this.sdir = "left";
    }

    /**
     * Turn up
     */
    public void turnUp(){
        /*# YOUR CODE HERE */
        this.sdir = "right";
    }

    /**
     * Turn down
     */
    public void turnDown(){
        /*# YOUR CODE HERE */
        this.sdir = "down";
    }
    
    /**
     * Moves the frog: 
     *   use SPEED unit forward in the correct direction
     *   by changing the position of the frog.
     * Make sure that the frog does not go outside the arena, by making sure 
     *  - the top of the frog is never smaller than FrogGame.TopWall
     *  - the bottom of the frog is never greater than FrogGame.BotWall
     *  - the left of the frog is never smaller than FrogGame.LeftWall
     *  - the right of the frog is never smaller than FrogGame.RightWall
     */
    public void move() {
        /*# YOUR CODE HERE */
        UI.println("move");
    }

    /**
     * Check whether the frog is touching the given point, eg, whether the
     *   given point is included in the area covered by the frog.
     * Return true if the frog is on the top of the position (x, y)
     * Return false otherwise
     */
    public boolean touching(double x, double y) {
        /*# YOUR CODE HERE */
        return false;
    }
    
    public int getX(){
        return 10;
        
    }
    
    public int getY(){
        return 10;
    }
    
    /**
     * Draws the frog at the current position.
     */
    public void draw(){
        /*# YOUR CODE HERE */
        UI.drawImage("snake.jpg",sleft,stop);
    }
}


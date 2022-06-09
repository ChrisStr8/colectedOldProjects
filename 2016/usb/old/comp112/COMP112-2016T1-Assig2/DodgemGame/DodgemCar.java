// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

import ecs100.*;
import java.awt.Color;
import java.util.*;

/** DodgemCar
 * Represents a single DodgemCar that can move around in the arena
 * 
 * A DodgemCar must have fields to store their state:
 *  - a position (x and y)
 *  - a directon of travel (an angle, preferable in radians, ie between -Math.PI and Math.PI
 *  - their remaining life
 *  - their color
 *
 * A DodgemCar needs
 *  - a constructor,
 *  - a method to make it move one unit
 *  - methods to turn the car to the left and the right
 *    A simple design changes the direction of the car directly.
 *    A better design has a "steering wheel": turning the car actually turns the
 *    steering wheel, and the steering wheel makes the direction change a little
 *    bit on each move.
 *  - a method to draw the car on the graphics pane
 *  - methods to check for collisions, either with the wall and obstacle, or with another car
 *    These methods should update the state of the car if there is a collision
 *  - a method to return the remaining life of the car.
 *
 * Drawing a DodgemCar should show the position and direction of the car.
 *   If the runing uses a steering wheel, then the current direction of the
 *   steering should also be shown.
 *   
 * A DodgemCar can collide with a wall, the obstacle, or another DodgemCar.
 * - Colliding with a wall gives a little bit of damage
 * - Colliding with the obstacle should give a lot of damage
 * - Colliding with the other car may give a little damage, but
 *   should also result in jumping back, and the directions of the cars being changed
 *  
 *   For a really nice design, the way the cars bump into each 
 *   other should depend on the relative directions of the cars -
 *   being bumped on the side should have a different effect from
 *   being bumped in the front or the back.
 *   
 */
public class DodgemCar{
    // Constants: Geometry and other parameters
    /*# YOUR CODE HERE */
    public static final double Radius = 30;
    public static final double Diam = Radius * 2;

    // fields for the state of the car
    /*# YOUR CODE HERE */
    double x = (DodgemGame.LeftWall+DodgemGame.RightWall)/2;
    double y = (DodgemGame.TopWall+DodgemGame.BotWall)/2;
    double direction = Math.random()*360;
    double lifeLeft = 10;

    //Constructor 
    /** 
     * The parameters specify the initial position and direction
     */
    public DodgemCar(double x, double y, double dir)  {
        /*# YOUR CODE HERE */
        
    }

    // other methods, eg for turning left & right, drawing, checking collisions, etc

    /**
     * Turn the steering wheel one step more to the left (negative angle)
     * This governs how much the car turns on each move
     * Steering wheel can't turn more than 45 degrees to left or right
     */
    public void turnLeft(){
        /*# YOUR CODE HERE */
        
    }

    /**
     * Turn the steering wheel one step more to the right (positive angle)
     * This governs how much the car turns on each move
     * Steering wheel can't turn more than 45 degrees to left or right
     */
    public void turnRight(){
        /*# YOUR CODE HERE */
        
    }

    /**
     * Moves the car 1 unit forward
     * First changes the current direction according to the position of the
     * steering wheel (takes 20 moves to turn the car by the angle of the steering wheel)
     * Then moves forward by 1 unit
     */
    public void move() {
        /*# YOUR CODE HERE */
        double newx = x + Math.cos(direction*Math.PI/180);
        double newy = y + Math.sin(direction*Math.PI/180);
        if (newx-Radius < DodgemGame.LeftWall || newx+Radius > DodgemGame.RightWall || newy-Radius < DodgemGame.TopWall || newy+Radius > DodgemGame.BotWall){
            direction= Math.random()*360;  
        }
        else{
            x = newx;
            y = newy;
       }
    }

    /** draw the car */
    public void draw() {
        /*# YOUR CODE HERE */
        UI.setColor(Color.red);
        UI.fillOval(x-Radius, y-Radius, Diam, Diam);
        UI.setColor(Color.blue);
        UI.drawOval(x-Radius, y-Radius, Diam, Diam);
        double xfront = x+Radius*0.8*Math.cos(direction*Math.PI/180);
        double yfront = y+Radius*0.8*Math.sin(direction*Math.PI/180);
        UI.fillOval(xfront-2, yfront-2, 5, 5);

    }

    /**
     * Check whether the car has collided with a wall. (Core)
     * if so, move it back so it isn't overlapping the wall, and 
     * reduce its life by the appropriate amount.
     */
    public void checkCollideWall(){
        /*# YOUR CODE HERE */
        
    }        

    /**
     * Check whether the car has collided with the obstacle. (Completion)
     * if so, move it back so it isn't overlapping the obstacle, and
     * reduce its life by the appropriate amount.
     */
    public void checkCollideObstacle(){
        /*# YOUR CODE HERE */
        if (x-Radius < DodgemGame.LeftWall || x+Radius > DodgemGame.RightWall || y-Radius < DodgemGame.TopWall || y+Radius > DodgemGame.BotWall){
            direction= Math.random()*360;  
        }
    }

    /** @return whether this car is touching the other car */
    public boolean checkCollideCar(DodgemCar other){
        /*# YOUR CODE HERE */
        return(true);
    }

    /**
     * Returns the amount of life left of this car (needed for Completion)
     */
    public double life(){
        /*# YOUR CODE HERE */
        return(lifeLeft);
    }

    /**
     * Useful method for debugging: 
     * Returns a String rendering of the DodgemCar Object
     * which can be printed out for debugging.
     * Assumes that you called the fields x, y, and direction; you could change it.
     */
    public String toString(){
        return String.format("Car@(%.0f,%.0f)->%s", this.x, this.y, this.direction);
    }

}


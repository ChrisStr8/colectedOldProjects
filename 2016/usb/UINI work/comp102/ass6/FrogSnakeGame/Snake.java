// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment
 * Name:Christopher Straight
 * Usercode:strachri
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
    public int SPEED;
    public int sleft;
    public int stop;
    public int ssize;
    public int sdir; // 1 is up. 2 is down. 3 is left. 4 is right.
    
    public Snake(){
        this.SPEED = 2;
        this.ssize = 30;
        this.sleft = (int )(Math.random() * (FrogSnakeGame.RightWall-ssize) + FrogSnakeGame.LeftWall);
        this.stop = (int )(Math.random() * (FrogSnakeGame.BotWall-ssize) + FrogSnakeGame.TopWall);
        this.sdir = (int )(Math.random() * 4 + 1);
    }
    
    public void move(){
       if(sdir == 4 && (sleft + ssize) > FrogSnakeGame.RightWall){
            this.sleft -= SPEED;
            int newdir = sdir;
            while(newdir == 4){
                newdir = (int )(Math.random() * 4 + 1);
            }
            this.sdir = newdir;
        }else if(sdir == 3 && sleft < FrogSnakeGame.LeftWall){
            this.sleft += SPEED;
            int newdir = sdir;
             while(newdir == 3){
                newdir = (int )(Math.random() * 4 + 1);
            }
            this.sdir = newdir;
        }else if(sdir == 2 && (stop + ssize) > FrogSnakeGame.BotWall){
            this.stop -= SPEED;
            int newdir = sdir;
             while(newdir == 2){
                newdir = (int )(Math.random() * 4 + 1);
            }
            this.sdir = newdir;
        }else if(sdir == 1 && stop < FrogSnakeGame.TopWall){
            this.stop += SPEED;
            int newdir = sdir;
             while(newdir == 1){
                newdir = (int )(Math.random() * 4 + 1);
            }
            this.sdir = newdir;
        }
        
       else if(sdir == 4 && (sleft + ssize) < FrogSnakeGame.RightWall){
            this.sleft += SPEED;
        }else if(sdir == 3 && sleft > FrogSnakeGame.LeftWall){
            this.sleft -= SPEED;
        }else if(sdir == 2 && (stop + ssize) < FrogSnakeGame.BotWall){
            this.stop += SPEED;
        }else if(sdir == 1 && stop > FrogSnakeGame.TopWall){
            this.stop -= SPEED;
        }
    }
    
    public int getX(){
        return stop;
    }
    
    public int getY(){
        return sleft;
    }
    
    public void speedUp(){
        this.SPEED += 1;
    }
    
    public void resetSpeed(){
        this.SPEED = 2;
    }
    
    public void draw(){
        UI.drawImage("snake.jpg", sleft, stop, ssize, ssize);
    }
}

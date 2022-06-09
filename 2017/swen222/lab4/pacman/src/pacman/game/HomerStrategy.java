package pacman.game;

import pacman.ui.Board;

import java.util.Random;

/**
 * Created by straigchri on 17/08/17.
 */
public class HomerStrategy implements StrategyGhost.Strategy{
    protected static final Random random = new Random(System.currentTimeMillis());

    @Override
    public int speed() {
        return 3;
    }

    @Override
    public void tick(Board game, int direction, MovingCharacter character) {
        // check whether we are at an intersection.
        if (direction == MovingCharacter.DOWN
                || direction == MovingCharacter.UP) {
            // ok, moving in up/down direction
            if (!game.canMoveLeft(character) && !game.canMoveRight(character)) {
                return; // no horizontal movement possible
            }
        } else if (direction == MovingCharacter.RIGHT
                || direction == MovingCharacter.LEFT) {
            // ok, moving in left/right direction
            if (!game.canMoveUp(character) && !game.canMoveDown(character)) {
                return; // no horizontal movement possible
            }
        }

        // yes, we're at an intersection. Now, flip a coin to see if we're
        // really homing or going to move randomly. character is kinda important, as
        // otherwise having multiple homing ghosts just means they all act in
        // exactly the same manner.		
        if(random.nextInt(10) > 7) {
            character.queued = random.nextInt(4)+1; // don't stop
            return;
        }

        double targetDistance = 10000;
        int targetDeltaX=-1;
        int targetDeltaY=-1;

        // home in on target
        synchronized(game) {
            for(Character c : game.characters()) {
                if(c instanceof Pacman && !((Pacman)c).isDead()) {
                    // potential target
                    int deltaX = Math.abs(c.realX() - character.realX);
                    int deltaY = Math.abs(c.realY() - character.realY);
                    double distance = Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
                    if(distance < targetDistance) {
                        targetDeltaX = c.realX() - character.realX;
                        targetDeltaY = c.realY() - character.realY;
                        targetDistance = distance;
                    }
                }
            }
        }

        if(targetDeltaX != -1) {
            int deltaX = Math.abs(targetDeltaX);
            int deltaY = Math.abs(targetDeltaY);
            if(deltaX < deltaY) {
                // prefer to move north-south
                if(targetDeltaY < 0) {
                    tryMoveUp(targetDeltaX < 0, game, character);
                } else {
                    tryMoveDown(targetDeltaX < 0, game, character);
                }
            } else {
                // prefer to move east-west
                if(targetDeltaX < 0) {
                    tryMoveLeft(targetDeltaY < 0, game, character);
                } else {
                    tryMoveRight(targetDeltaY < 0, game, character);
                }
            }
        }
    }

    public void tryMoveUp(boolean preferLeft, Board game, MovingCharacter character) {
        if(game.canMoveUp(character)) {
            character.moveUp();
        } else if(preferLeft && game.canMoveLeft(character)) {
            character.moveLeft();
        } else if(!preferLeft && game.canMoveRight(character)) {
            character.moveRight();
        } else if(game.canMoveRight(character)) {
            character.moveRight();
        } else if(game.canMoveLeft(character)) {
            character.moveLeft();
        } else {
            character.moveDown(); // last resort
        }
    }

    public void tryMoveDown(boolean preferLeft, Board game, MovingCharacter character) {
        if(game.canMoveDown(character)) {
            character.moveDown();
        } else if(preferLeft && game.canMoveLeft(character)) {
            character.moveLeft();
        } else if(!preferLeft && game.canMoveRight(character)) {
            character.moveRight();
        } else if(preferLeft && game.canMoveRight(character)) {
            character.moveRight();
        } else if(!preferLeft && game.canMoveLeft(character)) {
            character.moveLeft();
        } else {
            character.moveUp(); // last resort
        }
    }

    public void tryMoveLeft(boolean preferUp, Board game, MovingCharacter character) {
        if(game.canMoveLeft(character)) {
            character.moveLeft();
        } else if(preferUp && game.canMoveUp(character)) {
            character.moveUp();
        } else if(!preferUp && game.canMoveDown(character)) {
            character.moveDown();
        } else if(game.canMoveUp(character)) {
            character.moveUp();
        } else if(game.canMoveDown(character)) {
            character.moveDown();
        } else {
            character.moveRight(); // last resort
        }
    }

    public void tryMoveRight(boolean preferUp, Board game, MovingCharacter character) {
        if(game.canMoveRight(character)) {
            character.moveRight();
        } else if(preferUp && game.canMoveUp(character)) {
            character.moveUp();
        } else if(!preferUp && game.canMoveDown(character)) {
            character.moveDown();
        } else if(game.canMoveUp(character)) {
            character.moveUp();
        } else if(game.canMoveDown(character)) {
            character.moveDown();
        } else {
            character.moveLeft(); // last resort
        }
    }
}

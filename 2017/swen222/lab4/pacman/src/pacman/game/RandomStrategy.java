package pacman.game;

import pacman.ui.Board;

import java.util.Random;

/**
 * Created by straigchri on 17/08/17.
 */
public class RandomStrategy implements StrategyGhost.Strategy {
    protected static final Random random = new Random(System.currentTimeMillis());

    @Override
    public int speed() {
        return 3;
    }

    @Override
    public void tick(Board game, int direction, MovingCharacter character) {
        // check whether we are at an intersection.
        if (direction == MovingCharacter.DOWN || direction == MovingCharacter.UP) {
            // ok, moving in up/down direction
            if (!game.canMoveLeft(character) && !game.canMoveRight(character)) {
                return; // no horizontal movement possible
            }
        } else if (direction == MovingCharacter.RIGHT || direction == MovingCharacter.LEFT) {
            // ok, moving in left/right direction
            if (!game.canMoveUp(character) && !game.canMoveDown(character)) {
                return; // no horizontal movement possible
            }
        }

        character.queued = random.nextInt(4)+1; // don't stop
    }
}

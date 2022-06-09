package pacman.game;

import pacman.ui.Board;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;

import static pacman.ui.BoardCanvas.loadImage;

/**
 * Created by straigchri on 17/08/17.
 */
public class StrategyGhost extends MovingCharacter implements Ghost{
    private Strategy strategy;
    private Image[] images;

    public StrategyGhost(int realX, int realY, int direction, Image[] images, Strategy strategy) {
        super(realX, realY, direction);
        this.images = images;
         this.strategy = strategy;
    }

    @Override
    public int speed() {
        return strategy.speed();
    }

    @Override
    public void tick(Board game){
        super.tick(game);
        strategy.tick(game, this.direction, this);
    }

    @Override
    public void draw(Graphics g) {
        switch(direction) {
            case MovingCharacter.RIGHT:
                g.drawImage(images[0], realX,realY, null, null);
                break;
            case MovingCharacter.UP:
                g.drawImage(images[1], realX,realY, null, null);
                break;
            case MovingCharacter.DOWN:
                g.drawImage(images[2], realX,realY, null, null);
                break;
            case MovingCharacter.LEFT:
                g.drawImage(images[3], realX,realY, null, null);
                break;
        }
    }

    @Override
    public void toOutputStream(DataOutputStream dout) throws IOException {

    }

    public interface Strategy {
        int speed();
        void tick(Board game, int direction, MovingCharacter character);
    }
}

package SSGame.GameInterface;

import SSGame.Game.SSGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created on 28/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
class KeyController implements KeyListener {
    private SSGame model;
    private BoardView boardView;

    public KeyController(SSGame m, BoardView b){
        model = m;
        boardView = b;
    }

    public void reset(SSGame m, BoardView b){
        model = m;
        boardView = b;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(model!=null && boardView!=null){
            if(boardView.selected!=null && !boardView.rotating){
                if(e.getKeyCode()==38){//up
                    model.move(boardView.selected.getName(), "up", false);
                }else if(e.getKeyCode()==39){//right
                    model.move(boardView.selected.getName(), "right", false);
                }else if(e.getKeyCode()==40){//down
                    model.move(boardView.selected.getName(), "down", false);
                }else if(e.getKeyCode()==37){//left
                    model.move(boardView.selected.getName(), "left", false);
                }
                boardView.selected = null;
                boardView.sx = -1;
                boardView.sy = -1;
            }
        }
    }
}

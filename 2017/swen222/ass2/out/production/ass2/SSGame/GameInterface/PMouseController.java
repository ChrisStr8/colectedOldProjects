package SSGame.GameInterface;

import SSGame.Game.GPiece;
import SSGame.Game.SSGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Queue;

/**
 * Created on 3/09/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
class PMouseController implements MouseListener {
    private final SSGame model;
    private final PiecesView panel;

    public PMouseController(SSGame model, PiecesView panel){
        this.model = model;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(panel.animation!=-1)return;
        int x = e.getX();
        int y = e.getY();
        x-=10;
        y-=10;
        x/=50;
        y/=50;

        if((panel.getYellow() && model.turn && model.getBoard().getLocation(7, 7)==null
                || !panel.getYellow() && !model.turn && model.getBoard().getLocation(2, 2)==null)) {
            if(!model.created) {
                if (panel.selected == null) {
                    if (x < 5 && y < 5) {
                        select(x, y);
                    }
                } else {
                    if (x == 0 && y == 0) {
                        model.create(panel.selected.getName(), 0);
                    } else if (x == 1 && y == 0) {
                        model.create(panel.selected.getName(), 1);
                    } else if (x == 0 && y == 1) {
                        model.create(panel.selected.getName(), 3);
                    } else if (x == 1 && y == 1) {
                        model.create(panel.selected.getName(), 2);
                    }
                    panel.animation = 5;
                    panel.selectedX = 10;
                    panel.selectedY = 10;
                    panel.selected = null;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void select(int x, int y){
        Queue<GPiece> pieces = panel.pieces();
        for (int xx = 0; xx < 5; xx++) {
            for (int yy = 0; yy < 5; yy++) {
                if (xx == x && yy == y) {
                    panel.selected = pieces.poll();
                    panel.animation = 0;
                    panel.selectedX = (x*50)+10;
                    panel.selectedY = (y*50)+10;
                }
                pieces.poll();
            }
        }
    }
}

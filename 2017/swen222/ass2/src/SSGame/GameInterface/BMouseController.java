package SSGame.GameInterface;

import SSGame.Game.GPiece;
import SSGame.Game.SSGame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created on 4/09/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 *
 * MouseListener for the boardView object
 */
class BMouseController implements MouseListener {
    private final SSGame model;
    private final BoardView board;


    public BMouseController(SSGame model, BoardView board){
        this.model = model;
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x-=10;
        y-=10;
        x/=50;
        y/=50;
        //System.out.println("x: "+x+", y: "+y);
        if(board.selected==null) {
            GPiece piece = model.getBoard().getLocation(x, y);
            if (piece != null && (model.turn && piece.isColour() || !model.turn && !piece.isColour())) {
                if(!model.created){
                    JOptionPane.showMessageDialog(board, "you need to place a piece first");
                }else if (!model.changed(piece)) {
                    board.selected = piece;
                    //System.out.println("clicked: " + board.selected.getName());
                    board.sx = x;
                    board.sy = y;
                }
            }
        }else {
            if (board.rotating) {
                int sx = board.sx;
                int sy = board.sy;
                sx *= 50;
                sy *= 50;
                sx += 10;
                sy += 10;
                //System.out.println("sx: "+sx+" sy: "+sy);
                if (!(e.getX()>=sx-10 && e.getX()<=sx+60) || !(e.getY()>=sy-10 && e.getY()<=sy+60)) {
                    model.rotate(board.selected.getName(), board.rotations);
                    board.rotating = false;
                    board.selected = null;
                    board.sx = -1;
                    board.sy = -1;
                    board.rotations = 0;
                } else {
                    board.rotations+=1;
                    if(board.rotations==3){
                        board.rotations = 0;
                    }
                }
            }else {
                if (x != board.sx || y != board.sy) {
                    board.selected = null;
                    board.sx = -1;
                    board.sy = -1;
                } else {
                    int xZero = (x * 50) + 10;
                    int yZero = (y * 50) + 10;
                    int xClick = e.getX() - xZero;
                    int yClick = e.getY() - yZero;
                    //System.out.println("x: " + xClick + " y: " + yClick);
                    int loc = getPositionInSquare(xClick, yClick);
                    if (loc == 1) {
                        //System.out.println("up");
                        model.move(board.selected.getName(), "up", false);
                    } else if (loc == 2) {
                        //System.out.println("right");
                        model.move(board.selected.getName(), "right", false);
                    } else if (loc == 3) {
                        //System.out.println("down");
                        model.move(board.selected.getName(), "down", false);
                    } else if (loc == 4) {
                        //System.out.println("left");
                        model.move(board.selected.getName(), "left", false);
                    } else if (loc == 5) {
                        //System.out.println("center");
                        board.rotating = true;
                        return;
                    }
                    board.selected = null;
                    board.sx = -1;
                    board.sy = -1;
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

    /**
     * returns an integer showing which area of a square the x, y position is.
     * x and y should be the distance from the 0, 0 point of the square
     * 1 = top
     * 2 = right
     * 3 = left
     * 4 = right
     * 5 = center
     * @param x x position
     * @param y y position
     * @return position integer
     */
    private int getPositionInSquare(int x, int y){
        int area = 8;
        if(x>=area && x<=50-area && y>=0 && y<=area){//top
            return 1;
        }else if(x>=50-area && x<=50 && y>=area && y<=50-area){//right
            return 2;
        }else if(x>=area && x<=50-area && y>=50-area && y<=50){//bottom
            return 3;
        }else if(x>=0 && x<=area && y>=area && y<=50-area){//left
            return 4;
        }else if(x>=area && x<=50-area && y>=area && y<=50-area){//center
            return 5;
        }
        return 0;
    }
}

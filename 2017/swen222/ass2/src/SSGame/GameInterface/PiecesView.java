package SSGame.GameInterface;

import SSGame.Game.GPiece;
import SSGame.Game.SSGame;
import SSGame.resources.ImgResources;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

/**
 * Created on 29/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
class PiecesView extends JComponent {
    private final SSGame model;
    private final boolean yellow;
    private final boolean cemetery;
    GPiece selected;
    int animation = -1;
    int selectedX = -1;
    int selectedY = -1;

    PiecesView(SSGame model, boolean yellow, boolean cemetery){
        this.model = model;
        this.yellow = yellow;
        this.cemetery = cemetery;
        selected=null;
    }

    @Override
    public void paint(Graphics _g) {
        _g.clearRect(0, 0, 1000, 1000);
        super.paintComponents(_g);
        Graphics2D g = (Graphics2D) _g;
        if(selected==null) {
            if(animation>=5){
                printAnimation(g);
            }else {
                paintPieces(g);
            }
        }else {
            if(animation==-1) {
                paintSelected(g);
            }else {
                printAnimation(g);
            }
        }
    }

    /**
     * Paints the selected piece and each of its possible rotations
     * @param g
     */
    private void paintSelected(Graphics g){
        ImgResources.paintSquare(g, 0, 0, selected, 1);
        ImgResources.paintSquare(g, 1, 0, selected, 2);
        ImgResources.paintSquare(g, 0, 1, selected, 4);
        ImgResources.paintSquare(g, 1, 1, selected, 3);
    }

    /**
     * paints all of the GPieces available to this component in a 5 by 5 grid
     * @param g
     */
    private void paintPieces(Graphics g){
        Queue<GPiece> pieces = pieces();
        for(int x=0; x<5; x++){
            for (int y=0; y<5; y++){
                if(pieces.isEmpty()){
                    ImgResources.paintBlank(g, x, y, true);
                }else {
                    ImgResources.paintSquare(g, x, y, pieces.poll(), 1);
                }
            }
        }
    }

    /**
     * paints animations
     * @param g
     */
    private void printAnimation(Graphics g){
        if(animation==0) {//a-b
            ImgResources.paintSquareLoc(g, selectedX, selectedY, selected, 1, 50);
            if (selectedX == 10 && selectedY == 10) animation = 1;
            if (selectedX > 10) selectedX -= 1;
            if (selectedY > 10) selectedY -= 1;
            if (selectedX < 10) selectedX += 1;
            if (selectedY < 10) selectedY += 1;
        }else if(animation==1) {
            ImgResources.paintSquareLoc(g, 10, 10, selected, 1, 50);
            ImgResources.paintSquareLoc(g, selectedX, selectedY, selected, 2, 50);
            if (selectedX == 60 && selectedY == 10) animation = 2;
            if (selectedX > 60) selectedX -= 1;
            if (selectedY > 10) selectedY -= 1;
            if (selectedX < 60) selectedX += 1;
            if (selectedY < 10) selectedY += 1;
        }else if(animation==2) {
            ImgResources.paintSquareLoc(g, 10, 10, selected, 1, 50);
            ImgResources.paintSquareLoc(g, 60, 10, selected, 2, 50);
            ImgResources.paintSquareLoc(g, selectedX, selectedY, selected, 3, 50);
            if (selectedX == 60 && selectedY == 60) animation = 3;
            if (selectedX > 60) selectedX -= 1;
            if (selectedY > 60) selectedY -= 1;
            if (selectedX < 60) selectedX += 1;
            if (selectedY < 60) selectedY += 1;
        }else if(animation==3) {
            ImgResources.paintSquareLoc(g, 10, 10, selected, 1, 50);
            ImgResources.paintSquareLoc(g, 60, 10, selected, 2, 50);
            ImgResources.paintSquareLoc(g, 60, 60, selected, 3, 50);
            ImgResources.paintSquareLoc(g, selectedX, selectedY, selected, 4, 50);
            if (selectedX == 10 && selectedY == 60) animation = 4;
            if (selectedX > 10) selectedX -= 1;
            if (selectedY > 60) selectedY -= 1;
            if (selectedX < 10) selectedX += 1;
            if (selectedY < 60) selectedY += 1;
        }else if(animation==4) {
            ImgResources.paintSquareLoc(g, 10, 10, selected, 1, 50);
            ImgResources.paintSquareLoc(g, 60, 10, selected, 2, 50);
            ImgResources.paintSquareLoc(g, 60, 60, selected, 3, 50);
            ImgResources.paintSquareLoc(g, 10, 60, selected, 4, 50);
            ImgResources.paintSquareLoc(g, selectedX, selectedY, selected, 1, 50);
            if (selectedX == 10 && selectedY == 10) {
                animation = -1;
                selectedX = -1;
                selectedY = -1;
            }
            if (selectedX > 10) selectedX -= 1;
            if (selectedY > 10) selectedY -= 1;
            if (selectedX < 10) selectedX += 1;
            if (selectedY < 10) selectedY += 1;
        }else if(animation==5){
            paintPieces(g);
            g.setColor(Color.white);
            g.fillRect(selectedX, selectedY, 250, 250);
            selectedY += 1;
            if(selectedY>=260){
                animation = -1;
                selectedX = -1;
                selectedY = -1;
            }
        }
    }

    /**
     * @return a Queue of all the pieces to be displayed by this component
     */
    Queue<GPiece> pieces(){
        Queue<GPiece> pieces;
        if(yellow){
            if(cemetery){
                pieces = model.yCemetery();
            }else {
                pieces = model.freePiecesY();
            }
        }else {
            if(cemetery){
                pieces = model.gCemetery();
            }else {
                pieces = model.freePiecesG();
            }
        }
        return pieces;
    }

    /**
     * which players pieces should be displayed by this component, true for yellow
     * false for green
     * @return player boolean
     */
    boolean getYellow(){
        return yellow;
    }
}

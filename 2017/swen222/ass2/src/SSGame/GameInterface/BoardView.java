package SSGame.GameInterface;

import SSGame.Game.Board;
import SSGame.Game.GPiece;
import SSGame.Game.SSGame;
import SSGame.resources.ImgResources;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 29/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
class BoardView extends JComponent {
    private final SSGame model;
    private GPiece[][] lastBoard;
    GPiece selected;
    int animationStep = -1;
    int sx = -1;
    int sy = -1;
    boolean rotating = false;
    int rotations = 0;

    BoardView(SSGame m){
        model = m;
    }

    @Override
    public void paint(Graphics _g) {
        super.paintComponents(_g);
        Graphics2D g = (Graphics2D) _g;
        paintBoard(g);
        if(selected!=null){
            g.setColor(new Color(128, 128, 128, 127));
            g.fillRect(10, 10, 500, 500 );
            if(rotating) {
                ImgResources.paintSquareScaled(g, sx, sy, selected, rotations+1, 20);
            }else {
                ImgResources.paintSquare(g, sx, sy, selected, 1);
            }
        }
    }

    /**
     * paints the board from the model
     * @param g
     */
    private void paintBoard(Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, 520, 520);
        boolean  dark = true;
        for(int x=0; x<10; x++){
            for(int y=0; y<10; y++){
                ImgResources.paintBlank(g, x, y, dark);
                dark = !dark;
            }
            dark = !dark;
        }

        g.setColor(Color.GREEN.darker());
        g.fillRect(110, 110, 50, 50);

        g.setColor(Color.YELLOW.darker());
        g.fillRect(360, 360, 50, 50);

        Board board = model.getBoard();
        //paint pieces
        if(animationStep < 0){//no pieces animating
            GPiece[][] loc = board.boardSave();
            lastBoard = loc;
            for(int x=0; x<10; x++) {
                for (int y = 0; y < 10; y++) {
                    if(loc[x][y]!=null){
                        ImgResources.paintSquare(g, x, y, loc[x][y], 1);
                    }
                }
            }
        }else {
            paintAnimations(g);
        }

        if(model.turn){
            g.setColor(Color.YELLOW.darker());
        }else{
            g.setColor(Color.GREEN.darker());
        }
        g.fillRect(10, 10 , 100, 100);
        g.fillRect(410, 410, 100, 100);
        g.drawImage(ImgResources.GREENP.img, 60, 60, null, null);
        g.drawImage(ImgResources.YELLOWP.img, 410, 410, null, null);
    }

    /**
     * paints all pieces the need to be animated
     * @param g
     */
    private void paintAnimations(Graphics g){
        GPiece[][] loc = unchanged();
        GPiece[][] removed = removed();
        GPiece[][] moved = moved();
        GPiece[][] created = created();
        for(int x=0; x<10; x++) {
            for (int y = 0; y < 10; y++) {
                if(loc[x][y]!=null){
                    ImgResources.paintSquare(g, x, y, loc[x][y], 1);
                }else if(removed[x][y]!=null){
                    Toolkit.getDefaultToolkit().beep();
                    int scale = (animationStep - 50)*2;
                    ImgResources.paintSquareScaled(g, x, y, removed[x][y], 1, scale);
                }else if(moved[x][y]!=null){
                    int amount = 50 - animationStep;
                    int direction = 1;
                    String[] newLoc = findPiece(moved[x][y], model.getBoard().boardSave()).split(",");
                    int newX = Integer.parseInt(newLoc[0]);
                    int newY = Integer.parseInt(newLoc[1]);
                    if(newX==x && newY<y){//up
                        direction = 1;
                    }else if(newX>x && newY==y){//right
                        direction = 2;
                    }else if(newX==x && newY>y){//down
                        direction = 3;
                    }else if(newX<x && newY==y){//left
                        direction = 4;
                    }
                    ImgResources.paintSquareMoved(g, x, y, moved[x][y], direction, amount);
                }else if(created[x][y]!=null){
                    int scale = (50 - animationStep)*2;
                    ImgResources.paintSquareScaled(g, x, y, removed[x][y], 1, scale);
                }
            }
        }
        animationStep -= 1;
    }

    /**
     * makes an array of all the pieces that weren't changed when the model last changed,
     * the location of a pieces in the array iss its location on the board
     * @return 2D array of GPieces
     */
    private GPiece[][] unchanged(){
        GPiece[][] unchanged = new GPiece[10][10];
        GPiece[][] newBoard = model.getBoard().getBoard();
        for(int x=0; x<10; x++) {
            for (int y = 0; y < 10; y++) {
                if(newBoard[x][y]!=null && lastBoard[x][y]!=null) {
                    if (newBoard[x][y].equals(lastBoard[x][y])) {
                        unchanged[x][y] = newBoard[x][y];
                    }
                }
            }
        }
        return unchanged;
    }

    /**
     * makes an array of all the pieces that were removed when the model last changed,
     * the location of a pieces in the array iss its location on the board
     * @return 2D array of GPieces
     */
    private GPiece[][] removed(){
        GPiece[][] removed = new GPiece[10][10];
        GPiece[][] newBoard = model.getBoard().getBoard();
        for(int x=0; x<10; x++) {
            for (int y = 0; y < 10; y++) {
                if (lastBoard[x][y]!=null && !contains(newBoard, lastBoard[x][y]) && lastBoard[x][y].cemetery) {
                    //System.out.println("remove");
                    removed[x][y] = lastBoard[x][y];
                }
            }
        }
        return removed;
    }

    /**
     * makes an array of all the pieces that were moved when the model last changed,
     * the location of a pieces in the array iss its location on the board
     * @return 2D array of GPieces
     */
    private GPiece[][] moved(){
        GPiece[][] removed = new GPiece[10][10];
        GPiece[][] newBoard = model.getBoard().getBoard();
        for(int x=0; x<10; x++) {
            for (int y = 0; y < 10; y++) {
                if(lastBoard[x][y]!=null && contains(newBoard, lastBoard[x][y]) && !lastBoard[x][y].cemetery) {
                    //System.out.println("move");
                    removed[x][y] = lastBoard[x][y];
                }
            }
        }
        return removed;
    }

    /**
     * makes an array of all the pieces that were created when the model last changed,
     * the location of a pieces in the array iss its location on the board
     * @return 2D array of GPieces
     */
    private GPiece[][] created(){
        GPiece[][] created = new GPiece[10][10];
        GPiece[][] newBoard = model.getBoard().getBoard();
        for(int x=0; x<10; x++) {
            for (int y = 0; y < 10; y++) {
                if(contains(newBoard, newBoard[x][y]) && !contains(lastBoard, newBoard[x][y])) {
                    //System.out.println("create");
                    created[x][y] = newBoard[x][y];
                }
            }
        }
        return created;
    }

    /**
     * checks if the given board contains the given piece
     * @param board
     * @param piece
     * @return
     */
    private boolean contains(GPiece[][] board, GPiece piece){
        for(int x=0; x<10; x++) {
            for (int y = 0; y < 10; y++) {
                if(board[x][y]!=null && board[x][y].equals(piece)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * finds the x, y location of piece on board. if the piece is not on the
     * board then null is returned
     * @param piece the piece to locate
     * @param board the board array to search
     * @return string containing the x and y separated by a comma
     */
    private String findPiece(GPiece piece, GPiece[][] board){
        for(int x=0; x<10; x++){
            for(int y=0; y<10; y++){
                if(board[x][y]!=null && board[x][y].equals(piece)){
                    //System.out.println(x+" "+y);
                    return  x+","+y;
                }
            }
        }
        return "null";
    }
}

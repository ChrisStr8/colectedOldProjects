// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment
 * Name:Christopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;

/** GoGame: Lets users play Go on a 19x19 board.
 *    Each turn, a user clicks on a place on the board,
 *    If the place already has a stone, the stone is removed.
 *    If the place doesn't have a stone, then a stone of the
 *     current colour is added, and the current colour is switched to the
 *     other colour.
 *    The state of the board is stored as a 2D array of Color, with
 *    - null meaning the cell is empty,
 *    - Color.white meaning that there is a white stone in the cell
 *    - Color.black meaning that there is a black stone in the cell
 *    After each click, the board is redrawn completely.
 *
 *   (Note, the real Go board has a 18x18 grid of squares, but the stones are
 *     played on the corners of the squares, so there are actually 19 x 19 places.
 *     The code is slightly simpler if you have a 19x19 grid of squares and 
 *     play the stones in the middle of the squares.)
 *    
 */

public class GoGame  {
    public static final Color BOARD_COLOR = new Color(230, 120, 0);
    public static final double BOARD_LEFT = 50;
    public static final double BOARD_TOP = 20;
    public static final double GRID_SIZE = 25;
    public static final int NUM_CELLS = 19;
    public static final double BOARD_DIM = GRID_SIZE*NUM_CELLS;
    public static final double BOARD_BOT = BOARD_TOP + BOARD_DIM;
    public static final double BOARD_RIGHT = BOARD_LEFT + BOARD_DIM;

    //fields
    private Color[][] board = new Color[NUM_CELLS][NUM_CELLS];
    private Color turn = Color.black;

    /*# YOUR CODE HERE */
     public GoGame(){
        UI.initialise();
        UI.addButton("Restart", this::restartGame);
        UI.addButton("Quit", UI::quit);
        UI.setMouseListener(this::doMouse);
        UI.setWindowSize(600,600);
        UI.setDivider(0.0);
        UI.setFontSize(18);
        this.restartGame();
    }
    
    public void doMouse(String action, double x, double y){
        if (action.equals("released")){
            if (!this.on(x, y)){ return; }
            int[] rowCol = this.rowCol(x,y);
            int row = rowCol[0];
            int col = rowCol[1];
            if (!this.place(row, col)){ return; }
            if (turn==Color.black){
                this.turn = Color.white;
                UI.drawString("White's turn", BOARD_LEFT, 20);
            }else if (turn==Color.white){
                this.turn = Color.black;
                UI.drawString("Black's turn", BOARD_LEFT, 20);
            }
            this.draw();
        }
    }
    
    public void restartGame(){
        board = new Color[NUM_CELLS][NUM_CELLS];
        turn = Color.black;
        this.draw();
    }
    
    public void draw(){
        UI.clearGraphics();
        UI.setColor(Color.lightGray);
        UI.fillRect(BOARD_LEFT, BOARD_TOP, BOARD_DIM, BOARD_DIM);
        UI.setColor(Color.black);
        double y = BOARD_TOP;
        for (int row=0; row<board.length; row++){
            double x = BOARD_LEFT;
            for (int col=0; col<board[row].length; col++){
                UI.drawRect(x, y, GRID_SIZE, GRID_SIZE);
                if (board[row][col]!= null){
                    UI.setColor(board[row][col]);
                    UI.fillOval(x, y, GRID_SIZE, GRID_SIZE);
                    UI.setColor(Color.black);
                }
                x += GRID_SIZE;
            }
            y += GRID_SIZE;
        }
        if (turn==Color.black){
            UI.drawString("Blacks's turn", BOARD_LEFT, 20);
        }else if (turn==Color.white){
            UI.drawString("Whites's turn", BOARD_LEFT, 20);
        }
    }
    
    public int[] rowCol(double x, double y){
        int row = (int) ((y-BOARD_TOP)/GRID_SIZE);
        int col = (int) ((x-BOARD_LEFT)/GRID_SIZE);
        return new int[]{row, col};
    }
    
    public boolean place(int row, int col){
        if (row<0 || row >=board.length || col<0 || col >=board[row].length) { return false; }
        if (!turn.equals(Color.black) && !turn.equals(Color.white)){ return false; }
        if (board[row][col]!= null) { return false; }
        board[row][col] = turn;
        return true;
    }
    
    public boolean on(double x, double y){
        return (y>=BOARD_TOP && y < BOARD_BOT  && x>=BOARD_LEFT && x < BOARD_RIGHT);
    }

    public static void main(String[] arguments){
        GoGame obj = new GoGame();
    }        

}

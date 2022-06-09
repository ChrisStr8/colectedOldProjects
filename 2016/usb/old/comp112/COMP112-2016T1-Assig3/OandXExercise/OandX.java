// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment
 * Name:christopher straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;

/** 
 * O's and X's game.
 * Displays a O's and X's board, and then allows the players
 * to take turns putting O's or X's on the board.
 * Doesn't allow a player to put their mark on a square that is already filled
 * After each turn, it checks the board to see if the player has won at that place.
 * Has a "Restart" button that resets the game to the beginning
 * Requires input from button and mouse.
 */

public class OandX{

    /** the board */
    private OandXBoard board = new OandXBoard();

    /** Whose turn is it, using O and X for the two values */
    private Symbol turn = Symbol.X;

    /** Whether the current game has finished
     */
    private boolean finished;

    /** Construct a new OandX object */
    public OandX(){
        UI.initialise();
        UI.addButton("Restart", this::restartGame);
        UI.addButton("Quit", UI::quit);
        UI.setMouseListener(this::doMouse);
        UI.setWindowSize(600,500);
        UI.setDivider(0.0);
        this.restartGame();
    }

    public void doMouse(String action, double x, double y){
        //Trace.println("Mouse "+action +" at "+ x +"/"+y);
        String outcome = null;
        if (action.equals("released")){
            // play turn if game not yet finished, and it is a valid place
            if (finished) { return; }
            if (!board.on(x, y)){ return; }
            int[] rowCol = board.rowCol(x,y);
            int row = rowCol[0];
            int col = rowCol[1];
            if (!board.place(turn, row, col)){ return; }
            if (board.checkWin(row, col)){
                finished = true;
                outcome = turn.toString();
            }
            else if (board.finished()){
                finished = true;
                outcome = "draw";
            }
            else {
                turn = turn.other();
            }
            redraw(outcome);
        }
    }

    /** Restart: set board to empty, reset the turn, redraw the board */
    public void restartGame(){
        board.reset();
        turn = Symbol.X;
        finished = false;
        redraw(null);
    }

    /** Redraw the board and the current O's and X's*/
    public void redraw(String outcome){
        UI.clearGraphics();
        board.draw();
        if (!finished) { //Not finished: say whose turn is next
            UI.setFontSize(18);
            UI.drawString(this.turn+"'s turn", OandXBoard.Left, 20);
        }
        else { // report the outcome 
            UI.setFontSize(48);
            if (outcome.equals("draw")){
                UI.drawString("It was a draw", OandXBoard.Left, OandXBoard.Bot + 60);
            }
            else {
                UI.drawString("Win for "+outcome, OandXBoard.Left, OandXBoard.Bot + 60);
            }
        }
    }

    public static void main(String[] arguments){
        new OandX();
    }        

}

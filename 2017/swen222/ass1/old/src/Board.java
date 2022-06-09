import java.util.HashMap;

/**
 * Created by chris on 3/08/17.
 */
public class Board {
    private GPiece[][] board;
    private HashMap<String, GPiece>

    public Board(){
        board = new GPiece[10][10];
    }

    public boolean create(GPiece piece){
        if(piece.isColour() && board[3][3]!=null){
            board[3][3] = piece;
            return true;
        }else if(!piece.isColour() && board[8][8]!=null){
            board[8][8] = piece;
            return true;
        }
        return false;
    }

    public void rotate(GPiece piece, int rotation){

    }

    public GPiece[][] getBoard(){
        return board;
    }
}

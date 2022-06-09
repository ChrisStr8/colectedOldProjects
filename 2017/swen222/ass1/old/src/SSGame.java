import java.util.HashMap;

/**
 * Created by chris on 3/08/17.
 */
public class SSGame {
    private boolean turn; // true is yellow, false is green
    private boolean created;
    private Board board;
    private HashMap<String, GPiece> yellowPeices;
    private HashMap<String, GPiece> yellowGreen;

    public SSGame(){
        turn = true;
        created = false;
        board = new Board();
    }

    public void pass(){
        if(created) {
            created = false;
            turn = !turn;
        }else{
            created = true;
        }
    }

    public Board getBoard() {
        return board;
    }
}

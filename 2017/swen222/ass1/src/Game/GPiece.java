package Game;

/**
 * Created by chris on 3/08/17.
 */
public class GPiece extends Piece {
    public boolean created;
    public boolean cemetery;
    private Symbol up;
    private Symbol down;
    private Symbol left;
    private Symbol right;

    public GPiece(String name, boolean colour, Symbol up, Symbol right, Symbol down, Symbol left){
        super(name, colour);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        created = false;
        cemetery = false;
    }

    public void rotate(){
        Symbol temp = up;
        up = right;
        right = down;
        down = left;
        left = temp;
    }

    public void undoRotate(){
        Symbol temp = up;
        up = left;
        left = down;
        down = right;
        right = temp;
    }

    public Symbol up() {
        return up;
    }

    public Symbol down() {
        return down;
    }

    public Symbol left() {
        return left;
    }

    public Symbol right() {
        return right;
    }
}
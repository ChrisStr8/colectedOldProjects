/**
 * Created by chris on 3/08/17.
 */
public class GPiece extends Piece{
    private Symbol up;
    private Symbol down;
    private Symbol left;
    private Symbol right;

    public GPiece(String name, boolean colour, Symbol up, Symbol down, Symbol left, Symbol right){
        super(name, colour);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void rotate(){
        Symbol temp = up;
        up = right;
        right = down;
        down = left;
        left = temp;
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
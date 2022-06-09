package SSGame.Game;

/**
 * Created by chris on 3/08/17.
 * the GPiece class stores information on the state of each game piece in the SSGame
 * if created is false then the GPiece is not currently on the board
 * if cemetery is true then the GPiece as been sent to the cemetery i.e. it has been taken out of the game
 */
public class GPiece{
    public boolean created;
    public boolean cemetery;

    private String name;
    private boolean colour; // true is yellow false is green
    private Symbol up;
    private Symbol down;
    private Symbol left;
    private Symbol right;


    public GPiece(String name, boolean colour, Symbol up, Symbol right, Symbol down, Symbol left){
        this.name = name;
        this.colour = colour;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        created = false;
        cemetery = false;
    }

    /**
     * rotates the piece 90 degrees i.e. up->right right->down down->left left->up
     */
    public void rotate(){
        Symbol temp = up;
        up = left;
        left = down;
        down = right;
        right = temp;
    }

    public void undoRotate(){
        Symbol temp = up;
        up = right;
        right = down;
        down = left;
        left = temp;
    }

    /**
     * returns the boolean indicating which players piece this is. true is Yellow false is Green.
     * @return  colour boolean
     */
    public boolean isColour() {
        return colour;
    }

    /**
     * returns the string representing the piece
     * @return name string
     */
    public String getName() {
        return name;
    }

    /**
     * returns the top symbol
     * @return top symbol
     */
    public Symbol up() {
        return up;
    }

    /**
     * returns the bottom symbol
     * @return bottom symbol
     */
    public Symbol down() {
        return down;
    }

    /**
     * returns the left symbol
     * @return left symbol
     */
    public Symbol left() {
        return left;
    }

    /**
     * returns the right symbol
     * @return right symbol
     */
    public Symbol right() {
        return right;
    }
}

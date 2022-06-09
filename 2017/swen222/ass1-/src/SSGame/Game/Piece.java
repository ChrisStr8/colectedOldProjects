package SSGame.Game;

/**
 * Created by chris on 3/08/17.
 * Piece objects store information about playing pieces in the game
 */
public class Piece {
    private String name;
    private boolean colour; // true is yellow false is green


    public Piece(String name, boolean colour) {
        this.name = name;
        this.colour = colour;
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
}

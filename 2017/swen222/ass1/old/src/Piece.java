/**
 * Created by chris on 3/08/17.
 */
public class Piece {
    private String name;
    private boolean colour; // true is yellow false is green


    public Piece(String name, boolean colour) {
        this.name = name;
        this.colour = colour;
    }

    public boolean isColour() {
        return colour;
    }

    public String getName() {
        return name;
    }
}

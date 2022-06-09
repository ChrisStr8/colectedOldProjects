package SSGame.resources;

import SSGame.Game.GPiece;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum ImgResources {
    YELLOWP("yellowPlayer.png"),
    GREENP("greenPlayer.png");

    public final Image img;

    ImgResources(String resourceName) {
        try{ img = ImageIO.read(ImgResources.class.getResource(resourceName)); }
        catch (IOException e){ throw new Error(e); }
    }

    /**
     * prints the given piece at the given x, y board position rotated by the given
     * amount
     * 1 = 0 degrees
     * 2 = 90 degrees
     * 3 = 180 degrees
     * 4 = 270 degrees
     * @param g
     * @param x x position
     * @param y y position
     * @param piece the GPiece to draw
     * @param rotation
     */
    public static void paintSquare(Graphics g, int x, int y, GPiece piece, int rotation){
        int nx = x * 50;//adjust for the size of the squares
        int ny = y * 50;

        nx += 10;//offset the board
        ny += 10;
        paintSquareLoc(g, nx, ny, piece, rotation, 50);
    }

    /**
     * paints piece at the given x, y board position, piece will be scaled by scale
     * and will be at the given rotation.
     * 1 = 0 degrees
     * 2 = 90 degrees
     * 3 = 180 degrees
     * 4 = 270 degrees
     * @param g
     * @param x x position
     * @param y y position
     * @param piece the GPiece to draw
     * @param rotation the amount of rotation
     * @param scale the amount to scale the piece
     */
    public static void paintSquareScaled(Graphics g, int x, int y, GPiece piece, int rotation, int scale){
        int nx = x * 50;//adjust for the size of the squares
        int ny = y * 50;

        nx += 10 - (scale/2);
        ny += 10 - (scale/2);
        paintSquareLoc(g, nx, ny, piece, rotation, 50+scale);
    }

    /**
     * paints the given piece amount number of pixels in the given direction away
     * from the x, y board position.
     * direction:
     * 1 = up
     * 2 = right
     * 3 = down
     * 4 = left
     * @param g
     * @param x x location
     * @param y y location
     * @param piece the GPiece to draw
     * @param direction direction to move the piece
     * @param amount amount to move the piece
     */
    public static void paintSquareMoved(Graphics g, int x, int y, GPiece piece, int direction, int amount){
        int nx = x * 50;//adjust for the size of the squares
        int ny = y * 50;

        nx += 10;//offset the board
        ny += 10;
        if(direction==1) {//up
            ny -= amount;
        }else if(direction==2) {//right
            nx += amount;
        }else if(direction==3) {//down
            ny += amount;
        }else if(direction==4) {//left
            nx -= amount;
        }
        paintSquareLoc(g, nx, ny, piece, 1, 50);
    }

    /**
     * paints piece at the given x, y location. the piece will be draw rotated to
     * the given position
     * 1 = 0 degrees
     * 2 = 90 degrees
     * 3 = 180 degrees
     * 4 = 270 degrees
     * the piece wil be size pixels high and wide
     * @param g
     * @param x x location
     * @param y y location
     * @param piece the GPiece to draw
     * @param rotation the amount of rotation
     * @param size the size of the piece
     */
    public static void paintSquareLoc(Graphics g, int x, int y, GPiece piece, int rotation, int size){
        if(piece==null) return;
        g.setColor(Color.BLACK);
        g.fillRect(x, y, size, size);
        if(Character.isUpperCase(piece.getName().charAt(0))){
            g.setColor(Color.GREEN);
        }else {
            g.setColor(Color.YELLOW);
        }
        g.fillOval(x, y, size-1, size-1);

        g.setColor(Color.RED);
        if(rotation==1) {
            paintSymbol(g, x, y, piece.up().getSymbol(), 1, size);
            paintSymbol(g, x, y, piece.right().getSymbol(), 2, size);
            paintSymbol(g, x, y, piece.down().getSymbol(), 3, size);
            paintSymbol(g, x, y, piece.left().getSymbol(), 4, size);
        }else if(rotation==2){
            paintSymbol(g, x, y, piece.up().getSymbol(), 2, size);
            paintSymbol(g, x, y, piece.right().getSymbol(), 3, size);
            paintSymbol(g, x, y, piece.down().getSymbol(), 4, size);
            paintSymbol(g, x, y, piece.left().getSymbol(), 1, size);
        }else if(rotation==3){
            paintSymbol(g, x, y, piece.up().getSymbol(), 3, size);
            paintSymbol(g, x, y, piece.right().getSymbol(), 4, size);
            paintSymbol(g, x, y, piece.down().getSymbol(), 1, size);
            paintSymbol(g, x, y, piece.left().getSymbol(), 2, size);
        }else if(rotation==4){
            paintSymbol(g, x, y, piece.up().getSymbol(), 4, size);
            paintSymbol(g, x, y, piece.right().getSymbol(), 1, size);
            paintSymbol(g, x, y, piece.down().getSymbol(), 2, size);
            paintSymbol(g, x, y, piece.left().getSymbol(), 3, size);
        }else {
            System.out.println("rotation should be between 1-4 not "+rotation);
            paintSymbol(g, x, y, piece.up().getSymbol(), 1, size);
            paintSymbol(g, x, y, piece.right().getSymbol(), 2, size);
            paintSymbol(g, x, y, piece.down().getSymbol(), 3, size);
            paintSymbol(g, x, y, piece.left().getSymbol(), 4, size);
        }
        if(piece.changed){
            g.setColor(Color.BLUE);
            g.drawLine(x, y, x+(size-1), y+(size-1));
            g.drawLine(x+(size-1), y, x, y+(size-1));
        }else {
            g.setColor(Color.BLACK);
        }
        g.drawRect(x, y, size-1, size-1);
    }

    /**
     * paints the x, y position on the board either gray or white depending on dark
     * @param g
     * @param x x position
     * @param y y position
     * @param dark grey if true white if false
     */
    public static void paintBlank(Graphics g, int x, int y, boolean dark){
        int nx = x * 50;//adjust for the size of the squares
        int ny = y * 50;

        nx += 10;//offset the board
        ny += 10;
        paintBlankLoc(g, nx, ny, 50, dark);
    }

    /**
     * paints a square of size at the given x, y location, the square is either grey
     * or white depending on dark
     * @param g
     * @param x x location
     * @param y y location
     * @param size size of the square
     * @param dark grey if true white if false
     */
    private static void paintBlankLoc(Graphics g, int x, int y, int size, boolean dark){
        if(dark){
            g.setColor(Color.GRAY);
        }else {
            g.setColor(Color.white);
        }
        g.fillRect(x, y, size, size);
    }

    /**
     * paints a sword or shield symbol
     * @param g
     * @param x the x position of the piece to paint the symbol on to
     * @param y the y position of the piece to paint the symbol on to
     * @param symbol the symbol to paint
     * @param direction which side of the piece to print the symbol on
     * @param size the size of the piece
     */
    private static void paintSymbol(Graphics g, int x, int y, String symbol, int direction, int size){
        int symbolWith = size/8;
        int halfSize = size/2;
        int center = halfSize-(symbolWith/2);
        if(symbol.equals("-")){
            if(direction==1) {//up
                g.fillRect(x + center, y, symbolWith, halfSize);
            }else if(direction==2){//right
                g.fillRect(x + halfSize, y + center, halfSize, symbolWith);
            }else if(direction==3){//down
                g.fillRect(x + center, y + halfSize, symbolWith, halfSize);
            }else if(direction==4){//left
                g.fillRect(x, y + center, halfSize, symbolWith);
            }
        }else if(symbol.equals("#")){
            if(direction==1) {//up
                g.fillRect(x, y, size, symbolWith);
            }else if(direction==2){//right
                g.fillRect(x + size-symbolWith, y, symbolWith, size);
            }else if(direction==3){//down
                g.fillRect(x, y + size-symbolWith, size, symbolWith);
            }else if(direction==4){//left
                g.fillRect(x, y, symbolWith, size);
            }
        }
    }
}
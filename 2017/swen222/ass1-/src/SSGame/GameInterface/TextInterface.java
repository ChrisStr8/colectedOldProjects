package SSGame.GameInterface;

import SSGame.Game.*;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by chris on 3/08/17.
 * Simple text interface for the Sword and Shield game
 */
public class TextInterface {
    private SSGame game;

    public TextInterface(){
        game = new SSGame();
        redraw();
        Scanner cls = new Scanner(System.in);
        while(!game.isGameOver()){
            String command = cls.next();
            switch (command) {
                case "create": {
                    String letter = cls.next();
                    int rotation = 0;
                    try {
                        rotation = Integer.parseInt(cls.next());
                    }catch (NumberFormatException e){
                        System.out.println("invalid command");
                        break;
                    }
                    if (!game.create(letter, rotation)) {
                        System.out.println("that piece can not be created");
                    } else {
                        redraw();
                    }
                    break;
                }
                case "rotate": {
                    String letter = cls.next();
                    int rotation = 0;
                    try {
                        rotation = Integer.parseInt(cls.next());
                    }catch (NumberFormatException e){
                        System.out.println("invalid command");
                        break;
                    }
                    if (!game.rotate(letter, rotation)) {
                        System.out.println("that piece can not be rotated");
                    } else {
                        redraw();
                    }
                    break;
                }
                case "move": {
                    String letter = cls.next();
                    String direction = cls.next();
                    if (!game.move(letter, direction, false)) {
                        System.out.println("that piece can not be moved");
                    } else {
                        redraw();
                    }
                    break;
                }
                case "pass":
                    game.pass();
                    redraw();
                    break;
                case "undo":
                    game.undo();
                    redraw();
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
        if(game.greenDead() && game.yellowDead()){
            System.out.println("DRAW");
        }else if(game.greenDead()){
            System.out.println("YELLOW WON");
        }else if(game.yellowDead()) {
            System.out.println("GREEN WON");
        }
    }

    /**
     * draws the board in the console.
     * above the board there is an indicator for which players turn it is and an indicator for whether the player has
     * created a piece this turn. to each side of the board the piece that can be played by each player are displayed
     * the board is a 10 by 10 grid of squares. 2 by 2 areas in the top left and bottom right count as off the board
     * they are filled with Os. the face squares are indicated by either the words Yface or Gface depending on which
     * player they belong to. | or - indicates a sword and # indicates a shield.
     */
    public void redraw(){
        Queue<GPiece> yellowPieces= game.freePiecesY();
        Queue<GPiece> greenPieces= game.freePiecesG();
        System.out.print("Yellow Pieces:             ");
        if(game.turn){
            System.out.print("Yellows turn");
        }else {
            System.out.print("Greens turn");
        }
        System.out.print("                                                                                Green Pieces:\n");
        System.out.println("                           Created: "+game.created);
        Board board = game.getBoard();
        GPiece[][] loc = board.getBoard();
        System.out.print("                           |");
        for(int i=0; i<10; i++){
            System.out.print("| - - - |");
        }
        System.out.print("|\n");
        for(int y=0; y<10; y++){ //iterate through columns
            GPiece[] yPieces = new GPiece[3];
            GPiece[] gPieces = new GPiece[3];
            for(int j=0; j<3; j++){
                if(!yellowPieces.isEmpty()){
                    yPieces[j] = yellowPieces.poll();
                }
                if(!greenPieces.isEmpty()){
                    gPieces[j] = greenPieces.poll();
                }
            }

            for(int i=0; i<3; i++) { //loop three times to draw each line of the row
                System.out.print("||");
                drawPiece(yPieces[0], i);
                drawPiece(yPieces[1], i);
                drawPiece(yPieces[2], i);

                for (int x = 0; x < 10; x++) { //iterate through rows
                    if(board.isFaceY(x, y) || board.isFaceG(x, y)){
                        if(x==1 && y==1){
                            System.out.print(" Yface ||");
                        }else if(x==8 && y==8){
                            System.out.print(" Gface ||");
                        }else {
                            System.out.print("OOOOOOO||");
                        }
                    }else if(x==2 && y==2 && loc[x][y]==null){
                        System.out.print("GGGGGGG||");
                    }else if(x==7 && y==7 && loc[x][y]==null){
                        System.out.print("YYYYYYY||");
                    }else {
                        drawPiece(loc[x][y], i);
                    }
                }
                drawPiece(gPieces[0], i);
                drawPiece(gPieces[1], i);
                drawPiece(gPieces[2], i);
                System.out.print("\n");
            }
            System.out.print("                           |");
            for(int i=0; i<10; i++){
                System.out.print("| - - - |");
            }
            System.out.print("|\n");
        }
        System.out.print("\n");
    }

    /**
     * prints the information about a piece to the console.
     * drawPiece will print one line at a time, this is to allow other pieces to be drawn on the same line.
     * each piece has 3 lines to print so the method will need to be called 3 times for each piece.
     * @param piece the piece to draw
     * @param line int between 0 and 2 specifying which line to draw
     */
    public void drawPiece(GPiece piece, int line){
        if(piece==null){
            System.out.print("       ||");
        }else {
            if (line == 0) {
                System.out.print("   " + verticalS(piece.up()) + "   ||");
            } else if (line == 1) {
                System.out.print(" " + horizontalS(piece.left()) + " " + piece.getName() + " " +
                        horizontalS(piece.right()) + " ||");
            } else if (line == 2) {
                System.out.print("   " + verticalS(piece.down()) + "   ||");
            }
        }
    }

    /**
     * converts the Symbol into the string that represents it for the top and bottom places
     * @param symbol symbol to be converted
     * @return representative string
     */
    public String verticalS(Symbol symbol){
        switch (symbol.getSymbol()) {
            case "-":
                return "|";
            case "#":
                return "#";
            default:
                return " ";
        }
    }

    /**
     * converts the Symbol into the string that represents it for the left and right places
     * @param symbol symbol to be converted
     * @return representative string
     */
    public String horizontalS(Symbol symbol){
        switch (symbol.getSymbol()) {
            case "-":
                return "-";
            case "#":
                return "#";
            default:
                return " ";
        }
    }

    public static void main(String[] args) {
        new TextInterface();
    }
}

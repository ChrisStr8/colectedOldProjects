package GameInterface;
import Game.*;

import java.util.Scanner;

/**
 * Created by chris on 3/08/17.
 */
public class TextInterface {
    private SSGame game;

    public TextInterface(){
        game = new SSGame();
        game.getBoard().addToLocation(game.findPiece("a"), 4, 4);
        game.getBoard().addToLocation(game.findPiece("c"), 2, 2);
        redraw();
        Scanner cls = new Scanner(System.in);
        while(!game.isGameOver()){
            String comand = cls.next();
            if(comand.equals("create")){
                String letter = cls.next();
                int rotation = Integer.parseInt(cls.next());
                game.create(letter, rotation);
            }else if(comand.equals("rotate")){
                String letter = cls.next();
                int rotation = Integer.parseInt(cls.next());
                game.rotate(letter, rotation);
            }else if(comand.equals("move")){
                String letter = cls.next();
                String direction = cls.next();
                game.move(letter, direction);
            }else if(comand.equals("pass")){
                game.pass();
            }else{
                System.out.println("invalid command");
            }
            redraw();
        }
        if(game.winner){
            System.out.println("YELLOW WON");
        }else {
            System.out.println("GREEN WON");
        }
    }

    public void redraw(){
        if(game.turn){
            System.out.println("Yellows turn");
        }else {
            System.out.println("Greens turn");
        }
        Board board = game.getBoard();
        GPiece[][] loc = board.getBoard();
        System.out.print("|");
        for(int i=0; i<10; i++){
            System.out.print("| - - - |");
        }
        System.out.print("|\n");
        for(int y=0; y<10; y++){
            for(int i=0; i<3; i++) {
                System.out.print("||");
                for (int x = 0; x < 10; x++) {
                    if(board.isFaceY(x, y) || board.isFaceG(x, y)){
                        System.out.print("OOOOOOO||");
                    }else if(x==2 && y==2 && loc[x][y]==null){
                        System.out.print("GGGGGGG||");
                    }else if(x==7 && y==7 && loc[x][y]==null){
                        System.out.print("YYYYYYY||");
                    }else {
                        if(loc[x][y]!=null){
                            if(i==0){
                                System.out.print("   "+verticalS(loc[x][y].up())+"   ||");
                            }else if(i==1){
                                System.out.print(" "+horizontalS(loc[x][y].left())+" "+loc[x][y].getName()+" "+
                                        horizontalS(loc[x][y].right())+" ||");
                            }else {
                                System.out.print("   "+verticalS(loc[x][y].down())+"   ||");
                            }
                        }else {
                            System.out.print("       ||");
                        }
                    }
                }
                System.out.print("\n");
            }
            System.out.print("|");
            for(int i=0; i<10; i++){
                System.out.print("| - - - |");
            }
            System.out.print("|\n");
        }
    }

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

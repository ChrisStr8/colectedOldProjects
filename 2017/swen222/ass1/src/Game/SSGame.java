package Game;

import Game.Board;
import Game.GPiece;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by chris on 3/08/17.
 */
public class SSGame {
    public boolean turn; // true is yellow, false is green
    private boolean created; // stores whether the player has completed the creation step
    private Board board;
    private HashMap<String, GPiece> yellowPieces = new HashMap<>();
    private HashMap<String, GPiece> greenPieces = new HashMap<>();
    private List<String> changed = new ArrayList<>();
    private Stack<String> actions = new Stack<>();

    public boolean winner;

    public SSGame(){
        turn = true;
        created = false;
        board = new Board();
        setupPieces();
    }

    private void setupPieces(){
        File pieces = new File("pieces.txt");
        try {
            Scanner sc = new Scanner(pieces);
            while(sc.hasNext()){
                String line = sc.nextLine();
                String[] parts = line.split("");
                Symbol up = new Symbol(parts[1]);
                Symbol right = new Symbol(parts[2]);
                Symbol down = new Symbol(parts[3]);
                Symbol left = new Symbol(parts[4]);
                yellowPieces.put(parts[0], new GPiece(parts[0], true, up, left, down, right));
                greenPieces.put(parts[0].toUpperCase(), new GPiece(parts[0].toUpperCase(), false, up, left, down, right));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean create(String letter){
        if(created) return false;
        GPiece piece = findPiece(letter);
        if(piece==null) return false;
        if(piece.created) return false;
        created = true;
        piece.created = true;

        return board.create(piece);
    }

    public boolean create(String letter, int rotation){
        if(created) return false;
        GPiece piece = findPiece(letter);
        if(piece==null) return false;
        if(piece.created) return false;
        created = true;
        piece.created = true;

        rotate(piece, rotation);
        return board.create(piece);
    }

    public void rotate(String letter, int rotation){
        if(changed.contains(letter))return;
        GPiece piece = findPiece(letter);
        if(piece==null) return;

        rotate(piece, rotation);
        changed.add(letter);
    }

    public void rotate(GPiece piece, int rotation){
        if(changed.contains(piece.getName()))return;

        changed.add(piece.getName());
        if(rotation==90){
            piece.rotate();
        }else if(rotation==180){
            piece.rotate();
            piece.rotate();
        }else if(rotation==270){
            piece.rotate();
            piece.rotate();
            piece.rotate();
        }else {
            for(int i=0; i<=rotation; i++){
                piece.rotate();
            }
        }
    }

    public void move(String letter, String direction){
        if(changed.contains(letter))return;
        GPiece piece = findPiece(letter);
        if(piece==null) return;
        changed.add(piece.getName());

        String location = board.findPiece(piece);
        if (location.equals("null")) return;
        String[] xy = location.split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);

        if (!board.move(x, y, direction)) {
            int x2 = -1;
            int y2 = -1;
            switch (direction) {
                case "up":
                    x2 = x;
                    y2 = 0;
                    break;
                case "right":
                    x2 = 9;
                    y2 = y;
                    break;
                case "down":
                    x2 = x;
                    y2 = 9;
                    break;
                case "left":
                    x2 = 0;
                    y2 = y;
                    break;
            }
            board.remove(x2, y2);
            board.move(x, y, direction);
        }
    }

    public void pass(){
        if(created) {
            created = false;
            changed.clear();
            turn = !turn;
        }else{
            created = true;
        }
    }

    public GPiece findPiece(String letter){
        GPiece piece;
        if(turn){
            piece = yellowPieces.get(letter);
        }else{
            piece = greenPieces.get(letter);
        }
        return piece;
    }


    public boolean isGameOver(){
        return false;
    }

    public Board getBoard() {
        return board;
    }
}

package SSGame.Game;

import javax.annotation.Resources;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by chris on 3/08/17.
 * Sword and Shield Game.
 * SSGame manages the GPieces and Board
 * any game actions should go through SSGame
 */
public class SSGame {
    public boolean turn; // true is yellow, false is green
    public boolean created; // stores whether the player has completed the creation step
    private Board board;
    private HashMap<String, GPiece> yellowPieces = new HashMap<>();
    private HashMap<String, GPiece> greenPieces = new HashMap<>();
    private List<String> changed = new ArrayList<>();
    private Stack<String> actions = new Stack<>();
    private Stack<GPiece[][]> oldBoards = new Stack<>();

    public SSGame(){
        turn = true;
        created = false;
        board = new Board();
        setupPieces();
    }

    /**
     * loads the pieces from a file called  pieces.txt.
     * each line of the file has information for one piece formatted as:
     * name (1 character)
     * up symbol
     * right symbol
     * down symbol
     * left symbol
     * there is no spacing between each symbol
     * the green pieces are loaded using the same information but the name is capitalised
     */
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
                yellowPieces.put(parts[0], new GPiece(parts[0], true, up, right, down, left));
                greenPieces.put(parts[0].toUpperCase(), new GPiece(parts[0].toUpperCase(), false, up, right, down, left));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds a piece to the board at its players creation space, then rotates the piece the specified amount.
     * if the piece is not owned by the player who is taking their turn then returns false.
     * if the piece is already played or in the cemetery then returns false.
     *
     * @param letter identifying string for the letter to be created
     * @param rotation the amount to rotate the piece
     * @return true if creation was successful, otherwise returns false
     */
    public boolean create(String letter, int rotation){
        if(created) return false;
        GPiece piece = findPiece(letter);
        if(piece==null) return false;
        if(piece.created) return false;
        if(piece.isColour()!=turn) return false;

        GPiece[][] oldBoard = board.boardSave();

        if(board.create(piece)){
            created = true;
            rotate(piece, rotation);
            changed.remove(letter);
            piece.created = true;
            actions.push("create,"+piece.getName());
            oldBoards.push(oldBoard);
            if(piece.isColour()){
                reactionCheck(7, 7);
            }else{
                reactionCheck(2, 2);
            }
            return true;
        }
        return false;
    }

    /**
     * finds the piece with the name stored in letter then rotates it by the given amount
     * @param letter identifier for the piece
     * @param rotation amount of rotation
     * @return true if successful
     */
    public boolean rotate(String letter, int rotation){
        if(changed.contains(letter))return false;
        GPiece piece = findPiece(letter);
        if(piece==null) return false;

        return rotate(piece, rotation);
    }

    /**
     * rotates the piece by the given amount of rotation.
     * the amount of rotation can be either
     * (0/90/180/270) (amount of degrees to rotate)
     * or
     * an int specifying the number of times to rotate the piece
     * the piece wont be rotated if its not the turn of the pieces player or
     * the piece has already been changed this turn
     *
     * @param piece the piece to be rotated
     * @param rotation the amount of rotation
     * @return true if the piece is successfully rotated
     */
    private boolean rotate(GPiece piece, int rotation){
        if(!created)return false;
        if(changed.contains(piece.getName()))return false;
        if(piece.isColour()!=turn) return false;
        if(!piece.created)return false;

        changed.add(piece.getName());
        if(rotation==90){
            piece.rotate();
            actions.push("rotate,"+piece.getName()+","+1);
        }else if(rotation==180){
            piece.rotate();
            piece.rotate();
            actions.push("rotate,"+piece.getName()+","+2);
        }else if(rotation==270){
            piece.rotate();
            piece.rotate();
            piece.rotate();
            actions.push("rotate,"+piece.getName()+","+3);
        }else {
            for(int i=1; i<=rotation; i++){
                piece.rotate();
            }
            actions.push("rotate,"+piece.getName()+","+rotation);
        }
        String[] location = board.findPiece(piece).split(",");
        reactionCheck(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
        return true;
    }

    /**
     * moves the piece with the name letter in the specified direction.
     * if there is a piece in the location to be moved to then it will be moved down
     * the piece wont be moved if:
     *  it has already been changed this turn
     *  it is not on the board
     *  its not it's players turn
     * @param letter identifier for the piece
     * @param direction the direction to move in e.g. left
     * @return true if the piece is successfully moved
     */
    public boolean move(String letter, String direction, boolean reaction){
        if(!created)return false;
        if(!reaction && changed.contains(letter))return false;
        GPiece piece = findPiece(letter);
        if(piece==null) return false;
        if(!reaction && piece.isColour()!=turn) return false;
        if(!piece.created) return false;

        String location = board.findPiece(piece);
        if (location.equals("null")) return false;

        String[] xy = location.split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);

        GPiece[][] oldBoard = board.boardSave();

        if (!board.move(x, y, direction)) {
            int x2 = -1;
            int y2 = -1;
            switch (direction) {
                case "up":
                    x2 = x;
                    if(x<2){
                        y2 = 2;
                    }else {
                        y2 = 0;
                    }
                    break;
                case "right":
                    y2 = y;
                    if(y>7){
                        x2 = 7;
                    }else {
                        x2 = 9;
                    }
                    break;
                case "down":
                    x2 = x;
                    if(x>7){
                        y2 = 7;
                    }else {
                        y2 = 9;
                    }
                    break;
                case "left":
                    y2 = y;
                    if(y<2){
                        x = 2;
                    }else {
                        x2 = 0;
                    }
                    break;
            }
            actions.push("move,remove,"+board.getLocation(x2,y2).getName()+x2+","+y2);
            GPiece piece2 = board.getLocation(x2,y2);
            piece2.cemetery = true;
            piece2.created = false;
            board.remove(x2, y2);
            board.move(x, y, direction);
        }
        if(!reaction) {
            changed.add(piece.getName());
        }
        actions.push("move,"+letter+","+direction);
        oldBoards.push(oldBoard);
        if(direction.equals("up") || direction.equals("down")){
            reactionCheckLine(true, x);
        }else {
            reactionCheckLine(false, y);
        }
        return true;
    }

    /**
     * moves to the next stage of the game. if the current player hasn't created yet it will set created to true,
     * if they have it will switch to the next players turn.
     */
    public void pass(){
        if(created) {
            StringBuilder message = new StringBuilder("passTurn");
            for (String s : changed) {
                message.append(",").append(s);
            }
            actions.push(message.toString());
            created = false;
            changed.clear();
            turn = !turn;
        }else{
            created = true;
            actions.push("pass");
        }
    }

    private void remove(int x, int y){
        GPiece[][] oldBoard = board.boardSave();
        GPiece piece = board.getLocation(x, y);
        if(board.remove(x, y)){
            piece.created = false;
            piece.cemetery = true;
            actions.push("remove,"+x+","+y);
            oldBoards.push(oldBoard);
        }
    }

    /**
     * reverses the last action and any results of that action
     */
    public void undo(){
        if(actions.empty()){
            System.out.println("nothing to undo");
            return;
        }
        System.out.print("undo ");
        String[] action = actions.pop().split(",");
        switch (action[0]) {
            case "pass":
                System.out.print("pass\n");
                created = false;
                break;
            case "passTurn":
                System.out.print("passTurn\n");
                created = true;
                turn = !turn;
                changed.addAll(Arrays.asList(action).subList(1, action.length));
                break;
            case "rotate": {
                System.out.print("rotate\n");
                GPiece piece = findPiece(action[1]);
                changed.remove(action[1]);
                changed.remove(piece.getName());
                for (int i = 0; i < Integer.parseInt(action[2]); i++) {
                    piece.undoRotate();
                }
                break;
            }
            case "move":
                System.out.print("move\n");
                if (action[1].equals("remove")) {
                    System.out.print("Remove\n");
                    undo();
                    GPiece piece = findPiece(action[2]);
                    piece.cemetery = false;
                    piece.created = true;
                } else {
                    changed.remove(action[1]);
                    board.setBoard(oldBoards.pop());
                }
                break;
            case "create": {
                undo();
                System.out.print("create\n");
                GPiece piece = findPiece(action[1]);
                created = false;
                piece.created = false;
                board.setBoard(oldBoards.pop());
                break;
            }
            case "remove": {
                System.out.print("Remove\n");
                board.setBoard(oldBoards.pop());
                GPiece piece = board.getLocation(Integer.parseInt(action[1]), Integer.parseInt(action[2]));
                piece.cemetery = false;
                piece.created = true;
                break;
            }
        }
        if(!actions.empty()) {
            String nextAction = actions.peek();
            if (nextAction.equals("reaction")) {
                actions.pop();
                undo();
            }
        }
    }

    /**
     * locate and return the GPiece with the name letter
     * @param letter identifying string for desired piece
     * @return GPiece with the correct letter string. will return null if the GPiece doesn't exist
     */
    public GPiece findPiece(String letter){
        GPiece piece;
        if(yellowPieces.containsKey(letter)){
            piece = yellowPieces.get(letter);
        }else{
            piece = greenPieces.get(letter);
        }
        return piece;
    }

    /**
     * checks for reaction along an entire line of the board, this could be either a row or column.
     * @param row indicates whether to iterate through a row or column
     * @param location either the x or y location depending on row
     */
    private void reactionCheckLine(boolean row, int location){
        //System.out.println("checkline row: "+row+" location: "+location);
        if(row){
            for(int x=0; x<10; x++){
                reactionCheck(x, location);
            }
        }else{
            for(int y=0; y<10; y++){
                reactionCheck(location, y);
            }
        }
    }

    /**
     * checks for any reactions between the piece at the given location and any pieces touching it
     * @param x the x position
     * @param y the y position
     */
    private void reactionCheck(int x, int y){
        //System.out.println("check x:"+x+" y:"+y);
        GPiece piece = board.getLocation(x, y);
        if(piece==null)return;
        String pUp = piece.up().getSymbol();
        String pDown = piece.down().getSymbol();
        String pLeft = piece.left().getSymbol();
        String pRight = piece.right().getSymbol();

        String up = "";
        String down = "";
        String left = "";
        String right = "";
        if(board.getLocation(x, y-1)!=null){
            up = board.getLocation(x, y-1).down().getSymbol();
        }
        if(board.getLocation(x, y+1)!=null){
            down = board.getLocation(x, y+1).up().getSymbol();
        }
        if(board.getLocation(x-1, y)!=null){
            left = board.getLocation(x-1, y).right().getSymbol();
        }
        if(board.getLocation(x+1, y)!=null){
            right = board.getLocation(x+1, y).left().getSymbol();
        }

        checkR(x, y, pUp, up, 1);
        checkR(x, y, pDown, down, 2);
        checkR(x, y, pLeft, left, 3);
        checkR(x, y, pRight, right, 4);
    }

    /**
     * checks for a reaction between piece1 and piece2.
     * side indicates which side of the GPiece at x, y piece1 and piece2 came from, this affects the
     * response to a reaction.
     * 1 = up
     * 2 = down
     * 3 = left
     * 4 = right
     * @param x the x position
     * @param y the y position
     * @param piece1 one of symbol strings from the GPiece at x, y
     * @param piece2 the symbol string touching x, y
     * @param side which sid piece comes from on its GPiece
     */
    private void checkR(int x, int y, String piece1, String piece2, int side){
        if(board.getLocation(x, y)==null)return;
        if(side>4)return;
        int x2, y2;
        if(side==1){//up
            x2 = x;
            y2 = y - 1;
        }else if(side==2){//down
            x2 = x;
            y2 = y + 1;
        }else if(side==3){//left
            x2 = x - 1;
            y2 = y;
        }else {//right
            x2 = x + 1;
            y2 = y;
        }
        switch (piece1) {
            case "#":
                if (piece2.equals("-")) {
                    actions.push("reaction");
                    String direction;
                    if(side==1){//up
                        direction = "up";
                    }else if(side==2){//down
                        direction = "down";
                    }else if(side==3){//left
                        direction = "left";
                    }else {//right
                        direction = "right";
                    }
                    move(board.getLocation(x2, y2).getName(), direction, true);
                }
                break;
            case "-":
                switch (piece2) {
                    case "-":
                        actions.push("reaction");
                        remove(x, y);
                        actions.push("reaction");
                        remove(x2, y2);
                        break;
                    case "#":
                        actions.push("reaction");
                        String direction;
                        if(side==1){//up
                            direction = "down";
                        }else if(side==2){//down
                            direction = "up";
                        }else if(side==3){//left
                            direction = "right";
                        }else {//right
                            direction = "left";
                        }
                        move(board.getLocation(x, y).getName(), direction, true);
                        break;
                    case ".":
                        actions.push("reaction");
                        remove(x2, y2);
                        break;
                }
                break;
            case ".":
                if (piece2.equals("-")) {
                    actions.push("reaction");
                    remove(x, y);
                }
                break;
        }
    }

    /**
     * list all the yellow Pieces not on the board or in the cemetery
     * @return list of GPieces
     */
    public Queue<GPiece> freePiecesY(){
        Queue<GPiece> pieces = new ArrayDeque<>();
        Set<String> yellowLetters = yellowPieces.keySet();
        for(String s : yellowLetters) {
            GPiece piece = yellowPieces.get(s);
            if(!piece.created && !piece.cemetery) {
                pieces.offer(piece);
            }
        }
        return pieces;
    }

    /**
     * list all the green Pieces not on the board or in the cemetery
     * @return list of GPieces
     */
    public Queue<GPiece> freePiecesG(){
        Queue<GPiece> pieces = new ArrayDeque<>();
        Set<String> greenLetters = greenPieces.keySet();
        for(String s : greenLetters) {
            GPiece piece = greenPieces.get(s);
            if(!piece.created && !piece.cemetery) {
                pieces.offer(piece);
            }
        }
        return pieces;
    }

    /**
     * indicates whether the game is over. the game is over if their is a sword touching a the face of either player
     * @return true if the game is over otherwise return false
     */
    public boolean isGameOver(){
        if(yellowDead()){
            return true;
        }else if(greenDead()){
            return true;
        }
        return false;
    }

    /**
     * indicates whether the yellow face is touching a piece with a sword facing it
     * @return true if the yellow face is touching a sword
     */
    public boolean yellowDead(){
        if(board.getLocation(7,8)!=null){
            GPiece piece = board.getLocation(7,8);
            if(piece.right().getSymbol().equals("-")){
                return true;
            }
        }
        if(board.getLocation(8,7)!=null){
            GPiece piece = board.getLocation(8,7);
            if(piece.down().getSymbol().equals("-")){
                return true;
            }
        }
        return false;
    }

    /**
     * indicates whether the green face is touching a piece with a sword facing it
     * @return true if the green face is touching a sword
     */
    public boolean greenDead(){
        if(board.getLocation(2,1)!=null){
            GPiece piece = board.getLocation(2,1);
            if(piece.left().getSymbol().equals("-")){
                return true;
            }
        }
        if(board.getLocation(1,2)!=null){
            GPiece piece = board.getLocation(1,2);
            if(piece.up().getSymbol().equals("-")){
                return true;
            }
        }
        return false;
    }

    /**
     * returns the object containing the board information
     * @return Board object
     */
    public Board getBoard() {
        return board;
    }
}

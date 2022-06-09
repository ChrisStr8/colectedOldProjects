package SSGame.Game;

/**
 * Created by chris on 3/08/17.
 * Board manages the board for SSGame
 * the board is an array of GPieces, null represents no piece
 */
public class Board {
    private GPiece[][] board;

    public Board(){
        board = new GPiece[10][10];
    }

    /**
     * adds a piece to the correct creation square for its player.
     * returns false if the piece can't be placed i.e. it is in the cemetery or there is
     * already a piece in the creation square.
     * the yellow creation square is at 7,7 and the green is at 2,2
     * @param piece the piece to be added
     * @return true if creation was successful
     */
    public boolean create(GPiece piece){
        if(piece.cemetery) return false;
        if (piece.isColour() && board[7][7]==null) {
            board[7][7] = piece;
            piece.created = true;
            return true;
        } else if (!piece.isColour() && board[2][2]==null) {
            board[2][2] = piece;
            piece.created = true;
            return true;
        }
        return false;
    }


    /**
     * add sets the specified x y location on the board to be the given GPiece
     * @param piece the piece to be added
     * @param x the x position
     * @param y th y position
     */
    public void addToLocation(GPiece piece, int x, int y){
        if(offBoard(x,y)) return;
        board[x][y]=piece;
    }

    /**
     * move a piece at the specified x y location in the specified direction.
     * if there is a piece in the location to be moved to, that piece will be moved as well.
     * if a piece if moved off the board false will be returned
     * true is returned for a successful movement or if the is no piece at the position
     * @param x the x position
     * @param y the y position
     * @param direction the direction to be moved in (up, right, down or left)
     * @return false if a piece would move off the board otherwise true
     */
    public boolean move(int x, int y, String direction){
        if(board[x][y]==null) return true;
        int x2 = -1;
        int y2 = -1;

        switch (direction) {
            case "up":
                x2 = x;
                y2 = y-1;
                break;
            case "right":
                x2 = x+1;
                y2 = y;
                break;
            case "down":
                x2 = x;
                y2 = y+1;
                break;
            case "left":
                x2 = x-1;
                y2 = y;
                break;
        }

        if(offBoard(x2,y2)){
            return false;
        }else if(!move(x2, y2, direction)){
            return false;
        }else {
            board[x2][y2] = board[x][y];
            board[x][y] = null;
            return true;
        }
    }

    /**
     * removes the specified piece, returns false if the piece is not on the board
     * @param piece the piece to be removed
     * @return true if the piece was removed, false if the piece wasn't on the board
     */
    public boolean remove(GPiece piece){
        String location = findPiece(piece);
        if(location.equals("null"))return false;
        String[] xy = location.split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        board[x][y]=null;
        return true;
    }

    /**
     * sets the specified location on the board to null
     * @param x the x position
     * @param y the y position
     * @return true if the piece was removed, false if the piece wasn't on the board
     */
    public boolean remove(int x, int y){
        if(board[x][y]==null)return false;
        board[x][y] = null;
        return true;
    }

    /**
     * return a string with the x and y location of the piece separated by a comma e.g. 2,4
     * @param piece the piece to find
     * @return x y location string
     */
    public String findPiece(GPiece piece){
        for(int x=0; x<10; x++){
            for(int y=0; y<10; y++){
                if(!offBoard(x,y) && board[x][y]!=null && board[x][y].equals(piece)){
                    //System.out.println(x+" "+y);
                    return  x+","+y;
                }
            }
        }
        return "null";
    }

    /**
     * returns the piece at the specified location, null is returned if there is no piece at that location
     * @param x the x position
     * @param y th y position
     * @return GPiece
     */
    public GPiece getLocation(int x, int y){
        return board[x][y];
    }

    /**
     * returns whether the x y position is on the board or not.
     * the corners with the faces count as off the board
     * @param x the x position
     * @param y the y position
     * @return true if the location is off the board otherwise false
     */
    public boolean offBoard(int x, int y){
        if(x<0 || x>9 || y<0 || y>9) return true;
        if(isFaceY(x, y)) return true;
        return isFaceG(x, y);
    }

    /**
     * returns true if a position is on the yellow face
     * @param x the x position
     * @param y the y position
     * @return true if on face otherwise false
     */
    public boolean isFaceG(int x, int y) {
        return x == 0 && y == 0 || x == 0 && y == 1 || x == 1 && y == 0 || x == 1 && y == 1;
    }

    /**
     * returns true if a position is on the green face
     * @param x the x position
     * @param y the y position
     * @return true if on face otherwise false
     */
    public boolean isFaceY(int x, int y) {
        return x == 9 && y == 9 || x == 9 && y == 8 || x == 8 && y == 9 || x == 8 && y == 8;
    }

    /**
     * returns a copy of the array used to store the pieces on the board
     * @return 2d array of GPieces
     */
    public GPiece[][] getBoard(){
        return board;
    }

    public GPiece[][] boardSave(){
        GPiece[][] save = new GPiece[10][10];
        for(int x=0; x<10; x++){
            System.arraycopy(board[x], 0, save[x], 0, 10);
        }
        return save;
    }

    public void setBoard(GPiece[][]  board){
        this.board = board;
    }
}

package Game;

/**
 * Created by chris on 3/08/17.
 */
public class Board {
    private GPiece[][] board;

    public Board(){
        board = new GPiece[10][10];
    }

    public boolean create(GPiece piece){
        if(!piece.cemetery) {
            if (piece.isColour() && board[3][3] != null) {
                board[3][3] = piece;
                piece.created = true;
                return true;
            } else if (!piece.isColour() && board[8][8] != null) {
                board[8][8] = piece;
                piece.created = true;
                return true;
            }
        }
        return false;
    }

    public void addToLocation(GPiece piece, int x, int y){
        board[x][y]=piece;
    }

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

        System.out.println(x2+" "+y2);

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
     * @return
     */
    public boolean remove(int x, int y){
        if(board[x][y]==null)return true;
        board[x][y].cemetery = true;
        board[x][y] = null;
        return true;
    }

    /**
     * return a string with the x and y location of the piece separated by a comma e.g. 2,4
     * @param piece the piece to find
     * @return x y location string
     */
    public String findPiece(GPiece piece){
        for(int x=0; x<10; x+=1){
            for(int y=0; y<10; y+=1){
                if(board[x][y]!=null && board[x][y].equals(piece)){
                    System.out.println(x+" "+y);
                    return  x+","+y;
                }
            }
        }
        return "null";
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
        if(isFaceG(x, y)) return true;
        return false;
    }

    /**
     * returns true if a position is on the yellow face
     * @param x the x position
     * @param y the y position
     * @return true if on face otherwise false
     */
    public boolean isFaceY(int x, int y) {
        return x == 0 && y == 0 || x == 0 && y == 1 || x == 1 && y == 0 || x == 1 && y == 1;
    }

    /**
     * returns true if a position is on the green face
     * @param x the x position
     * @param y the y position
     * @return true if on face otherwise false
     */
    public boolean isFaceG(int x, int y) {
        return x == 9 && y == 9 || x == 9 && y == 8 || x == 8 && y == 9 || x == 8 && y == 8;
    }

    /**
     * returns the array used to store the pieces on the board
     * @return the array of pieces
     */
    public GPiece[][] getBoard(){
        return board;
    }
}

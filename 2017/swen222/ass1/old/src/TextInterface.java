/**
 * Created by chris on 3/08/17.
 */
public class TextInterface {
    private SSGame game;

    public void draw(){
        GPiece[][] board = game.getBoard().getBoard();
        for(int i=0; i<10; i++){
            System.out.print("---");
        }
        for(int y=0; y<10; y++){
            System.out.print("|");
            for(int x=0; x<10; x++){
                String s = " ";
                if(board[x][y].up().getSymbol().equals("Sword")){
                    s = "|";
                }else if(board[x][y].up().getSymbol().equals("Shield")){
                    s = "#";
                }
                System.out.print(" "+s+" ");
                System.out.print("|");
            }
            System.out.print("\n");

            for(int x=0; x<10; x++){
                String s1 = " ";
                String s2 = " ";
                String name = board[x][y].getName();
                if(board[x][y].up().getSymbol().equals("Sword")){
                    s1 = "|";
                }else if(board[x][y].up().getSymbol().equals("Shield")){
                    s1 = "#";
                }
                System.out.print(s1+name+s2);
                System.out.print("|");
            }
            System.out.print("\n");

            for(int x=0; x<10; x++){
                String s = " ";
                if(board[x][y].up().getSymbol().equals("Sword")){
                    s = "|";
                }else if(board[x][y].up().getSymbol().equals("Shield")){
                    s = "#";
                }
                System.out.print(" "+s+" ");
                System.out.print("|");
            }
            System.out.print("\n");

            for(int i=0; i<10; i++){
                System.out.print("---");
            }
            System.out.print("\n");
        }
    }
}

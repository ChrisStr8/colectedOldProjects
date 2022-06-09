package SSGame.Tests;

import SSGame.Game.Board;
import SSGame.Game.GPiece;
import SSGame.Game.Symbol;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created on 6/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class BoardTests {
    private Symbol up = new Symbol("-");
    private Symbol down = new Symbol("#");
    private Symbol left = new Symbol(".");
    private Symbol right = new Symbol(".");


    @Test
    public void test_create1(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        GPiece piece2 = new GPiece("a", true, up, right, down, left);
        Board board = new Board();
        board.create(piece);
        board.create(piece2);
        assertEquals(piece2, board.getLocation(7,7));
        assertEquals(piece, board.getLocation(2,2));
    }

    @Test
    public void test_create2(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        GPiece piece2 = new GPiece("B", false, up, right, down, left);
        Board board = new Board();
        board.create(piece);
        board.create(piece2);
        assertEquals(piece, board.getLocation(2,2));
    }

    @Test
    public void test_addToLocation(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        assertEquals(piece, board.getLocation(5, 5));
        assertEquals(null, board.getLocation(6, 6));
    }

    @Test
    public void test_move_down(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        board.move(5, 5, "down");
        assertEquals(null, board.getLocation(5, 5));
        assertEquals(piece, board.getLocation(5, 6));
    }

    @Test
    public void test_move_up(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        board.move(5, 5, "up");
        assertEquals(null, board.getLocation(5, 5));
        assertEquals(piece, board.getLocation(5, 4));
    }

    @Test
    public void test_move_left(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        board.move(5, 5, "left");
        assertEquals(null, board.getLocation(5, 5));
        assertEquals(piece, board.getLocation(4, 5));
    }

    @Test
    public void test_move_right(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        board.move(5, 5, "right");
        assertEquals(null, board.getLocation(5, 5));
        assertEquals(piece, board.getLocation(6, 5));
    }

    @Test
    public void test_move_offBoard(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 9,5);
        assertFalse(board.move(9, 5, "right"));
        assertEquals(piece, board.getLocation(9, 5));
    }

    @Test
    public void test_move_push(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        GPiece piece2 = new GPiece("a", true, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        board.addToLocation(piece2, 5, 4);
        board.move(5, 5, "up");
        assertEquals(null, board.getLocation(5, 5));
        assertEquals(piece, board.getLocation(5, 4));
        assertEquals(piece2, board.getLocation(5, 3));
    }

    @Test
    public void test_move_pushOffBoard(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        GPiece piece2 = new GPiece("a", true, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,1);
        board.addToLocation(piece2, 5, 0);
        assertFalse(board.move(5, 1, "up"));
        assertEquals(piece, board.getLocation(5, 1));
        assertEquals(piece2, board.getLocation(5, 0));
    }

    @Test
    public void test_remove1(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        assertTrue(board.remove(piece));
        assertEquals(null, board.getLocation(5,5));
    }

    @Test
    public void test_remove1_offBoard(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        assertFalse(board.remove(piece));
    }

    @Test
    public void test_remove2(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 5,5);
        assertTrue(board.remove(5, 5));
        assertEquals(null, board.getLocation(5,5));
    }

    @Test
    public void test_remove2_offBoard(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        assertFalse(board.remove(5, 5));
    }

    @Test
    public void test_findPiece(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 6, 6);
        assertEquals("6,6", board.findPiece(piece));
    }

    @Test
    public void test_findPiece_offBoard(){
        GPiece piece = new GPiece("B", false, up, right, down, left);
        Board board = new Board();
        assertEquals("null", board.findPiece(piece));
    }

    @Test
    public void test_getLocation(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 6, 6);
        assertEquals(null, board.getLocation(4, 3));
        assertEquals(piece, board.getLocation(6, 6));
    }

    @Test
    public void test_offBoard(){
        Board board = new Board();
        assertTrue(board.offBoard(1, 1));
        assertTrue(board.offBoard(9, 9));
        assertTrue(board.offBoard(-1, -1));
        assertTrue(board.offBoard(10, 10));
        assertTrue(board.offBoard(-1, 5));
        assertTrue(board.offBoard(5, -1));
    }

    @Test
    public void test_isFaceY(){
        Board board = new Board();
        assertTrue(board.isFaceY(8,8));
        assertTrue(board.isFaceY(8,9));
        assertTrue(board.isFaceY(9,8));
        assertTrue(board.isFaceY(9,9));
    }

    @Test
    public void test_isFaceG(){
        Board board = new Board();
        assertTrue(board.isFaceG(0,0));
        assertTrue(board.isFaceG(0,1));
        assertTrue(board.isFaceG(1,0));
        assertTrue(board.isFaceG(1,1));
    }

    @Test
    public void test_getBoard(){
        GPiece piece = new GPiece("A", false, up, right, down, left);
        Board board = new Board();
        board.addToLocation(piece, 6, 6);
        GPiece[][] boardArray = board.getBoard();
        assertEquals(piece, boardArray[6][6]);
    }
}

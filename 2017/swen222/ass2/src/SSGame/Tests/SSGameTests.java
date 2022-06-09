package SSGame.Tests;

import SSGame.Game.Board;
import SSGame.Game.GPiece;
import SSGame.Game.SSGame;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created on 6/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class SSGameTests {

    @Test
    public void test_create1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertTrue(game.create("a", 90));
        assertEquals(game.findPiece("a"), board.getLocation(7,7));
        assertEquals(null, board.getLocation(2,2));
    }

    @Test
    public void test_create2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.create("A", 90));
        assertEquals(null, board.getLocation(7,7));
        assertEquals(null, board.getLocation(2,2));
    }

    @Test
    public void test_create3(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.create("A", 90));
        assertEquals(null, board.getLocation(7,7));
        assertEquals(null, board.getLocation(2,2));
        game.pass();
        game.pass();
        assertTrue(game.create("A", 90));
        assertEquals(null, board.getLocation(7,7));
        assertEquals(game.findPiece("A"), board.getLocation(2,2));
    }

    @Test
    public void test_create4(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        board.addToLocation(game.findPiece("h"),8,7);
        board.addToLocation(game.findPiece("c"),7,8);
        board.addToLocation(game.findPiece("r"),6,7);
        board.addToLocation(game.findPiece("w"),7,6);
        game.create("g", 0);
    }

    @Test
    public void test_rotate1_num1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.rotate("k", 2));
        game.create("k", 0);
        assertTrue(game.rotate("k", 3));
        assertEquals("#", board.getLocation(7,7).up().getSymbol());
    }

    @Test
    public void test_rotate1_num2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.rotate("k", 2));
        game.create("k", 0);
        assertTrue(game.rotate("k", 4));
        assertEquals("-", board.getLocation(7,7).up().getSymbol());
    }

    @Test
    public void test_rotate1_degrees1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.rotate("k", 90));
        game.create("o", 0);
        assertTrue(game.rotate("o", 270));
        assertEquals(".", board.getLocation(7,7).up().getSymbol());
    }

    @Test
    public void test_rotate1_degrees2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.rotate("k", 90));
        game.create("o", 0);
        assertTrue(game.rotate("o", 90));
        assertEquals("#", board.getLocation(7,7).up().getSymbol());
    }

    @Test
    public void test_move1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        game.create("a", 0);
        game.move("a", "up", false);
        assertEquals(game.findPiece("a"), board.getLocation(7,6));
        assertNotEquals(game.findPiece("a"), board.getLocation(7,7));
    }

    @Test
    public void test_move2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        game.create("a", 90);
        game.move("a", "up", false);
        game.pass();
        game.pass();
        game.pass();
        game.create("d", 180);
        game.move("d", "up", false);
        assertEquals(game.findPiece("a"), board.getLocation(7,5));
        assertEquals(game.findPiece("d"), board.getLocation(7,6));
        game.pass();
        game.pass();
        game.pass();
        game.create("p", 180);
        game.move("p", "up", false);
        assertEquals(game.findPiece("a"), board.getLocation(7,3));
        assertEquals(game.findPiece("d"), board.getLocation(7,4));
        assertEquals(game.findPiece("p"), board.getLocation(7,6));
        game.pass();
        game.pass();
        game.pass();
        game.create("c", 0);
        game.move("n", "up", false);
        assertEquals(game.findPiece("a"), board.getLocation(7,1));
        assertEquals(game.findPiece("d"), board.getLocation(7,2));
        assertEquals(game.findPiece("p"), board.getLocation(7,4));
        assertEquals(game.findPiece("c"), board.getLocation(7,6));
        game.pass();
        game.pass();
        game.pass();
        game.create("e", 0);
        game.move("e", "up", false);
        game.move("c", "up", false);
        assertTrue(game.findPiece("a").cemetery);
        assertFalse(game.findPiece("a").created);
        assertEquals(game.findPiece("d"), board.getLocation(7,1));
        assertEquals(game.findPiece("p"), board.getLocation(7,2));
        assertEquals(game.findPiece("c"), board.getLocation(7,4));
        assertEquals(game.findPiece("e"), board.getLocation(7,6));
    }

    @Test
    public void test_pass(){
        SSGame game = new SSGame();
        assertTrue(game.turn);
        assertFalse(game.created);
        game.pass();
        assertTrue(game.turn);
        assertTrue(game.created);
        game.pass();
        assertFalse(game.turn);
        assertFalse(game.created);
    }

    @Test
    public void test_undo_pass(){
        SSGame game = new SSGame();
        game.pass();
        game.undo();
        assertTrue(game.turn);
        assertFalse(game.created);
        game.pass();
        game.pass();
        game.undo();
        assertTrue(game.turn);
        assertTrue(game.created);
        game.pass();
        game.pass();
        game.undo();
        assertFalse(game.turn);
        assertFalse(game.created);
    }

    @Test
    public void test_undo_rotate(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        game.create("q", 0);
        game.rotate("q", 90);
        assertEquals("#", board.getLocation(7,7).up().getSymbol());
        game.undo();
        assertEquals("-", board.getLocation(7,7).up().getSymbol());
    }

    @Test
    public void test_undo_move1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        game.create("a", 0);
        game.move("a", "up", false);
        assertEquals(game.findPiece("a"), board.getLocation(7,6));
        assertNotEquals(game.findPiece("a"), board.getLocation(7,7));
        game.undo();
        assertEquals(game.findPiece("a"), board.getLocation(7,7));
    }

    @Test
    public void test_undo_move2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        game.create("a", 90);
        game.move("a", "down", false);
        game.pass();
        game.pass();
        game.pass();
        game.pass();
        game.move("a", "down", false);
        game.pass();
        game.pass();
        game.pass();
        game.pass();
        game.move("a", "down", false);
        assertEquals("null", board.findPiece(game.findPiece("a")));
        game.undo();
        assertEquals(game.findPiece("a"), board.getLocation(7,9));
    }

    @Test
    public void test_undo_create(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        game.create("a", 0);
        assertEquals(game.findPiece("a"), board.getLocation(7,7));
        assertTrue(game.findPiece("a").created);
        game.undo();
        assertEquals(null, board.getLocation(7,7));
        assertFalse(game.findPiece("a").created);
    }

    @Test
    public void test_findPiece(){
        SSGame game = new SSGame();
        game.create("a", 90);
        game.move("a", "up", false);
        game.pass();
        game.pass();
        game.pass();
        game.create("c", 0);
        assertEquals(game.findPiece("c"), game.getBoard().getLocation(7,7));
    }

    @Test
    public void test_freePiecesY(){
        SSGame game = new SSGame();
        Queue<GPiece> pieces = game.freePiecesY();
        assertEquals(pieces.size(), 24);
        game.create("a", 0);
        pieces = game.freePiecesY();
        assertEquals(pieces.size(), 23);
        game.undo();
        pieces = game.freePiecesY();
        assertEquals(pieces.size(), 24);
    }

    @Test
    public void test_freePiecesG(){
        SSGame game = new SSGame();
        game.pass();
        game.pass();
        Queue<GPiece> pieces = game.freePiecesG();
        assertEquals(pieces.size(), 24);
        game.create("A", 0);
        pieces = game.freePiecesG();
        assertEquals(pieces.size(), 23);
        game.undo();
        pieces = game.freePiecesG();
        assertEquals(pieces.size(), 24);
    }

    @Test
    public void test_isGameOver_green(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.isGameOver());
        GPiece piece = game.findPiece("g");
        board.addToLocation(piece, 7,7);
        assertFalse(game.isGameOver());
        board.addToLocation(piece, 2,2);
        assertFalse(game.isGameOver());
        board.addToLocation(piece, 1,2);
        assertTrue(game.isGameOver());
    }

    @Test
    public void test_isGameOver_yellow(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.isGameOver());
        GPiece piece = game.findPiece("g");
        board.addToLocation(piece, 7,7);
        assertFalse(game.isGameOver());
        board.addToLocation(piece, 2,2);
        assertFalse(game.isGameOver());
        board.addToLocation(piece, 7,8);
        assertTrue(game.isGameOver());
    }

    @Test
    public void test_yellowDead1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.yellowDead());
        GPiece piece = game.findPiece("g");
        board.addToLocation(piece, 7,8);
        assertTrue(game.yellowDead());
    }

    @Test
    public void test_yellowDead2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.yellowDead());
        GPiece piece = game.findPiece("g");
        board.addToLocation(piece, 8,7);
        assertTrue(game.yellowDead());
    }

    @Test
    public void test_greenDead1(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.greenDead());
        GPiece piece = game.findPiece("g");
        board.addToLocation(piece, 2,1);
        assertTrue(game.greenDead());
    }

    @Test
    public void test_greenDead2(){
        SSGame game = new SSGame();
        Board board = game.getBoard();
        assertFalse(game.greenDead());
        GPiece piece = game.findPiece("g");
        board.addToLocation(piece, 1,2);
        assertTrue(game.greenDead());
    }

    @Test
    public void test_getBoard(){
        SSGame game = new SSGame();
        game.create("a", 0);
        Board board = game.getBoard();
        assertEquals(game.findPiece("a"), board.getLocation(7,7));
    }
}

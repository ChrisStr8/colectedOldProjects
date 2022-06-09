package SSGame.Tests;

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
public class GPieceTests {
    private final Symbol up = new Symbol("-");
    private final Symbol down = new Symbol("#");
    private final Symbol left = new Symbol(".");
    private final Symbol right = new Symbol(".");

    @Test
    public void test_rotate(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals(up, piece.up());
        assertEquals(right, piece.right());
        assertEquals(down, piece.down());
        assertEquals(left, piece.left());

        piece.rotate();

        assertEquals(left, piece.up());
        assertEquals(up, piece.right());
        assertEquals(right, piece.down());
        assertEquals(down, piece.left());

    }

    @Test
    public void test_undoRotate(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        piece.rotate();
        piece.undoRotate();

        assertEquals(up, piece.up());
        assertEquals(right, piece.right());
        assertEquals(down, piece.down());
        assertEquals(left, piece.left());
    }

    @Test
    public void test_name1(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals("a", piece.getName());
    }

    @Test
    public void test_name2(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals("a", piece.getName());
    }

    @Test
    public void test_isColour1(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals(false, piece.isColour());
    }

    @Test
    public void test_isColour2(){
       GPiece piece = new GPiece("a", true, up, right, down, left);
        assertEquals(true, piece.isColour());
    }

    @Test
    public void test_up(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals(up, piece.up());
    }

    @Test
    public void test_down(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals(down, piece.down());
    }

    @Test
    public void test_left(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals(left, piece.left());
    }

    @Test
    public void test_right(){
        GPiece piece = new GPiece("a", false, up, right, down, left);
        assertEquals(right, piece.right());
    }
}

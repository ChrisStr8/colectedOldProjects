package SSGame.Tests;

import SSGame.Game.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created on 6/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class PieceTests {

    @Test
    public void test_name1(){
        Piece piece = new Piece("test", false);
        assertEquals("test", piece.getName());
    }

    @Test
    public void test_name2(){
        Piece piece = new Piece("a", false);
        assertEquals("a", piece.getName());
    }

    @Test
    public void test_isColour1(){
        Piece piece = new Piece("test", false);
        assertEquals(false, piece.isColour());
    }

    @Test
    public void test_isColour2(){
        Piece piece = new Piece("c", true);
        assertEquals(true, piece.isColour());
    }
}

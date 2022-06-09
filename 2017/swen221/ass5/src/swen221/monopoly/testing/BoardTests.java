package swen221.monopoly.testing;

import org.junit.*;
import swen221.monopoly.Board;
import swen221.monopoly.locations.Location;

import static org.junit.Assert.*;

/**
 * Created on 8/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class BoardTests {

    @Test
    public void Test_getStartLocation(){
        Board board = new Board();
        assertEquals("Go", board.getStartLocation().getName());
    }

    @Test
    public void Test_findLocation_1(){
        Board board = new Board();
        assertEquals("Park Lane", board.findLocation("Park Lane").getName());
    }

    @Test
    public void Test_findLocation_2(){
        Board board = new Board();
        Location l = board.findLocation("aaaaaaaaaaa");
        if(l!=null){
            fail("should not find a location");
        }
    }

    @Test
    public void Test_findLocation_3(){
        Board board = new Board();
        assertEquals("Euston Road", board.findLocation(board.getStartLocation(), 8).getName());
        assertEquals("Park Lane", board.findLocation(board.getStartLocation(), 37).getName());
    }
}

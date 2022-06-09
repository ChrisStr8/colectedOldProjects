package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;
import static swen221.monopoly.Player.Token.Battleship;

import swen221.monopoly.Player;
import swen221.monopoly.locations.*;

/**
 * Created on 8/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class StreetTests {

    @Test
    public void test_getColourGroup(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        assertEquals(colorGroup, oldKentRoad.getColourGroup());
    }

    @Test
    public void test_getRent_1(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        assertEquals(2, oldKentRoad.getRent(1));
        player.sell(oldKentRoad);
        player.buy(whiteChapel);
        assertEquals(4, whiteChapel.getRent(1));
    }

    @Test
    public void test_getRent_2(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        player.buy(whiteChapel);
        assertEquals(4, oldKentRoad.getRent(1));
        assertEquals(8, whiteChapel.getRent(1));
    }

    @Test
    public void test_getRent_3(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        player.buy(whiteChapel);
        oldKentRoad.setHouses(1);
        whiteChapel.setHotels(1);
        assertEquals(29, oldKentRoad.getRent(1));
        assertEquals(208, whiteChapel.getRent(1));
    }

    @Test
    public void test_getRent_4(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        player.buy(whiteChapel);
        oldKentRoad.setHouses(2);
        whiteChapel.setHotels(2);
        assertEquals(54, oldKentRoad.getRent(1));
        assertEquals(408, whiteChapel.getRent(1));
    }

    @Test
    public void test_getRent_5(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        player.buy(whiteChapel);
        oldKentRoad.setHouses(2);
        oldKentRoad.setHotels(1);
        assertEquals(254, oldKentRoad.getRent(1));
        assertEquals(8, whiteChapel.getRent(1));
    }

    @Test
    public void test_getHouses(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        oldKentRoad.setHouses(3);
        assertEquals(3, oldKentRoad.getHouses());
    }

    @Test
    public void test_getHotels(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        oldKentRoad.setHotels(3);
        assertEquals(3, oldKentRoad.getHotels());
    }
}

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
public class PropertyTests {

    @Test
    public void test_getPrice(){
        Property property = new Station("Kings Cross Station", 200);
        assertEquals(200, property.getPrice());
    }

    @Test
    public void test_mortgage(){
        Property property = new Station("Kings Cross Station", 200);
        property.mortgage();
        assertTrue(property.isMortgaged());
    }

    @Test
    public void test_unmortgage(){
        Property property = new Station("Kings Cross Station", 200);
        property.mortgage();
        property.unmortgage();
        assertFalse(property.isMortgaged());
    }

    @Test
    public void test_hasOwner(){
        Property property = new Station("Kings Cross Station", 200);
        Player player = new Player("player", Battleship, 1500, property);
        assertFalse(property.hasOwner());
        player.buy(property);
        assertTrue(property.hasOwner());
        player.sell(property);
        assertFalse(property.hasOwner());
    }

    @Test
    public void test_getOwner(){
        Property property = new Station("Kings Cross Station", 200);
        Player player = new Player("player", Battleship, 1500, property);
        Player player1 = new Player("player1", Battleship, 150, property);
        player.buy(property);
        assertEquals(player, property.getOwner());
        assertNotEquals(player1, property.getOwner());
        player.sell(property);
        assertEquals(null, property.getOwner());
    }
}

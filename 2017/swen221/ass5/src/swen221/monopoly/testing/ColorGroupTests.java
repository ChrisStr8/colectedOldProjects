package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;
import static swen221.monopoly.Player.Token.Battleship;

import swen221.monopoly.Player;
import swen221.monopoly.locations.*;

import java.util.Iterator;

/**
 * Created by chris on 8/06/17.
 */
public class ColorGroupTests {

    @Test
    public void test_getColour(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        assertEquals("Brown", colorGroup.getColour());
    }

    @Test
    public void test_getHouseCost(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        assertEquals(50, colorGroup.getHouseCost());
    }

    @Test
    public void test_allPropertiesOwnedBy_true(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        player.buy(whiteChapel);
        assertTrue(colorGroup.allPropertiesOwnedBy(player));
    }

    @Test
    public void test_allPropertiesOwnedBy_false(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Player player = new Player("player", Battleship, 1500, oldKentRoad);
        player.buy(oldKentRoad);
        assertFalse(colorGroup.allPropertiesOwnedBy(player));
    }

    @Test
    public void test_iterator(){
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);
        ColourGroup colorGroup = new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        Iterator<Street> iterator = colorGroup.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(oldKentRoad, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(whiteChapel, iterator.next());
        assertFalse(iterator.hasNext());
    }
}

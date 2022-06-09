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
public class UtilityTests {

    @Test
    public void getRent(){
        Utility utility = new Utility("Electric Company", 150);
        Player player = new Player("player", Battleship, 1500, utility);
        player.buy(utility);
        assertEquals(8, utility.getRent(2));
    }

    @Test
    public void getRent_2(){
        Utility utility = new Utility("Electric Company", 150);
        Utility utility1 = new Utility("Water Works", 150);
        Player player = new Player("player", Battleship, 1500, utility);
        player.buy(utility);
        player.buy(utility1);
        assertEquals(24, utility.getRent(3));
    }

}

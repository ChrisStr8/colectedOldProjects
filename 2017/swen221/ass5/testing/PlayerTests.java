package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;
import swen221.monopoly.Player;
import swen221.monopoly.locations.Street;
import static swen221.monopoly.Player.Token.*;

/**
 * Created on 8/06/17.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class PlayerTests {

    @Test
    public void Test_getName(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        assertEquals("player", player.getName());
    }

    @Test
    public void Test_getLocation(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        assertEquals(street, player.getLocation());
    }

    @Test
    public void Test_getBalance(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        assertEquals(1500, player.getBalance());
    }

    @Test
    public void Test_getToken(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        assertEquals(Battleship, player.getToken());
    }

    @Test
    public void Test_setLocation(){
        Street street = new Street("Old Kent Road", 60, 2);
        Street newStreet = new Street("Mayfair", 400, 50);
        Player player = new Player("player", Battleship, 1500, street);
        player.setLocation(newStreet);
        assertEquals(newStreet, player.getLocation());
    }

    @Test
    public void Test_deduct(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        player.deduct(500);
        assertEquals(1000, player.getBalance());
    }

    @Test
    public void Test_credit(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        player.credit(500);
        assertEquals(2000, player.getBalance());
    }

    @Test
    public void Test_buy(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        player.buy(street);
        assertEquals(1440, player.getBalance());
        assertEquals(street, player.iterator().next());
        assertEquals(player, street.getOwner());
    }

    @Test
    public void Test_buy_owned(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        player.buy(street);
        Player player2 = new Player("player2", Iron, 1500, street);
        try{
            player2.buy(street);
        } catch (IllegalArgumentException ignored){}
    }

    @Test
    public void Test_sell(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        player.buy(street);
        player.sell(street);
        assertEquals(1500, player.getBalance());
        assertEquals(null, street.getOwner());
        assertFalse(player.iterator().hasNext());
    }

    @Test
    public void Test_sell_notOwner(){
        Street street = new Street("Old Kent Road", 60, 2);
        Player player = new Player("player", Battleship, 1500, street);
        player.buy(street);
        Player player2 = new Player("player2", Iron, 1500, street);
        try{
            player2.sell(street);
        }catch (IllegalArgumentException ignored){}
    }
}


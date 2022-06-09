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
public class StationTests {

    @Test
    public void test_getRent_1(){
        Station station = new Station("Kings Cross Station", 200);
        Player player = new Player("player", Battleship, 1500, station);
        player.buy(station);
        assertEquals(25, station.getRent(1));
    }

    @Test
    public void test_getRent_2(){
        Station station = new Station("Kings Cross Station", 200);
        Station station1 = new Station("Marylebone Station", 200);
        Player player = new Player("player", Battleship, 1500, station);
        player.buy(station);
        player.buy(station1);
        assertEquals(50, station.getRent(1));
    }

    @Test
    public void test_getRent_3(){
        Station station = new Station("Kings Cross Station", 200);
        Station station1 = new Station("Marylebone Station", 200);
        Station station2 = new Station("Fenchurch St. Station", 200);
        Player player = new Player("player", Battleship, 1500, station);
        player.buy(station);
        player.buy(station1);
        player.buy(station2);
        assertEquals(75, station.getRent(1));
    }

    @Test
    public void test_getRent_4(){
        Station station = new Station("Kings Cross Station", 200);
        Station station1 = new Station("Marylebone Station", 200);
        Station station2 = new Station("Fenchurch St. Station", 200);
        Station station3 = new Station("Livepool St. Station", 200);
        Player player = new Player("player", Battleship, 44000, station);
        player.buy(station);
        player.buy(station1);
        player.buy(station2);
        player.buy(station3);
        assertEquals(100, station.getRent(1));
    }
}
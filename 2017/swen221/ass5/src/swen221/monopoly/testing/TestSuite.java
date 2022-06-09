package swen221.monopoly.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created on 8/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        swen221.monopoly.testing.BoardTests.class,
        swen221.monopoly.testing.PlayerTests.class,
        swen221.monopoly.testing.ColorGroupTests.class,
        swen221.monopoly.testing.SpecialAreaTests.class,
        swen221.monopoly.testing.StationTests.class,
        swen221.monopoly.testing.StreetTests.class,
        swen221.monopoly.testing.UtilityTests.class,
        swen221.monopoly.testing.PropertyTests.class,
        swen221.monopoly.testing.GameOfMonopolyTests.class
        //swen221.monopoly.testing.MonopolyTests.class
})

public class TestSuite {
}
package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;
import swen221.monopoly.locations.*;

/**
 * Created on 8/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class SpecialAreaTests {

    @Test
    public void test_hasOwner() {
        SpecialArea specialArea = new SpecialArea("Go");
        assertFalse(specialArea.hasOwner());
    }

    @Test
    public void test_getOwner() {
        SpecialArea specialArea = new SpecialArea("Go");
        try {
            specialArea.getOwner();
            fail();
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void test_getRent() {
        SpecialArea specialArea = new SpecialArea("Go");
        try {
            specialArea.getRent();
            fail();
        } catch (RuntimeException ignored) {
        }
    }
}
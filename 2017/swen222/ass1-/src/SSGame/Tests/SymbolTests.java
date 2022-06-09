package SSGame.Tests;

import SSGame.Game.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created on 5/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class SymbolTests {
    @Test
    public void test_sword(){
        Symbol s = new Symbol("-");
        assertEquals("-", s.getSymbol());
    }

    @Test
    public void test_shield(){
        Symbol s = new Symbol("#");
        assertEquals("#", s.getSymbol());
    }

    @Test
    public void test_nothing(){
        Symbol s = new Symbol(".");
        assertEquals(".", s.getSymbol());
    }

    @Test
    public void test_bad1(){
        try {
            Symbol s = new Symbol("g");
            fail("should not be able to create a symbol other than -, . or #");
        }catch(Error ignored){}
    }

    @Test
    public void test_bad2(){
        try {
            Symbol s = new Symbol("7");
            fail("should not be able to create a symbol other than -, . or #");
        }catch(Error ignored){}
    }
}

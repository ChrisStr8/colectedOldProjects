package Tests;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * Created on 5/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Tests.BoardTests.class,
        Tests.GPieceTests.class,
        Tests.PieceTests.class,
        Tests.SSGameTests.class,
        Tests.SymbolTests.class
})

public class GameTestSuite {
    @Test
    public void test1(){
        assertFalse(false);
    }
}

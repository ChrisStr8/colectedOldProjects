package SSGame.Tests;


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
        SSGame.Tests.BoardTests.class,
        SSGame.Tests.GPieceTests.class,
        SSGame.Tests.SSGameTests.class,
        SSGame.Tests.SymbolTests.class
})

public class TestSuite {
}

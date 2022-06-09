package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;

import swen221.monopoly.*;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Property;
import swen221.monopoly.locations.Street;

public class MonopolyTests {
    // this is where you must write your tests; do not alter the package, or the
    // name of this file.  An example test is provided for you.

	/*@Test
	public void testValidBuyProperty_1() {
		// Construct a "mini-game" of Monopoly and with a single player. The
		// player attempts to buy a property. We check that the right amount has
		// been deducted from his/her balance, and that he/she now owns the
		// property and vice-versa.
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Park Lane",1500);
			game.buyProperty(player);
			assertEquals(1150,player.getBalance());
			assertEquals("Park Lane",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Park Lane");
			assertEquals(player,street.getOwner());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}*/

	/*@Test
	public void testValidBuyOwnedProperty_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Oxford Street",1500);
			game.buyProperty(player);
			assertEquals(1200,player.getBalance());
			assertEquals("Oxford Street",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Oxford Street");
			assertEquals(player,street.getOwner());
			Player player2 = setupMockPlayer(game,"Oxford Street",1500);
			game.buyProperty(player2);
			fail();
		} catch (GameOfMonopoly.InvalidMove ignored) {}
	}*/

	/*@Test
	public void testValidMortgageProperty_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Oxford Street",1500);
			game.buyProperty(player);
			assertEquals(1200,player.getBalance());
			assertEquals("Oxford Street",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Oxford Street");
			assertEquals(player,street.getOwner());
			game.mortgageProperty(player, game.getBoard().findLocation("Oxford Street"));
			assertEquals(1350,player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}*/

	/*@Test
	public void testValidUnMortgageProperty_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Oxford Street",1500);
			game.buyProperty(player);

			//buy property
			assertEquals(1200,player.getBalance());
			assertEquals("Oxford Street",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Oxford Street");
			assertEquals(player,street.getOwner());

			//mortgage property
			game.mortgageProperty(player, game.getBoard().findLocation("Oxford Street"));
			assertEquals(1350,player.getBalance());
			Property p = (Property)game.getBoard().findLocation("Oxford Street");
			assertTrue(p.isMortgaged());

			//un-mortgage property
			game.unmortgageProperty(player, game.getBoard().findLocation("Oxford Street"));
			assertEquals(1185,player.getBalance());
			assertFalse(p.isMortgaged());

		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}*/

	/*@Test
	public void passGo_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Mayfair",1500);
			game.movePlayer(player, 4);
			assertEquals(1700, player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}*/

    @Test
    public void movePlayer_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",1500);
            game.movePlayer(player, 2);
            assertEquals(player.getLocation().getName(), "Whitechapel Road");
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void startIsGo_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        assertEquals(game.getBoard().getStartLocation(), game.getBoard().findLocation("Go"));
    }

    @Test
    public void findNonLocation_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        assertEquals(game.getBoard().findLocation("aaaaaaaaaaaaaa"), null);
    }

	/*@Test
	public void testValidBuildHouse_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Old Kent Road",1500);

			//buy property1
			game.buyProperty(player);
			assertEquals(1440,player.getBalance());
			assertEquals("Old Kent Road",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Old Kent Road");
			assertEquals(player,street.getOwner());

			////buy property2
			game.movePlayer(player, 2);
			assertEquals(player.getLocation().getName(), "Whitechapel Road");
			game.buyProperty(player);
			assertEquals(1380,player.getBalance());

			//build house
			game.buildHouses(player, player.getLocation(), 1);
			assertEquals(1330,player.getBalance());
			Street s = (Street)player.getLocation();
			assertEquals(1, s.getHouses());
			assertEquals(0, s.getHotels());

		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}*/

	/*@Test
	public void testValidBuildHotel_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Old Kent Road",1500);

			//buy property1
			game.buyProperty(player);
			assertEquals(1440,player.getBalance());
			assertEquals("Old Kent Road",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Old Kent Road");
			assertEquals(player,street.getOwner());

			////buy property2
			game.movePlayer(player, 2);
			assertEquals(player.getLocation().getName(), "Whitechapel Road");
			game.buyProperty(player);
			assertEquals(1380,player.getBalance());

			game.buildHouses(player, player.getLocation(), 5);
			assertEquals(1130,player.getBalance());
			Street s = (Street)player.getLocation();
			assertEquals(5, s.getHouses());
			assertEquals(0, s.getHotels());

			game.buildHotel(player, player.getLocation());
			assertEquals(1080,player.getBalance());
			s = (Street)player.getLocation();
			assertEquals(0, s.getHouses());
			assertEquals(1, s.getHotels());


		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}*/

    @Test
    public void testValidSellProperty_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Park Lane",1500);
            game.buyProperty(player);
            assertEquals(1150,player.getBalance());
            assertEquals("Park Lane",player.iterator().next().getName());
            Street street = (Street) game.getBoard().findLocation("Park Lane");
            assertEquals(player,street.getOwner());

            player.sell(player.iterator().next());
            assertNotEquals(player,street.getOwner());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }



    /**
     * Setup a mock game of monopoly with a player located at a given location.
     */
    private Player setupMockPlayer(GameOfMonopoly game, String locationName, int balance)
            throws GameOfMonopoly.InvalidMove {
        Board board = game.getBoard();
        Location location = board.findLocation(locationName);
        return new Player("Dave", Player.Token.ScottishTerrier, balance, location);
    }
}
/*ame,"Park Lane",1500);
        game.buyProperty(player);
        assertEquals(1150,player.getBalance());
        assertEquals("Park Lane",player.iterator().next().getName());
        Street street = (Street) game.getBoard().findLocation("Park Lane");
        assertEquals(player,street.getOwner());
        } catch (GameOfMonopoly.InvalidMove e) {
        fail(e.getMessage());
        }
        }

@Test
public void test_buyProperty_Owned() {
        GameOfMonopoly game*/

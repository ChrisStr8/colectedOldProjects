package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;
import swen221.monopoly.GameOfMonopoly;
import swen221.monopoly.Board;
import swen221.monopoly.Player;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Property;
import swen221.monopoly.locations.Street;

/**
 * Created on 8/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class GameOfMonopolyTests {

    @Test
    public void test_getBoard(){
        GameOfMonopoly game = new GameOfMonopoly();
        if(game.getBoard()==null){
            fail();
        }
    }

    @Test
    public void test_movePlayer(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",1500);
            game.movePlayer(player, 2);
            assertEquals("Whitechapel Road", player.getLocation().getName());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_movePlayer_pastGo(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Mayfair",1500);
            game.movePlayer(player, 4);
            assertEquals(1700, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_movePlayer_toOwned_1(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",1500);
            Player player1 = setupMockPlayer(game,"Go",1500);
            game.buyProperty(player);
            game.movePlayer(player1, 1);
            assertEquals("Old Kent Road", player1.getLocation().getName());
            assertEquals(1498, player1.getBalance());
            assertEquals(1442, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_movePlayer_toOwned_2(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"The Angel Islington",1500);
            Player player1 = setupMockPlayer(game,"Go",1);
            game.buyProperty(player);
            game.movePlayer(player1, 6);
            assertEquals("The Angel Islington", player1.getLocation().getName());
            assertEquals(-5, player1.getBalance());
            assertEquals(1406, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_movePlayer_toOwned_3(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"The Angel Islington",1500);
            Player player1 = setupMockPlayer(game,"Go",1);
            game.buyProperty(player);
            game.mortgageProperty(player, game.getBoard().findLocation("The Angel Islington"));
            game.movePlayer(player1, 6);
            assertEquals("The Angel Islington", player1.getLocation().getName());
            assertEquals(1, player1.getBalance());
            assertEquals(1450, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_buyProperty(){
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
    }

    @Test
    public void test_buyProperty_Owned() {
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
    }

    @Test
    public void test_buyProperty_notEnoughMoney() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",50);
            game.buyProperty(player);
            fail("buying a property without enough money should throw an error");
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buyProperty_notProperty() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.movePlayer(player, 1);
            game.buyProperty(player);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_sellProperty(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.buyProperty(player);
            game.sellProperty(player, game.getBoard().findLocation("Oxford Street"));
            assertFalse(game.getBoard().findLocation("Oxford Street").hasOwner());
            assertEquals(1500, player.getBalance());
            assertFalse(player.iterator().hasNext());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_sellProperty_notProperty(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.movePlayer(player, 1);
            game.sellProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_sellProperty_notOwned(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.sellProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_sellProperty_notOwner(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            Player player1 = setupMockPlayer(game,"Oxford Street",1400);
            game.buyProperty(player);
            game.sellProperty(player1, player1.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_sellProperty_mortgaged(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            game.sellProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_mortgageProperty(){
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
    }

    @Test
    public void test_mortgageProperty_alreadyMortgaged(){
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
            game.mortgageProperty(player, game.getBoard().findLocation("Oxford Street"));
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_mortgageProperty_notProperty(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.buyProperty(player);
            game.movePlayer(player, 1);
            game.mortgageProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_mortgageProperty_alreadyOwned(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            Player player1 = setupMockPlayer(game,"Oxford Street",1400);
            game.buyProperty(player);
            game.mortgageProperty(player1, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_mortgageProperty_notOwned(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Oxford Street",1500);
            game.mortgageProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_unmortgageProperty(){
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
    }

    @Test
    public void test_unmortgageProperty_notProperty(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Oxford Street", 1500);
            game.movePlayer(player, 1);
            game.unmortgageProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_unmortgageProperty_notMortgaged(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Oxford Street", 1500);
            game.buyProperty(player);
            game.unmortgageProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_unmortgageProperty_notOwned(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Oxford Street", 1500);
            game.unmortgageProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_unmortgageProperty_notEnoughMoney(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Oxford Street", 310);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            game.unmortgageProperty(player, player.getLocation());
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHouses(){
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
    }

    @Test
    public void test_buildHouses_notStreet(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",1500);
            game.movePlayer(player, 1);
            game.buildHouses(player, player.getLocation(), 1);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHouses_Mortgaged(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",1500);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            game.buildHouses(player, player.getLocation(), 1);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHouses_colorGroupNotOwned(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.buyProperty(player);
            game.buildHouses(player, player.getLocation(), 1);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHouses_notEnoughSpace(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.buyProperty(player);
            game.buildHouses(player, player.getLocation(), 6);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHouses_notEnoughMoney(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",171);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buildHouses(player, player.getLocation(), 2);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHouses_hotel(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.buyProperty(player);
            game.buildHouses(player, player.getLocation(), 5);
            game.buildHotel(player, player.getLocation());
            Street street = (Street)player.getLocation();
            assertEquals(0, street.getHouses());
            game.buildHouses(player, player.getLocation(), 1);
            fail();
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHotel(){
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
    }

    @Test
    public void test_buildHotel_notStreet(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",1500);
            game.movePlayer(player, 1);
            game.buildHotel(player, player.getLocation());
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHotel_not5Houses(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, player.getLocation(), 2);
            game.buildHotel(player, player.getLocation());
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHotel_mortgaged(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, player.getLocation(), 5);
            game.mortgageProperty(player, player.getLocation());
            game.buildHotel(player, player.getLocation());
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHotel_colorGroupNotOwned(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.buyProperty(player);
            game.buildHotel(player, player.getLocation());
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

    @Test
    public void test_buildHotel_notEnoughMoney(){
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game,"Old Kent Road",15000);
            game.buyProperty(player);
            game.buildHotel(player, player.getLocation());
        } catch (GameOfMonopoly.InvalidMove ignored) {}
    }

//----------------------------------------------------------------------------------------------------------------------

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

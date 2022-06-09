package swen221.cardgame.cards.variations;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import swen221.cardgame.cards.core.Card;
import swen221.cardgame.cards.core.CardGame;
import swen221.cardgame.cards.core.Player;
import swen221.cardgame.cards.util.AbstractCardGame;

/**
 * A simple variation of Whist where only a single hand is played.
 * 
 * @author David J. Pearce
 * 
 */
public class ClassicWhist extends AbstractCardGame {

	public ClassicWhist() {

	}

	public String getName() {
		return "Single Hand Whist";
	}
	
	public boolean isGameFinished() {
		for (Player.Direction d : Player.Direction.values()) {
			if (scores.get(d) == 5) {
				return true;
			}
		}
		return false;
	}
	
	public void deal(List<Card> deck) {	
		currentTrick = null;
		for (Player.Direction d : Player.Direction.values()) {
			players.get(d).getHand().clear();
		}
		Player.Direction d = Player.Direction.NORTH;
		for (int i = 0; i < deck.size(); ++i) {
			Card card = deck.get(i);
			players.get(d).getHand().add(card);
			d = d.next();
		}		
	}

	@Override
	public ClassicWhist clone(){
		ClassicWhist clone = new ClassicWhist();

		Collection<Player.Direction> keys = players.keySet();
		for (Player.Direction d : keys) {
			clone.players.put(d,players.get(d).clone());
		}
		clone.tricks.putAll(tricks);
		clone.scores.putAll(scores);

		clone.trumps = trumps;
		clone.currentTrick = currentTrick.clone();

		return clone;
	}
}

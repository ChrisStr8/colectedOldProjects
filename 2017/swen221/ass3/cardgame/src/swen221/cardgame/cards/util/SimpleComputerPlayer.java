package swen221.cardgame.cards.util;

import swen221.cardgame.cards.core.Card;
import swen221.cardgame.cards.core.Player;
import swen221.cardgame.cards.core.Trick;

import java.util.*;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {
		Card[] eligibleCards = getEligibleCards(trick);
		boolean canWin = winPotential(trick, eligibleCards);
		if (trick.getCardsPlayed().size() == 3 && canWin) {//player is last and can win i.e must win
			for(int i=0; i<eligibleCards.length; i++) {//return lowest card that can win
				if (cardCanWin(trick, eligibleCards[i])) {
					return eligibleCards[i];
				}
			}
		} else if (canWin){//player can win so return highest card
			return eligibleCards[eligibleCards.length - 1];
		}
		return eligibleCards[0];//player can't win so return lowest card
	}

	/**
	 * returns a list of cards that are eligible to be played, the list is sorted
	 * in order of Eligibility
	 * @param trick the current trick
	 * @return a sorted array of cards
	 */
	private Card[] getEligibleCards(Trick trick){
		Set<Card> eligibleCards = new HashSet<>();
		if(trick.getLeadPlayer()==player.direction) {//player is leader. all cards are eligible
			eligibleCards.addAll(player.getHand().matches(Card.Suit.HEARTS));
			eligibleCards.addAll(player.getHand().matches(Card.Suit.CLUBS));
			eligibleCards.addAll(player.getHand().matches(Card.Suit.DIAMONDS));
			eligibleCards.addAll(player.getHand().matches(Card.Suit.SPADES));

		}else{
			Card.Suit leadSuit = trick.getCardPlayed(trick.getLeadPlayer()).suit();
			if (player.getHand().matches(leadSuit).size()>0) {//player has cards of leaders suit
				eligibleCards.addAll(player.getHand().matches(leadSuit));
			} else {//player does not have leaders suit. all cards are eligible
				eligibleCards.addAll(player.getHand().matches(Card.Suit.HEARTS));
				eligibleCards.addAll(player.getHand().matches(Card.Suit.CLUBS));
				eligibleCards.addAll(player.getHand().matches(Card.Suit.DIAMONDS));
				eligibleCards.addAll(player.getHand().matches(Card.Suit.SPADES));
			}
		}

		Card[] cards = new Card[eligibleCards.size()];
		int i = 0;
		for (Card c : eligibleCards) {//add cards to array for sorting
			cards[i] = c;
			i++;
		}
		return selectionSort(cards, trick.getTrumps());
	}

	/**
	 * Calculates the order of eligibility. if a is more eligible that b then 1
	 * will be returned, if b is more eligible than a then -1 will be returned
	 * if a and b are equally eligible then 0 will be returned (this will only
	 * happen if the cards are the same)
	 * @param a card b
	 * @param b card a
	 * @param trumps the trumps suit
	 * @return an int indicating which card is more eligible
	 */
	private int compareEligibility(Card a, Card b, Card.Suit trumps){

		if(a.suit()==trumps && b.suit()==trumps){//both trumps
			return a.rank().ordinal() - b.rank().ordinal();
		}else if(a.suit()==trumps && b.suit()!=trumps){
			return 1;
		}else if(a.suit()!=trumps && b.suit()==trumps){
			return -1;
		}else{// neither trumps
			int eligibility = a.rank().ordinal() - b.rank().ordinal();
			if(eligibility!=0){
				return eligibility;
			}else{
				return a.suit().ordinal() - b.suit().ordinal();
			}
		}
	}

	/**
	 * Sorts the elements of an array of Cards using selection sort
	 * @param data the array to be sorted
	 * @param trumps the trumps suit
	 * @return the sorted array
	 */
	private  Card[] selectionSort(Card[] data, Card.Suit trumps){
		// for each position, from 0 up, find the next smallest item
		// and swap it into place
		for (int place=0; place<data.length-1; place++){
			int minIndex = place;
			for (int sweep=place+1; sweep<data.length; sweep++){
				if (compareEligibility(data[sweep], data[minIndex], trumps) < 0)
					minIndex=sweep;
			}
			swap(data, place, minIndex);
		}
		return data;
	}

	/**
	 * Swaps the specified elements of an array.
	 * @param data the array being sorted
	 * @param index1 the index of element 1 in data
	 * @param index2 the index of element 2 in data
	 */
	private  void swap(Card[ ] data, int index1, int index2){
		if (index1==index2) return;
		Card temp = data[index1];
		data[index1] = data[index2];
		data[index2] = temp;
	}

	/**
	 * returns true if the player can potentially win.
	 * returns false if the player cannot win.
	 * @param trick the current trick
	 * @param cards the array of eligible cards
	 * @return an boolean showing if the player can win
	 */
	private boolean winPotential(Trick trick, Card[] cards){
		for (Card c : cards) {
			if(cardCanWin(trick, c)){
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if a particular card could win the trick.
	 * returns true if it is possible for the card to win the trick and
	 * false otherwise
	 * @param trick the current trick
	 * @param card the card to check
	 * @return
	 */
	private boolean cardCanWin(Trick trick, Card card){
		if(trick.getLeadPlayer()==player.direction) {
			return true;
		}
		List<Card> playedCards = trick.getCardsPlayed();
		Card winningCard = getWinningCard(playedCards, trick.getTrumps());

		if(card.suit()==trick.getTrumps() && winningCard.suit()!=trick.getTrumps()){
			return true;
		}
		if(card.suit()==winningCard.suit() && card.rank().ordinal()>winningCard.rank().ordinal()){
			return true;
		}

		return false;
	}

	/**
	 * returns a the wining card in a list of cards given the trumps suit it
	 * is passed
	 * @param playedCards the list of cards
	 * @param trumps the trumps suit
	 * @return
	 */
	private Card getWinningCard(List<Card> playedCards, Card.Suit trumps){
		if(playedCards.size()==1){//only one card played so it is the winner(leaders card)
			return playedCards.get(0);
		}
		Card winningCard = playedCards.get(0);
		for (Card c : playedCards) {
			if(c.suit()==trumps && winningCard.suit()!=trumps){
				winningCard = c;
			}else if(c.suit()==winningCard.suit() && c.rank().ordinal()>winningCard.rank().ordinal()){
				winningCard = c;
			}
		}

		return winningCard;
	}
}

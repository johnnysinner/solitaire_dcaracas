package com.svi.solitaire.object;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deckofcards;

	public Deck() {
		this.deckofcards = new ArrayList<Card>();
	}

	public void addCardAtBottom(Card card) {
		addCard(card, this.deckofcards.size());
	}

	public void addCardAtTop(Card card) {
		addCard(card, 0);
	}

	public void addCard(Card card, int index) {
		this.deckofcards.add(index, card);
	}

	public Card drawCardFromTop() {
		return this.deckofcards.remove(0);
	}

	public int getSize() {
		return this.deckofcards.size();
	}

	public ArrayList<Card> getCards() {
		return deckofcards;
	}

	public void shuffleCards() {
		Collections.shuffle(this.deckofcards);
	}

	public boolean isEmpty() {
		if (deckofcards.isEmpty()) {
			return true;
		} else {
			return false;

		}
	}

	public void addAllCards(Deck deck) {
		for (Card card : deck.getCards()) {
			this.deckofcards.add(card);
		}
	}

	public void clear() {
		this.deckofcards.clear();
	}

	public Card remove(int i) {
		return this.deckofcards.remove(i);
	}

}

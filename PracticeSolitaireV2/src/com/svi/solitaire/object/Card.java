package com.svi.solitaire.object;

import com.svi.solitaire.models.Color;
import com.svi.solitaire.models.Rank;
import com.svi.solitaire.models.Suit;

public class Card {

	private Rank rank;
	private Suit suit;
	private boolean isFacedUp;

	public Color getColor() {
		if (suit.getValue() > 2) {
			return Color.RED;
		} else {
			return Color.BLACK;
		}
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public boolean isFacedUp() {
		return isFacedUp;
	}

	public void setFacedUp(boolean isFacedUp) {
		this.isFacedUp = isFacedUp;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	@Override
	public String toString() {
		if (this.isFacedUp == true) {
			return "[" + suit.getName() + "-" + rank.getName() + "]";
		} else {
			return "[ == ]";
		}
	}

}

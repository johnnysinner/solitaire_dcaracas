package com.svi.solitaire.main;


import com.svi.solitaire.models.Rank;
import com.svi.solitaire.models.Suit;

public class GameMethods {

	public static Suit findSuit(String suitName) {
		for (Suit suit : Suit.values()) {
			if (suit.getName().trim().equals(suitName)) {
				return suit;
			}
		}

		return null;
	}

	public static Rank findRank(String rankName) {
		for (Rank rank : Rank.values()) {
			if (rank.getName().trim().equals(rankName)) {
				return rank;
			}
		}
		return null;
	}

}

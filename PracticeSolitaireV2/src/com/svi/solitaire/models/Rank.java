package com.svi.solitaire.models;

public enum Rank {
	ACE(1, " A"), KING(13, " K"), QUEEN(12, " Q"), JACK(11, " J"), TEN(10, "10"), NINE(9, " 9"), EIGHT(8,
			" 8"), SEVEN(7, " 7"), SIX(6, " 6"), FIVE(5, " 5"), FOUR(4, " 4"), THREE(3, " 3"), TWO(2, " 2");

	private String rankName;
	private int rankValue;

	private Rank(int rankValue, String rankName) {
		this.rankValue = rankValue;
		this.rankName = rankName;
	}

	public String getName() {
		return rankName;
	}

	public int getValue() {
		return rankValue;
	}

	public String toString() {
		return rankName;

	}

}

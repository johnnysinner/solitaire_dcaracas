package com.svi.solitaire.models;

public enum Suit {
	DIAMONDS ("D", 4),
	HEARTS ("H", 3),
	SPADES ("S", 2),
	CLUBS ("C", 1);
	
	private String suitName;
	private int suitValue;
	
	private Suit(String suitName, int suitValue) {
		this.suitName = suitName;
		this.suitValue = suitValue;
	}
	public String getName() {
		return suitName;
	}
	public int getValue() {
		return suitValue;
	}
	
}

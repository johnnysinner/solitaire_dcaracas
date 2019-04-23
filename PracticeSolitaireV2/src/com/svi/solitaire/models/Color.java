package com.svi.solitaire.models;

public enum Color {
	RED(0, "red"), BLACK(1, "black");
	private String colorName;
	private int colorValue;

	Color(int colorValue, String colorName) {
		this.colorValue = colorValue;
		this.colorName = colorName;
	}

	public String getColorName() {
		return colorName;
	}

	public int getColorValue() {
		return colorValue;
	}
}

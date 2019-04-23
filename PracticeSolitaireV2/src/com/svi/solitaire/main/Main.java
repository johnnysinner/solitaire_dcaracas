package com.svi.solitaire.main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int numberOfDraw = 1;
		SolitaireGame game = new SolitaireGame();
		Scanner userInput = new Scanner(System.in);
		boolean wrongInput = true;
		do {
			try {
				System.out.println("\nPlease Enter the Number of Draw/s (1 or 3) : ");
				numberOfDraw = userInput.nextInt();
				if (numberOfDraw == 1 || numberOfDraw == 3) {
					wrongInput = false;
					System.out.println("\nCondition accepted \nNumber of draws is " + numberOfDraw);
				} else {
					System.out.println("\nInvalid Input \nCannot draw " + numberOfDraw + " number of times");
					wrongInput = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Input nPlease input a valid number");
				userInput.nextLine();
			}
		} while (wrongInput);
		userInput.close();
		
		game.start(numberOfDraw);
		System.out.println("=== END ===");
	}
}

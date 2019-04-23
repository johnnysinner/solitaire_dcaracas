package com.svi.solitaire.main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		SolitaireGame game = new SolitaireGame();
		Scanner scan = new Scanner(System.in);
		int numberOfDraw = 0;
		String inputUser;

		boolean wrongInput = true;

		do {

			System.out.println("Do you want to shuffle the Deck? (Yes / No): ");
			inputUser = scan.next();
			if (inputUser.equalsIgnoreCase("yes") || inputUser.equalsIgnoreCase("y")) {
				wrongInput = false;
			} else if (inputUser.equalsIgnoreCase("No") || inputUser.equalsIgnoreCase("n")) {
				wrongInput = false;
			} else {
				wrongInput = true;
			}

		} while (wrongInput);
		wrongInput = true;
		do {
			try {
				System.out.println("\nPlease Enter the Number of Draw/s (1 or 3) : ");
				numberOfDraw = scan.nextInt();
				if (numberOfDraw == 1 || numberOfDraw == 3) {
					wrongInput = false;
					System.out.println("\nCondition accepted \nNumber of draws is " + numberOfDraw);
				} else {
					System.out.println("\nInvalid Input \nCannot draw " + numberOfDraw + " number of times");
					wrongInput = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Input \nPlease input a valid number");
				scan.next();
			}
		} while (wrongInput);

		scan.close();

		game.start(numberOfDraw, inputUser);
		System.out.println("=== END ===");
	}
}

package com.svi.solitaire.main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		SolitaireGame game = new SolitaireGame();
		Scanner scan = new Scanner(System.in);
		int numberOfDraws = 0;
		String inputUser;

		boolean wrongInput = true;

		do {

			System.out.println("Do you want to shuffle the Deck? (Yes / No): ");
			inputUser = scan.nextLine();
			if (inputUser.equalsIgnoreCase("yes") == true) {
				wrongInput = false;
			} else if (inputUser.equalsIgnoreCase("No")) {
				wrongInput = false;
			} else {
				wrongInput = true;
			}

		} while (wrongInput);

		do {
			try {
				System.out.println("\nPlease Enter the Number of Draw/s (1 or 3) : ");
				int numberOfDraw = scan.nextInt();
				if (numberOfDraw == 1 || numberOfDraw == 3) {
					numberOfDraws = numberOfDraw;
					wrongInput = false;
					System.out.println("\nCondition accepted \nNumber of draws is " + numberOfDraw);
				} else {
					System.out.println("\nInvalid Input \nCannot draw " + numberOfDraw + " number of times");
					wrongInput = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Input nPlease input a valid number");
				scan.nextLine();
			}
		} while (wrongInput);

		scan.close();

		game.start(numberOfDraws, inputUser);
		System.out.println("=== END ===");
	}
}

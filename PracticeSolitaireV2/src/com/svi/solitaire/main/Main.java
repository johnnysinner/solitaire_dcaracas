package com.svi.solitaire.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.svi.solitaire.models.Rank;
import com.svi.solitaire.models.Suit;
import com.svi.solitaire.object.Card;

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

		File f1 = new File("input.txt");
		Scanner scanner;
		if (!f1.exists()) {
			System.out.println("File " + f1 + " not found.\nInput the path of the txt file:");
		} else if (f1.exists()) {
			try {
				scanner = new Scanner(f1);
				System.out.println("The file " + f1 + " has been found.");
				while (scanner.hasNextLine()) {
					String data[] = scanner.nextLine().split(",");
					for (String data1 : data) {
						String pair[] = data1.split("-");
						if (pair.length == 2) {
							Rank rank = GameMethods.findRank(pair[1]);
							Suit suit = GameMethods.findSuit(pair[0]);

							if (rank != null && suit != null) {
								Card card = new Card();
								card.setSuit(suit);
								card.setRank(rank);
								SolitaireGame.deck.addCardAtBottom(card);
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		while (!f1.exists()) {
			String fileNameInput = scan.nextLine();
			f1 = new File(fileNameInput);
			try {
				scanner = new Scanner(f1);
				while (scanner.hasNextLine()) {
					String data[] = scanner.nextLine().split(",");
					for (String data1 : data) {
						String pair[] = data1.split("-");
						if (pair.length == 2) {
							Rank rank = GameMethods.findRank(pair[1]);
							Suit suit = GameMethods.findSuit(pair[0]);

							if (rank != null && suit != null) {
								Card card = new Card();
								card.setSuit(suit);
								card.setRank(rank);
								SolitaireGame.deck.addCardAtBottom(card);
							}
						}
					}

				}
			} catch (FileNotFoundException e) {
				System.out.println("File not Found. Input again the path:");
			}
		}
		scan.close();

		game.start(numberOfDraw, inputUser);
		System.out.println("=== END ===");
	}
}

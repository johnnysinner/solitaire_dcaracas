package com.svi.solitaire.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.svi.solitaire.models.Rank;
import com.svi.solitaire.models.Suit;
import com.svi.solitaire.object.Card;
import com.svi.solitaire.object.Deck;

public class SolitaireGame {

	private Deck deck = new Deck();
	private Deck talondeck = new Deck();
	ArrayList<ArrayList<Card>> tableaus = new ArrayList<>();
	ArrayList<ArrayList<Card>> foundation = new ArrayList<>();
	private int movesdone = 0;
	private boolean isGameFinish = false;
	private int totalMovesDone = 0;

	public SolitaireGame() {
		printWelcomeMessage();
	}

	// loop of the game
	void start(int numberOfDraw, String inputUser) {
		populateDeck();
		checkIfTheUserWantShuffle(inputUser);
		populateTableau();
		populateFoundation();
		printAll();
		makeFacedUpAllinDeck();
		while (!isGameFinish) {
			moveCardFromLineToLine();
			moveKingFromLinetoEmptyLine();
			sendCardtoFoundation();
			drawCard(numberOfDraw);
			checkOtherMoves();
			checkTheGameIfWon();
		}
	}

	private void checkIfTheUserWantShuffle(String inputUser) {
		if (inputUser.equalsIgnoreCase("Yes") || inputUser.equalsIgnoreCase("y"))
			Collections.shuffle(this.deck.getCards());
	}

	// print some text before start of the game
	private void printWelcomeMessage() {
		System.out.println("     =============================================");
		System.out.println("         ~ WELCOME TO KLONDIKE SOLITAIRE GAME ~");
		System.out.println("     =============================================");
	}

	// printing the entire game;
	private void printAll() {
		printFoundation();
		printTableausStacks();
		printFirstCardOfDrawPile();
	}

	private void printFirstCardOfDrawPile() {
		System.out.print("[xxxx]       -------------->     ");
		if (this.talondeck.isEmpty()) {
			System.out.println("[    ]");
		} else {
			System.out.println(this.talondeck.getCards().get(this.talondeck.getSize() - 1).toString());
		}
		System.out.println(
				"Remain in Draw Pile: " + this.deck.getSize() + " Remain in Talon : " + this.talondeck.getSize());
		System.out.println("_____________________________________________________");
	}

	private void printFoundation() {
		System.out.println("______________________________________________________");
		System.out.print("\t    Foundation: ");

		for (ArrayList<Card> suit : this.foundation) {
			if (suit.isEmpty()) {
				System.out.print("[ -- ]  ");
			} else {
				System.out.print(suit.get(suit.size() - 1).toString() + "  ");
			}
		}
		System.out.println("\n");
	}

	private void printTableausStacks() {
		for (int x = 1; x <= this.tableaus.size(); x++) {
			System.out.print(x + "- col\t");
		}
		System.out.println("");
		for (int i = 0; i < highestNumberOfCardinTableau(); i++) {
			for (int j = 0; j < this.tableaus.size(); j++) {
				if (i < this.tableaus.get(j).size()) {
					System.out.print(this.tableaus.get(j).get(i).toString() + "  ");
				} else {
					System.out.print("\t");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// populating methods
	public void populateDeck() {
		File f1 = new File("input.txt");
		Scanner scanner;
		Scanner scan = new Scanner(System.in);
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
								deck.addCardAtBottom(card);
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
								this.deck.addCardAtBottom(card);
							}
						}
					}

				}
			} catch (FileNotFoundException e) {
				System.out.println("File not Found. Input again the path:");
			}
		}
		scan.close();
	}

	private void populateFoundation() {
		/* adding four list inside the foundation list */
		for (int i = 0; i < 4; ++i) {
			ArrayList<Card> suit = new ArrayList<>();
			this.foundation.add(suit);
		}
	}

	private void populateTableau() {
		System.out.println("");
		// 7 tableaus
		for (int i = 0; i < 7; ++i) {
			ArrayList<Card> stack = new ArrayList<>();
			this.tableaus.add(stack);
		}
		for (int i = 0; i < 7; i++) {

			for (int x = i; x < 7; x++) {
				if (x == i) {
					this.deck.getCards().get(0).setFacedUp(true);
				}
				this.tableaus.get(x).add(this.deck.drawCardFromTop());

			}
		}
	}

	// for checking inside the drawPile, This only makes the remaining card open
	private void makeFacedUpAllinDeck() {
		System.out.println("\nRemaining in Deck Pile: ");
		for (int i = 0; i < this.deck.getSize(); i++) {
			deck.getCards().get(i).setFacedUp(true);
			System.out.print(deck.getCards().get(i).toString());
		}
		System.out.println("");
	}

	// reserve method for perfectShuffle
	private void shuffleDeck(int numOfShuffles) {
		Deck shuffledDeck = new Deck();
		Deck temporaryDeck = new Deck();

		for (int i = 0; i < numOfShuffles; i++) {
			if (shuffledDeck.isEmpty()) {
				temporaryDeck = this.deck;
			} else {
				temporaryDeck = shuffledDeck;
			}
			shuffledDeck = new Deck();
			for (int j = 0; j < deck.getSize() / 2; j++) {
				shuffledDeck.addCardAtTop(temporaryDeck.getCards().get(j));
				shuffledDeck.addCardAtTop(temporaryDeck.getCards().get(j + (this.deck.getSize() / 2)));
			}
		}
		this.deck = shuffledDeck;
		System.out.println("\nShuffled Deck :");
		for (int i = 0; i < this.deck.getSize(); i++) {
			this.deck.getCards().get(i).setFacedUp(true);
			System.out.print(this.deck.getCards().get(i) + ", ");
			this.deck.getCards().get(i).setFacedUp(false);
		}
	}

	// some important variable that need to check
	private int numberOfOpenCards(ArrayList<Card> tableau) {
		/* checking the open cards in the line per tableau */
		int count = 0;
		for (Card card : tableau) {
			if (card.isFacedUp() == true)
				count++;
		}
		return count;
	}

	private int highestNumberOfCardinTableau() {
		/*
		 * This method is used for printing the tableau
		 */
		int count = 0;
		for (ArrayList<Card> tableau : this.tableaus) {
			if (tableau.size() > count) {
				count = tableau.size();
			}
		}
		return count;
	}

	// Start of the Move in Game
	private Card drawCard(int numberOfDraw) {
		/*
		 * In this method,if there is no move done in one loop of deck pile,
		 * this will finish the game as will only loop the drawing of the cards
		 * in the deck pile
		 */
		Card card = new Card();
		if (this.deck.getSize() == 0) {
			System.out.println("Moves done in one loop of the deck: " + this.movesdone);
			this.totalMovesDone += this.movesdone;
			if (this.movesdone == 0) {
				this.isGameFinish = true;
				System.out.println("The Game is Finish");
				System.out.println("Total moves done: " + this.totalMovesDone);
				return null;
			}
			this.movesdone = 0;
			this.deck.addAllCards(talondeck);
			if (this.deck.getSize() == 0)
				return null;
			System.out.println("Returning all cards to Deck Pile");
			talondeck.clear();
		}
		if (numberOfDraw == 1) {
			card = deck.drawCardFromTop();
			System.out.println("Drew:" + card + "from the Deck Pile");
			this.talondeck.addCardAtBottom(card);

		} else if (numberOfDraw == 3) {
			if (this.deck.getSize() >= 3) {
				for (int x = 0; x < 3; x++) {
					card = deck.drawCardFromTop();
					System.out.println("Drew:" + card + "from the Deck Pile");
					this.talondeck.addCardAtBottom(card);
				}
			} else {
				for (int x = 0; x < this.deck.getSize(); x++) {
					card = deck.drawCardFromTop();
					System.out.println("Drew:" + card + "from the Deck Pile");
					this.talondeck.addCardAtBottom(card);
				}
			}
		}
		printAll();
		return card;
	}

	private void moveCardFromLineToLine() {

		// priority the lines that has close cards
		for (ArrayList<Card> tableauOrigin : this.tableaus) {
			int i = 0;
			for (ArrayList<Card> tableauDestination : this.tableaus) {
				i++;
				if (tableauDestination.isEmpty() || tableauOrigin.isEmpty())
					continue;
				Card topCardOfTableauOriginThatIsOpen = tableauOrigin
						.get(tableauOrigin.size() - numberOfOpenCards(tableauOrigin));
				Card topCardOfTableauDestination = tableauDestination.get(tableauDestination.size() - 1);
				if (tableauOrigin.size() - numberOfOpenCards(tableauOrigin) > 0) {
					if (topCardOfTableauOriginThatIsOpen.getRank().getValue() + 1 == topCardOfTableauDestination
							.getRank().getValue()
							&& topCardOfTableauDestination.getColor() != topCardOfTableauOriginThatIsOpen.getColor()) {
						int indexOfCardToBeMove = tableauOrigin.size() - numberOfOpenCards(tableauOrigin);
						for (int x = numberOfOpenCards(tableauOrigin); x > 0; x--) {
							System.out.print(tableauOrigin.get(indexOfCardToBeMove).toString() + " ");
							tableauDestination.add(tableauOrigin.remove(indexOfCardToBeMove));
						}
						System.out.println(" has been moved to column " + i);
						if (tableauOrigin.size() > 0) {
							tableauOrigin.get(tableauOrigin.size() - 1).setFacedUp(true);
						}
						this.movesdone += 1;
						printAll();
						checkOtherMoves();
						return;
					}
				}

			}
			// loop for moving the cards to another line along without close
			// card on top of it.
			int y = 0;
			for (ArrayList<Card> tableauDestination : this.tableaus) {
				y++;
				if (tableauDestination.isEmpty() || tableauOrigin.isEmpty())
					continue;
				Card topCardOfTableauOriginThatIsOpen = tableauOrigin
						.get(tableauOrigin.size() - numberOfOpenCards(tableauOrigin));
				Card topCardOfTableauDestination = tableauDestination.get(tableauDestination.size() - 1);

				if (topCardOfTableauOriginThatIsOpen.getRank().getValue() + 1 == topCardOfTableauDestination.getRank()
						.getValue()
						&& topCardOfTableauDestination.getColor() != topCardOfTableauOriginThatIsOpen.getColor()) {
					int indexOfCardToBeMove = tableauOrigin.size() - numberOfOpenCards(tableauOrigin);
					for (int x = numberOfOpenCards(tableauOrigin); x > 0; x--) {
						System.out.print(tableauOrigin.get(indexOfCardToBeMove).toString() + " ");
						tableauDestination.add(tableauOrigin.remove(indexOfCardToBeMove));
					}
					System.out.println(" has been moved to column " + y);
					if (tableauOrigin.size() > 0) {
						tableauOrigin.get(tableauOrigin.size() - 1).setFacedUp(true);
					}
					this.movesdone += 1;
					printAll();
					checkOtherMoves();
					return;

				}

			}
		}

	}

	private void moveCardFromTalonDecktoLine() {
		int i = 0;
		for (ArrayList<Card> tableauDestination : this.tableaus) {
			if (tableauDestination.isEmpty() || this.talondeck.isEmpty())
				continue;
			i++;
			Card lastcardOfDrawPile = this.talondeck.getCards().get(talondeck.getSize() - 1);
			Card lastcardOfTableau = tableauDestination.get(tableauDestination.size() - 1);
			if (lastcardOfDrawPile.getRank().getValue() + 1 == lastcardOfTableau.getRank().getValue()
					&& lastcardOfDrawPile.getColor() != lastcardOfTableau.getColor()) {
				System.out.println(lastcardOfDrawPile.toString() + " has been moved to column " + i);
				tableauDestination.add(this.talondeck.remove(talondeck.getSize() - 1));
				this.movesdone += 1;
				printAll();
				checkOtherMoves();
				return;
			}
		}
	}

	private void moveKingFromLinetoEmptyLine() {
		// if the king is on 0 index, it will skip the index
		for (ArrayList<Card> tableauOrigin : this.tableaus) {
			int i = 0;
			if (tableauOrigin.size() > 1) {
				if (tableauOrigin.get(0).getRank() == Rank.KING)
					continue;
				if (tableauOrigin.get(tableauOrigin.size() - numberOfOpenCards(tableauOrigin)).getRank() == Rank.KING) {
					for (ArrayList<Card> tableauDestination : this.tableaus) {
						i++;
						if (tableauDestination.isEmpty()) {
							int indexOfCardToBeMove = tableauOrigin.size() - numberOfOpenCards(tableauOrigin);
							for (int x = numberOfOpenCards(tableauOrigin); x > 0; x--) {
								System.out.print(tableauOrigin.get(indexOfCardToBeMove).toString() + " ");
								tableauDestination.add(tableauOrigin.remove(indexOfCardToBeMove));
							}
							System.out.println(" has been moved to column " + i);
							if (tableauOrigin.size() > 0) {
								tableauOrigin.get(tableauOrigin.size() - 1).setFacedUp(true);
							}
							this.movesdone += 1;
							printAll();
							checkOtherMoves();
							return;
						}
					}
				}
			}
		}
	}

	private void moveKingFromTalonDeckToEmptyColumn() {
		/*
		 * this method will send the king from Talon Deck to Empty Column, as
		 * this is also important for drawing 3 cards
		 */
		if (!this.talondeck.isEmpty()) {
			Card topCardofDeckPile = this.talondeck.getCards().get(this.talondeck.getSize() - 1);
			if (topCardofDeckPile.getRank() == Rank.KING) {
				int i = 1;
				for (ArrayList<Card> tableau : this.tableaus) {
					if (tableau.isEmpty()) {
						if (this.talondeck.isEmpty())
							break;
						System.out.println(topCardofDeckPile.toString() + "move to column " + i);
						tableau.add(this.talondeck.remove(this.talondeck.getSize() - 1));
						this.movesdone += 1;
						printAll();						
						checkOtherMoves();
						return;
					}
					i++;
				}
				i = 0;
			}
		}
	}

	private void sendCardtoFoundation() {

		/* sending ACES from tableau lines to foundation */
		for (ArrayList<Card> tableau : this.tableaus) {
			if (tableau.size() == 0)
				continue;
			if (tableau.get(tableau.size() - 1).getRank() == Rank.ACE) {
				System.out.println(tableau.get(tableau.size() - 1).toString() + "sent to Foundation");
				if (tableau.get(tableau.size() - 1).getSuit() == Suit.CLUBS) {
					this.foundation.get(0).add(tableau.remove(tableau.size() - 1));
				} else if (tableau.get(tableau.size() - 1).getSuit() == Suit.SPADES) {
					this.foundation.get(1).add(tableau.remove(tableau.size() - 1));
				} else if (tableau.get(tableau.size() - 1).getSuit() == Suit.HEARTS) {
					this.foundation.get(2).add(tableau.remove(tableau.size() - 1));
				} else if (tableau.get(tableau.size() - 1).getSuit() == Suit.DIAMONDS) {
					this.foundation.get(3).add(tableau.remove(tableau.size() - 1));
				}
				this.movesdone += 1;
				printFoundation();
				if (tableau.size() > 0)
					tableau.get(tableau.size() - 1).setFacedUp(true);
				printTableausStacks();
				printFirstCardOfDrawPile();
				sendCardtoFoundation();
				return;
			}
			/*
			 * sending the next card that is sent send to foundation from line
			 * to foundation
			 */
			for (ArrayList<Card> suitFoundation : this.foundation) {
				if (!suitFoundation.isEmpty()) {
					if (tableau.size() == 0)
						break;
					Card topcardofsuitFoundation = suitFoundation.get(suitFoundation.size() - 1);
					Card topCardOfTableau = tableau.get(tableau.size() - 1);
					if (topCardOfTableau.getSuit() == topcardofsuitFoundation.getSuit()
							&& topCardOfTableau.getRank().getValue() - 1 == topcardofsuitFoundation.getRank()
									.getValue()) {
						System.out.println(tableau.get(tableau.size() - 1).toString() + " sent to Foundation");
						suitFoundation.add(tableau.remove(tableau.size() - 1));
						this.movesdone += 1;
						printFoundation();
						if (tableau.size() > 0)
							tableau.get(tableau.size() - 1).setFacedUp(true);
						printTableausStacks();
						printFirstCardOfDrawPile();
						sendCardtoFoundation();
						return;

					}
				}
			}
		}
		/*
		 * if the topcard of the talon deck is ACE it will send to the
		 * foundation accordingly
		 */
		if (this.talondeck.getSize() > 0) {
			Card topCardofTalonDeck = this.talondeck.getCards().get(this.talondeck.getSize() - 1);
			if (topCardofTalonDeck.getRank() == Rank.ACE) {
				System.out.println(topCardofTalonDeck.toString() + "sent to Foundation");
				if (topCardofTalonDeck.getSuit() == Suit.CLUBS) {
					this.foundation.get(0).add(this.talondeck.remove(this.talondeck.getSize() - 1));
				} else if (topCardofTalonDeck.getSuit() == Suit.SPADES) {
					this.foundation.get(1).add(this.talondeck.remove(this.talondeck.getSize() - 1));
				} else if (topCardofTalonDeck.getSuit() == Suit.HEARTS) {
					this.foundation.get(2).add(this.talondeck.remove(this.talondeck.getSize() - 1));
				} else if (topCardofTalonDeck.getSuit() == Suit.DIAMONDS) {
					this.foundation.get(3).add(this.talondeck.remove(this.talondeck.getSize() - 1));
				}
				this.movesdone += 1;
				printAll();
				sendCardtoFoundation();
				return;
			}
			/*
			 * checking if the topcard of the talon deck is suitable for sending
			 * it to the foundation, it is important for drawing 3 cards at a
			 * time
			 */

			for (ArrayList<Card> suitFoundation : this.foundation) {
				if (this.talondeck.isEmpty())
					break;
				if (suitFoundation.isEmpty())
					continue;
				if (topCardofTalonDeck.getSuit() == suitFoundation.get(suitFoundation.size() - 1).getSuit()
						&& topCardofTalonDeck.getRank().getValue() - 1 == suitFoundation.get(suitFoundation.size() - 1)
								.getRank().getValue()) {
					System.out.println(topCardofTalonDeck.toString() + " send to Foundation");
					suitFoundation.add(this.talondeck.remove(this.talondeck.getSize() - 1));
					this.movesdone += 1;
					printAll();
					sendCardtoFoundation();
				}
			}
		}
	}

	// loop for other moves
	private void checkOtherMoves() {
		sendCardtoFoundation();
		moveCardFromTalonDecktoLine();
		moveCardFromLineToLine();
		moveKingFromLinetoEmptyLine();
		moveKingFromTalonDeckToEmptyColumn();
	}

	// check if the game is won
	private void checkTheGameIfWon() {
		/*
		 * The game will be finish, if the total size of all list in foundation
		 * is equal to 52
		 */
		int numberOfCardsInTheFoundation = 0;
		numberOfCardsInTheFoundation = this.foundation.get(0).size() + this.foundation.get(1).size()
				+ this.foundation.get(2).size() + this.foundation.get(3).size();
		if (numberOfCardsInTheFoundation == 52) {
			this.isGameFinish = true;
			System.out.println("You have won the game");
			this.totalMovesDone += this.movesdone;
			System.out.println("You have move " + this.totalMovesDone + " times to win the game.");
		}
	}
}
package com.svi.solitaire.main;

import java.util.ArrayList;
import com.svi.solitaire.models.Rank;
import com.svi.solitaire.models.Suit;
import com.svi.solitaire.object.Card;
import com.svi.solitaire.object.Deck;

public class SolitaireGame {

	private Deck deck = new Deck();
	ArrayList<ArrayList<Card>> tableaus = new ArrayList<>();
	ArrayList<Card> talondeck = new ArrayList<>();
	ArrayList<ArrayList<Card>> foundation = new ArrayList<>();
	private int movesdone = 0;
	private boolean isGameFinish = false;
	private int totalMovesDone = 0;

	public SolitaireGame() {
		printWelcomeMessage();
		populateDeck();
		this.deck.shuffleCards();
		populateTableau();
		populateFoundation();
		printAll();
		makeFacedUpAllinDeck();
	}

	// loop of the game
	void start(int numberOfDraw) {
		while (!isGameFinish) {
			sendCardtoFoundation();
			moveKingFromLinetoEmptyLine();
			moveCardFromLineToLine();
			drawCard(numberOfDraw);
			checkOtherMoves();
			checkTheGameIfWon();
		}
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
		printStacks();
		printFirstCardOfDrawPile();
	}

	private void printFirstCardOfDrawPile() {
		System.out.print("[xxxx]       -------------->     ");
		if (this.talondeck.isEmpty()) {
			System.out.println("[    ]");
		} else {
			System.out.println(this.talondeck.get(this.talondeck.size() - 1).toString());
		}
		System.out
				.println("Remain in Draw Pile: " + this.deck.getSize() + " Remain in Talon : " + this.talondeck.size());
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

	private void printStacks() {
		for (int x = 1; x <= this.tableaus.size(); x++) {
			System.out.print(x + "- col\t");
		}
		System.out.println("");
		for (int i = 0; i < highestNumberOfCardinTableau(); i++) {
			for (int j = 0; j < this.tableaus.size(); j++) {
				if (i < this.tableaus.get(j).size()) {
					System.out.print(this.tableaus.get(j).get(i) + "  ");
				} else {
					System.out.print("\t");
				}
			}
			System.out.print("\n");
		}
		System.out.println("");
	}

	// populating methods
	private void populateDeck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				Card card = new Card();
				card.setRank(rank);
				card.setSuit(suit);
				this.deck.addCardAtBottom(card);
			}
		}
	}

	private void populateFoundation() {

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
		int count = 0;
		for (Card card : tableau) {
			if (card.isFacedUp() == true)
				count++;
		}
		return count;
	}

	private int highestNumberOfCardinTableau() {
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
		Card card = new Card();
		if (numberOfDraw == 1) {
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
				this.deck.getCards().addAll(talondeck);
				if (this.deck.getSize() == 0)
					return null;
				System.out.println("Returning all cards to Deck Pile");
				talondeck.clear();
			}
			card = deck.drawCardFromTop();
			System.out.println("Drew:" + card + "from the Deck Pile");
			this.talondeck.add(card);

		} else if (numberOfDraw == 3) {
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
				this.deck.getCards().addAll(talondeck);
				if (this.deck.getSize() == 0)
					return null;
				System.out.println("Returning all cards to Deck Pile");
				talondeck.clear();
			}
			if (this.deck.getSize() >= 3) {
				for (int x = 0; x < 3; x++) {
					card = deck.drawCardFromTop();
					System.out.println("Drew:" + card + "from the Deck Pile");
					this.talondeck.add(card);
				}
			} else {
				for (int x = 0; x < this.deck.getSize(); x++) {
					card = deck.drawCardFromTop();
					System.out.println("Drew:" + card + "from the Deck Pile");
					this.talondeck.add(card);
				}
			}
		}
		printAll();
		return card;
	}

	private void moveCardFromLineToLine() {
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
			Card lastcardOfDrawPile = this.talondeck.get(talondeck.size() - 1);
			Card lastcardOfTableau = tableauDestination.get(tableauDestination.size() - 1);
			if (lastcardOfDrawPile.getRank().getValue() + 1 == lastcardOfTableau.getRank().getValue()
					&& lastcardOfDrawPile.getColor() != lastcardOfTableau.getColor()) {
				System.out.println(lastcardOfDrawPile.toString() + " has been moved to column " + i);
				tableauDestination.add(this.talondeck.remove(talondeck.size() - 1));
				this.movesdone += 1;
				printAll();
				checkOtherMoves();
				return;
			}
		}
	}

	private void moveKingFromLinetoEmptyLine() {
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
		if (!this.talondeck.isEmpty()) {
			Card topCardofDeckPile = this.talondeck.get(this.talondeck.size() - 1);
			if (topCardofDeckPile.getRank() == Rank.KING) {
				int i = 1;
				for (ArrayList<Card> tableau : this.tableaus) {
					if (tableau.isEmpty()) {
						if (this.talondeck.isEmpty())
							break;
						System.out.println(topCardofDeckPile.toString() + "move to column " + i);
						tableau.add(this.talondeck.remove(this.talondeck.size() - 1));
						this.movesdone += 1;
						printFoundation();
						printStacks();
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
				printStacks();
				printFirstCardOfDrawPile();
				sendCardtoFoundation();
				return;
			}

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
						printStacks();
						printFirstCardOfDrawPile();
						sendCardtoFoundation();
						return;

					}
				}
			}
		}

		if (this.talondeck.size() > 0) {
			Card topCardofDeckPile = this.talondeck.get(this.talondeck.size() - 1);
			if (topCardofDeckPile.getRank() == Rank.ACE) {
				System.out.println(topCardofDeckPile.toString() + "sent to Foundation");
				if (topCardofDeckPile.getSuit() == Suit.CLUBS) {
					this.foundation.get(0).add(this.talondeck.remove(this.talondeck.size() - 1));
				} else if (topCardofDeckPile.getSuit() == Suit.SPADES) {
					this.foundation.get(1).add(this.talondeck.remove(this.talondeck.size() - 1));
				} else if (topCardofDeckPile.getSuit() == Suit.HEARTS) {
					this.foundation.get(2).add(this.talondeck.remove(this.talondeck.size() - 1));
				} else if (topCardofDeckPile.getSuit() == Suit.DIAMONDS) {
					this.foundation.get(3).add(this.talondeck.remove(this.talondeck.size() - 1));
				}
				this.movesdone += 1;
				printAll();
				sendCardtoFoundation();
				return;
			}
			for (ArrayList<Card> suitFoundation : this.foundation) {
				if (this.talondeck.isEmpty())
					break;
				if (suitFoundation.isEmpty())
					continue;
				if (topCardofDeckPile.getSuit() == suitFoundation.get(suitFoundation.size() - 1).getSuit()
						&& topCardofDeckPile.getRank().getValue() - 1 == suitFoundation.get(suitFoundation.size() - 1)
								.getRank().getValue()) {
					System.out.println(topCardofDeckPile.toString() + " send to Foundation");
					suitFoundation.add(this.talondeck.remove(this.talondeck.size() - 1));
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

	// check if the game is won, if the total size of foundation = 52
	private void checkTheGameIfWon() {
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

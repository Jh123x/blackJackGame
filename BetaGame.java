import java.util.*;

public class BetaGame { // not sure if duplicate cards are fixed. There is currently known issue of 4
						// aces causing problem. Bad habit : using too much while loops

	public static int result;
	public static ArrayList<String> cardValues;
	public static boolean keepGoing;
	public static int houseResult;
	public static int aceCount;
	public static int houseAceCount;
	public static int betValue;
	public static int playerCredits;
	public static Scanner in;

	private static void startGame() {

		in = new Scanner(System.in);
		System.out.println("Welcome to blackjack! You currently have " + playerCredits + " credits"); // this system is
																										// 100% not
																										// working LOL
		System.out.println("To forfeit the game, just type end when asked to enter bet amount");
		System.out.println("Please enter bet amount:");
		try {
			betValue = in.nextInt();
			while (betValue > playerCredits) {
				System.out.println("Not enough credits, please enter again:");
				betValue = in.nextInt();

			}
		} catch (Exception ex) { // not working
			System.out.println("game end");
			System.exit(0);
			playerCredits = 0;
			keepGoing = false;
		}

		playerCredits = playerCredits - betValue;

	}

	private static void calculateBet() {
		playerCredits = playerCredits + betValue;
		System.out.println("You have won " + betValue + " credits");
		System.out.println();
	}

	private static void dealhand() {
		if (playerCredits >= 0) {
			shuffle();
			String firstCard = hit();
			System.out.println("You got " + firstCard);
			int firstCardValue = getCardValue(firstCard);
			String secondCard = hit();
			System.out.println("You got " + secondCard);
			int secondCardValue = getCardValue(secondCard);
			result = checkResult(firstCardValue, secondCardValue);
			System.out.println("You have a card value of " + result);
			if (result == 21) {
				System.out.println("Blackjack! You won.");
				betValue = betValue * 3;
			}
		}
	}

	private static void shuffle() {
		cardValues = new ArrayList<String>();
		cardValues.add("King of Spades");
		cardValues.add("King of Hearts");
		cardValues.add("King of Diamonds");
		cardValues.add("King of Clubs");
		cardValues.add("Queen of Spades");
		cardValues.add("Queen of Hearts");
		cardValues.add("Queen of Diamonds");
		cardValues.add("Queen of Clubs");
		cardValues.add("Jack of Spades");
		cardValues.add("Jack of Hearts");
		cardValues.add("Jack of Diamonds");
		cardValues.add("Jack of Clubs");
		cardValues.add("10 of Spades");
		cardValues.add("10 of Hearts");
		cardValues.add("10 of Diamonds");
		cardValues.add("10 of Clubs");
		cardValues.add("9 of Spades");
		cardValues.add("9 of Hearts");
		cardValues.add("9 of Diamonds");
		cardValues.add("9 of Clubs");
		cardValues.add("8 of Spades");
		cardValues.add("8 of Hearts");
		cardValues.add("8 of Diamonds");
		cardValues.add("8 of Clubs");
		cardValues.add("7 of Spades");
		cardValues.add("7 of Hearts");
		cardValues.add("7 of Diamonds");
		cardValues.add("7 of Clubs");
		cardValues.add("6 of Spades");
		cardValues.add("6 of Hearts");
		cardValues.add("6 of Diamonds");
		cardValues.add("6 of Clubs");
		cardValues.add("5 of Spades");
		cardValues.add("5 of Hearts");
		cardValues.add("5 of Diamonds");
		cardValues.add("5 of Clubs");
		cardValues.add("4 of Spades");
		cardValues.add("4 of Hearts");
		cardValues.add("4 of Diamonds");
		cardValues.add("4 of Clubs");
		cardValues.add("3 of Spades");
		cardValues.add("3 of Hearts");
		cardValues.add("3 of Diamonds");
		cardValues.add("3 of Clubs");
		cardValues.add("2 of Spades");
		cardValues.add("2 of Hearts");
		cardValues.add("2 of Diamonds");
		cardValues.add("2 of Clubs");
		cardValues.add("Ace of Spades");
		cardValues.add("Ace of Hearts");
		cardValues.add("Ace of Diamonds");
		cardValues.add("Ace of Clubs");
		Collections.shuffle(cardValues);
	}

	private static String hit() {
		String nextCard = dealCard();
		return nextCard;
	}

	private static void stand() {
		keepGoing = false;
		BetaGame.housePlays();
	}

 /*	private static String split() {
		playerCredits = playerCredits - betValue;
		betValue = betValue * 2;
		
	} */

	private static void doubleDown() {
		playerCredits = playerCredits - betValue;
		betValue = betValue * 2;
		String nextCard = hit();
		System.out.println("You got " + nextCard);
		int nextCardValue = getCardValue(nextCard);
		result = checkResult(result, nextCardValue);
		if (result > 21 && aceCount == 1) { // this seems to fix the ace issue, recommend further testing
			result = result - 10;
			aceCount = 0;
		}
		System.out.println("You have a final card value of " + result);
		if (result == 21) {
			System.out.println("Blackjack! You won!");
			betValue = betValue * 2;
		} else if (result > 21) {
			System.out.println("You lost.");
			betValue = 0;
			keepGoing = false;
		}

	}

	private static void housePlays() { // to compare value against player value to determine win/loss condition
		String firstHouseCard = hit();
		System.out.println("The house got " + firstHouseCard);
		int firstCardValue = getCardValue(firstHouseCard);
		String secondHouseCard = hit();
		System.out.println("The house got " + secondHouseCard);
		int secondCardValue = getCardValue(secondHouseCard);
		houseResult = checkResult(firstCardValue, secondCardValue);
		while (true) {
			if (houseResult > 21 && houseAceCount == 1) { // this seems to fix the ace issue, recommend further testing
				houseResult = houseResult - 10;
				houseAceCount = 0;
			}
			if (houseResult < 17) {
				String nextCard = hit();
				System.out.println("The house got " + nextCard);
				int nextCardValue = getCardValue(nextCard);
				houseResult = checkResult(houseResult, nextCardValue);
			} else if (houseResult > 21) {
				System.out.println("The house has a card value of " + houseResult);
				System.out.println("The house has lost. You won!");
				betValue = betValue * 2;
				break;
			} else {
				System.out.println("The house has a card value of " + houseResult);
				if (houseResult > result) {
					System.out.println("The house has won. You lose.");
					betValue = 0;
				} else if (houseResult == result) {
					System.out.println("The house has the same card value as you. It's a draw!");
				} else {
					System.out.println("The house has lost. You won!");
					betValue = betValue * 2;
				}
				break;
			}
		}

	}

	private static void askUserMenu() {
		if (playerCredits >= 0) {
			in = new Scanner(System.in);
			System.out.println("What will you do:");
			String userInput = in.nextLine();

			if (userInput.equalsIgnoreCase("hit")) {
				String nextCard = hit();
				System.out.println("You got " + nextCard);
				int nextCardValue = getCardValue(nextCard);
				result = checkResult(result, nextCardValue);
				if (result > 21 && aceCount == 1) { // this seems to fix the ace issue, recommend further testing
					result = result - 10;
					aceCount = 0;
				}
				System.out.println("You have a card value of " + result);
				if (result == 21) {
					System.out.println("Blackjack! You won!");
					betValue = betValue * 2;
				}
			} else if (userInput.equalsIgnoreCase("stand")) {
				System.out.println("You have a final card value of " + result);
				stand();
			} else if (userInput.equalsIgnoreCase("split")) {
				// placeholder

			} else if (userInput.equalsIgnoreCase("doubledown")) {
				doubleDown();
				if (keepGoing) {
					stand();
				}

			} else if (userInput.equalsIgnoreCase("split")) {
				System.out.println("work in progress");
			}

		}

	}

	private static String dealCard() { // currently dealing duplicate cards, need to fix
		String dealtCard = cardValues.get(0);
		cardValues.remove(0);
		return dealtCard;
	}

	private static int getCardValue(String dealtCard) {

		String arr[] = dealtCard.split(" ");
		if (arr[0].equals("Ace")) {
			int aceValue = 1; // need work on second value 11 for ace (seems to be fixed but could use further
								// testing)
			aceCount++;
			houseAceCount++;
			if (aceCount == 1) {
				aceValue = 11;
			} else if (houseAceCount == 1) {
				aceValue = 11;
			}

			return aceValue;

		} else if (arr[0].contentEquals("King") || arr[0].contentEquals("Queen") || arr[0].contentEquals("Jack")) {
			return 10;

		} else {
			return Integer.parseInt(arr[0]);
		}
	}

	private static int checkResult(int firstValue, int secondValue) {
		int newValue = firstValue + secondValue;
		return newValue;
	}

	public static void main(String[] args) {
		playerCredits = 100;
		while (playerCredits>0) {
			BetaGame.startGame();
			BetaGame.dealhand();
			keepGoing = true;
			while (keepGoing) {
				if (result < 21) {
					BetaGame.askUserMenu();
				} else if (result > 21) {
					System.out.println("You lost.");
					betValue = 0;
					break;
				}else {
					break;
				}

			}
			BetaGame.calculateBet();

		}
		if (playerCredits == 0) {
			System.out.println("You have 0 credits left. Game over.");
		}

	}

}

/*
 * else if (result > 21 && firstCard.contains("Ace") || result > 21 &&
 * secondCard.contains("Ace")) { result = result - 10; // troubleshoot for ace
 * being bigger than it should be
 * 
 * }
 */

/*
 * else if (houseResult > 21 && firstHouseCard.contains("Ace") || houseResult >
 * 21 && secondHouseCard.contains("Ace")) { houseResult = houseResult - 10; //
 * also to prevent ace being bigger than it should
 * 
 * }
 */

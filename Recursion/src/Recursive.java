/*  Student information for assignment:
*
*  On My honor, Manuel Ponce, this programming assignment is My own work
*  and I have not provided this code to any other student.
*
*  Number of slip days used: 0
*
*  Student 1 (Student whose Canvas account is being used)
*  UTEID: mip445	
*  email address: maniponce22@gmail.com
*  Grader name: Lucas
*  Section number: 
*
*  Student 2
*  UTEID:
*  email address:
*
*/

//imports

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Various recursive methods to be implemented. Given shell file for CS314
 * assignment.
 */
public class Recursive {

	/**
	 * Problem 1: convert a base 10 int to binary recursively. <br>
	 * pre: n != Integer.MIN_VALUE<br>
	 * <br>
	 * post: Returns a String that represents N in binary. All chars in returned
	 * String are '1's or '0's. Most significant digit is at position 0
	 * 
	 * @param n the base 10 int to convert to base 2
	 * @return a String that is a binary representation of the parameter n
	 */
	public static String getBinary(int n) {
		if (n == Integer.MIN_VALUE) {
			throw new IllegalArgumentException(
					"Failed precondition: getBinary. " + "n cannot equal Integer.MIN_VALUE. n: " + n);
		}

		// creates the new string builder that will build the binary number and return
		// the string
		String binaryString = Integer.toString(findingBinary(n));
		return binaryString;
	}

	// private helper method to find the binary number of a decimal number
	private static int findingBinary(int num) {
		if (num == 0) // Base Case: is zero when you have divided the number by 2 enough times.
			return 0;
		else {
			return (num % 2 + (10 * findingBinary(num / 2))); // Else find the remainder of the number divided by two,
																// then add the multiple of ten and the recursive binary
																// number of the number divided by two
		}
	}

	/**
	 * Problem 2: reverse a String recursively.<br>
	 * pre: stringToRev != null<br>
	 * post: returns a String that is the reverse of stringToRev
	 * 
	 * @param stringToRev the String to reverse.
	 * @return a String with the characters in stringToRev in reverse order.
	 */
	public static String revString(String stringToRev) {
		if (stringToRev == null) {
			throw new IllegalArgumentException("Failed precondition: revString. parameter may not be null.");
		}
		if (stringToRev.isEmpty()) // Base Case: Reaching the end of reversed string.
			return stringToRev;
		else {
			return revString(stringToRev.substring(1)) + stringToRev.charAt(0); // else get the char at index 0 and
																				// substring starting at index 1
		}
	}

	/**
	 * Problem 3: Returns the number of elements in data that are followed directly
	 * by value that is double that element. pre: data != null post: return the
	 * number of elements in data that are followed immediately by double the value
	 * 
	 * @param data The array to search.
	 * @return The number of elements in data that are followed immediately by a
	 *         value that is double the element.
	 */
	public static int nextIsDouble(int[] data) {
		if (data == null) {
			throw new IllegalArgumentException("Failed precondition: revString. parameter may not be null.");
		}
		int doubleCount = 0;
		return doubleHelper(data, 0, doubleCount); // must change. Need to write a helper method
	}

	// CS314 students, add your nextIsDouble helper method here
	private static int doubleHelper(int[] data, int index, int count) {

		if (index == data.length - 1) // Base Case: get to the end of the array and return the number of doubles found
			return count;
		else {
			if (data[index + 1] == data[index] * 2) // if current index has multitple next, increment count.
				count++;

			index++;
			return doubleHelper(data, index, count); // else move to the next element and see if you find if it has its
														// multiple next
		}
	}

	/**
	 * Problem 4: Find all combinations of mnemonics for the given number.<br>
	 * pre: number != null, number.length() > 0, all characters in number are
	 * digits<br>
	 * post: see tips section of assignment handout
	 * 
	 * @param number The number to find mnemonics for
	 * @return An ArrayList<String> with all possible mnemonics for the given number
	 */
	public static ArrayList<String> listMnemonics(String number) {
		if (number == null || number.length() == 0 || !allDigits(number)) {
			throw new IllegalArgumentException("Failed precondition: listMnemonics");
		}

		ArrayList<String> result = new ArrayList<>();
		recursiveMnemonics(result, "", number);
		return result;
	}

	/*
	 * Helper method for listMnemonics mnemonics stores completed mnemonics
	 * mneominicSoFar is a partial (possibly complete) mnemonic digitsLeft are the
	 * digits that have not been used from the original number
	 */
	private static void recursiveMnemonics(ArrayList<String> mnemonics, String mnemonicSoFar, String digitsLeft) {
		// CS314 students, complete this method
		char charNum = digitsLeft.charAt(0); // get the first number
		digitsLeft = digitsLeft.substring(1); // update numbers left
		String letters = digitLetters(charNum); // find the letters for that number

		for (int i = 0; i < letters.length(); i++) { // go through the letters for the number adding it to the mnemonic
			mnemonicSoFar = mnemonicSoFar + letters.charAt(i);

			if (digitsLeft.length() == 0) // if no more letters to go through add completed mnemonic
				mnemonics.add(mnemonicSoFar);
			else // else recursively call mnemonic to get the next letter
				recursiveMnemonics(mnemonics, mnemonicSoFar, digitsLeft);

			mnemonicSoFar = mnemonicSoFar.substring(0, mnemonicSoFar.length() - 1); // reset the mnemonic to before the
																					// letter was added
		}
		digitsLeft = charNum + digitsLeft; // reset the digitsLeft back the way it started out as.
	}

	// used by method digitLetters
	private static final String[] letters = { "0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ" };

	/*
	 * helper method for recursiveMnemonics pre: ch is a digit '0' through '9' post:
	 * return the characters associated with this digit on a phone keypad
	 */
	private static String digitLetters(char ch) {
		if (ch < '0' || ch > '9') {
			throw new IllegalArgumentException("parameter ch must be a digit, 0 to 9. Given value = " + ch);
		}
		int index = ch - '0';
		return letters[index];
	}

	/*
	 * helper method for listMnemonics pre: s != null post: return true if every
	 * character in s is a digit ('0' through '9')
	 */
	private static boolean allDigits(String s) {
		if (s == null) {
			throw new IllegalArgumentException("Failed precondition: allDigits. String s cannot be null.");
		}
		boolean allDigits = true;
		int i = 0;
		while (i < s.length() && allDigits) {
			allDigits = s.charAt(i) >= '0' && s.charAt(i) <= '9';
			i++;
		}
		return allDigits;
	}

	/**
	 * Problem 5: Draw a Sierpinski Carpet.
	 * 
	 * @param size  the size in pixels of the window
	 * @param limit the smallest size of a square in the carpet.
	 */
	public static void drawCarpet(int size, int limit) {
		DrawingPanel p = new DrawingPanel(size, size);
		Graphics g = p.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size, size);
		g.setColor(Color.WHITE);
		drawSquares(g, size, limit, 0, 0);
	}

	/*
	 * Helper method for drawCarpet Draw the individual squares of the carpet.
	 * 
	 * @param g The Graphics object to use to fill rectangles
	 * 
	 * @param size the size of the current square
	 * 
	 * @param limit the smallest allowable size of squares
	 * 
	 * @param x the x coordinate of the upper left corner of the current square
	 * 
	 * @param y the y coordinate of the upper left corner of the current square
	 */
	private static void drawSquares(Graphics g, int size, int limit, double x, double y) {
		if (size <= limit) // base case: do nothing
			return;
		else {
			int castedX = (int) x; // casted x to be passed
			int castedY = (int) y; // casted y to be passed
			int newSize = size / 3; // the newSize if size is greater than limit
			g.fillRect(castedX + newSize, castedY + newSize, newSize, newSize); // filling the middle square white.

			// used to go through the remaining 8 squares
			for (int i = 0; i <= 2; i++) {
				for (int j = 0; j <= 2; j++) {
					drawSquares(g, newSize, limit, castedX + (i * newSize), castedY + (j * newSize));
				}
			}
		}
	}

	/**
	 * Problem 6: Determine if water at a given point on a map can flow off the map.
	 * <br>
	 * pre: map != null, map.length > 0, map is a rectangular matrix, 0 <= row <
	 * map.length, 0 <= col < map[0].length <br>
	 * post: return true if a drop of water starting at the location specified by
	 * row, column can reach the edge of the map, false otherwise.
	 * 
	 * @param map The elevations of a section of a map.
	 * @param row The starting row of a drop of water.
	 * @param col The starting column of a drop of water.
	 * @return return true if a drop of water starting at the location specified by
	 *         row, column can reach the edge of the map, false otherwise.
	 */
	public static boolean canFlowOffMap(int[][] map, int row, int col) {
		if (map == null || map.length == 0 || !isRectangular(map) || !inbounds(row, col, map)) {
			throw new IllegalArgumentException("Failed precondition: canFlowOffMap");
		}

		if (row == 0 || col == 0 || row == map.length - 1 || col == map[row].length - 1) { // base case: if the water is
																							// on the edge
			return true; // water flows off whenever its on the edge
		}

		if (row + 1 < map.length && map[row + 1][col] < map[row][col]) { // checks if water can flow to row under
																			// current row
			if (canFlowOffMap(map, row + 1, col))
				return true;
		}
		if (row - 1 >= 0 && map[row - 1][col] < map[row][col]) { // checks if water can flow to row above current row
			if (canFlowOffMap(map, row - 1, col))
				return true;
		}
		if (col - 1 >= 0 && map[row][col - 1] < map[row][col]) { // checks if water can flow to col before current col
			if (canFlowOffMap(map, row, col - 1))
				return true;
		}
		if (col + 1 < map[row].length && map[row][col + 1] < map[row][col]) { // checks if water can flow to col after
																				// current col
			if (canFlowOffMap(map, row, col + 1))
				return true;
		}
		return false; // water cannot move to any adjacent cell because current cells are <= all
						// adjacent cellss
	}

	/*
	 * helper method for canFlowOfMap - CS314 students you should not have to call
	 * this method, pre: mat != null,
	 */
	private static boolean inbounds(int r, int c, int[][] mat) {
		assert mat != null : "Failed precondition: inbounds";
		return r >= 0 && r < mat.length && mat[r] != null && c >= 0 && c < mat[r].length;
	}

	/*
	 * helper method for canFlowOfMap - CS314 students you should not have to call
	 * this method, pre: mat != null, mat.length > 0 post: return true if mat is
	 * rectangular
	 */
	private static boolean isRectangular(int[][] mat) {
		assert (mat != null) && (mat.length > 0) : "Violation of precondition: isRectangular";

		boolean correct = true;
		final int numCols = mat[0].length;
		int row = 0;
		while (correct && row < mat.length) {
			correct = (mat[row] != null) && (mat[row].length == numCols);
			row++;
		}
		return correct;
	}

	/**
	 * Problem 7: Find the minimum difference possible between teams based on
	 * ability scores. The number of teams may be greater than 2. The goal is to
	 * minimize the difference between the team with the maximum total ability and
	 * the team with the minimum total ability. <br>
	 * pre: numTeams >= 2, abilities != null, abilities.length >= numTeams <br>
	 * post: return the minimum possible difference between the team with the
	 * maximum total ability and the team with the minimum total ability.
	 * 
	 * @param numTeams  the number of teams to form. Every team must have at least
	 *                  one member
	 * @param abilities the ability scores of the people to distribute
	 * @return return the minimum possible difference between the team with the
	 *         maximum total ability and the team with the minimum total ability.
	 *         The return value will be greater than or equal to 0.
	 */
	public static int minDifference(int numTeams, int[] abilities) {
		// checking precons
		if (numTeams < 2 || abilities == null || abilities.length < numTeams)
			throw new IllegalArgumentException("Failed precondition: minDifference");

		int index = 0; // used for keeping track of what person we are on when adding
		ArrayList<ArrayList<Integer>> teams = new ArrayList<ArrayList<Integer>>(); // to keep track of teams with the
																					// abilities of people on the team
		for (int i = 0; i < numTeams; i++) {
			teams.add(new ArrayList<Integer>());
		}
		int mainMin = Integer.MAX_VALUE;

		return teamPlacer(abilities, numTeams, teams, mainMin, index); // places poeple on teams
	}

	// private helper method to help put people on teams.
	private static int teamPlacer(int[] abilities, int numTeams, ArrayList<ArrayList<Integer>> teams, int mainMin,
			int ind) {

		if (ind == abilities.length) { // base case: if there is no more people to add and the number of teams has been
										// met.
			// temps for max and min
			int min = diffFind(teams, mainMin);
			return min < mainMin ? min : mainMin; // if min is less than mainMin update it to main min, else stay with
													// mainMin
		} else {
			// go through the teams and add people
			for (int i = 0; i < numTeams; i++) {
				teams.get(i).add(abilities[ind]);
				int num = ind + 1;
				mainMin = teamPlacer(abilities, numTeams, teams, mainMin, num); // places next person on a team

				teams.get(i).remove(teams.get(i).size() - 1); // remove the last person placed on the team
			}
			return mainMin;
		}
	}

	private static int diffFind(ArrayList<ArrayList<Integer>> teams, int dif) {
		// finds the max and min for the current teams
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		// check precons
		for (ArrayList<Integer> innerTeams : teams) {
			// checking if any of the innerTeams are null
			if (innerTeams == null)
				throw new IllegalArgumentException("InnerTeam is null");
		}

		// go through the innerTeams and add up abilitites and find the max and min from
		// all the teams and return the difference
		for (ArrayList<Integer> innerTeams : teams) {
			int count = 0;

			if (innerTeams.size() == 0) // if the has zero people return the mainDiff passed in
				return dif;

			// used to add up the total abilities for the teams and find the min count and
			// max count
			for (int i = 0; i < innerTeams.size(); i++) {
				count = count + innerTeams.get(i);
			}
			if (min > count)
				min = count;
			if (max < count)
				max = count;
		}

		return max - min; // returns the difference between max and min to be used to compare to others to
							// find the smallest difference
	}

	/**
	 * Problem 8: Maze solver. Return 2 if it is possible to escape the maze after
	 * collecting all the coins. Return 1 if it is possible to escape the maze but
	 * without collecting all the coins. Return 0 if it is not possible to escape
	 * the maze. More details in the assignment handout. <br>
	 * pre: board != null <br>
	 * pre: board is a rectangular matrix <br>
	 * pre: board only contains characters 'S', 'E', '$', 'G', 'Y', and IMPASSIBLE
	 * <br>
	 * pre: there is a single 'S' character present <br>
	 * post: rawMaze is not altered as a result of this method. Return 2 if it is
	 * possible to escape the maze after collecting all the coins. Return 1 if it is
	 * possible to escape the maze but without collecting all the coins. Return 0 if
	 * it is not possible to escape the maze. More details in the assignment
	 * handout.
	 * 
	 * @param rawMaze represents the maze we want to escape. rawMaze is not altered
	 *                as a result of this method.
	 * @return per the post condition
	 */

	// constant for valid chars in maze
	private static final char START = 'S';
	private static final char COIN = '$';
	private static final char GREEN = 'G';
	private static final char YELLOW = 'Y';
	private static final char IMPASSIBLE = '*';
	private static final char EXIT = 'E';

	public static int canEscapeMaze(char[][] rawMaze) {
		// copy of maze
		Maze usedMaze = new Maze(rawMaze); // copy of maze

		// gets initial postions
		int currentRow = usedMaze.rowStart();
		int currentCol = usedMaze.colStart();
		// gets total number of coins in maze
		int totCoins = usedMaze.returnTotCoins(); // totalCoins
		int coinCount = 0; // coin count for trip during maze

		return exploreMaze(totCoins, usedMaze, currentRow, currentCol, coinCount);
	}

	private static int exploreMaze(int totalCoins, Maze tempMaze, int row, int col, int curCoins) {

		// IF THERE IS NO EXIT RETURN 0
		if (!tempMaze.anExit())
			return 0;
		// baseCase: found an exit with full coins
		if (curCoins == totalCoins && tempMaze.getMaze()[row][col] == EXIT) { // Has all coins and is on exit
			return 2;
		}

		// else if: found an exit, but not enough coins
		else if (tempMaze.getMaze()[row][col] == EXIT) { // doesnt have all coins but is on exit
			return 1;
		}

		else if (tempMaze.getMaze()[row][col] != EXIT || tempMaze.getMaze()[row][col] != IMPASSIBLE) { // can still move

			char update = tempMaze.getMaze()[row][col]; // keeps the current positions value later used to reset.
			curCoins = (update == '$') ? curCoins + 1 : curCoins; // if current spot is a coin, update coinCount. If not
																	// keep current coin count.
			tempMaze.updateValueOfSquare(row, col, tempMaze.getMaze()); // update current square
			int best = 0; // stores the best result so far from recursive call
			int result = 0;

			// I TRIED LUCAS!!! I COULD NOT GET THE LOOP FOR DIRECTIONS TO WORK. MY B
			if (row + 1 < tempMaze.getMaze().length && tempMaze.getMaze()[row + 1][col] != IMPASSIBLE) {
				result = exploreMaze(totalCoins, tempMaze, row + 1, col, curCoins);
				if (result == 2)
					return 2;
				else if (result == 1)
					best = result;
			}

			if (row - 1 >= 0 && tempMaze.getMaze()[row - 1][col] != IMPASSIBLE) {
				result = exploreMaze(totalCoins, tempMaze, row - 1, col, curCoins);
				if (result == 2)
					return 2;
				else if (result == 1)
					best = result;
			}
			if (col + 1 < tempMaze.getMaze()[0].length && tempMaze.getMaze()[row][col + 1] != IMPASSIBLE) {

				result = exploreMaze(totalCoins, tempMaze, row, col + 1, curCoins);
				if (result == 2)
					return 2;
				else if (result == 1)
					best = result;
			}
			if (col - 1 >= 0 && tempMaze.getMaze()[row][col - 1] != IMPASSIBLE) {
				result = exploreMaze(totalCoins, tempMaze, row, col - 1, curCoins);

				if (result == 2)
					return 2;
				else if (result == 1)
					best = result;
			}

			tempMaze.getMaze()[row][col] = update; // resetting the update made.
			curCoins = update == '$' ? curCoins - 1 : curCoins; // reset the coinCount if the reset position was a coin
			// }
			return best; // return the best result found from the maze

		}
		return 0; // could not exit
	}

//++++++++++++++++++++++++++++++++++++++++//	
//++++++++++INNER CLASS FOR MAZE++++++++++//
//++++++++++++++++++++++++++++++++++++++++//
	public static class Maze {

		// instance variables
		private char[][] copyMaze;
		private int[] startCoordinates;
		private int totalCoins;

		// constructor
		Maze(char[][] original) {
			copyMaze = original; // copy of original maze

			// CheckingPreconditions
			if (this.valChars() || !this.isRecMaze() || this == null || !this.oneStart())
				throw new IllegalArgumentException("Failed precondition: TheMaze");

			findStart(copyMaze);
			totalCoins = getTotalCoins(copyMaze);
		}

		// returns maze
		public char[][] getMaze() {
			return copyMaze;
		}

		// returns total Coins in maze
		public int returnTotCoins() {
			return totalCoins;
		}

		// returns the starting position for row
		public int rowStart() {
			return startCoordinates[0];
		}

		// returns the starting position for col
		public int colStart() {
			return startCoordinates[1];
		}

		// finds the position of the start coordinate
		private void findStart(char[][] copyMaze) {
			startCoordinates = new int[2];
			for (int r = 0; r < copyMaze.length; r++) {
				for (int c = 0; c < copyMaze[r].length; c++) {
					if (copyMaze[r][c] == START) {
						startCoordinates[0] = r; // row start
						startCoordinates[1] = c; // col start
					}
				}
			}
		}

		// finds totalCoins
		private int getTotalCoins(char[][] copyMaze) {
			int coinCount = 0;
			for (int r = 0; r < copyMaze.length; r++) {
				for (int c = 0; c < copyMaze[r].length; c++) {
					if (copyMaze[r][c] == COIN) {
						coinCount++;
					}
				}
			}
			return coinCount;
		}

		// checks to see if there is one start
		public boolean oneStart() {
			for (int r = 0; r < copyMaze.length; r++) {
				for (int c = 0; c < copyMaze[r].length; c++) {
					int count = 0;
					if (copyMaze[r][c] == START) {
						count++;
						if (count > 1)
							return false;
					}
				}
			}
			return true;
		}

		// checks to see if there is an exit
		public boolean anExit() {
			for (int r = 0; r < copyMaze.length; r++) {
				for (int c = 0; c < copyMaze[r].length; c++) {
					if (copyMaze[r][c] == EXIT) {
						return true;
					}
				}
			}
			return false;
		}

		// checks to see if all chars are valid
		public boolean valChars() {
			for (int r = 0; r < copyMaze.length; r++) {
				for (int c = 0; c < copyMaze[r].length; c++) {
					char temp = copyMaze[r][c];
					if (temp != COIN || temp != START || temp != EXIT || temp != GREEN || temp != YELLOW)
						return false;
				}
			}

			return true;
		}

		// helper method to check if the maze is rectangular
		public boolean isRecMaze() {
			assert (copyMaze != null) && (copyMaze.length > 0) : "Violation of precondition: isRectangular";

			boolean correct = true;
			final int numCols = copyMaze[0].length;
			int row = 0;
			while (correct && row < copyMaze.length) {
				correct = (copyMaze[row] != null) && (copyMaze[row].length == numCols);
				row++;
			}
			return correct;
		}

		// updates value of squares
		public void updateValueOfSquare(int row, int col, char[][] copyMaze) {
			// update green to yellow
			if (copyMaze[row][col] == GREEN) {
				copyMaze[row][col] = YELLOW;
			}

			// update yellow to impassable
			else if (copyMaze[row][col] == YELLOW) {
				copyMaze[row][col] = IMPASSIBLE;
			}

			// update coin to yellow
			else if (copyMaze[row][col] == COIN) {
				copyMaze[row][col] = YELLOW;
			}
			// update start to green
			else if (copyMaze[row][col] == START) {
				copyMaze[row][col] = GREEN;
			}
		}
	}
}
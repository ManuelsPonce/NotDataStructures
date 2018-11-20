/*  Student information for assignment:
 *
 *  On my honor, Manuel Ponce, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name:Manuel Ponce
 *  email address: maniponce22@gmail.com
 *  UTEID: mip445
 *  Section 5 digit ID: 51399
 *  Grader name:Lucas
 *  Number of slip days used on this assignment: One
 */

// add imports as necessary

import java.util.Set;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Manages the details of EvilHangman. This class keeps
 * tracks of the possible words from a dictionary during
 * rounds of hangman, based on guesses so far.
 *
 */
public class HangmanManager {

	//Constants variables
	private static final char DASH = '-';

	// instance vars
	private ArrayList<String> hangWords; //full dictionary
	private ArrayList<String> activeWords; // active words being updated in the dictionary
	private ArrayList<Character> lettersUsed; //letters already used by the user
	private int wordLength; //length provided by user
	private int numGuessesLeft;
	private String currentPatt; //Current Pattern 
	private HashMap<Integer, Integer> numWordsLength; //Map with length of words as key and number of words that fit that pattern as the value
	private HangmanDifficulty diff; //Difficulty user picks
	private int diffCount;

	/**
	 * Create a new HangmanManager from the provided set of words and phrases.
	 * pre: words != null, words.size() > 0
	 * @param words A set with the words for this instance of Hangman.
	 * @param debugOn true if we should print out debugging to System.out.
	 */
	public HangmanManager(Set<String> words, boolean debugOn) {
		//Check pre-condition
		if ((words == null) || words.size() <= 0) 
			throw new IllegalArgumentException("Violation of precondition: Words cannot be null or words cannot be empty");

		//creates a new ArrayList of Strings to store the words being passed in. 
		hangWords = new ArrayList<String>();
		for (String tempWord : words) { 
			hangWords.add(tempWord);
		}

		populateHashMap(); //creating map for keys of length of words and values of the number of words of that length
	}

	/**
	 * Create a new HangmanManager from the provided set of words and phrases. 
	 * Debugging is off.
	 * pre: words != null, words.size() > 0
	 * @param words A set with the words for this instance of Hangman.
	 */
	public HangmanManager(Set<String> words) {
		//creates a new HangmanManager using the previous constructer with debug set to off.
		this(words, false);
	}

	/**
	 * Get the number of words in this HangmanManager of the given length.
	 * pre: none
	 * @param length The given length to check.
	 * @return the number of words in the original Dictionary with the given length
	 */
	public int numWords(int length) {
		//make sure the length is greater than 0 and also check that value being returned from the map is not null (length might be too long and there are no words with that length), then return 0
		if (numWordsLength.get(length) == null || length < 1)
			return 0;
		//otherwise return the number of words with that length
		return numWordsLength.get(length);
	}


	/**
	 * The number of words still possible (live) based on the guesses so far. Guesses will eliminate possible words.
	 * @return the number of words that are still possibilities based on the original dictionary and the guesses so far.
	 */
	public int numWordsCurrent() {
		return activeWords.size(); 
	}

	/**
	 * Get for a new round of Hangman. Think of a round as a complete game of Hangman.
	 * @param wordLen the length of the word to pick this time. numWords(wordLen) > 0
	 * @param numGuesses the number of wrong guesses before the player loses the round. numGuesses >= 1
	 * @param diff The difficulty for this round.
	 */
	public void prepForRound(int wordLen, int numGuesses, HangmanDifficulty diff) {

		//resetting the game before every start. (resets most instance variables)
		this.diff = diff;
		wordLength = wordLen;
		numGuessesLeft = numGuesses;
		lettersUsed = new ArrayList<Character>(); 
		getActiveWords();
		resetPattern();
		diffCount = 0;
	}


	/**
	 * Get the number of wrong guesses the user has left in this round (game) of Hangman.
	 * @return the number of wrong guesses the user has left in this round (game) of Hangman.
	 */
	public int getGuessesLeft() {
		return numGuessesLeft;
	}


	/**
	 * Return a String that contains the letters the user has guessed so far during this round.
	 * The String is in alphabetical order. The String is in the form [let1, let2, let3, ... letN].
	 * For example [a, c, e, s, t, z]
	 * @return a String that contains the letters the user has guessed so far during this round.
	 */
	public String getGuessesMade() {
		//creating a StringBuilder that will be used to create string of characters used
		StringBuilder guessString = new StringBuilder();
		guessString.append("["); //for beginning bracket
		//sorts and add user guessed letters in here.
		Collections.sort(lettersUsed);
		for (int i = 0; i < lettersUsed.size(); i++) {
			if(i == lettersUsed.size() - 1) {
				guessString.append(lettersUsed.get(i));
			}
			else {
				guessString.append(lettersUsed.get(i) + ", ");
			}
		}
		guessString.append("]"); //for ending bracket
		return guessString.toString();
	}


	/**
	 * Check the status of a character.
	 * @param guess The characater to check.
	 * @return true if guess has been used or guessed this round of Hangman, false otherwise.
	 */
	public boolean alreadyGuessed(char guess) {
		return lettersUsed.contains(guess);
	}


	// pre: !alreadyGuessed(ch)
	// post: return a tree map with the resulting patterns and the number of
	// words in each of the new patterns.
	// the return value is for testing and debugging purposes
	/**
	 * Update the game status (pattern, wrong guesses, word list), based on the give
	 * guess.
	 * @param guess pre: !alreadyGuessed(ch), the current guessed character
	 * @return return a tree map with the resulting patterns and the number of
	 * words in each of the new patterns.
	 * The return value is for testing and debugging purposes.
	 */
	public TreeMap<String, Integer> makeGuess(char guess) {
		//Check pre-condition
		if (alreadyGuessed(guess)) 
			throw new IllegalStateException("Violation of precondition: Already guessed that letter.");

		//local variables
		HashMap<String, ArrayList<String>> patternMap = new HashMap<>(); //map used for population of nested class objects
		TreeMap<String, Integer> valueMap = new TreeMap<>(); //map used for returning
		ArrayList<CountPattern> countPatternObj = new ArrayList<>(); //arrayList of innerClass objects used to find difficult list of words

		lettersUsed.add(guess); //add guessed char to char list of guessed chars				
		populatePatternMap(patternMap, guess); //going through the list of active words and setting the patterns as keys and the words as the values    	
		populateObjList(patternMap, countPatternObj); //going through the newly created map and creating countPattern objects for each key and value used to be able to sort
		Collections.sort(countPatternObj); //sorting the array list of objects to get hardest family of words at element zero
		
		//PICKING CURRENT PATTERN BASED ON DIFFICULTY
		//difficulty picker for hard: ALWAYS HARD
		if (diff == HangmanDifficulty.HARD) 
			pickPatternHard(countPatternObj, patternMap);
		//difficulty for medium: PICKS HARD 3 TIMES THEN 2ND HARDEST AND REPEATS PATTERN
		else if (diff == HangmanDifficulty.MEDIUM)
			if (diffCount < 3) {
				pickPatternHard(countPatternObj, patternMap);
				diffCount++;
			}
			else {
				if(patternMap.keySet().size() == 1)
					pickPatternHard(countPatternObj, patternMap);
				else
				pickPatternMedium(countPatternObj, patternMap);
				diffCount = 0;
			}
		//difficulty for easy: ALTERNATE EVERYTIME BETWEEN 1ST AND 2ND HARDEST LIST
		else 
			if (diffCount == 0) {
				pickPatternHard(countPatternObj, patternMap);
				diffCount++;
			}
			else {
				if(patternMap.keySet().size() == 1)
					pickPatternHard(countPatternObj, patternMap);
				else
				pickPatternEasy(countPatternObj, patternMap);
				diffCount = 0;
			} 		

		//populating the value map that will be returned for testing
		populateTestingMap(patternMap, valueMap); 
		return valueMap; 

	}


	/**
	 * Get the current pattern. The pattern contains '-''s for unrevealed (or guessed)
	 * characters and the actual character for "correctly guessed" characters.
	 * @return the current pattern.
	 */
	public String getPattern() {
		return currentPatt;

	}

	/**
	 * Return the secret word this HangmanManager finally ended up picking for this round.
	 * If there are multiple possible words left one is selected at random.
	 * <br> pre: numWordsCurrent() > 0
	 * @return return the secret word the manager picked.
	 */
	public String getSecretWord() {
		//Check pre-condition
		if (numWordsCurrent() < 0) 
			throw new IllegalArgumentException("Violation of precondition: Words cannot be zero");

		String finalWord;
		//if there is only one word left
		if (numWordsCurrent() == 1) {
			finalWord = activeWords.get(0);
		}
		//else pick a random word from the remaining words
		else {
			Random rand = new Random(); 
			int randValue = rand.nextInt(activeWords.size());
			finalWord = activeWords.get(randValue);			
		}
		return finalWord;
	}


	//***********************************HELPER METHODS***********************************//

	//Gets the list of active words by the length of words the user wants and the words in the hangMan dictionary
	private void getActiveWords() {
		activeWords = new ArrayList<>();
		//going through the original dictionary and only adding words that match the length user wants
		for (int i = 0; i < hangWords.size(); i++) {
			if (hangWords.get(i).length() == wordLength) {
				activeWords.add(hangWords.get(i));
			}
		}
	}

	//resets the current pattern to dashes "-" depending on the wordlength the user wants
	private void resetPattern() {
		StringBuilder pat = new StringBuilder(); 
		for (int i = 0; i < wordLength; i++) {
			pat.append(DASH);
		}
		currentPatt = pat.toString();
	}

	//Creating the beginning patter based on the current word and guess user made then returns that pattern.
	private String createPattern(char c, String Word) {
		StringBuilder tempPat = new StringBuilder();
		//while you are checking every letter in the string if the letter matches with the guess the user made append that letter, else add a dash.
		for (int charCount = 0; charCount < wordLength; charCount++) {
			if (Word.charAt(charCount) == c) {
				tempPat.append(c);
			}
			else {
				tempPat.append(currentPatt.charAt(charCount));
			} 						
		}
		return tempPat.toString();
	}

	//populates a hash map with the keys being the length of a word, and the values is the number of words that have that length.
	private void populateHashMap() {
		numWordsLength = new HashMap<>();
		//going through all the words in the original dictionary
		for (String tempString : hangWords) {
			//if the key is in the map: update value
			if (numWordsLength.containsKey(tempString.length()))
				//Updates the value associated to the key by 1, indicating another word with the same length was found.
				numWordsLength.put(tempString.length(), (numWordsLength.get(tempString.length()) + 1));
			else {
				//Creates a new key of the length of the word and gives it an initial value of 1
				numWordsLength.put(tempString.length(), 1);
			}
		}
	}

	//Does the picking of pattern for the hard difficulty
	private void pickPatternHard(ArrayList<CountPattern> listObj, HashMap<String, ArrayList<String>> values) {
		//if current pattern equals hardest pattern, update the active list and decrement the number of guesses.
		if (currentPatt.equals(listObj.get(0).getObjPattern())) {
			numGuessesLeft--;
			activeWords = values.get(currentPatt);
		}
		//update current list and update current pattern
		else {
			currentPatt = listObj.get(0).getObjPattern();
			activeWords = values.get(currentPatt);
		}
	}

	//Does the picking of pattern for the medium difficulty
	//difficulty for medium: PICKS HARD 3 TIMES THEN 2ND HARDEST AND REPEATS PATTERN
	private void pickPatternMedium(ArrayList<CountPattern> listObj, HashMap<String, ArrayList<String>> values) {
		//if pattern did not change, numGuessesLeft goes down by one and list is updated
		if (lettersUsed.size() % 4 != 0) {
			numGuessesLeft--;
			activeWords = values.get(currentPatt);
		}
		else {
			currentPatt = listObj.get(1).getObjPattern();
			activeWords = values.get(currentPatt);		
		}
	}
	
	//Does the picking of pattern for the medium difficulty
	//difficulty for medium: PICKS HARD 3 TIMES THEN 2ND HARDEST AND REPEATS PATTERN
	private void pickPatternEasy(ArrayList<CountPattern> listObj, HashMap<String, ArrayList<String>> values) {
		//if pattern did not change, numGuessesLeft goes down by one and list is updated
		if (lettersUsed.size() % 2 != 0) {
			numGuessesLeft--;
			activeWords = values.get(currentPatt);
		}
		else {
			currentPatt = listObj.get(1).getObjPattern();
			activeWords = values.get(currentPatt);		
		}
	}

	//populate the pattern map based on pattern as key and list of words for that pattern as values. The map being passed is empty always, char passed is user guess
	private void populatePatternMap(HashMap<String, ArrayList<String>> patternMap, char guess ) {
		String tempPattern;

		//while still being able to iterate through the keys create the pattern and store that pattern in the map with the word as a value.
		for (String tempWord : activeWords) {
			tempPattern = createPattern(guess, tempWord); //pattern of current word based on the guess
			//If pattern as key exists in map already, just add word to the list of words for that pattern in the value
			if (patternMap.containsKey(tempPattern)) {
				patternMap.get(tempPattern).add(tempWord);
			}
			//else add key and pattern to map
			else {
				ArrayList<String> temp = new ArrayList<>();
				temp.add(tempWord);
				patternMap.put(tempPattern, temp); 
			}    	
		}   	
	}

	//populates the return map in makeGuess
	//populates a dummy map with patterns as keys and number of words that fit the pattern as the value. Used for testing purpose.
	private void populateTestingMap(HashMap<String, ArrayList<String>> patternMap, TreeMap<String, Integer> valueMap ) {   	
		for (String tempString : patternMap.keySet()) {
			if (!valueMap.containsKey(tempString))
				valueMap.put(tempString, patternMap.get(tempString).size());
		}   	
	}

	//going through the newly created map and creating countPattern objects for each key in order to be able to sort and
	//find the hardest list set
	private void populateObjList(HashMap<String, ArrayList<String>> patternMap, ArrayList<CountPattern> countPatternObjList) {
		for (String tempWord : patternMap.keySet()) {
			countPatternObjList.add(new CountPattern(tempWord, patternMap.get(tempWord).size()));	    		
		}
	}

	//***********************************NESTED CLASS***********************************//
	private static class CountPattern implements Comparable<CountPattern> {

		//Instance variables of nested class
		private String pattern;
		private int numWords;

		//constructor
		public CountPattern(String pat, int wordCount) {
			pattern = pat;
			numWords = wordCount;
		}

		public String getObjPattern() {
			return pattern;
		}
		public int getObjNumber() {
			return numWords;
		}

		//Used to sort the arrayList of objects that contain the pattern and number of words for that pattern
		//How to determine difficulty
		// 1) Family with the maximum elements
		// 2) If there are two or more families with the maximum number of elements break the tie by picking the one that reveals the fewest characters
		// 3) If there are two or more families with the maximum number of elements and reveal the same number of characters  break the tie by picking the pattern that is "smallest" based based on the lexigraphical ordering of Strings. 
		public int compareTo(CountPattern other) {
			//compare based on number of words
			int lengthWords = other.numWords - this.numWords;
			//if its a tie based on length of words go into tie breaker
			if (lengthWords == 0) {
				//tie breaker for same number of words of patterns
				int charCountThis = 0, charCountOther = 0;
				//counting the chars in the pattern for this countPattern and other countPattern
				for (int charCount = 0; charCount < this.pattern.length(); charCount++) {
					//if the current char doesnt equal a dash, increment the char count for either this or other.
					if (this.pattern.charAt(charCount) != DASH) {
						charCountThis++;
					}
					if (other.pattern.charAt(charCount) != DASH) {
						charCountOther++;
					}
				}
				int comparedCharCount = charCountThis - charCountOther;				
				if (comparedCharCount != 0) {
					//returns negative if other is greater than this based on how many times a char appears in the pattern
					return comparedCharCount;
				}
				//TIE BREAKER for same number of revealing chars
				//check for which pattern shows the letter the earliest
				else {
					//Compares patterns and gets actual lexigraphical value
					int comparedPatterns = this.pattern.compareTo(other.pattern);
					return comparedPatterns;
				}
			}
			//returns negative if other is greater than this based on number of words the pattern contains
				return lengthWords;
		} 
	}  //end of nested class
} //end of outer class
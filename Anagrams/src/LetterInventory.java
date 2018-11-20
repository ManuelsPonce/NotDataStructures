/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, Manuel Ponce, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: mip445
 *  email address: maniponce22@gmail.com
 *  Grader name: Lucas
 *  Number of slip days I am using: 0
 */

//Imports


/*
 * LetterInventory.java A class that represents a collection of letters from the English alphabet.
 */
public class LetterInventory {
	
	//Class constant
	private final static int ALPHA = 26;
	private final static char[] LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	//Instance Variables
	private int[] letterCon; //Array of ints to store the number of times each English letter occurs.
	private int size; //size of LetterCon
	
	//Constructor 
	public LetterInventory(String word) {
    	//Pre-Con: Word cannot be null
    	if (word == null)
            throw new IllegalArgumentException("Pre-Con: String cannot be null");
    	
		letterCon = new int[ALPHA]; //always 26 Letters in the English language.
		countLetters(word); //count the freq of letters in the word
		
	}
	
	//a method named isEmpty that returns true if the size of this LetterInventory is 0, false otherwise. This method shall be O(1) with respect to the size of the alphabet.
	public boolean isEmpty() {				
		return this.size() == 0; //if size = 0 return true, otherwise...
	}
	
	//a method named size that returns the total number of letters in this LetterInventory. This method shall be O(1).
	public int size() {
		return size;
	}
	
	//a method named get that accepts a char and returns the frequency of that letter in this LetterInventory. 
	//The precondition requires that the char be an English letter. 
	//It may be an upper or lower case letter. The answer returned must be the same given the upper or lower case version of a letter. This method shall be O(1).
	public int get(char lett) {
		char tempChar = Character.toLowerCase(lett); //"The answer returned must be the same given the upper or lower case version of a letter."
    	//Pre-Con: make sure its a letter
    	if (!Character.isLetter(lett))
            throw new IllegalArgumentException("Pre-Con: letter Must be in the english dictionary");
    							
		int index = tempChar - 'a'; //ASCII values, where 'a' = 62? finds the index for the array to return freq.
		return letterCon[index];
		
	}
	
	//counts the freq of the letters that appear in the word
	private void countLetters(String word) {
		word = word.toLowerCase();
		for (int i = 0; i < word.length(); i++) {
			char currentChar = word.charAt(i); //char being compared
			if('a' <= currentChar && currentChar <= 'z') { //check if the char is in the alphabet
				int index = currentChar - 'a'; //get the index 
				letterCon[index]++;
				size++;	//updating the size.
			}
		}
	}
	
	//a method named toString that returns a String representation of this LetterInventory. All letters in the inventory are listed in alphabetical order. 
	//For example if a LetterInventory is created from the String "tall" toString would return the String "allt".
	//If a LetterInventory is created from the String "Isabelle M. Scott!!" the method would return the String "abceeillmosstt".
	@Override
	public String toString() {
		String result = new String();
		for (int i = 0; i < ALPHA; i++) { //go through the container of letter frequencies 
			if (letterCon[i] > 0) {  //only add leter if it appears (freq is greater than 0).
				for (int numLet = 0; numLet < letterCon[i]; numLet++) { //add that letter the for the frequency it appears
					result += LETTERS[i];
				}
			}
		}
		return result;
	}
	
	//a method named add with one parameter, another LetterInventory object. The method returns a new LetterInventory created by adding the frequencies from the calling LetterInventory object to the frequencies of the letters in the explicit parameter. 
	//The precondition requires that the LetterInventory sent as an explicit parameter not be null. 
	//The postcondition requires that neither the calling object or the explicit parameter are altered as a result of this method call. 
	//This method shall be O(M) where M is the number of letters in the alphabet our LetterInventory is using.
	public LetterInventory add(LetterInventory other) {
		//check pre-cons
    	//Pre-Con: other cannot be null
    	if (other == null)
            throw new IllegalArgumentException("Pre-Con: adding LetterInventory cannot be null");
    	
		LetterInventory newContainer = new LetterInventory(""); //new container to be returned with the addition of both letterInventories
		for (int i = 0; i < ALPHA; i++) { //go through the alphabet
			if (letterCon[i] > 0 || other.letterCon[i] > 0) //if either this or other has letters in their container, add it to the new container
				newContainer.letterCon[i] = letterCon[i] + other.letterCon[i]; 
		}
		newContainer.size = this.size + other.size; //update size for new container
		return newContainer;
	}
	
	//a method named subtract with one parameter, another LetterInventory object. 
	//The methods returns a new LetterInventory object created by subtracting the letter frequencies of the explicit parameter from the calling object's (this) letter frequencies.
	//If any of the resulting letter counts are less than 0 the method shall return null. the precondition requires the LetterInventory sent as an explicit parameter not be null. The post-condition requires that neither the calling object or the explicit parameter are altered as a result of this method call. 
	//This method shall be O(M) where M is the number of letters in the alphabet our LetterInventory is using
	public LetterInventory subtract(LetterInventory other) {
		//check pre-cons
    	//Pre-Con: other cannot be null
    	if (other == null)
            throw new IllegalArgumentException("Pre-Con: subtracting  LetterInventory cannot be null");
    	
		LetterInventory newContainer = new LetterInventory(""); //new container to be returned with the subtraction of both letterInventories
		for (int i = 0; i < ALPHA; i++) { //go through the alphabet
			if (letterCon[i] > 0 || other.letterCon[i] > 0) { //if either this or other has letters in their container, add it to the new container
				int tempFreq = letterCon[i] - other.letterCon[i]; //temp freq from the subtraction. 
				if (tempFreq < 0) //if the subtraction results in a negative, return null
					return null;  
				newContainer.letterCon[i] = tempFreq; //else just add the subtraction
			}
		}
		newContainer.size = this.size - other.size; //updating the new Con size;
		return newContainer;
	}
	
	//override the equals method from Object. Two LetterInventorys are equal if the frequency for each letter in the alphabet is the same.
	@Override
	public boolean equals(Object other) {
		if (other instanceof LetterInventory) {		
			LetterInventory otherCon = (LetterInventory)other; //casting safely...
			//go through both arrays and check if they have the same frequencies for each letter. Return false if one of them is different.
			for (int i = 0; i < ALPHA; i++) {
				if(letterCon[i] != otherCon.letterCon[i])
					return false;
			}
			return true; //they match by content and instenceof
		}
		return false; //they are not instances of each other
	}

}


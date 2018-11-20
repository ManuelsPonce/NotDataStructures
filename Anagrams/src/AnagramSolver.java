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
import java.util.Map;
import java.util.List; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap; 

/*
 * AnagramSolver.java. A class that solves and returns a list of anagrams for a given phrase and maximum number of words.
 */
public class AnagramSolver {
	
	//Instance variables
	private Map<String, LetterInventory> letterInventories; //holds orignal dictionary with letter inventories
	
	//constructor 
	public AnagramSolver(List<String> words) {
		Collections.sort(words); //sorting original dictionary
		populateMap(words); //creates the hash map with the words as keys and their letterInventory as the values.
	}
	
	//a public method named getAnagrams that has two parameters: a String, and an int. The String is the target word or phrase and your method will form anagrams out of that word or phrase. 
	//The String sent as a parameter does not have to be in the AnagramSolver's dictionary. 
	//It may contain characters which must be ignored. (Recall this is handled by the LetterInventory class already.) 
	//The int parameter indicates the maximum number of words allowed in the anagrams of the given String. 
	//If the int is 0 there is no maximum number of words for the anagrams being formed. 
	//The precondition requires the String not be null and the String contain at least one English letter. The int parameter must be greater than or equal to 0.
	public List<List<String>> getAnagrams(String word, int numWords) {
		
		 //if target word is in the dictionary map just use the letterInventory in the map else create a new LI 
		LetterInventory trgtWord = letterInventories.containsKey(word) ? letterInventories.get(word) : new LetterInventory(word); 
		
		//Pre-Con check
    	if (numWords < 0 || trgtWord.size() == 0 || word == null)
            throw new IllegalArgumentException("Pre-Con: word mst have 1 english letter in it or cannot be null");
    	
    	ArrayList<List<String>> finalAnagrams = new ArrayList<List<String>>(); //List of list of anagrams 	
    	ArrayList<String> posListWords = new ArrayList<String>(); //reduced dictionary of words
    	findReducedWords(trgtWord, posListWords, numWords); //reducing dictionary
    	ArrayList<String> modiList =  new ArrayList<String>(); //the list that will be constantly modified
    	
    	if(numWords == 0)
    		numWords = trgtWord.size(); //if they have no limit, the limit is inffered that the max is the length of the word  		
     	
    	int index = 0; //used to virtually shrink the dictionary
    	
    	anagramHelper(finalAnagrams, trgtWord, numWords, modiList, index, posListWords); //kicker for the recursive call.
    	
    	sortTheLists(finalAnagrams); //sort the inner anagramLists
    	Collections.sort(finalAnagrams, new AnagramCompare());//sort final anagrams
    	
    	return finalAnagrams;
	}
	
	//getAnagramHelper
	private void anagramHelper(ArrayList<List<String>> finList, LetterInventory trgtWord, int maxWords, ArrayList<String> modifiedList, int index, ArrayList<String> posListWords) {
		
		if(trgtWord.isEmpty() && modifiedList.size() <= maxWords) //base case that you cannot subtract anagrams from the word passed in. and you havent passed the maxAnagrams value.
			finList.add(new ArrayList<String>(modifiedList)); 
		else if(modifiedList.size() >= maxWords) //base case that you reached your max number of anagrams but still have letters. thus not a valid anagram
			return;
		else {
			//go through the dictionary
			for (int i = index; i < posListWords.size(); i++) {
				LetterInventory nullCheck = trgtWord.subtract(letterInventories.get(posListWords.get(i))); //subtraction of letter invents
				if(nullCheck != null) {
					LetterInventory tempInven = trgtWord; //used to store the current letterInventory before change
					modifiedList.add(posListWords.get(i)); //add the current word
					trgtWord = nullCheck; //making your new option for the anagrams
					index = i; //updating index to start from current position.
					anagramHelper(finList, trgtWord, maxWords, modifiedList, index, posListWords); //recursive call with new options
					trgtWord = tempInven; //adding letter frequencies back to trgt word LetterInventory
					modifiedList.remove(modifiedList.size() - 1); //removing the last word added into the list of anagrams
				}
			}
		}
	}
	
	//creates the map that stores the words in the dic and their letterInventories
	private void populateMap(List<String> words) {
		letterInventories = new HashMap<String, LetterInventory>();
		for (String tempWord : words) {
				letterInventories.put(tempWord, new LetterInventory(tempWord));
		}
	}
	
	//helper to sort the inner ArrayLists
	private void sortTheLists(ArrayList<List<String>> list) {
		for (int i = 0; i < list.size(); i++) {
			Collections.sort(list.get(i));
		}
	}
	
	
	// helper to find the words that could be an anagram
	private void findReducedWords(LetterInventory phrase, ArrayList<String> possiWords, int maxAn) {
		//when max anagrams is not 1
		if (maxAn != 1) {
			for (String wordInMap : letterInventories.keySet()) {
				if (phrase.subtract(letterInventories.get(wordInMap)) != null) {
						possiWords.add(wordInMap);
				}
			}
		}
		//special case that max anagrams is 1 only add words from the dictionary that are the same length as the phrase passed in
		else {
			for (String wordInMap : letterInventories.keySet()) {
				if(wordInMap.length() == phrase.size()) {
					if (phrase.subtract(letterInventories.get(wordInMap)) != null) {
							possiWords.add(wordInMap);
					}
				}
			}
		}
	}
	 
	
	/************************************/
	/****NESTED CLASS FOR COMPARATOR*****/
	/************************************/
	private class AnagramCompare implements Comparator<List<String>> {

		@Override
		public int compare(List<String> arg0, List<String> arg1) {
			int val = arg0.size() - arg1.size(); //first compare size of lists
			if(val == 0) { //if lists sizes are the same, compare strings 
				for (int i = 0; i < arg0.size(); i++) {
					val = arg0.get(i).compareTo(arg1.get(i));
					if (val != 0)
						return val; //if the strings arent the same return the value
				}
				return val; //none of the strings are different
			}
			else 
				return val; //the sizes are different
		}				
	}


}

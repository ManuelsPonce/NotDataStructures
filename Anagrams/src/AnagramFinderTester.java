import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, <NAME>, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID:
 *  email address:
 *  Grader name:
 *  Number of slip days I am using:
 */

public class AnagramFinderTester {

    
    private static final String testCaseFileName = "testCaseAnagrams.txt";
    private static final String dictionaryFileName = "d3.txt";

    /**
     * main method that executes tests.
     * @param args Not used.
     */
    public static void main(String[] args) {

        letterInventoryTests();
  
        // tests on the anagram solver itself
        boolean displayAnagrams = getChoiceToDisplayAnagrams();
        AnagramSolver solver = new AnagramSolver(AnagramMain.readWords(dictionaryFileName));
        runAnagramTests(solver, displayAnagrams);
    }
    
    private static void letterInventoryTests() {
        LetterInventory lets1 = new LetterInventory("");
        Object expected = 0;
        Object actual = lets1.size();
        showTestResults(expected, actual, 1, " size on empty LetterInventory");

        expected = "";
        actual = lets1.toString();
        showTestResults(expected, actual, 2, " toString on empty LetterInventory");

        expected = 0;
        actual = lets1.get('A');
        showTestResults(expected, actual, 3, " get on empty LetterInventory");

        expected = 0;
        actual = lets1.get('z');
        showTestResults(expected, actual, 4, " get on empty LetterInventory");

        expected = true;
        actual = lets1.isEmpty();
        showTestResults(expected, actual, 5, " isEmpty on empty LetterInventory");

        expected = "";
        actual = lets1.toString();
        showTestResults(expected, actual, 6, " LetterInventory toString on empty LetterInventory");
        
        
        lets1 = new LetterInventory("mmmmm");
        expected = "mmmmm";
        actual = lets1.toString();
        showTestResults(expected, actual, 7, " LetterInventory toString");


        LetterInventory lets2 = new LetterInventory("\"Stanford University\"!! Jr<>(())G");
        expected = "adefgiijnnorrrssttuvy";
        actual = lets2.toString();
        showTestResults(expected, actual, 8, " LetterInventory constructor and toString");

        expected = 21;
        actual = lets2.size();
        showTestResults(expected, actual, 9, " LetterInventory size");

        expected = false;
        actual = lets2.isEmpty();
        showTestResults(expected, actual, 10, " LetterInventory isEmpty");

        expected = null;
        actual = lets2.subtract(lets1);
        showTestResults(expected, actual, 11, "LetterInventory subtract");

        lets1 = new LetterInventory("stand ---- ton");
        expected = "efgiijrrrsuvy";
        actual = lets2.subtract(lets1).toString();
        showTestResults(expected, actual, 12, "LetterInventory subtract");

        expected = 13;
        actual = lets2.subtract(lets1).size();
        showTestResults(expected, actual, 13, "LetterInventory size after subtract");

        expected = false;
        actual = lets2.isEmpty();
        showTestResults(expected, actual, 14, "LetterInventory isEmpty after subtract");

        expected = null;
        actual = lets1.subtract(lets2);
        showTestResults(expected, actual, 15, "LetterInventory subtract");

        expected = false;
        actual = lets1.equals(lets2);
        showTestResults(expected, actual, 16, "LetterInventory equals");

        lets1 = new LetterInventory("Ol33vIA33");
        expected = "aadefgiiijlnnoorrrssttuvvy";
        LetterInventory lets3 = lets1.add(lets2);
        actual = lets3.toString();
        showTestResults(expected, actual, 17, "LetterInventory add");

        expected = 26;
        actual = lets3.size();
        showTestResults(expected, actual, 18, "LetterInventory size after add");

        expected = false;
        actual = lets3.isEmpty();
        showTestResults(expected, actual, 19, "LetterInventory isEmpty after add");

        lets3 = lets1.add(lets2);
        LetterInventory lets4 = lets2.add(lets1);
        showTestResults(lets3, lets4, 20, "LetterInventory add and equals");

        expected = false;
        StringBuilder foo = new StringBuilder();
        actual = lets3.equals(foo);
        showTestResults(expected, actual, 21, "LetterInventory equals");

        expected = false;
        String str = "abeeills";
        lets3 = new LetterInventory("ISAbelle!!");
        actual = lets3.equals(str);
        showTestResults(expected, actual, 22, "LetterInventory equals");

        expected = true;
        lets2 = new LetterInventory("\\abeei\"ll0212s");
        lets3 = new LetterInventory("ISAbelle!!");
        actual = lets3.equals(lets2);
        showTestResults(expected, actual, 23, "LetterInventory equals");
    }
    
    private static void cs314StudentTestsForLetterInventory() {
        // CS314 Students, delete the above tests when you turn in your assignment
        // CS314 Students add your LetterInventory tests here. 
    }

    private static boolean getChoiceToDisplayAnagrams() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter y or Y to display anagrams during tests: ");
        String response = console.nextLine();
        console.close();
        return response.length() > 0 && response.toLowerCase().charAt(0) == 'y';
    }

    private static boolean showTestResults(Object expected, Object actual, int testNum, String featureTested) {
        System.out.println("Test Number " + testNum + " testing " + featureTested);
        System.out.println("Expected result: " + expected);
        System.out.println("Actual result: " + actual);
        boolean passed = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        if(passed)
            System.out.println("Passed test " + testNum);
        else
            System.out.println("!!! FAILED TEST !!! " + testNum);
        System.out.println();
        return passed;
    }

    /**
     * Method to run tests on Anagram solver itself.
     * pre: the files d3.txt and testCaseAnagrams.txt are in the local directory
     * 
     * assumed format for file is
     * <NUM_TESTS>
     * <TEST_NUM>
     * <MAX_WORDS>
     * <PHRASE>
     * <NUMBER OF ANAGRAMS>
     * <ANAGRAMS>
     */
    private static void runAnagramTests(AnagramSolver solver, boolean displayAnagrams) {
        int solverTestCases = 0;
        int solverTestCasesPassed = 0;
        Stopwatch st = new Stopwatch();
        try {
            Scanner sc = new Scanner(new File(testCaseFileName));
            final int NUM_TEST_CASES = Integer.parseInt(sc.nextLine().trim());
            System.out.println(NUM_TEST_CASES);
            for(int i = 0; i < NUM_TEST_CASES; i++) {
                // expected results
                TestCase currentTest = new TestCase(sc);
                solverTestCases++;
                st.start();
                // actual results
                List<List<String>> actualAnagrams = solver.getAnagrams(currentTest.phrase, currentTest.maxWords);
                st.stop();
                if(displayAnagrams) {
                    displayAnagrams("actual anagrams", actualAnagrams);
                    displayAnagrams("expected anagrams", currentTest.anagrams);
                }


                if(checkPassOrFailTest(currentTest, actualAnagrams))
                    solverTestCasesPassed++;
                System.out.println("Time to find anagrams: " + st.time());
            }
            sc.close();
        }
        catch(IOException e) {
            System.out.println("\nProblem while running test cases on AnagramSolver. Check" +
                    " that file testCaseAnagrams.txt is in the correct location.");
            System.out.println(e);
            System.out.println("AnagramSolver test cases run: " + solverTestCases);
            System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
        }
        System.out.println("\nAnagramSolver test cases run: " + solverTestCases);
        System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
    }


    // print out all of the anagrams in a list of anagram
    private static void displayAnagrams(String type,
            List<List<String>> anagrams) {

        System.out.println("Results for " + type);
        System.out.println("num anagrams: " + anagrams.size());
        System.out.println("anagrams: ");
        for(List<String> singleAnagram : anagrams)
            System.out.println(singleAnagram);
    }


    // determine if the test passed or failed
    private static boolean checkPassOrFailTest(TestCase currentTest,
            List<List<String>> actualAnagrams) {
        
        boolean passed = true;
        System.out.println();
        System.out.println("Test number: " + currentTest.testCaseNumber);
        System.out.println("Phrase: " + currentTest.phrase);
        System.out.println("Word limit: " + currentTest.maxWords);
        System.out.println("Expected Number of Anagrams: " + currentTest.anagrams.size());
        if(actualAnagrams.equals(currentTest.anagrams)) {
            System.out.println("Passed Test");
        }
        else{
            System.out.println("\n!!! FAILED TEST CASE !!!");
            System.out.println("Recall MAXWORDS = 0 means no limit.");
            System.out.println("Expected number of anagrams: " + currentTest.anagrams.size());
            System.out.println("Actual number of anagrams:   " + actualAnagrams.size());
            if(currentTest.anagrams.size() == actualAnagrams.size()) {
                System.out.println("Sizes the same, but either a difference in anagrams or anagrams not in correct order.");
            }
            System.out.println();
            passed = false;
        }  
        return passed;
    }

    // class to handle the parameters for an anagram test 
    // and the expected result
    private static class TestCase {

        private int testCaseNumber;
        private String phrase;
        private int maxWords;
        private List<List<String>> anagrams;

        // pre: sc is positioned at the start of a test case
        private TestCase(Scanner sc) {
            testCaseNumber = Integer.parseInt(sc.nextLine().trim());
            maxWords = Integer.parseInt(sc.nextLine().trim());
            phrase = sc.nextLine().trim();
            anagrams = new ArrayList<List<String>>();
            readAndStoreAnagrams(sc);
        }

        // pre: sc is positioned at the start of the resulting anagrams
        // read in the number of anagrams and then for each anagram:
        //  - read in the line
        //  - break the line up into words
        //  - create a new list of Strings for the anagram
        //  - add each word to the anagram
        //  - add the anagram to the list of anagrams
        private void readAndStoreAnagrams(Scanner sc) {
            int numAnagrams = Integer.parseInt(sc.nextLine().trim());
            for(int j = 0; j < numAnagrams; j++){
                String[] words = sc.nextLine().split("\\s+");
                ArrayList<String> anagram = new ArrayList<String>();
                for(String st : words)
                    anagram.add(st);
                anagrams.add(anagram);
            }
            assert anagrams.size() == numAnagrams : "Wrong number of angrams read or expected";
        }
    }
}

/*import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
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
 

public class AnagramFinderTester {

    
    private static final String testCaseFileName = "testCaseAnagrams.txt";
    private static final String dictionaryFileName = "d3.txt";

    *//**
     * main method that executes tests.
     * @param args Not used.
     *//*
    public static void main(String[] args) {

    	
    	cs314StudentTestsForLetterInventory();
  
    }
    
    private static void cs314StudentTestsForLetterInventory() {
        // CS314 Students, delete the above tests when you turn in your assignment
        // CS314 Students add your LetterInventory tests here. 
    	
    	//SET OF TESTS 1
    	System.out.println("SET OF TESTS #1");
        LetterInventory letters1 = new LetterInventory("Manuel!!!!!!");
        
        //Test1 constructor test
        Object expected = 6;
        Object actual = letters1.size();
        if (actual == expected)
        	System.out.println("Passsed test 1: create a succesfull LetterInventory");
        else
        	System.out.println("FAILED test 1: create a succesfull LetterInventory");
        
        //Test2 size()
        if (actual == expected)
        	System.out.println("Passsed test 2: LetterInventory size test");
        else
        	System.out.println("FAILED test 2: LetterInventory size test");
        
        //Test3 isEmpty()
        if (letters1.isEmpty() == false)
        	System.out.println("Passsed test 3: LetterInventory isEmpty test");
        else
        	System.out.println("FAILED test 3: LetterInventory isEmpty test");
        
        //Test4 get()
        if (letters1.get('m') == 1)
        	System.out.println("Passsed test 4: LetterInventory get test");
        else
        	System.out.println("FAILED test 4: LetterInventory get test");
        
        //Test5 toString()
        if (letters1.toString().equals("aelmnu"))
        	System.out.println("Passsed test 5: LetterInventory toString test");
        else
        	System.out.println("FAILED test 5: LetterInventory toString test");
        
        //Test6 equals()
        if (letters1.equals(letters1))
        	System.out.println("Passsed test 6: LetterInventory equals test");
        else
        	System.out.println("FAILED test 6: LetterInventory equals test");
        
        //Test7 add()
        if (letters1.add(letters1).size() == 12)
        	System.out.println("Passsed test 7: LetterInventory add test");
        else
        	System.out.println("FAILED test 7: LetterInventory add test");
        
        //Test8 add()
        if (letters1.subtract(letters1).size() == 0)
        	System.out.println("Passsed test 8: LetterInventory subtract test");
        else
        	System.out.println("FAILED test 8: LetterInventory subtract test");
    	
      //SET OF TESTS 2
    	System.out.println("\nSET OF TESTS #2");
        LetterInventory letters2 = new LetterInventory("");
        
        //Test1 constructor test
        Object expected2 = 0;
        Object actual2 = letters2.size();
        if (actual2 == expected2)
        	System.out.println("Passsed test 1: create a succesfull LetterInventory");
        else
        	System.out.println("FAILED test 1: create a succesfull LetterInventory");
        
        //Test2 size()
        if (actual2 == expected2)
        	System.out.println("Passsed test 2: LetterInventory size test");
        else
        	System.out.println("FAILED test 2: LetterInventory size test");
        
        //Test3 isEmpty()
        if (letters2.isEmpty() == true)
        	System.out.println("Passsed test 3: LetterInventory isEmpty test");
        else
        	System.out.println("FAILED test 3: LetterInventory isEmpty test");
        
        //Test4 get()
        if (letters2.get('m') == 0)
        	System.out.println("Passsed test 4: LetterInventory get test");
        else
        	System.out.println("FAILED test 4: LetterInventory get test");
        
        //Test5 toString()
        if (letters2.toString().equals(""))
        	System.out.println("Passsed test 5: LetterInventory toString test");
        else
        	System.out.println("FAILED test 5: LetterInventory toString test");
        
        //Test6 equals()
        LetterInventory letters3 = new LetterInventory("abc");
        if (!letters2.equals(letters3))
        	System.out.println("Passsed test 6: LetterInventory equals test");
        else
        	System.out.println("FAILED test 6: LetterInventory equals test");
        
        //Test7 add()
        if (letters2.add(letters3).size() == 3)
        	System.out.println("Passsed test 7: LetterInventory add test");
        else
        	System.out.println("FAILED test 7: LetterInventory add test");
        
        //Test8 add()
        if (letters2.subtract(letters3) == null)
        	System.out.println("Passsed test 8: LetterInventory subtract test");
        else
        	System.out.println("FAILED test 8: LetterInventory subtract test");
    	
    }

    private static boolean getChoiceToDisplayAnagrams() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter y or Y to display anagrams during tests: ");
        String response = console.nextLine();
        console.close();
        return response.length() > 0 && response.toLowerCase().charAt(0) == 'y';
    }

    private static boolean showTestResults(Object expected, Object actual, int testNum, String featureTested) {
        System.out.println("Test Number " + testNum + " testing " + featureTested);
        System.out.println("Expected result: " + expected);
        System.out.println("Actual result: " + actual);
        boolean passed = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        if(passed)
            System.out.println("Passed test " + testNum);
        else
            System.out.println("!!! FAILED TEST !!! " + testNum);
        System.out.println();
        return passed;
    }

    *//**
     * Method to run tests on Anagram solver itself.
     * pre: the files d3.txt and testCaseAnagrams.txt are in the local directory
     * 
     * assumed format for file is
     * <NUM_TESTS>
     * <TEST_NUM>
     * <MAX_WORDS>
     * <PHRASE>
     * <NUMBER OF ANAGRAMS>
     * <ANAGRAMS>
     *//*
    private static void runAnagramTests(AnagramSolver solver, boolean displayAnagrams) {
        int solverTestCases = 0;
        int solverTestCasesPassed = 0;
        Stopwatch st = new Stopwatch();
        try {
            Scanner sc = new Scanner(new File(testCaseFileName));
            final int NUM_TEST_CASES = Integer.parseInt(sc.nextLine().trim());
            System.out.println(NUM_TEST_CASES);
            for(int i = 0; i < NUM_TEST_CASES; i++) {
                // expected results
                TestCase currentTest = new TestCase(sc);
                solverTestCases++;
                st.start();
                // actual results
                List<List<String>> actualAnagrams = solver.getAnagrams(currentTest.phrase, currentTest.maxWords);
                st.stop();
                if(displayAnagrams) {
                    displayAnagrams("actual anagrams", actualAnagrams);
                    displayAnagrams("expected anagrams", currentTest.anagrams);
                }


                if(checkPassOrFailTest(currentTest, actualAnagrams))
                    solverTestCasesPassed++;
                System.out.println("Time to find anagrams: " + st.time());
            }
            sc.close();
        }
        catch(IOException e) {
            System.out.println("\nProblem while running test cases on AnagramSolver. Check" +
                    " that file testCaseAnagrams.txt is in the correct location.");
            System.out.println(e);
            System.out.println("AnagramSolver test cases run: " + solverTestCases);
            System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
        }
        System.out.println("\nAnagramSolver test cases run: " + solverTestCases);
        System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
    }


    // print out all of the anagrams in a list of anagram
    private static void displayAnagrams(String type,
            List<List<String>> anagrams) {

        System.out.println("Results for " + type);
        System.out.println("num anagrams: " + anagrams.size());
        System.out.println("anagrams: ");
        for(List<String> singleAnagram : anagrams)
            System.out.println(singleAnagram);
    }


    // determine if the test passed or failed
    private static boolean checkPassOrFailTest(TestCase currentTest,
            List<List<String>> actualAnagrams) {
        
        boolean passed = true;
        System.out.println();
        System.out.println("Test number: " + currentTest.testCaseNumber);
        System.out.println("Phrase: " + currentTest.phrase);
        System.out.println("Word limit: " + currentTest.maxWords);
        System.out.println("Expected Number of Anagrams: " + currentTest.anagrams.size());
        if(actualAnagrams.equals(currentTest.anagrams)) {
            System.out.println("Passed Test");
        }
        else{
            System.out.println("\n!!! FAILED TEST CASE !!!");
            System.out.println("Recall MAXWORDS = 0 means no limit.");
            System.out.println("Expected number of anagrams: " + currentTest.anagrams.size());
            System.out.println("Actual number of anagrams:   " + actualAnagrams.size());
            if(currentTest.anagrams.size() == actualAnagrams.size()) {
                System.out.println("Sizes the same, but either a difference in anagrams or anagrams not in correct order.");
            }
            System.out.println();
            passed = false;
        }  
        return passed;
    }

    // class to handle the parameters for an anagram test 
    // and the expected result
    private static class TestCase {

        private int testCaseNumber;
        private String phrase;
        private int maxWords;
        private List<List<String>> anagrams;

        // pre: sc is positioned at the start of a test case
        private TestCase(Scanner sc) {
            testCaseNumber = Integer.parseInt(sc.nextLine().trim());
            maxWords = Integer.parseInt(sc.nextLine().trim());
            phrase = sc.nextLine().trim();
            anagrams = new ArrayList<List<String>>();
            readAndStoreAnagrams(sc);
        }

        // pre: sc is positioned at the start of the resulting anagrams
        // read in the number of anagrams and then for each anagram:
        //  - read in the line
        //  - break the line up into words
        //  - create a new list of Strings for the anagram
        //  - add each word to the anagram
        //  - add the anagram to the list of anagrams
        private void readAndStoreAnagrams(Scanner sc) {
            int numAnagrams = Integer.parseInt(sc.nextLine().trim());
            for(int j = 0; j < numAnagrams; j++){
                String[] words = sc.nextLine().split("\\s+");
                ArrayList<String> anagram = new ArrayList<String>();
                for(String st : words)
                    anagram.add(st);
                anagrams.add(anagram);
            }
            assert anagrams.size() == numAnagrams : "Wrong number of angrams read or expected";
        }
    }
}*/


/*  Student information for assignment:
 *
 *  On <MY|OUR> honor, <NAME1> and <NAME2), this programming assignment is <MY|OUR> own work
 *  and <I|WE> have not provided this code to any other student.
 *
 *  Number of slip days used:
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID:
 *  email address:
 *  Grader name:
 *  Section number:
 *
 *  Student 2
 *  UTEID:
 *  email address:
 *
 */


import java.util.ArrayList;
import java.util.Collections;

/**
 * Tester class for the methods in Recursive.java
 * @author scottm
 *
 */
public class RecursiveTester {

    // run the tests
    public static void main(String[] args) {
    	

        studentTests();
    }
    

    // pre: r != null
    // post: run student test
    private static void studentTests() {
        // CS314 students put your tests here
    	//Test Recursion #1
    	String binary = Recursive.getBinary(13);
    	System.out.println("Expected: 1101");
    	System.out.println("Actual: " + binary);
    	if (binary.equals("1101"))
    		System.out.println("Passed Binary test #1");
    	else 
    		System.out.println("Failed Binary test #1");
    	
    	//Test Recursion #2
    	binary = Recursive.getBinary(0);
    	System.out.println("Expected: 0");
    	System.out.println("Actual: " + binary);
    	if (binary.equals("0"))
    		System.out.println("Passed Binary test #2");
    	else 
    		System.out.println("Failed Binary test #2");
    	
    	//Test Recursion #3
    	binary = Recursive.getBinary(-1);
    	System.out.println("Expected: -1");
    	System.out.println("Actual: " + binary);
    	if (binary.equals("-1"))
    		System.out.println("Passed Binary test #3");
    	else 
    		System.out.println("Failed Binary test #3");
    	
    	//Test RevString #4
    	String rev = Recursive.revString("Manuel");
    	System.out.println("Expected: leunaM");
    	System.out.println("Actual: " + rev);
    	if (rev.equals("leunaM"))
    		System.out.println("Passed ReverseString test #4");
    	else 
    		System.out.println("Failed ReverseString test #4");
    	
    	//Test RevString #5
    	rev = Recursive.revString("Man Man");
    	System.out.println("Expected: naM naM");
    	System.out.println("Actual: " + rev);
    	if (rev.equals("naM naM"))
    		System.out.println("Passed ReverseString test #5");
    	else 
    		System.out.println("Failed ReverseString test #5");
    	
    	//Test RevString #6
    	rev = Recursive.revString("");
    	System.out.println("Expected: ");
    	System.out.println("Actual: " + rev);
    	if (rev.equals(""))
    		System.out.println("Passed ReverseString test #6");
    	else 
    		System.out.println("Failed ReverseString test #6");
    	
    	//Test nextIsDouble #7
    	int[] test7 = {1,2,4,8,16,32};
    	int dubCount = Recursive.nextIsDouble(test7);
    	System.out.println("Expected: 5");
    	System.out.println("Actual: " + dubCount);
    	if (dubCount == 5)
    		System.out.println("Passed nextIsDouble test #7");
    	else 
    		System.out.println("Failed nextIsDouble test #7");
    	
    	//Test nextIsDouble #8
    	int[] test8 = {1};
    	dubCount = Recursive.nextIsDouble(test8);
    	System.out.println("Expected: 0");
    	System.out.println("Actual: " + dubCount);
    	if (dubCount == 0)
    		System.out.println("Passed nextIsDouble test #8");
    	else 
    		System.out.println("Failed nextIsDouble test #8");
    	
    	//Test phoneMnumonic #9
        ArrayList<String> mnemonics = Recursive.listMnemonics("1");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("1");
        System.out.println("Actual: " + mnemonics);
        System.out.println("Expected: " + expected);
        if (mnemonics.equals(expected))
            System.out.println( "Test 9 passed. Phone mnemonics.");
        else
            System.out.println( "Test 9 failed. Phone mnemonics.");

        //Test 10 mnemonic
        mnemonics = Recursive.listMnemonics("2");
        Collections.sort(mnemonics);
        expected.clear();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        Collections.sort(expected);
        System.out.println("Actual: " + mnemonics);
        System.out.println("Expected: " + expected);
        if (mnemonics.equals(expected))
            System.out.println( "Test 10 passed. Phone mnemonics.");
        else
            System.out.println( "Test 10 failed. Phone mnemonics.");
        
        //Test 11 flowOfMap
        int[][] world = {{5,5,5,5,5,5},
                {5,5,5,5,5,5},
                {5,5,5,5,5,5},
                {5,5,4,4,5,5},
                {5,5,3,3,5,5},
                {5,5,2,2,5,5},
                {5,5,5,1,5,5},
                {5,5,5,-2,5,5}};
        
        if (Recursive.canFlowOffMap(world, 0, 0))
            System.out.println( "Test 11 passed. FlowOffMap.");
        else
            System.out.println( "Test 11 failed. FlowOffMap.");
        
        //Test 12 flowOfMap
        int[][] world2 = {{5,5,5,5,5,5},
                {5,5,5,5,5,5},
                {5,5,5,5,5,5}};

        
        if (Recursive.canFlowOffMap(world2, 1, 1))
            System.out.println( "Test 12 failed. FlowOffMap.");
        else
            System.out.println( "Test 12 passed. FlowOffMap.");
        
        //Test 13 minDifference
        int[] abilities = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("Actual: " + Recursive.minDifference(3, abilities));
        if(Recursive.minDifference(3, abilities) == 1)
            System.out.println( "Test 13 passed. min difference.");
        else
            System.out.println( "Test 13 failed. min difference.");
        
        int[] abilities2 = {1, 1};
        System.out.println("Actual: " + Recursive.minDifference(2, abilities2));
        if(Recursive.minDifference(2, abilities2) == 0)
            System.out.println( "Test 13 passed. min difference.");
        else
            System.out.println( "Test 13 failed. min difference.");
        
        //Test 14 maze //Check Precondition
        char[][] maze1 = {{'*', '*', 'S', '*'}};
        int res = Recursive.canEscapeMaze(maze1);
        if(res == 0)
        	System.out.println( "Test 14 passed. Maze");
        else 
            System.out.println( "Test 14 passed. Maze");
        
        //Test 15 maze
        char[][] maze2 = {{'*', '*', 'S', 'E', '*'}};
        res = Recursive.canEscapeMaze(maze2);
        if(res == 2)
        	System.out.println( "Test 15 passed. Maze");
        else 
            System.out.println( "Test 15 passed. Maze");
        //Test 15 maze
        char[][] maze3 = {{'*', '*','S', '*', '$'}};
        res = Recursive.canEscapeMaze(maze3);
        if(res == 1)
        	System.out.println( "Test 16 passed. Maze");
        else 
            System.out.println( "Test 16 passed. Maze");

    }


}
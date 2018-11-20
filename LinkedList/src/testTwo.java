import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class testTwo {
    //region Test facilitation methods
    private static int testNum = 0;
    private static int testCategory;
    private static int categoryPassed;
    private static int totalInCategory;
    private static int testsPassed;
    private static int totalTests;
    private static ArrayList<String> failedTestNames;

    // Change this value to modify the data size for tests
    private static final int TEST_SIZE = 100;
    private static final int TWO_TEST_SIZE = TEST_SIZE * 2;
    private static final int THREE_TEST_SIZE = TEST_SIZE * 3;


    private static void printTestHeader(String header) {
        // Print test category information
        if (totalInCategory != 0) {
            if (categoryPassed == totalInCategory)
                System.out.println("  ☻ PASSED all tests in category\n");
            else
                System.out.printf("  ‼ FAILED tests in category: passed %d of %d tests\n\n", categoryPassed, totalInCategory);
        }

        // Reset category test count
        testsPassed += categoryPassed;
        totalTests += totalInCategory;
        categoryPassed = 0;
        totalInCategory = 0;

        // Print test header
        System.out.printf("Test category #%d: %s\n", ++testCategory, header);
    }

    private static void printTestResult(String name, boolean passing, Object expected, Object actual) {
        totalInCategory++;
        testNum++;
        if (passing) {
            categoryPassed++;
            System.out.printf("  √ PASSED test #%d: %s\n", testNum, name);
        }
        else {
            System.out.printf("  ! FAILED test #%d: %s\n      Expected: %s\n      Actual: %s\n", testNum, name, expected, actual);
            failedTestNames.add(String.format("Test #%d: %s", testNum, name));
        }
    }
    //endregion

    public static void main(String[] args) {
        //region Test initialisation
        failedTestNames = new ArrayList<>();
        LinkedList<Integer> testList = new LinkedList<>();
        IList<Integer> testIList;
        Random rand = new Random();
        boolean passing;
        int expectedInt, actualInt;
        String expectedString, actualString;
        long runtime;
        //endregion

        //region Add tests
        printTestHeader("add(E item)");

        try {
            testList.add(null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = TEST_SIZE;
        for (int i = 0; i < expectedInt; i++)
            testList.add(i);
        actualInt = testList.size();
        passing = expectedInt == actualInt;
        printTestResult("Adding list elements", passing, expectedInt, actualInt);

        passing = true;
        actualInt = 0;
        for (int i : testList) {
            if (i != actualInt) {
                passing = false;
                break;
            }
            actualInt++;
        }
        printTestResult("List elements correct", passing, expectedInt, actualInt);

        printTestHeader("addFirst(E item)");

        try {
            testList.addFirst(null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = TWO_TEST_SIZE;
        for (int i = -1; i >= -TEST_SIZE; i--)
            testList.addFirst(i);
        actualInt = testList.size();
        passing = expectedInt == actualInt;
        printTestResult("Prepending list elements", passing, expectedInt, actualInt);

        passing = true;
        expectedInt = TEST_SIZE;
        actualInt = -TEST_SIZE;
        for (int i : testList) {
            if (i != actualInt) {
                passing = false;
                break;
            }
            actualInt++;
        }
        printTestResult("List elements correct", passing, expectedInt, actualInt);

        printTestHeader("addLast(E item)");

        try {
            testList.addLast(null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = THREE_TEST_SIZE;
        for (int i = TEST_SIZE; i < TWO_TEST_SIZE; i++)
            testList.addLast(i);
        actualInt = testList.size();
        passing = expectedInt == actualInt;
        printTestResult("Appending list elements", passing, expectedInt, actualInt);

        passing = true;
        expectedInt = TWO_TEST_SIZE;
        actualInt = -TEST_SIZE;
        for (int i : testList) {
            if (i != actualInt) {
                passing = false;
                break;
            }
            actualInt++;
        }
        printTestResult("List elements correct", passing, expectedInt, actualInt);
        //endregion

        //region Get tests
        printTestHeader("get(int pos)");

        try {
            testList.get(-1);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (pos = -1)", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.get(testList.size());
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (pos = size())", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = TWO_TEST_SIZE;
        passing = true;
        runtime = System.nanoTime();
        for (actualInt = -TEST_SIZE; actualInt < expectedInt && passing; actualInt++)
            passing &= actualInt == testList.get(actualInt + TEST_SIZE);
        runtime = System.nanoTime() - runtime;
        printTestResult("Returned correct items (runtime " + runtime + " ns)", passing, expectedInt, actualInt);

        passing = true;
        for (int i = 0; i < TEST_SIZE && passing; i++) {
            actualInt = rand.nextInt(THREE_TEST_SIZE);
            int valAtIdx = testList.get(actualInt);

            if (valAtIdx < -TEST_SIZE || valAtIdx >= TWO_TEST_SIZE)
                passing = false;
        }
        printTestResult("Random access within expected range", passing, "all indices contain correct values",
                String.format("index %d contains incorrect value", actualInt));
        //endregion

        //region Set tests
        printTestHeader("set(int pos, E item)");

        try {
            testList.set(-1, 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (pos = -1)", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.set(testList.size(), 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (pos = size())", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.set(0, null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        passing = true;
        expectedInt = THREE_TEST_SIZE;
        runtime = System.nanoTime();
        for (int i = TWO_TEST_SIZE; i < THREE_TEST_SIZE; i++)
            testList.set(i, i - TWO_TEST_SIZE);
        runtime = System.nanoTime() - runtime;
        for (actualInt = TWO_TEST_SIZE; actualInt < THREE_TEST_SIZE && passing; actualInt++)
            passing &= testList.get(actualInt).equals(actualInt - TWO_TEST_SIZE);
        printTestResult("Value persistence (runtime " + runtime + " ns)", passing, expectedInt, actualInt);

        passing = true;
        for (int i = 0; i < TEST_SIZE && passing; i++) {
            actualInt = rand.nextInt(THREE_TEST_SIZE);
            int newVal = rand.nextInt(Integer.MAX_VALUE);
            testList.set(actualInt, newVal);
            passing &= testList.get(actualInt).equals(newVal);
        }
        printTestResult("Random access persistence", passing, "all indices contain correct values",
                String.format("index %d contains incorrect value", actualInt));
        //endregion

        //region IndexOf tests
        // Repopulate list after random access tests
        testList = new LinkedList<>();
        for (int i = 0; i <= TWO_TEST_SIZE; i++)
            testList.add(i);

        printTestHeader("indexOf(E item)");

        try {
            actualInt = testList.indexOf(null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = 0;
        actualInt = testList.indexOf(0);
        passing = expectedInt == actualInt;
        printTestResult("Find element at beginning of list", passing, expectedInt, actualInt);

        expectedInt = TEST_SIZE;
        actualInt = testList.indexOf(TEST_SIZE);
        passing = expectedInt == actualInt;
        printTestResult("Find element at midpoint of list", passing, expectedInt, actualInt);

        expectedInt = TEST_SIZE;
        actualInt = testList.indexOf(TEST_SIZE);
        passing = expectedInt == actualInt;
        printTestResult("Find element at end of list", passing, expectedInt, actualInt);

        expectedInt = -1;
        actualInt = testList.indexOf(Integer.MAX_VALUE);
        passing = expectedInt == actualInt;
        printTestResult("Find element not in list", passing, expectedInt, actualInt);

        // Add more elements to list for starting index tests
        for (int i = 0; i <= TEST_SIZE; i++)
            testList.set(i + TEST_SIZE, i);

        printTestHeader("indexOf(E item, int pos)");

        try {
            actualInt = testList.indexOf(null, 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        try {
            actualInt = testList.indexOf(2, -1);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Start index less than 0", passing, "Exception thrown",
                "no exception thrown");

        try {
            actualInt = testList.indexOf(2, testList.size());
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Start index greater than or equal to size", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = TEST_SIZE / 2;
        actualInt = testList.indexOf(TEST_SIZE / 2, 0);
        passing = expectedInt == actualInt;
        printTestResult("Start index of 0 returns first match", passing, expectedInt, actualInt);

        expectedInt = TEST_SIZE * 3 / 2;
        actualInt = testList.indexOf(TEST_SIZE / 2, TEST_SIZE);
        passing = expectedInt == actualInt;
        printTestResult("Start index in middle returns late match", passing, expectedInt, actualInt);

        expectedInt = TEST_SIZE / 2;
        actualInt = testList.indexOf(TEST_SIZE / 2, TEST_SIZE / 2);
        passing = expectedInt == actualInt;
        printTestResult("Start index at match returns same index", passing, expectedInt, actualInt);
        //endregion

        //region GetSubList tests
        printTestHeader("getSubList(int start, int stop)");

        try {
            testIList = testList.getSubList(-1, testList.size());
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (start < 0)", passing, "Exception thrown",
                "no exception thrown");

        try {
            testIList = testList.getSubList(testList.size() + 1, testList.size() + 2);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (start > size())", passing, "Exception thrown",
                "no exception thrown");

        try {
            testIList = testList.getSubList(0, testList.size() + 1);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (stop > size())", passing, "Exception thrown",
                "no exception thrown");

        try {
            testIList = testList.getSubList(1, 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (start > stop)", passing, "Exception thrown",
                "no exception thrown");

        testIList = testList.getSubList(0, 0);
        expectedInt = 0;
        actualInt = testIList.size();
        passing = expectedInt == actualInt;
        printTestResult("Size is 0 for start = stop", passing, expectedInt, actualInt);

        try {
            testIList.get(0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("List contains no elements for start = stop", passing, "Exception thrown",
                "no exception thrown");

        testIList = testList.getSubList(0, TEST_SIZE);
        passing = true;
        for (actualInt = 0; actualInt < TEST_SIZE && passing; actualInt++)
            passing &= testIList.get(actualInt) == actualInt;
        printTestResult("Sublist contains correct elements", passing, TEST_SIZE, actualInt);

        testList.set(0, Integer.MAX_VALUE);
        expectedInt = 0;
        actualInt = testIList.get(0);
        passing = expectedInt == actualInt;
        printTestResult("Sublist is deep copy", passing, expectedInt, actualInt);
        //endregion

        //region Iterator tests
        LinkedList<String> testList2 = new LinkedList<>();
        LinkedList<Integer> testList3 = new LinkedList<>();
        for (int i = 0; i < THREE_TEST_SIZE; i++)
            testList3.add(i);

        printTestHeader("iterator()");
        Iterator<Integer> iterator1 = testList.iterator();
        passing = iterator1 != null;
        printTestResult("Iterator initialisation 1", passing, "an Iterator object", "null");

        Iterator<String> iterator2 = testList2.iterator();
        passing = iterator2 != null;
        printTestResult("Iterator initialisation 2 (empty list)", passing, "an Iterator object", "null");

        Iterator<Integer> iterator3 = testList3.iterator();
        passing = iterator3 != null;
        printTestResult("Iterator initialisation 3", passing, "an Iterator object", "null");

        printTestHeader("Iterator.hasNext()");
        passing = !(iterator2.hasNext());
        printTestResult("Empty list", passing, false, passing);

        passing = iterator1.hasNext();
        printTestResult("Beginning of list", passing, true, passing);

        expectedInt = THREE_TEST_SIZE;
        actualInt = 0;
        while (iterator3.hasNext()) {
            actualInt++;
            iterator3.next();
        }
        passing = expectedInt == actualInt;
        printTestResult("Iteration reaches all list elements", passing, expectedInt, actualInt);

        passing = !(iterator3.hasNext());
        printTestResult("End of list", passing, false, passing);

        printTestHeader("Iterator.next()");

        try {
            iterator2.next();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Empty list", passing, "Exception thrown", "no exception thrown");

        iterator3 = testList3.iterator();
        expectedInt = THREE_TEST_SIZE;
        passing = true;
        for (actualInt = 0; actualInt < THREE_TEST_SIZE && passing; actualInt++) {
            passing &= iterator3.next() == actualInt;
        }
        printTestResult("Returns correct values", passing, expectedInt, actualInt);

        try {
            iterator3.next();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("End of list", passing, "Exception thrown", "no exception thrown");

        printTestHeader("Iterator.remove()");
        iterator3 = testList3.iterator();
        try {
            iterator3.remove();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Remove before next()", passing, "Exception thrown", "no exception thrown");

        expectedInt = 1;
        iterator3.next();
        iterator3.remove();
        actualInt = testList3.get(0);
        passing = expectedInt == actualInt;
        printTestResult("Remove mutates underlying list", passing, expectedInt, actualInt);

        try {
            iterator3.remove();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Consecutive removals without next() call", passing, "Exception thrown",
                "no exception thrown");

        expectedInt = 0;
        while (iterator3.hasNext()) {
            iterator3.next();
            iterator3.remove();
        }
        actualInt = testList3.size();
        passing = expectedInt == actualInt;
        printTestResult("Remove entire list", passing, expectedInt, actualInt);
        //endregion

        //region Insert tests
        printTestHeader("insert(int pos, E item)");

        try {
            testList.insert(0, null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (item = null)", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.insert(-1, 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Start index less than 0", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.insert(testList.size() + 1, 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Start index greater than size", passing, "Exception thrown",
                "no exception thrown");

        testList2.insert(0, "hello");
        expectedString = "hello";
        actualString = testList2.get(0);
        passing = expectedString.equals(actualString);
        passing &= testList2.size() == 1;
        printTestResult("Insert in empty list", passing, expectedString, actualString);

        testList.set(0, 0);
        testList.insert(0, -1);
        expectedInt = -1;
        actualInt = testList.get(0);
        passing = expectedInt == actualInt;
        passing &= testList.get(1) == 0;
        printTestResult("Insert at beginning of list", passing, expectedInt, actualInt);

        int oldSize = testList.size();
        testList.insert(oldSize, TEST_SIZE + 1);
        expectedInt = TEST_SIZE + 1;
        actualInt = testList.get(oldSize);
        passing = expectedInt == actualInt;
        passing &= testList.get(oldSize - 1) == TEST_SIZE;
        printTestResult("Insert at end of list", passing, expectedInt, actualInt);

        expectedInt = TEST_SIZE;
        oldSize = testList.size();
        passing = true;
        for (actualInt = 0; actualInt < TEST_SIZE && passing; actualInt++) {
            testList.insert(TEST_SIZE / 2, actualInt);
            passing &= testList.get(TEST_SIZE / 2) == actualInt;
        }
        passing &= testList.size() == oldSize + TEST_SIZE;
        printTestResult("Insert in middle of list", passing, expectedInt, actualInt);
        //endregion

        //region Remove tests
        printTestHeader("remove(int pos)");

        try {
            testList.remove(-1);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (pos < 0)", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.remove(testList.size());
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (pos = size())", passing, "Exception thrown",
                "no exception thrown");

        expectedString = testList2.get(0);
        actualString = testList2.remove(0);
        passing = expectedString.equals(actualString);
        passing &= testList2.size() == 0;
        printTestResult("Remove only element", passing, expectedString, actualString);

        oldSize = testList.size();
        expectedInt = testList.get(1);
        testList.remove(0);
        actualInt = testList.get(0);
        passing = expectedInt == actualInt;
        passing &= testList.size() == oldSize - 1;
        printTestResult("Remove at beginning of list", passing, expectedInt, actualInt);

        expectedInt = testList.get(testList.size() - 2);
        testList.remove(testList.size() - 1);
        actualInt = testList.get(testList.size() - 1);
        passing = expectedInt == actualInt;
        passing &= testList.size() == oldSize - 2;
        printTestResult("Remove at end of list", passing, expectedInt, actualInt);

        for (int i = 0; i < THREE_TEST_SIZE; i++)
            testList3.add(i);

        passing = true;
        for (int i = 0; i < TEST_SIZE && passing; i++) {
            int removeIdx = rand.nextInt(testList3.size());
            expectedInt = testList3.get(removeIdx);
            actualInt = testList3.remove(removeIdx);
            passing &= expectedInt == actualInt;
        }
        passing &= testList3.size() == TWO_TEST_SIZE;
        printTestResult("Remove random within list", passing, expectedInt, actualInt);

        printTestHeader("remove(E obj)");

        try {
            testList.remove(null);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (obj = null)", passing, "Exception thrown",
                "no exception thrown");

        passing = !(testList2.remove("Nothing here"));
        passing &= testList2.size() == 0;
        printTestResult("Remove from empty list", passing, false, !passing);

        expectedString = "Resistance is futile";
        testList2.add(expectedString);
        passing = testList2.remove(expectedString);
        passing &= testList2.size() == 0;
        printTestResult("Remove only list item", passing, true, passing);

        testList3.makeEmpty();
        for (int i = 0; i < TEST_SIZE; i++)
            testList3.add(i);

        oldSize = testList3.size();
        passing = testList3.remove((Integer) 0);
        passing &= testList3.get(0) == 1;
        passing &= testList3.size() == oldSize - 1;
        printTestResult("Remove first list element", passing, true, passing);

        passing = testList3.remove((Integer) (TEST_SIZE - 1));
        passing &= testList3.get(testList3.size() - 1) == TEST_SIZE - 2;
        printTestResult("Remove last list element", passing, true, passing);

        expectedInt = TEST_SIZE - 2;
        passing = true;
        for (actualInt = 1; actualInt < TEST_SIZE - 1 && passing; actualInt++) {
            // Explicit cast required for integer to avoid calling overloaded method remove(int pos)
            passing &= testList3.remove((Integer) actualInt);
        }
        passing &= testList3.size() == 0;
        printTestResult("Remove all from list", passing, expectedInt, actualInt);

        printTestHeader("removeFirst()");

        try {
            testList3.removeFirst();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (empty list)", passing, "Exception thrown",
                "no exception thrown");

        expectedString = "scientia est potestas";
        testList2.add(expectedString);
        actualString = testList2.removeFirst();
        passing = expectedString.equals(actualString);
        passing &= testList2.size() == 0;
        printTestResult("Remove only list item", passing, expectedString, actualString);

        for (int i = 0; i < TEST_SIZE; i++)
            testList3.add(i);

        expectedInt = TEST_SIZE;
        passing = true;
        for (actualInt = 0; actualInt < TEST_SIZE && passing; actualInt++) {
            passing &= testList3.removeFirst() == actualInt;
        }
        passing &= testList3.size() == 0;
        printTestResult("Remove all from list", passing, expectedInt, actualInt);

        printTestHeader("removeLast()");

        try {
            testList3.removeLast();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (empty list)", passing, "Exception thrown",
                "no exception thrown");

        expectedString = "They don't think it be like it is, but it do";
        testList2.add(expectedString);
        actualString = testList2.removeLast();
        passing = expectedString.equals(actualString);
        passing &= testList2.size() == 0;
        printTestResult("Remove only list item", passing, expectedString, actualString);

        for (int i = 0; i < TEST_SIZE; i++)
            testList3.add(i);

        expectedInt = TEST_SIZE;
        passing = true;
        for (actualInt = TEST_SIZE - 1; actualInt >= 0 && passing; actualInt--) {
            passing &= testList3.removeLast() == actualInt;
        }
        passing &= testList3.size() == 0;
        printTestResult("Remove all from list", passing, expectedInt, actualInt);

        printTestHeader("removeRange(int start, int stop)");

        try {
            testList.removeRange(-1, testList.size());
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (start < 0)", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.removeRange(testList.size() + 1, testList.size() + 2);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (start > size())", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.removeRange(0, testList.size() + 1);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (stop > size())", passing, "Exception thrown",
                "no exception thrown");

        try {
            testList.removeRange(1, 0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Precondition check (start > stop)", passing, "Exception thrown",
                "no exception thrown");

        testList.makeEmpty();
        testList3.makeEmpty();
        for (int i = 0; i < THREE_TEST_SIZE; i++) {
            testList.add(i);
            testList3.add(i);
        }

        testList.removeRange(0, 0);
        iterator1 = testList.iterator();
        iterator3 = testList3.iterator();
        passing = true;
        while (iterator1.hasNext())
            passing &= iterator1.next().equals(iterator3.next());
        passing &= testList.size() == testList3.size();
        printTestResult("Remove no element (start = stop)", passing, "list is not modified", "list is modified");

        expectedString = "ouden monimoteron tou prosopinou";
        testList2.add(expectedString);
        testList2.removeRange(0, 1);
        passing = testList2.size() == 0;
        printTestResult("Remove entire list (0 to size)", passing, "empty list", "elements remain in list");

        expectedInt = THREE_TEST_SIZE / 3;
        testList.removeRange(0, TEST_SIZE);
        actualInt = testList.get(0);
        passing = expectedInt == actualInt;
        passing &= testList.size() == TWO_TEST_SIZE;
        printTestResult("Remove range from beginning", passing, expectedInt, actualInt);

        expectedInt = TWO_TEST_SIZE - 1;
        testList.removeRange(TEST_SIZE, TWO_TEST_SIZE);
        actualInt = testList.get(testList.size() - 1);
        passing = expectedInt == actualInt;
        passing &= testList.size() == TEST_SIZE;
        printTestResult("Remove range from end", passing, expectedInt, actualInt);

        for (int i = 0; i < TEST_SIZE; i++)
            testList.insert(TEST_SIZE / 2, i);

        testList.removeRange(TEST_SIZE / 2, TEST_SIZE * 3 / 2);
        iterator1 = testList.iterator();
        expectedInt = TWO_TEST_SIZE - 1;
        actualInt = TEST_SIZE;
        passing = true;
        while (iterator1.hasNext() && passing) {
            passing &= iterator1.next() == actualInt;
            actualInt++;
        }
        passing &= testList.size() == TEST_SIZE;
        printTestResult("Remove range in middle", passing, expectedInt, actualInt);
        //endregion

        //region Equals tests
        testList.makeEmpty();
        testList2.makeEmpty();
        testList3.makeEmpty();
        for (int i = 0; i < THREE_TEST_SIZE; i++) {
            testList.add(i);
            testList2.add(Integer.toString(i));
            testList3.add(i);
        }

        printTestHeader("equals(Object obj)");

        passing = !(testList.equals(null));
        printTestResult("List equals null", passing, false, !passing);

        passing = !(testList.equals("ehyeh asher lo ehyeh"));
        printTestResult("List equals different type", passing, false, !passing);

        testList.add(0);
        passing = !(testList.equals(testList3));
        printTestResult("List equals list of different size", passing, false, !passing);

        passing = !(testList.equals(testList2));
        printTestResult("List equals list of different type", passing, false, !passing);

        testList.removeLast();
        passing = testList.equals(testList3);
        printTestResult("List equals equivalent list", passing, true, passing);

        passing = testList.equals(testList);
        printTestResult("List equals same list", passing, true, passing);
        //endregion

        //region ToString tests
        printTestHeader("toString()");

        testList2.makeEmpty();
        expectedString = "[]";
        actualString = testList2.toString();
        passing = expectedString.equals(actualString);
        printTestResult("Empty list", passing, expectedString, actualString);

        testList2.add("You're almost done!");
        expectedString = "[You're almost done!]";
        actualString = testList2.toString();
        passing = expectedString.equals(actualString);
        printTestResult("List with one element", passing, expectedString, actualString);

        testList2.add("Even closer!");
        expectedString = "[You're almost done!, Even closer!]";
        actualString = testList2.toString();
        passing = expectedString.equals(actualString);
        printTestResult("List with two elements", passing, expectedString, actualString);

        actualString = testList.toString();
        passing = !(actualString.endsWith(", ]") && actualString.endsWith(",]"));
        printTestResult("List with many elements (termination check)", passing, expectedString, actualString);
        //endregion

        //region Other tests
        printTestHeader("makeEmpty()");

        testList.makeEmpty();
        try {
            testList.get(0);
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("get() permits no elements", passing, "Exception thrown",
                "no exception thrown");

        testList2.makeEmpty();
        expectedInt = 0;
        actualInt = testList2.size();
        passing = expectedInt == actualInt;
        printTestResult("Size after emptying is 0", passing, expectedInt, actualInt);

        testList3.makeEmpty();
        iterator3 = testList3.iterator();
        try {
            iterator3.next();
            passing = false;
        } catch (Exception e) {
            passing = true;
        }
        printTestResult("Iterator contains no elements", passing, true, passing);
        //endregion

        //region Print test results
        if (totalInCategory != 0) {
            if (categoryPassed == totalInCategory)
                System.out.println("  ☻ PASSED all tests in category");
            else
                System.out.printf("  ‼ FAILED tests in category: passed %d of %d tests\n", categoryPassed, totalInCategory);
        }

        testsPassed += categoryPassed;
        totalTests += totalInCategory;
        System.out.printf("\nFinished running tests: passed %d of %d\n", testsPassed, totalTests);

        if (testsPassed != totalTests) {
            System.out.println("\nSummary of tests failed:");
            for (String name : failedTestNames)
                System.out.printf("- %s\n", name);
        }
        //endregion
    }
}
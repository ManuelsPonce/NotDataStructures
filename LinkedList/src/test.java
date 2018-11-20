import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class test {

    public static void main(String[] args) {
        // main things
        LinkedList<Integer> list = new LinkedList<Integer>();
        ArrayList<Integer> compare = new ArrayList<Integer>();
        
        // used in test 1.3
        LinkedList<ArrayList<Integer>> list2 = new LinkedList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> compare2 = new ArrayList<ArrayList<Integer>>();

        // used for the stupid tests 
        ArrayList<ArrayList<ArrayList<Integer>>> stupid = new ArrayList<>();        // used in toString() test
        LinkedList<LinkedList<LinkedList<Integer>>> stupid2 = new LinkedList<>();
        LinkedList<LinkedList<LinkedList<Integer>>> stupid3 = new LinkedList<>();

        // part of the stupid tests
        ArrayList<Integer> arr1 = new ArrayList<Integer>();
        LinkedList<Integer> ll1 = new LinkedList<>();

        // also part of the stupid tests
        ArrayList<ArrayList<Integer>> arr2 = new ArrayList<>();
        LinkedList<LinkedList<Integer>> ll2 = new LinkedList<>(); 
    
        // Test 1: add
      
        // Test 1.1
        list.add(1);
        passed(1.1 , "add method", list.get(0) == 1 && list.size() == 1);

        // Test 1.2
        list = new LinkedList<Integer>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
            compare.add(i);
        }
        passed(1.2, "add method", list.toString().equals(compare.toString()));

        // Test 1.3
        list2.add(new ArrayList<Integer>());
        compare2.add(new ArrayList<Integer>());
        passed(1.3, "add method", list2.toString().equals(compare2.toString()));

        // test 2 insert

        // 2.1
        list.insert(3, 90);
        passed(2.1, "insert", list.get(3) == 90);

        // 2.2
        list.insert(0, -999);
        passed(2.2, "insert idx 0", list.get(0) == -999);

        // 2.3
        int oldSize = list.size();
        list.insert(list.size(), -1);
        passed(2.3, "insert at idx size", list.get(oldSize) == -1 && list.size() == oldSize + 1);

        // 2.4
        list = new LinkedList<Integer>();
        list.insert(0, -1);
        passed(2.4, "insert on empty list", list.get(0) == -1 && list.size() == 1);

        // test 3 set method

        // 3.1
        list.set(0, -1);
        passed(3.1, "set method on single element list", list.get(0) == -1);

        // 3.2
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 50; i++)
            list.add(i);
        try {
            list.set(51, -9);
            passed(3.2, "set on index oob", false);
        } catch (Exception e) {
            passed(3.3, "set on index oob", true); 
        }

        // 3.3 
        list.set(list.size() - 1, -111);
        passed(3.3, "set on last element", list.get(list.size() - 1) == -111);

        // test 4 get

        // 4.1
        try {
            list.get(list.size());
            passed(4.1, "get on index oob", false);
        } catch (Exception e) {
            passed(4.1, "get on index oob", true); 
        }

        // 4.2
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 100; i++)
            list.add(i);
        boolean test = true;
        for(int i = 0; i < list.size(); i++)
            if(list.get(i) != i)
                test = false;
        passed(4.2, "get on each item in list", test);

        // 4.3
        final int SECOND_LAST = 99999;
        for(int i = 0; i <= 100000; i++)
            list.add(i);
        passed(4.3, "get on second last item in 100k item list", list.get(list.size() - 2) == SECOND_LAST);

        // test 5 remove(int idx)

        // 5.1
        list = new LinkedList<Integer>();
        try {
            list.remove(0);
            passed(5.1, "remove(int pos) on empty list", false);

        } catch (Exception e) {
            passed(5.1, "remove(int pos) on empty list", true);
        }

        // 5.2
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 100; i++)
            list.add(i);
        int originalSize = list.size();
        passed(5.2, "remove(int pos) test return val and size", list.remove(list.size() - 2) == 99 && list.size() == originalSize - 1);

        // 5.2
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 100000; i++)
            list.add(i);
        originalSize = list.size();
        int removed = list.get(list.size() - 1);
        passed(5.3, "remove(int pos) on 100k size list from the end", list.remove(list.size()-1) == removed && list.size() == originalSize -1);

        // 5.4
        originalSize = list.size();
        removed = list.get(list.size() - 2);
        passed(5.4, "remove(int pos) on 100k size list at second to last idx", list.remove(list.size()-2) == removed && list.size() == originalSize -1);

        // Test 6 remove(E obj)
       
        // 6.1
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 100; i++)
            list.add(i*2);
        boolean test2 = true;
        for(int i = 0; i <= 100; i++) 
            if(!list.remove(new Integer(i*2)))
                test2 = false;
        passed(6.1, "remove(E obj) on every element in list and test return val", test2 && list.size() == 0);

        // 6.2
        passed(6.2, "remove(E obj) on an item that doesn't exist", !list.remove(new Integer(999)));

        // 6.3
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 1000; i++)
            list.add(i);
        passed(6.3, "remove(E obj) on end of list size 1000", list.remove(new Integer(list.size() - 1)) && list.size() == 1000);

        // 6.4 
        list = new LinkedList<Integer>();
        int[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 8};
        originalSize = items.length;
        for(int i : items)
            list.add(i);
        test = list.remove(new Integer(8));
        passed(6.4, "remove(E obj) ensure removal of first occurence", list.get(list.size() - 1) == 8 && list.get(7) == 9 && list.size() == originalSize -1);

        // 6.5
        try {
            list.remove(null);
            passed(6.4, "remove(E obj) null parameter", false);
        } catch (Exception e) {
            passed(6.5, "remove(E obj) null parameter", true);
        }

        // Test 7 getSublist

        // 7.1
        passed(7.1, "sublist: ensure size,size works", list.getSubList(list.size(), list.size()).equals(new LinkedList<Integer>()));

        // 7.2

        try {
            list.getSubList(list.size() + 1, list.size() + 1);
            passed(7.2, "sublist: ensure no oob", false);
        } catch(Exception e) {
            passed(7.2, "sublist: ensure no oob", true);
        }

        // 7.3
        String a = "0123456789";
        String b = a.substring(4,8);

        LinkedList<String> list3 = new LinkedList<String>();
        for(int i = 0; i < a.length(); i++)
            list3.add(a.substring(i, i+1));
        list3 = (LinkedList<String>) list3.getSubList(4,8);

        test = true;
        if(!(list3.size() == b.length()))
            test = false;
        for(int i = 0; i < b.length() && test; i++)
            if(!(b.substring(i, i+1).equals(list3.get(i))))
                test = false;
        passed(7.3, "sublist: general sublist test ", test);

        // test 8 size

        // 8.1
        list = new LinkedList<Integer>();
        passed(8.1, "empty list size test", list.size() == 0);

        // 8.2 
        for(int i = 0; i < 100; i++)
            list.add(i);
        passed(8.2, "size: check size after adding 100 Nodes", list.size() == 100);

        // 8.3
        Iterator<Integer> iter = list.iterator();

        while (iter.hasNext()) {
          iter.next();
          iter.remove();  
        }

        passed(8.3, "size after removal with iterator", list.size() == 0);

        // test 9 indexOf 

        // 9.1
        list = new LinkedList<Integer>();

        try {
            list.indexOf(null);
            passed(9.1, "indexOf: ensure does not accept null parameter", false);
        } catch(Exception e) {
            passed(9.1, "indexOf: ensure does not accept null parameter", true);
        }

        // 9.2
        list = new LinkedList<Integer>();
        for(int i = 0; i <= 10000; i++)
            list.add(i);
        passed(9.2, "indexOf: general indexOf test", list.indexOf(500) == 500);

        // 9.3
        passed(9.3, "indexOf: test for index not present", list.indexOf(-2) == -1);


        // test 10 indexOf(E item, int pos)

        // 10.1
        list = new LinkedList<Integer>();
        for(int i = 0; i < 100; i++)
            list.add(1);
        for(int i = 0; i < 100; i++)
            list.add(2);
        for(int i = 0; i < 100; i++)
            list.add(3);
        list.add(1);

        passed(10.1, "indexOf(E item int pos): check idxof item that is present but before pos", list.indexOf(2, 205) == -1);

        // 10.2
        try {
            list.indexOf(null, 5);
            passed(10.2, "indexOf(E item, int pos): ensure does not accept null parameter", false);
        } catch(Exception e) {
            passed(10.2, "indexOf(E item, int pos): ensure does not accept null parameter", true);
        }

        // 10.3
        passed(10.3, "indexOf(E item int pos): general test", list.indexOf(1, 105) == 300);

        // test 11 makeEmpty()

        // 11.1
        list.makeEmpty();
        passed(11.1, "makeEmpty: general make empty test", list.size() == 0);

        // 11.2 
        list.makeEmpty();
        passed(11.2,"makeEmpty: makeEmpty on an already empty list", list.size() == 0);

        // 11.3
        for(int i = 0; i < 200; i++)
            list.add(i);
        list.makeEmpty();
        for(int i = 0; i < 100; i++)
            list.add(i);
        passed(11.3, "makeEmpty: add 200, make empty, add again", list.size() == 100);

        // test 12 Iterator hasNext()
        
        // 12.1
        list.makeEmpty();
        iter = list.iterator();
        passed(12.1, "hasNext on an empty list", !iter.hasNext());

        // 12.2
        list.add(25);
        iter = list.iterator();
        passed(12.2, "hasNext on nonEmpty list", iter.hasNext());

        // 12.3 
        for(int i = 0 ; i < 100; i++)
            list.add(i);
        iter = list.iterator();

        for(int i = 0; i < list.size()/2; i++)
            iter.next();
        passed(12.3, "hasNext on middle element", iter.hasNext());

        // test 13 Iterator next() 

        // 13.1
        list.makeEmpty();
        for(int i = 0; i < 100; i++)
            list.add(i);
        int count = 0;
        for(iter = list.iterator(); iter.hasNext();) {
            iter.next();
            count++;
        }

        passed(13.1, "next() ensure visit all nodes", count == list.size());

        // 13.2 

        try {
            iter.next();
            passed(13.2, "next() NullPointerException test", false); 
        } catch(NoSuchElementException e) {
          passed(13.2, "next() NullPointerException test", true);   
        }

        // 13.3

        list.makeEmpty();
        list.add(420);
        iter = list.iterator();
        count = iter.next();
        passed(13.3, "next() test on single Node list", !iter.hasNext() && count == 420);

        // test 14 iterator remove()
      
        // 14.1
        for(int i = 0; i < 1000; i++)
            list.add(i);
        for(iter = list.iterator(); iter.hasNext();) {
            iter.next();
            iter.remove();
        }
        passed(14.1, "iterator remove(): remove the whole list", list.size() == 0);

        // 14.2
        list.makeEmpty();
        list.add(69);
        iter = list.iterator();
        iter.next();
        iter.remove();
        passed(14.2, "iterator remove(): remove a single Node in list", list.size() == 0);

        // 14.3 
        list.add(1);
        list.add(2);
        list.add(3);

        iter = list.iterator();
        iter.next();
        iter.remove();

        try {
            iter.remove();
            passed(14.3, "iterator remove(): IllegalStateException test", false);
        } catch(Exception e) {
            passed(14.3, "iterator remove(): IllegalStateException test", true);
        }

        // test 15 remove range
        
        // 15.1
        list.makeEmpty();
        for (int i = 0; i <= 100; i++)
            list.add(i);
        list.removeRange(8,101);
        passed(15.1, "removeRange from idx to end", list.size() == 8);

        // 15.2
        list.makeEmpty();
        for(int i = 0; i < 2000; i++)
            list.add(i);
        list.removeRange(0,2000);
        passed(15.2, "removeRange entire list", list.size() == 0);

        // 15.3
        try {
            list.remove(list.size());
            passed(15.3, "removeRange oob test", false);
        } catch (Exception e) {
            passed(15.3, "removeRange oob test", true);
        }

        // test 16 equals 
        
        // 16.1
        list.makeEmpty();
        compare.clear();
        passed(16.1, "equals on LL and AL", !list.equals(compare));

        // 16.2 
        list2 = null;
        passed(16.2, "equals on null", !list.equals(list2));

        // 16.3
        LinkedList<Character> c1= new LinkedList<Character>();
        LinkedList<String> s1 = new LinkedList<String>();

        for(int i = 0; i < 100; i++) {
            c1.add(new Character((char)i));
            s1.add(new Character((char)i).toString());
        }

        passed(16.3, "equals on a list of characters vs list of String of those characters" , !c1.equals(s1) && !s1.equals(c1));

        IList<Integer> list4 = new LinkedList<Integer>();
        list.makeEmpty();

        for(int i = 0; i < 100; i++) {
            list.add(i);
            list4.add(i);
        }

        passed(16.4, "equals on IList static and LL static (both ways)", list.equals(list4) && list4.equals(list));

        // 16.5

        final int N = 10;
        // the final linked list will contain (N^6) elements
        for(int i = 0; i < N; i++) {
            arr1.add(i);
            ll1.add(i);
            for(int j = 0; j < N; j++) {
                   arr2.add(arr1); 
                   ll2.add(ll1);        
                for(int k = 0; k < N; k++) {
                    stupid.add(arr2);
                    stupid2.add(ll2);
                    stupid3.add(ll2);
                }
            }
        }

        passed(16.5, "equals: linked list of linked lists of linked lists", stupid2.equals(stupid3) && stupid3.equals(stupid2));

        // test 17 toString() 
     
        // 17.1
        compare.clear();
        list.makeEmpty();

        for(int i = 0; i < 10000; i++) {
            compare.add(i);
            list.add(i);
        }

        passed(17.1, "toString(): general toString() test", list.toString().equals(compare.toString()));

        // 17.2
        compare.clear();
        for(int i = 0; i < 10000; i++) 
            if(i %2 == 0)
                list.remove(new Integer(i));
        for(int i = 1; i < 10000; i += 2)
            compare.add(i);

        passed(17.2, "toString(): test after removal of all odd ints", list.toString().equals(compare.toString()));

        // 17.3
        
        // uses lists defined in 16.4
        passed(17.3, "toString(): LinkedList of linkedLists of LinkedLists", stupid2.toString().equals(stupid.toString()));
        stupid = null;
        stupid2 = null;
        stupid3 = null;

        // test 18 addFirst

        // 18.1
        list.makeEmpty();
        compare.clear();
        for(int i = 0; i < 100; i++) {
            list.addFirst(i);
            compare.add(99-i);
        }
        passed(18.1, "addFirst: add 100 items to front", list.toString().equals(compare.toString()));

        // 18.2
        list.makeEmpty();
        compare.clear();

        try {
            list.addFirst(null);
            passed(18.2, "addFirst: precondition check, null parameter", false);
        } catch (Exception e) {
            passed(18.2, "addFirst: precondition check, null parameter", true);
        }

        // 18.3
        list.makeEmpty();
        list.addFirst(0);
        int first = list.get(0);
        int last = list.get(list.size() -1);
    
        passed(18.3, "addFirst: adding to an empty list, check correct pointer to first and last node", list.size() == 1 && first == last);

        // test 19 addLast

        // Test 19.1
        list.makeEmpty();
        compare.clear();
        list.add(1);
        first = list.get(0);
        last = list.get(list.size() -1);
        passed(19.1 , "addLast add to empty list, ensure correct first, last", list.get(0) == 1 && list.size() == 1 && first == last);

        // Test 19.2
        list = new LinkedList<Integer>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
            compare.add(i);
        }
        passed(19.2, "addLast: general ", list.toString().equals(compare.toString()));

        // Test 19.3
        list2 = new LinkedList<ArrayList<Integer>>();
        compare2 = new ArrayList<ArrayList<Integer>>();
        list2.add(new ArrayList<Integer>());
        compare2.add(new ArrayList<Integer>());
        passed(19.3, "addLast linkedList of an ArrayList", list2.toString().equals(compare2.toString()));

        // test 20 removeFirst

        // 20.1
        list.makeEmpty();
        try {
            list.removeFirst();
            passed(20.1, "remove first on empty list", false);
        } catch(Exception e) {
            passed(20.1, "remove first on empty list", true);
        }

        // 20.2 
        for(int i = 0; i < 100; i++)
            list.add(i);
        first = list.removeFirst();

        passed(20.2, "remove first: general", first == 0 && list.size() == 99);

        // 20.3 
        list.makeEmpty();
        list.add(1);
        first = list.removeFirst();

        passed(20.3, "remove first: remove single Node list", first == 1 && list.size() == 0);

        // test 21 removeLast()

        // 21.1
        list.makeEmpty();
        try {
            list.removeLast();
            passed(21.1, "remove last on empty list", false);
        } catch(Exception e) {
            passed(21.1, "remove last on empty list", true);
        }

        // 21.2
        list.makeEmpty();
        list.add(1);
        last = list.removeLast();

        passed(21.2, "remove last: remove single Node list", last == 1 && list.size() == 0);

        // 21.3 
        list.makeEmpty();
        for(int i = 0; i < 100; i++)
            list.add(i);
        last = list.removeLast();

        passed(21.3, "remove last: general test on size 100 list", last == 99 && list.size() == 99);
        
        
    }

    private static void passed(double testnum, String test, boolean pass) {
        if(pass)
            System.out.println("PASSED test " + testnum + ": " + test);
        else System.out.println("FAILED! test " + testnum + ": " + test);
    }
}
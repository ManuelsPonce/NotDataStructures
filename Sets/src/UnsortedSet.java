/*  Student information for assignment:
 *
 *  On MY honor, Manuel Ponce, this programming assignment is MY own work
 *  and I have not provided this code to any other student.
 *
 *  Number of slip days used: 2
 *
 *  Student 1 (Student whose turnin account is being used)
 *  UTEID: mip445	
 *  email address: maniponce22@gmail.com
 *  Grader name: Lucas
 *  Section number:
 *  
 *  Student 2 
 *  UTEID:
 *  email address:
 *  Grader name:
 *  Section number:
 *  
 */

import java.util.Iterator;
import java.util.ArrayList;

/**
 * A simple implementation of an ISet. 
 * Elements are not in any particular order.
 * Students are to implement methods that 
 * were not implemented in AbstractSet and override
 * methods that can be done more efficiently. 
 * An ArrayList must be used as the internal storage container.
 *
 */
public class UnsortedSet<E> extends AbstractSet<E> {
	private ArrayList<E> myCon = new ArrayList<E>(); //Internal Storage container
	
	/**
     * Add an item to this set.
     * <br> item != null
     * @param item the item to be added to this set. item may not equal null.
     * @return true if this set changed as a result of this operation, false otherwise.
     */
	//O(N)
    public boolean add(E item) {
		//Pre-Con check
    	if (item == null)
            throw new IllegalArgumentException("Pre-Con: element cannot be null");
    	
    	if (!myCon.contains(item)) { //cannot be doubles in the set. or add if container is null
    		myCon.add(item); //add item and increase the size by one and return true because set was modified.
    		return true;
    	}
    	return false; //set was not modified.
    }
    
    
	/**
     * Return an Iterator object for the elements of this set.
     * pre: none
     * @return an Iterator object for the elements of this set
     */
    //O(1)
    public Iterator<E> iterator() {
    	return myCon.iterator();
    }
    
	/**
     * Return the number of elements of this set.
     * pre: none
     * @return the number of items in this set
     */
    public int size() {
    	return myCon.size();
    }
    
	/**
     * Create a new set that is the difference of this set and otherSet. Return an ISet of 
     * elements that are in this Set but not in otherSet. Also called
     * the relative complement. 
     * <br>Example: If ISet A contains [X, Y, Z] and ISet B contains [W, Z] then
     * A.difference(B) would return an ISet with elements [X, Y] while
     * B.difference(A) would return an ISet with elements [W]. 
     * <br>pre: otherSet != null
     * <br>post: returns a set that is the difference of this set and otherSet.
     * Neither this set or otherSet are altered as a result of this operation.
     * <br> pre: otherSet != null
     * @param otherSet != null
     * @return a set that is the difference of this set and otherSet
     */
    //O(N^2)
    public ISet<E> difference(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	ISet<E> result = new UnsortedSet<E>();
    	for(E temp: myCon) {
    		if (!otherSet.contains(temp))
    			result.add(temp);
    	}
    	
    	return result;
    }
    
	/**
     * Create a new set that is the union of this set and otherSet.
     * <br>pre: otherSet != null
     * <br>post: returns a set that is the union of this set and otherSet.
     * Neither this set or otherSet are altered as a result of this operation.
     * <br> pre: otherSet != null
     * @param otherSet != null
     * @return a set that is the union of this set and otherSet
     */
    //O(N^2)
    public ISet<E> union(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	ISet<E> result = this;
    	result.addAll(otherSet);
    	return result;
    }

}
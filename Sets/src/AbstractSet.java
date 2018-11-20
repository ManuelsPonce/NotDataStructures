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

/**
 * Students are to complete this class. 
 * Students should implement as many methods
 * as they can using the Iterator from the iterator 
 * method and the other methods. 
 *
 */
public abstract class AbstractSet<E> implements ISet<E> {

    /* NO INSTANCE VARIABLES ALLOWED.
     * NO DIRECT REFERENCE TO UnsortedSet OR SortedSet ALLOWED.
     * (In other words the data types UnsortedSet and SortedSet
     * will not appear any where in this class.)
     * Also no direct references to ArrayList or other Java Collections.
     */ 
	public boolean contains(E item) {
		//Pre-Con check
    	if (item == null)
            throw new IllegalArgumentException("Pre-Con: element cannot be null");
    	
		Iterator<E> iter = this.iterator(); //get an iterator for this set.
		//linear search for the item
		while(iter.hasNext()) {
			E temp = iter.next();
			if (item.equals(temp)) //if item found return true
				return true;
		}
		return false; //item not found
	}
	
 	/**
     * A union operation. Add all items of otherSet that are not already present in this set
     * to this set.
     * @param otherSet != null
     * @return true if this set changed as a result of this operation, false otherwise.
     */
   public boolean addAll(ISet<E> otherSet) {
		//Pre-Con check
   	if (otherSet == null)
           throw new IllegalArgumentException("Pre-Con: element cannot be null");
   	
   	boolean added = false;
   	for(E temp : otherSet) { //go through the otherSet and add the item.
   		boolean result = add(temp);
   		if(!added && result)//if add is not possible
   			added = true;
   	}
   	if (!added) //no item was able to be added.
   		return false;
   	
   	return true; //at least one item was added.
	   
   }
	
	/**
     * Determine if all of the elements of otherSet are in this set.
     * <br> pre: otherSet != null
     * @param otherSet != null
     * @return true if this set contains all of the elements in otherSet, 
     * false otherwise.
     */
	public boolean containsAll(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: element cannot be null");
    	
		for (E val :  otherSet) { //go through each element in the set
			if (!contains(val)) //if passing the current element to contains() and it isnt contained in the set return false.
				return false;
		}
		return true;
	}
	
	/**
     * Make this set empty.
     * <br>pre: none
     * <br>post: size() = 0
     */
    public void clear() {
    	//create an iterator to go through and set and remove the items in the set.
    	Iterator<E> iter = this.iterator();
    	while (iter.hasNext()) {
    		iter.next();
    		iter.remove();
    	}
    }
    
    /**
     * Determine if this set is equal to other.
     * Two sets are equal if they have exactly the same elements.
     * The order of the elements does not matter.
     * <br>pre: none
     * @param other the object to compare to this set 
     * @return true if other is a Set and has the same elements as this set
     */
    public boolean equals(Object other) {
    	//safe cast check
    	if (other instanceof ISet<?>) {
    		ISet<?> otherSet = (ISet<?>)other;
    		
    		//check sizes of sets first
    		if (this.size() == otherSet.size()) {
    			//create iterators for other and this
    			Iterator<E> iterThis = this.iterator();
    			
    			//while still can iterate over sets check if they contain the current item
    			while (iterThis.hasNext()) {
    				E tempThis = iterThis.next();
    				boolean found = false;
    				Iterator<?> iterOther = otherSet.iterator();
    				
    				while(iterOther.hasNext() && !found) {
    					Object otherTemp = iterOther.next();
    					
    					if(tempThis.equals(otherTemp))
    						found = true;
    				}
    				
    				if(!found)
    					return false; //one set had an item that the other set did no contain.
    			}
    			return true; //gets past the instance of check, the element checks and size check. Thus they are equal.
    		}
    		return false; //not the same size
    	}
    	return false; //not an ISet.
    }
    
	/**
     * Remove the specified item from this set if it is present.
     * pre: item != null
     * @param item the item to remove from the set. item may not equal null.
     * @return true if this set changed as a result of this operation, false otherwise
     */
    public boolean remove(E item) {
		//Pre-Con check
    	if (item == null)
            throw new IllegalArgumentException("Pre-Con: element cannot be null");
    	//go through set and if current item in set equals item passed in, remove and return true.
    	Iterator<E> iter = this.iterator();
    	while (iter.hasNext()) {
    		E temp = iter.next();
    		if(temp.equals(item)) {
    			iter.remove();
    			return true;
    		}
    	}
    	return false; //no item was removed.
    }
    
	/**
     * Return the number of elements of this set.
     * pre: none
     * @return the number of items in this set
     */
    public int size() {
    	//go through set and count items.
    	Iterator<E> iter = this.iterator();
    	int count = 0;
    	while (iter.hasNext()) {
    		iter.next();
    		count++;
    	}
    	return count;
    }
    
	/**
     * create a new set that is the intersection of this set and otherSet.
     * <br>pre: otherSet != null<br>
     * <br>post: returns a set that is the intersection of this set and otherSet.
     * Neither this set or otherSet are altered as a result of this operation.
     * <br> pre: otherSet != null
     * @param otherSet != null
     * @return a set that is the intersection of this set and otherSet
     */
    public ISet<E> intersection(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	//create an iterator to go through the set
    	Iterator<E> iterThis = this.iterator();
    	//begin with a set that is the union of both sets.
    	ISet<E> result = union(otherSet);
    	//go through the union set and remove items that are not in both sets
    	while(iterThis.hasNext()) {
    		E temp = iterThis.next();
    		if (!otherSet.contains(temp))
    			iterThis.remove();
    	}
    	
    	return result; //return intersection of sets
    }
      
    //Mikes code for to String
    public String toString() {
        StringBuilder result = new StringBuilder();
        String seperator = ", ";
        result.append("(");

        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            result.append(seperator);
        }
        // get rid of extra separator
        if (this.size() > 0)
            result.setLength(result.length() - seperator.length());

        result.append(")");
        return result.toString();
    }
}
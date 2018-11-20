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
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * In this implementation of the ISet interface the elements in the Set are 
 * maintained in ascending order.
 * 
 * The data type for E must be a type that implements Comparable.
 * 
 * Students are to implement methods that were not implemented in AbstractSet 
 * and override methods that can be done more efficiently. An ArrayList must 
 * be used as the internal storage container. For methods involving two set, 
 * if that method can be done more efficiently if the other set is also a 
 * SortedSet do so.
 */
public class SortedSet<E extends Comparable<? super E>> extends AbstractSet<E> {

    private ArrayList<E> myCon;
    private int size;

    /**
     * create an empty SortedSet
     */
    public SortedSet() {
    	myCon = new ArrayList<E>();
    }

    /**
     * create a SortedSet out of an unsorted set. <br>
     * @param other != null
     */
    //O(N)
    public SortedSet(ISet<E> other) {
		//Pre-Con check
    	if (other == null)
            throw new IllegalArgumentException("Pre-Con: element cannot be null");
    	
    	//creating a copy of the other i list container. ///use for each
    	ArrayList<E> copy = new ArrayList<E>();
    	
    	for (E temp: other) {
    		copy.add(temp);
    	}
    	
    	System.out.println(copy.size());  	
    	
    	mergeSort(copy); //sort the unsorted set
    	myCon = copy; //ISet container becomes the sorted copy set.
    }
    
	/**
     * Return the number of elements of this set.
     * pre: none
     * @return the number of items in this set
     */
    //O(1)
    public int size() {
    	return myCon.size();
    }

	/**
     * Add an item to this set.
     * <br> item != null
     * @param item the item to be added to this set. item may not equal null.
     * @return true if this set changed as a result of this operation, false otherwise.
     */
    public boolean add(E item) {
		//Pre-Con check
    	if (item == null)
            throw new IllegalArgumentException("Pre-Con: element cannot be null");
    	
    	if (!myCon.contains(item)) { //cannot be doubles in the set.  		
    		myCon.add(bsearchNonRecursive(myCon, item), item); //add item in specified location of where it should be
    		return true;
    	}
    	return false; //set was not modified.
    }
    
 	/**
     * A union operation. Add all items of otherSet that are not already present in this set
     * to this set.
     * @param otherSet != null
     * @return true if this set changed as a result of this operation, false otherwise.
     */
    //O(N)
   public boolean addAll(ISet<E> otherSet) {
		//Pre-Con check
   	if (otherSet == null)
           throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
   	
   	if (otherSet instanceof SortedSet<?>) {
   		int indexThis = 0;
   		int indexOther = 0;
   		SortedSet<E> otherSortedSet = (SortedSet<E>) otherSet;
   		int min = otherSortedSet.size() <= this.size() ? otherSortedSet.size() : this.size();
   		SortedSet<E> result = new SortedSet<E>();
   		boolean changed = false;
   		
   		while (indexThis < min && indexOther < min) {
   			if (myCon.get(indexThis).compareTo(otherSortedSet.myCon.get(indexOther)) == 0) {
   				result.add(myCon.get(indexThis));
   				indexThis++;
   				indexOther++;
   			}
   			else if(myCon.get(indexThis).compareTo(otherSortedSet.myCon.get(indexOther)) > 0) {
   				result.add(otherSortedSet.myCon.get(indexOther));
   				indexOther++;
   			}
   			else if(myCon.get(indexThis).compareTo(otherSortedSet.myCon.get(indexOther)) < 0) {
   				result.add(myCon.get(indexThis));
   				indexThis++;
   			}
   			if (result.size() != 0 && !changed)
   				changed = true;  				
   		}
   		
   		if (min == myCon.size()) {
   			while (indexOther < otherSortedSet.myCon.size()) {
   				result.add(otherSortedSet.myCon.get(indexOther));
   				indexOther++;
   			}
   		}
   		else {
   			while (indexThis < myCon.size()) {
   				result.add(myCon.get(indexThis));
   				indexThis++;
   			}
   		}		
   		
   		this.myCon = result.myCon;
   		return changed; //this set contains all of the elements in other.
   	}
	   else {
		  return super.addAll(otherSet);
	   }
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
     * Return the smallest element in this SortedSet.
     * <br> pre: size() != 0
     * @return the smallest element in this SortedSet.
     */
    //O(1)
    public E min() {
		//Pre-Con check
    	if (myCon.size() == 0)
            throw new IllegalArgumentException("Pre-Con: set is empty");
    	return myCon.get(0); //getting the smallest item.
    }

    /**
     * Return the largest element in this SortedSet.
     * <br> pre: size() != 0
     * @return the largest element in this SortedSet.
     */
    //O(1)
    public E max() {
		//Pre-Con check
    	if (myCon.size() == 0)
            throw new IllegalArgumentException("Pre-Con: set is empty");
    	return myCon.get(myCon.size()-1); //getting the smallest item.
    }
    
    /**
     * Determine if this set is equal to other.
     * Two sets are equal if they have exactly the same elements.
     * The order of the elements does not matter.
     * <br>pre: none
     * @param other the object to compare to this set 
     * @return true if other is a Set and has the same elements as this set
     */
    //O(N)
    public boolean equals(Object other) {
    	//first check if it is an ISet
    	if (other instanceof ISet<?>) {
    		
	    	//safe cast check
	    	if (other instanceof SortedSet<?>) {
	    		SortedSet<?> otherSet = (SortedSet<?>)other;
	    		
	    		//check sizes of sets first
	    		if (this.size() == otherSet.size()) {
	    			//create one iterator
	    			Iterator<E> iterThis = this.iterator();
	    			Iterator<?> iterOther = otherSet.iterator();
	    			
	    			//while still can iterate over sets check if they contain the current item
	    			while (iterThis.hasNext()) {
	    				E tempThis = iterThis.next();
	    				Object otherTemp = iterOther.next();
	    					if(!tempThis.equals(otherTemp))
	    						return false;
	    			}
	    			return true; //gets past the instance of check, the element checks and size check. Thus they are equal.
	    		}
	    			return false; //not the same size.
	    		}
	    	else
    		super.equals(other); //calling the super equals because other is not a sortedSet
    	}
    	return false; //not an ISet
    }
    
	/**
     * Determine if item is in this set. 
     * <br>pre: item != null
     * @param item element whose presence is being tested. Item may not equal null.
     * @return true if this set contains the specified item, false otherwise.
     */
    //O(LogN)
    public boolean contains(E item) {
		//Pre-Con check
    	if (item == null)
            throw new IllegalArgumentException("Pre-Con: item cannot be null");
    	
    	int res = bsearch(myCon, item); //binary search for sorted item
    	return res == -1 ? false : true; //if returns -1, not in set. else return true.
    }
    
	/**
     * Determine if all of the elements of otherSet are in this set.
     * <br> pre: otherSet != null
     * @param otherSet != null
     * @return true if this set contains all of the elements in otherSet, 
     * false otherwise.
     */
    //O(N)
    public boolean containsAll(ISet<E> otherSet) {
    	//calling size must be greater than or u
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	if (this.size() < otherSet.size())
    		return false;
    	
    	if (otherSet instanceof SortedSet<?>) {
    		int thisIndex = 0;
    		int otherIndex = 0;
    		SortedSet<E> otherSorSet = (SortedSet<E>) otherSet;
    		//boolean found = false;
    		
    		while(otherIndex < otherSorSet.myCon.size()) {
    			///if this is bigger stop 
    			//if other is bigger incrament this
    			//if compare to == 0 incrament both;
    			if (myCon.get(thisIndex).compareTo(otherSorSet.myCon.get(otherIndex)) > 0)
    					return false;
    			else if (myCon.get(thisIndex).compareTo(otherSorSet.myCon.get(otherIndex)) < 0) {			
    				thisIndex++;
    			}
    			else {
    				thisIndex++;
    				otherIndex++;
    				
    			}
    		}
    		return true;
    	}
    	else 
    		return super.containsAll(otherSet); //other set was not sorted set
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
    //O(N)
    public ISet<E> intersection(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	SortedSet<E> otherSortedSet; //used for casting.
    	
    	if(!(otherSet instanceof SortedSet<?>))
    		otherSortedSet = new SortedSet<E>(otherSet); //O(nlogn)
    	else 
    		otherSortedSet = (SortedSet<E>)otherSet;
   	
    	ISet<E> result = new SortedSet<E>();
    	int thisIndex = 0;
		int otherIndex = 0;
		
		while(otherIndex < otherSortedSet.myCon.size() && thisIndex < myCon.size()) {
			if (myCon.get(thisIndex).compareTo(otherSortedSet.myCon.get(otherIndex)) > 0) {
				otherIndex++;
			}
					
			else if (myCon.get(thisIndex).compareTo(otherSortedSet.myCon.get(otherIndex)) < 0) {			
				thisIndex++;
			}
			else {
				result.add(myCon.get(thisIndex));
				thisIndex++;
				otherIndex++;
				
			}
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
    //O(N)
    public ISet<E> union(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	
    	
    	ISet<E> result = new SortedSet<E>();
    	result.addAll(this);
    	result.addAll(otherSet);
    	return result;
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
    //O(N)
    public ISet<E> difference(ISet<E> otherSet) {
		//Pre-Con check
    	if (otherSet == null)
            throw new IllegalArgumentException("Pre-Con: otherSet cannot be null");
    	
    	if (otherSet.size() == 0)
    		return this;
    	
    	SortedSet<E> otherSortedSet; //used for casting.
    	
    	if(!(otherSet instanceof SortedSet<?>))
    		otherSortedSet = new SortedSet<E>(otherSet); //O(nlogn)
    	else 
    		otherSortedSet = (SortedSet<E>)otherSet;
   	
    	SortedSet<E> result = new SortedSet<E>();
    	int thisIndex = 0;
		int otherIndex = 0;
		
		while(otherIndex < otherSortedSet.myCon.size() && thisIndex < myCon.size()) {
			if (myCon.get(thisIndex).compareTo(otherSortedSet.myCon.get(otherIndex)) > 0) {
				otherIndex++;
			}
					
			else if (myCon.get(thisIndex).compareTo(otherSortedSet.myCon.get(otherIndex)) < 0) {	
				result.add(myCon.get(thisIndex));
				thisIndex++;
			}
			else {
				thisIndex++;
				otherIndex++;
				
			}
		}
		while(thisIndex < myCon.size()) {
			result.add(myCon.get(thisIndex));
			thisIndex++;
		}
    	return result;
    }
    
    //mergeSort taken from the class slides
    private void mergeSort(ArrayList<E> set) {
    	ArrayList<E> result = new ArrayList<E>();
    	sort(set, result, 0, set.size()-1);
    }
	private void sort(ArrayList<E> set, ArrayList<E> result, int low, int high) {
		if (low < high) {
			int center = (low + high) / 2;
			sort(set, result, low, center);
			sort(set, result, center + 1, high);
			merge(set, result, low, center +1, high);
		}
	}
	private void merge(ArrayList<E>set, ArrayList<E> result, int left, int right, int rightEnd) {
		int leftEnd = right - 1;
		int tempPos = left;
		int numElements = rightEnd - left + 1;
		//main loop
		while( left <= leftEnd && right <= rightEnd){
			if( set.get(left).compareTo(set.get(right)) <= 0) {
				result.set(tempPos, set.get(left));
				left++;
			}
			else{
				result.set(tempPos, set.get(right));
				right++;
			}
			tempPos++;
		}
		//copy rest of left half
		while( left <= leftEnd){
			result.set(tempPos, set.get(left));
			tempPos++;
			left++;			                      
		}
		//copy rest of right half
		while( right <= rightEnd){
			result.set(tempPos, set.get(right));
			tempPos++;
			right++;			                      
		}
		//Copy temp back into list
		for(int i = 0; i < numElements; i++, rightEnd--)
			set.set(rightEnd, result.get(rightEnd));
	}
		
		//binary search algo from class slides
		private int bsearch(ArrayList<E> data, E target) {
			return bsearch(data, target, 0, data.size()-1);
		}
		
		private int bsearch(ArrayList<E> data, E target,
		int low, int high) {
		if( low <= high){
		int mid = low + ((high - low) / 2);
		if( data.get(mid) == target )
		return mid;
		else if(data.get(mid).compareTo(target) > 0)
		return bsearch(data, target, low, mid - 1);
		else
		return bsearch(data, target, mid + 1, high);
		}
		return -1;
		}
		

		//binary search algo from class 
		private <T extends Comparable<? super T>> int bsearchNonRecursive(ArrayList<E> data, E target) {
		int result = -1;
		int low = 0;
		int high = data.size() - 1;
		while( result == -1 && low <= high ) {
		int mid = low + ((high - low) / 2);
		int compareResult = target.compareTo(data.get(mid));
		if(compareResult == 0)
		result = mid;
		else if(compareResult > 0)
		low = mid + 1;
		else
		high = mid - 1; // compareResult < 0
		}
		result = low;
		return result;
		}


}

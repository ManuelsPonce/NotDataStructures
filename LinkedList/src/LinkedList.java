/*  Student information for assignment:
 *
 *  On my honor, MAnuel Ponce, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID:mip445
 *  email address: maniponce22@gmail.com	 
 *  Grader name: Lucas
 *  Number of slip days I am using: 0
 */

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedList<E> implements IList<E> {
    // CS314 student. Add you instance variables here.
    // You decide what instance variables to use.
    // Must adhere to assignment requirements. No ArrayLists or Java LinkedLists.
	
	//CONSTANTS
	private static final int HALF_LIST = 2; //used to divide the list in half for some methods.
	
	//instance variables 
	private DoubleListNode<E> headerNode; //HEADER CIRCULAR LINKEDLIST APPROACH
	private int size; //size of list
	
	//constructor
	public LinkedList() {
		headerNode = new DoubleListNode<>(); //creating the new header node
		headerNode.setNext(headerNode); //header node is first
		headerNode.setPrev(headerNode); //header node is also last
	}

    /**
     * add item to the front of the list.
     * <br>pre: item != null
     * <br>post: size() = old size() + 1, get(0) = item
     * @param item the data to add to the front of this list
     * 
     */
	//BIG O for addFrist: O(1)
    public void addFirst(E item){
    	//Checking pre-con
    	if (item == null)
            throw new IllegalArgumentException("item cannot be null");
    	
    	DoubleListNode<E> newNode = new DoubleListNode<E>(headerNode, item, headerNode.getNext()); //creating the new node that will be added.
    	headerNode.getNext().setPrev(newNode); //Old first nodes prev ref is pointing to the new first node
    	headerNode.setNext(newNode); //headers next ref now points to new node
       	size++; //added to list, thus increase size 
    }


    /**
     * add item to the end of the list.
     * <br>pre: item != null
     * <br>post: size() = old size() + 1, get(size() -1) = item
     * @param item the data to add to the end of this list
     */
	//BIG O for addLast: O(1)
    public void addLast(E item){
    	//Checking pre-con
    	if (item == null)
            throw new IllegalArgumentException("item cannot be null");
    	
    	DoubleListNode<E> newNode = new DoubleListNode<E>(headerNode.getPrev(), item, headerNode); //creating the new node that will be added.
    	headerNode.getPrev().setNext(newNode); //setting the old last node to point to the new last node
       	headerNode.setPrev(newNode); //headers next ref now points to new node
    	size++; //added to list, thus increase size
    }


    /**
     * remove and return the first element of this list.
     * <br>pre: size() > 0
     * <br>post: size() = old size() - 1
     * @return the old first element of this list
     */
	//BIG O for removeFirst: O(1)
    public E removeFirst(){
      	//Checking pre-con
    	if (size <= 0)
            throw new IllegalArgumentException("cannot remove from an empty list");
    	
    	E tempData = headerNode.getNext().getData(); //keep the old first data
    	//if list only contains one item, just make first and last null & size equals 0
    	if (size == 1) {
    		makeEmpty(); //if size of list is 1, just set prev and next ref of header node to null & set size equal to zero
    	}
    	else { 
    		headerNode.getNext().getNext().setPrev(headerNode); //the new first node prev ref now points to header
    		headerNode.setNext(headerNode.getNext().getNext()); //the header node now points to the node after the first node
    		size--; //removing an element of list decreases the size
    	}   	
        return tempData; //returns the old firsts data
    }


    /**
     * remove and return the last element of this list.
     * <br>pre: size() > 0
     * <br>post: size() = old size() - 1
     * @return the old last element of this list
     */
	//BIG O for removeLast: O(1)
    public E removeLast(){
      	//Checking pre-con
    	if (size <= 0)
            throw new IllegalArgumentException("cannot remove from an empty list");
    	
    	E tempData = headerNode.getPrev().getData(); //keep the old first data
    	//if list only contains one item, just make first and last equal null & size equals 0
    	if (size == 1) { 
    		makeEmpty();
    	}
    	else { 
    		headerNode.getPrev().getPrev().setNext(headerNode); //the new last node next ref now points to header
    		headerNode.setPrev(headerNode.getPrev().getPrev()); //the header node now points to the node after the deleted last node
    		size--; 
    	}
    	
        return tempData; //returns the old firsts data
    }


	@Override
    /**
     * Add an item to the end of this list.
     * <br>pre: item != null
     * <br>post: size() = old size() + 1, get(size() - 1) = item
     * @param item the data to be added to the end of this list, item != null
     */
	//BIG O for add: O(1)
	public void add(E item) {
      	//Checking pre-con
    	if (item == null)
            throw new IllegalArgumentException("item cannot be null");
    	
		addLast(item); //adding to the end of the list the passed in item
	}


	@Override
    /**
     * Insert an item at a specified position in the list.
     * <br>pre: 0 <= pos <= size(), item != null
     * <br>post: size() = old size() + 1, get(pos) = item, all elements in
     * the list with a positon >= pos have a position = old position + 1
     * @param pos the position to insert the data at in the list
     * @param item the data to add to the list, item != null
    */
	//BIG O for insert: O(N)
	public void insert(int pos, E item) {
      	//Checking pre-con
    	if ((pos < 0 || pos > size()) || item == null)
            throw new IllegalArgumentException("position is not in list or item cannot be null");
		
		if (pos == 0)
			this.addFirst(item); //if trying to add at position 1, then add to the front of the list
		else if (pos == size())
			this.addLast(item); // else if trying to add at position size-1, then add to the end of the list
		else {
			DoubleListNode<E> temp = getNodeAtPos(pos); //new temp node used to preserve the item at the position being inserted at
			DoubleListNode<E> newNode = new DoubleListNode<>(temp.getPrev(), item, temp); //new node being inserted
			temp.getPrev().setNext(newNode); //setting the previous node of pos node to point to new node
			temp.setPrev(newNode); //set new node inserted to point to previous node
			size++; //added to the list
			}
		}

	@Override
    /**
     * Change the data at the specified position in the list.
     * the old data at that position is returned.
     * <br>pre: 0 <= pos < size(), item != null
     * <br>post: get(pos) = item, return the
     * old get(pos)
     * @param pos the position in the list to overwrite  
     * @param item the new item that will overwrite the old item, item != null
     * @return the old data at the specified position
     */
	//BIG O for set: O(N)
	public E set(int pos, E item) {
      	//Checking pre-con
    	if (pos < 0 || pos >= size() || item == null)
            throw new IllegalArgumentException("position is not in list or item cannot be null");
    	
    	DoubleListNode<E> temp = getNodeAtPos(pos); //temp node of the position passed
    	E tempData = temp.getData(); //keep the old data that will be replaced
    	temp.setData(item); //setting the new data to that node
    	
		return tempData; //return old data
	}


	@Override
	/**
	 * Remove an element in the list based on position.
	 * <br>pre: 0 <= pos < size()
	 * <br>post: size() = old size() - 1, all elements of
	 * list with a position > pos have a position = old position - 1
	 * @param pos the position of the element to remove from the list
	 * @return the data at position pos
	 */
	//BIG O for remove(pos): O(N)
	public E remove(int pos) {
	  	//Checking pre-con
		if (pos < 0 || pos > size-1)
	        throw new IllegalArgumentException("position is not in list");
		
		DoubleListNode<E> temp = getNodeAtPos(pos); //the temp node that will be removed
		 E tempData = temp.getData(); //keep the old data that will be replaced
		 temp.getPrev().setNext(temp.getNext()); //setting the node previous to the one being removed to point to the node after the one being removed
		 temp.getNext().setPrev(temp.getPrev()); //setting node after the removed one to point to he node prev to one being removed
		
		 size--; //size decreased b/c removed a node
		 
		 return tempData; //nodes data that was removed
	}

	@Override
    /**
     * Get an element from the list.
     * <br>pre: 0 <= pos < size()
     * <br>post: return the item at pos
     * @param pos specifies which element to get
     * @return the element at the specified position in the list
     */
	//BIG O for addFrist: O(N)
	public E get(int pos) {
      	//Checking pre-con
    	if (pos < 0 || pos >= size)
            throw new IllegalArgumentException("position is not in list");
    	
		return getNodeAtPos(pos).getData(); //gets the data for a node at a data calling a private helper method
	}


	@Override
    /**
     * Remove the first occurrence of obj in this list.
     * Return <tt>true</tt> if this list changed as a result of this call, <tt>false</tt> otherwise.
     * <br>pre: obj != null
     * <br>post: if obj is in this list the first occurrence has been removed and size() = old size() - 1. 
     * If obj is not present the list is not altered in any way.
     * @param obj The item to remove from this list. obj != null
     * @return Return <tt>true</tt> if this list changed as a result of this call, <tt>false</tt> otherwise.
     */
	//BIG O for remove(obejct): O(N)
	public boolean remove(E obj) {
      	//Checking pre-con
    	if (obj == null)
            throw new IllegalArgumentException("Items cannot be null");
    	
    	DoubleListNode<E> temp = headerNode; //temp node
		for (int i = 0; i < size(); i++) {
			temp = temp.getNext(); //becoming first node
			if (temp.getData().equals(obj)) {
				temp.getPrev().setNext(temp.getNext()); //updating the prev nodes next pointer
				temp.getNext().setPrev(temp.getPrev()); //updating the nexts nodes prev pointer
				size--;
				return true;
			}
		}
		return false; //return false b/c item was not in list
	}


	@Override
    /**
     * Return a sublist of elements in this list from <tt>start</tt> inclusive to <tt>stop</tt> exclusive.
     * This list is not changed as a result of this call.
     * <br>pre: <tt>0 <= start <= size(), start <= stop <= size()</tt>
     * <br>post: return a list whose size is stop - start and contains the elements at positions start through stop - 1 in this list.
     * @param start index of the first element of the sublist.
     * @param stop stop - 1 is the index of the last element of the sublist.
     * @return a list with <tt>stop - start</tt> elements, The elements are from positions <tt>start</tt> inclusive to
     * <tt>stop</tt> exclusive in this list. If start == stop an empty list is returned.
     */
	//BIG O for Sublist: O(N)
	public IList<E> getSubList(int start, int stop) {
      	//Checking pre-con
    	if (start > stop || start < 0 || stop > size())
            throw new IllegalArgumentException("Stop and Start must be positions in the list. Start cannot be greater that stop");
    	
    	IList<E> subList = new LinkedList<>(); //the subList being created
    	DoubleListNode<E> temp = getNodeAtPos(start); //temp Node
    	for (int i = start; i < stop; i++) { //looping through start to stop
    		subList.add(temp.getData()); //adding elements to the subList
    		temp = temp.getNext();
    	}   	
		return subList;
	}


	@Override
    /**
     * Return the size of this list. In other words the number of elements in this list.
     * <br>pre: none
     * <br>post: return the number of items in this list
     * @return the number of items in this list
     */
	//BIG O for size: O(1)
	public int size() {
		return size; //returns size
	}


	@Override
    /**
     * Find the position of an element in the list.
     * <br>pre: item != null
     * <br>post: return the index of the first element equal to item
     * or -1 if item is not present
     * @param item the element to search for in the list. item != null
     * @return return the index of the first element equal to item or a -1 if item is not present
     */
	//BIG O for indexOf(Item): O(N)
	public int indexOf(E item) {
		return indexOf(item, 0); //calls indexOf based on position. but in this case its zero.
	}

	@Override
    /**
     * find the position of an element in the list starting at a specified position.
     * <br>pre: 0 <= pos < size(), item != null
     * <br>post: return the index of the first element equal to item starting at pos
     * or -1 if item is not present from position pos onward
     * @param item the element to search for in the list. Item != null
     * @param pos the position in the list to start searching from
     * @return starting from the specified position return the index of the first element equal to item or a -1 if item is not present between pos and the end of the list
     */
	//BIG O for indexOf(item, pos): O(N)
	public int indexOf(E item, int pos) {
      	//Checking pre-con
    	if (item == null || pos >= size || pos < 0)
            throw new IllegalArgumentException("Items cannot be null and position must be in list");
    	
		DoubleListNode<E> temp = getNodeAtPos(pos);//temp start node starts at node at given pos
		int index = pos; //start index at starting pos
		while (index < size) {
			if (temp.getData().equals(item))
				return index; //return the index if the data matches the item wanted
			temp = temp.getNext(); //else update to next node
			index++;
		}
    	return -1; //else element isnt in the list
	}


	@Override
    /**
     * return the list to an empty state.
     * <br>pre: none
     * <br>post: size() = 0
     */
	//BIG O for makeEmpty: O(1)
	public void makeEmpty() {
		//set the header node to point to itself and make size zero
		headerNode.setPrev(headerNode);
		headerNode.setNext(headerNode);
		size = 0;
	}


	@Override
    /**
    * return an Iterator for this list.
    * <br>pre: none
    * <br>post: return an Iterator object for this List
    */
	//BIG O for Iterator: O(1)
	public Iterator<E> iterator() {
		return new LinkedListIter(); //returns a new iterator from the inner iterator class.
	}


	@Override
    /**
     * Remove all elements in this list from <tt>start</tt> inclusive to <tt>stop</tt> exclusive.
     * <br>pre: <tt>0 <= start < size(), start <= stop <= size()</tt>
     * <br>post: <tt>size() = old size() - (stop - start)</tt>
     * @param start position at beginning of range of elements to be removed
     * @param stop stop - 1 is the position at the end of the range of elements to be removed
     */
	//BIG O for removeRange: O(N)
	public void removeRange(int start, int stop) {
      	//Checking pre-con
    	if (start > stop || start < 0 || stop > size())
            throw new IllegalArgumentException("Stop and Start must be positions in the list. Start cannot be greater that stop");
    	
    	int count = 0;
    	DoubleListNode<E> prevStart = headerNode; //used to store the node of the position start
    	//finding the previous node to starting pos stated (Starting is Inclusive)
    	while(count < start) {
      		prevStart = prevStart.getNext();
      		count++;
    	}
    	DoubleListNode<E> tempEnd = prevStart; //used to store stop node (stopping is exclusive)
    	//while starting at the starting node, keep going until you reach the stop node
		while(count <= stop) {
			tempEnd = tempEnd.getNext();
			count++;
		}
		prevStart.setNext(tempEnd); //setting the previous node to start to point to the stop node
		tempEnd.setPrev(prevStart); //setting the stop node to point to the previous node from start
		size = size - (stop-start); //update size based on the range removed
	}
	
	@Override
    /**
     * Return a String version of this list enclosed in
     * square brackets, []. Elements are in
     * are in order based on position in the 
     * list with the first element
     * first. Adjacent elements are separated by comma's
     * @return a String representation of this IList
     */
	//BIG O for itoString: O(N)
	public String toString() {
		
		StringBuilder string = new StringBuilder(); //string of list to be built
		string.append("["); //adding opening square brace
		int count = 0;
		
		DoubleListNode<E> tempNode = headerNode.getNext(); //setting temp to the first node with data
		while (count < size) {
			if (count == size-1)
				string.append(tempNode.getData()); //special case that its the last node and we dont want a "," and space after that
			else { 
				string.append(tempNode.getData() + ", "); //else just add the data + a "," and space
			}
			tempNode = tempNode.getNext(); //update current node to the next node
			count++;
		}
		string.append("]"); //adding closing square bracket
		return string.toString();
	}
	
	@Override
    /**
     * Determine if this IList is equal to other. Two
     * ILists are equal if they contain the same elements
     * in the same order.
     * @return true if this IList is equal to other, false otherwise
     */
	//BIG O for equals: O(N)
	public boolean equals(Object other) {
		if (other == null) 
			return false;
		else {
			if (other instanceof IList) { //making sure they are instances of each other in order to be equal
				IList<?> otherList = (IList<?>)other; //castin is safe because they passed the casting if statement
				if (this.size() != otherList.size())  {//if sizes dont match then list dont match
					return false;
				}
				else { //go through the both list and compare elements linearly
					Iterator<?> thisIter = this.iterator(); //creating iters to go through the lists
					Iterator<?> otherIter = otherList.iterator();
	
					while(thisIter.hasNext()) { //while you can still go through the lists
						if (!thisIter.next().equals(otherIter.next()))
								return false; //they did not match
					}	
				}
				return true; // passed the size check and the element check, thus they are equal
			}
			return false; //failed the instance of check
		}
	}
	
	//Helper method to get a node a spacific position. 
	// 	//BIG O for getNodeAtPos: O(N)
	private DoubleListNode<E> getNodeAtPos(int pos) {   	
		DoubleListNode<E> node; //temp node used as the return node
		if (pos <= size/HALF_LIST) { //checks to see if its faster to start from the front of the list or the back
			node = headerNode.getNext(); //starts at the first node after the header node (front)
			for (int i = 0; i < pos; i++) { //loop through each node until reaching desired node
				node = node.getNext();
			}
		}
			else {
				node = headerNode.getPrev(); // (End) starts from the last node in the list and go backwards in the loop until desired node found
				for (int i = size-1; i > pos; i--) {
					node = node.getPrev();
				}
			}			
		return node;
	}
	
	//*****************INNER CLASS************************//
	//****************************************************//
	private class LinkedListIter implements Iterator<E> {
		
		//instance variables
		private boolean nextCheck; //makes sure hasNext has been called before remove. 
		private DoubleListNode<E> currentNode;
		
		//Constructor
		public LinkedListIter() {
			nextCheck = false;
			currentNode = headerNode; //starts the iterator on the header node
		}

		@Override
		//BIG O for hasNext: O(1)
		public boolean hasNext() {
			return currentNode.getNext() != headerNode; 
		}

		@Override
		//moves the iterator over the list returning the item behind the iterator
		//BIG O for next: O(1)
		public E next() {
			//checking pre-con
			if (!hasNext()) 
				throw new NoSuchElementException();
			
            E result = currentNode.getNext().getData(); //get data of previous node
            nextCheck = true; //okay to remove now
            currentNode = currentNode.getNext(); //update to next node
            return result;
		}
		
		@Override
		//removes the item the iterator has just passed
		//BIG O for remove: O(1)
		public void remove() {
			//check pre-con
            if (!nextCheck) {
                throw new IllegalStateException();
            }
            nextCheck = false; //not okay to remove after you have removed
            currentNode.getPrev().setNext(currentNode.getNext()); //setting previous node point to node after current
            currentNode.getNext().setPrev(currentNode.getPrev()); //setting next node point to node previous to current
           currentNode = currentNode.getPrev(); //updating where the iterator is at
           size--; //removed from the list
        }			
	}
}
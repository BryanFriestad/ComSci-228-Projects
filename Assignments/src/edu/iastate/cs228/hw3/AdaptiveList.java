package edu.iastate.cs228.hw3;
/*
 *  @author Bryan Friestad
 *
 *  An implementation of List<E> based on a doubly-linked list with an array for indexed reads/writes
 *
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * This is an adaptive, doubly-linked list that is backed by an array for implementations
 * of some functions. head and tail do not hold data, they are just placeholder nodes for easy access
 * to the front and back of the list. The first node to be added between head and tail is the first
 * item in the list and it is at position/index 0. The list is empty if tail.prev == head &&
 * head.link == tail, meaning that there is nothing in between the two placeholders. The index of 
 * the final node in the list is numItems - 1 or size() - 1.
 * 
 * @author Bryan Friestad
 * 
 *
 * @param <E>
 */
public class AdaptiveList<E> implements List<E>

{
	/**
	 * @author Bryan Friestad
	 * This represents a node in the AdaptiveList.
	 * The Adaptive list has three public fields:
	 * <ul>
	 *   <li> E data</li>
	 *   <li> ListNode link</li>
	 *   <li> ListNode prev</li>
	 * </ul>
	 */
	protected class ListNode // private member of outer class
	{                     
		public E data;        // public members:
		public ListNode link; // used outside the inner class
		public ListNode prev; // used outside the inner class
    
		/**
		 * This is the constructor for the ListNode class.
		 * It takes in a generic parameter called item to represent
		 * the data of the object and then sets the fields link and
		 * prev to null.
		 * 
		 * @param item
		 */
		public ListNode(E item)
		{
			data = item;
			link = prev = null;
		}
	}
  
	public ListNode head;  // dummy node made public for testing.
	public ListNode tail;  // dummy node made public for testing.
	private int numItems;  // number of data items
	private boolean linkedUTD; // true if the linked list is up-to-date.

	public E[] theArray;  // the array for storing elements
	private boolean arrayUTD; // true if the array is up-to-date.

	public AdaptiveList()
	{
		clear();
	}

	@Override
	public void clear()
	{
		head = new ListNode(null);
		tail = new ListNode(null);
		head.link = tail;
		tail.prev = head;
		numItems = 0;
		linkedUTD = true;
		arrayUTD = false;
		theArray = null;
	}

	/**
	 * This function returns the value of the private variable linkedUTD
	 * @return True or False
	 */
	public boolean getlinkedUTD()
	{
		return linkedUTD;
	}
	
	/**
	 * This function returns the value of the private variable arrayUTD
	 * @return True or False
	 */
	public boolean getarrayUTD()
	{
		return arrayUTD;
	}
	

  	public AdaptiveList(Collection<? extends E> c)
  	{
  		this();
  		addAll(c);
  	}

  	// Removes the node from the linked list.
  	// This method should be used to remove a node from the linked list.
  	private void unlink(ListNode toRemove)
  	{
  		if ( toRemove == head || toRemove == tail )
  			throw new RuntimeException("An attempt to remove head or tail");
  		toRemove.prev.link = toRemove.link;
  		toRemove.link.prev = toRemove.prev;
  	}

  	// Inserts new node toAdd right after old node current.
  	// This method should be used to add a node to the linked list.
  	private void link(ListNode current, ListNode toAdd)
  	{
  		if ( current == tail )
  			throw new RuntimeException("An attempt to link after tail");
  		if ( toAdd == head || toAdd == tail )
  			throw new RuntimeException("An attempt to add head/tail as a new node");
  		toAdd.link = current.link;
  		toAdd.link.prev = toAdd;
  		toAdd.prev = current;
  		current.link = toAdd;
  	}

  	private void updateArray() // makes theArray up-to-date.
  	{
  		if(arrayUTD){
  			return; //return right away if the array is already UTD
  		}
  		if ( numItems < 0 )
  			throw new RuntimeException("numItems is negative: " + numItems);
  		if ( ! linkedUTD )
  			throw new RuntimeException("linkedUTD is false");
  		
  		theArray = (E[]) new Object[numItems]; //I know java doesn't like this, but I felt like any other way was improper
  		ListNode cur = head;
  		for(int i = 0; i < theArray.length; i++){
  			cur = cur.link;
  			theArray[i] = cur.data;
  		}
  		arrayUTD = true;
  	}

  	private void updateLinked() // makes the linked list up-to-date.
  	{
  		if(linkedUTD){
  			return; //return right away if the linked list is already UTD
  		}
  		if ( numItems < 0 )
  			throw new RuntimeException("numItems is negative: " + numItems);
  		if ( ! arrayUTD )
  			throw new RuntimeException("arrayUTD is false");

  		if ( theArray == null || theArray.length < numItems )
  			throw new RuntimeException("theArray is null or shorter");

  		//There should never be a different number of indices in the array
  		//than there are in the linked list because the only two functions to use
  		//the array are set and get, neither of which change the number of nodes
  		int i = 0;
  		for(ListNode temp = head.link; temp.link != null; temp = temp.link, i++){
  			temp.data = theArray[i];
  		}
  		linkedUTD = true;
  	}

  	@Override
  	public int size()
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		return numItems; // may need to be revised.
  	}

  	@Override
  	public boolean isEmpty()
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		if(head.link == tail && tail.prev == head) return true;
  		return false;
  	}

  	@Override
  	public boolean add(E obj)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		ListNode toAdd = new ListNode(obj);
  		link(tail.prev, toAdd);
  		numItems++;
  		arrayUTD = false;
  		return true;
  	}

  	@Override
  	public boolean addAll(Collection< ? extends E> c)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		if(c.isEmpty()) return false;
  		for(E e : c){
  			add(e);
  		}
  		arrayUTD = false;
  		return true;
  	} // addAll 1

  	@Override
  	public boolean remove(Object obj)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		if(isEmpty()) return false;
  		ListNode temp;
  		boolean found = false;
  		for(temp = head.link; temp != tail; temp = temp.link){
  			if(obj == temp.data || (obj != null && obj.equals(temp.data))){
  				found = true;
  				break;
  			}
  		}
  		if(!found){
  			return false; //made it to the end and did not find
  		}
  		else{
  			unlink(temp);
  		}
  		numItems--;
  		arrayUTD = false;
  		return true;
  	}

  	private void checkIndex(int pos) // a helper method
  	{
  		if ( pos >= numItems || pos < 0 )
  			throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + numItems);
  	}

  	private void checkIndex2(int pos) // a helper method for the ListIterator
  	{
  		if ( pos > numItems || pos < 0 )
  			throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + numItems);
  	}

  	private void checkNode(ListNode cur) // a helper method
  	{
  		if ( cur == null || cur == tail )
  			throw new RuntimeException("numItems: " + numItems + " is too large");
  	}

  	/**
  	 * Returns the ListNode that is at passed index in the list. The list starts at index 0 and ends at size - 1.
  	 * 
  	 * @param pos
  	 * @return The ListNode at index pos in the list
  	 */
  	private ListNode findNode(int pos)   // a helper method
  	{
  		ListNode cur = head.link;
  		for ( int i = 0; i < pos; i++ )
  		{
  			checkNode(cur);
  			cur = cur.link;
  		}
  		checkNode(cur);
  		return cur;
  	}

  	@Override
  	public void add(int pos, E obj)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		checkIndex2(pos);
  		if(pos == numItems){
  			add(obj); //adds to the end if the passed position denoted end placement
  		}
  		link(findNode(pos).prev, new ListNode(obj)); //adds the new ListNode right after the node previous to the one at "pos", effectively taking it's place and moving everything else right
  		numItems++;
  		arrayUTD = false;
  	}

  	@Override
  	public boolean addAll(int pos, Collection< ? extends E> c)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		if(c.isEmpty()) return false;
  		int i = pos;
  		for(E e : c){
  			add(i++, e);
  		}
  		arrayUTD = false;
  		return true;
  	} // addAll 2

  	@Override
  	public E remove(int pos)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		ListNode toRemove = findNode(pos);
  		unlink(toRemove);
  		numItems--;
  		arrayUTD = false;
  		return toRemove.data;
  	}

  	@Override
  	public E get(int pos)
  	{
  		if(!arrayUTD){
  			updateArray();
  		}
  		checkIndex(pos);
  		return theArray[pos];
  	}//needs to use the array

  	@Override
  	public E set(int pos, E obj)
  	{
  		if(!arrayUTD){
  			updateArray();
  		}
  		checkIndex(pos);
  		theArray[pos] = obj;
  		linkedUTD = false;
  		return theArray[pos];
  	}//needs to use the array

  	@Override
  	public boolean contains(Object obj)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		ListNode temp = head;
  		boolean found = false;
  		while(!found){
  			temp = temp.link;
  			if(temp == null || temp == tail) break;
  			found = (obj == temp.data || (obj != null && obj.equals(temp.data)));
  		}
  		return found;
  	}

  	@Override
  	public boolean containsAll(Collection< ? > c)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		for(Object o : c){
  			if(!contains(o)){
  				return false;
  			}
  		}
  		return true;
  	} // containsAll


  	@Override
  	public int indexOf(Object obj)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		
  		ListNode temp = head;
  		int i;
  		for(i = 0, temp = temp.link; temp != tail; temp = temp.link, i++){
  			if(obj == temp.data || (obj != null && obj.equals(temp.data))) return i;
  		}
  		return -1;
  	}

  	@Override
  	public int lastIndexOf(Object obj)
  	{
  		if(!linkedUTD){
  			updateLinked();
  		}
  		
  		ListNode temp = tail;
  		int i;
  		for(i = numItems - 1, temp = temp.prev; temp != head; temp = temp.prev, i--){
  			if(obj == temp.data || (obj != null && obj.equals(temp.data))) return i;
  		}
  		return -1;
  	}

  	@Override
  	public boolean removeAll(Collection<?> c)
  	{
  		if(c == null){
  			throw new NullPointerException();
  		}
  		if(c.isEmpty()) {
  			return false;
  		}
  		boolean isChanged = false;
  		for(Object o : c){
  			while(contains(o)){
	  			if(remove(o)){
	  				isChanged = true;
	  			}
  			}
  		}
  		return isChanged;
  	}

  	@Override
  	public boolean retainAll(Collection<?> c)
  	{
  		if(c == null) throw new NullPointerException();
  		boolean changed = false;
  		for(ListNode temp = head.link; temp != tail; temp = temp.link){
  			if(!c.contains(temp.data)){
  				unlink(temp);
  				numItems--;
  				changed = true;
  			}
  		}
  		return changed;
  	}

  	@Override
  	public Object[] toArray()
  	{
  		if(!arrayUTD) updateArray();
  		Object[] temp = new Object[numItems];
  		for(int i = 0; i < numItems; i++){
  			temp[i] = (Object) theArray[i];
  		}
  		return temp;
  	}
  
  	@Override
  	public <T> T[] toArray(T[] arr)
  	{
  		if(arr == null) throw new NullPointerException();
  		if(arr.length < numItems){
  	       arr = Arrays.copyOf(arr, numItems);
  		}
  		for(int i = 0; i < numItems; i++){
  			arr[i] = (T) theArray[i]; //this doesn't check to make sure that T is a supertype of E, but idk how to check
  			//I tried using instance of, but that doesn't work for Generics
  		}
  		if(numItems < arr.length){
  			arr[numItems] = null;
  		}
  		return arr;
  	}

  	/**
  	 * This function throws a UnsupportedOperationException as it is not 
  	 * a valid function for this data structure.
  	 * 
  	 * @throws UnsupportedOperationException();
  	 */
  	@Override
  	public List<E> subList(int fromPos, int toPos)
  	{
  		throw new UnsupportedOperationException();
  	}

  	private class AdaptiveListIterator implements ListIterator<E>
  	{
  		private int    index;  // index of next node;
  		private ListNode cur;  // node at index - 1 or the item behind the cursor
  		private ListNode last; // node last visited by next() or previous()

  		public AdaptiveListIterator()
  		{
  			if (!linkedUTD) updateLinked();
  			cur = null;
  			last = null;
  			index = 0;
  		}
  		
  		public AdaptiveListIterator(int pos)
  		{
  			if (!linkedUTD) updateLinked();
  			checkIndex2(pos);
  			index = pos; //pos is the starting cursor position. the index of the element before it is pos - 1
  			last = null;
  			if(index == 0){
  				cur = null;
  			}
  			else{
  				cur = findNode(pos - 1);
  			}
  		}

  		@Override
  		public boolean hasNext()
  		{
  			if(cur == null){
  				return !isEmpty();
  			}
  			else{
  				return (cur.link != tail);
  			}
  		}

  		@Override
  		public E next()
  		{
  			if(!hasNext()) throw new NoSuchElementException();
  			if(index > size()) throw new RuntimeException("index: " + index + " is off"); //if a list has n items, then the cursor indices are (0, ..., n)
  			if(cur == null){
  				last = cur = head.link;
  				index++;
  			}
  			else{
  				last = cur = cur.link;
  				index++;
  			}
  			return cur.data;
  		} 

  		@Override
  		public boolean hasPrevious()
  		{
  			if(cur == null) return false;
  			else return cur != head;
  		}

  		@Override
  		public E previous()
  		{
  			if(!hasPrevious()) throw new NoSuchElementException();
  			if(index < 1) throw new RuntimeException("index " + index + " is not correct");
  			last = cur;
  			cur = (cur.prev == head ? null : cur.prev); //sets cur equal to null if it's the first element in the list
  			index--;
  			return last.data; //returns the data of the node that the cursor just moved over
  		}
    
  		@Override
  		public int nextIndex()
  		{
  			return index; //index is ONE MORE than the "list index" of the item behind it, so the "list index" of next() would just be index
  		}

  		@Override
  		public int previousIndex()
  		{
  			return index - 1; //index is ONE MORE than the "list index" of the item behind it
  		}

  		public void remove()
  		{
  			if(last == null) throw new IllegalStateException("Neither previous() nor next() have been called OR add()/remove() has been called since then");
  			checkNode(last);
  			if(last == cur){ //meaning there was a call to next rather than previous (last is behind cursor)
  				index--;
  				cur = (cur.prev == head ? null : cur.prev);
  			}
  			unlink(last);
  			numItems--;
  			last = null;
  		}

  		public void add(E obj)
  		{ 
  			ListNode toAdd = new ListNode(obj);
  			if(cur == null){
  				link(head, toAdd);
  				cur = head.link;
  			}
  			else{
  				link(cur, toAdd);
  				cur = cur.link;
  			}
  			index++;
  			numItems++;
  			last = null;
  		} // add

  		@Override
  		public void set(E obj)
  		{
  			if(last == null) throw new IllegalStateException("Neither previous() nor next() have been called OR add()/remove() has been called since then");
  			checkNode(last);
  			last.data = obj;
  		} // set
  	} // AdaptiveListIterator
  
  	@Override
  	public boolean equals(Object obj)
  	{
  		if ( ! linkedUTD ) updateLinked();
  		if ( (obj == null) || ! ( obj instanceof List<?> ) )
  			return false;
  		List<?> list = (List<?>) obj;
  		if ( list.size() != numItems ) return false;
  		Iterator<?> iter = list.iterator();
  		for ( ListNode tmp = head.link; tmp != tail; tmp = tmp.link )
  		{
  			if ( ! iter.hasNext() ) return false;
  			Object t = iter.next();
  			if ( ! (t == tmp.data || t != null && t.equals(tmp.data) ) )
  				return false;
  		}
  		if ( iter.hasNext() ) return false;
  		return true;
  	} // equals

  	@Override
  	public Iterator<E> iterator()
  	{
  		return new AdaptiveListIterator();
  	}

  	@Override
  	public ListIterator<E> listIterator()
  	{
  		return new AdaptiveListIterator();
  	}

  	@Override
  	public ListIterator<E> listIterator(int pos)
  	{
  		checkIndex2(pos);
  		return new AdaptiveListIterator(pos);
  	}

  	// Adopted from the List<E> interface.
  	@Override
  	public int hashCode()
  	{
  		if ( ! linkedUTD ) updateLinked();
  		int hashCode = 1;
  		for ( E e : this )
  			hashCode = 31 * hashCode + ( e == null ? 0 : e.hashCode() );
  		return hashCode;
  	}

  	// You should use the toString*() methods to see if your code works as expected.
  	@Override
  	public String toString()
  	{
  		String eol = System.getProperty("line.separator");
  		return toStringArray() + eol + toStringLinked();
  	}

  	public String toStringArray()
  	{
  		String eol = System.getProperty("line.separator");
  		StringBuilder strb = new StringBuilder();
  		strb.append("A sequence of items from the most recent array:" + eol );
  		strb.append('[');
  		if ( theArray != null )
  			for ( int j = 0; j < theArray.length; )
  			{
  				if ( theArray[j] != null )
  					strb.append( theArray[j].toString() );
  				else
  					strb.append("-");
  				j++;
  				if ( j < theArray.length )
  					strb.append(", ");
  			}
  		strb.append(']');
  		return strb.toString();
  	}

  	public String toStringLinked()
  	{
  		return toStringLinked(null);
  	}

  	// iter can be null.
  	public String toStringLinked(ListIterator<E> iter)
  	{
  		int cnt = 0;
  		int loc = iter == null? -1 : iter.nextIndex();

  		String eol = System.getProperty("line.separator");
  		StringBuilder strb = new StringBuilder();
  		strb.append("A sequence of items from the most recent linked list:" + eol );
  		strb.append('(');
  		for ( ListNode cur = head.link; cur != tail; )
  		{
  			if ( cur.data != null )
  			{
  				if ( loc == cnt )
  				{
  					strb.append("| ");
  					loc = -1;
  				}
  				strb.append(cur.data.toString());
  				cnt++;

  				if ( loc == numItems && cnt == numItems )
  				{
  					strb.append(" |");
  					loc = -1;
  				}
  			}
  			else
  				strb.append("-");
      
  			cur = cur.link;
  			if ( cur != tail )
  				strb.append(", ");
  		}
  		strb.append(')');
  		return strb.toString();
  	}
}
package edu.iastate.cs228.hw3;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
public class Hk3Tester {

	AdaptiveList<String> Elements;
	AdaptiveList<String> Elements1;
	AdaptiveList<String> Elements2;
	AdaptiveList<String> Elements3;
	AdaptiveList<String> Elements4;
	ListIterator<String> listI;
	ListIterator<String> listIatMiddle;
	ListIterator<String> listIatEnd;
	
	@Before
	public void setUp() {
		Elements = new AdaptiveList<String>();
		Elements.add("jim");
		Elements.add("jim1");
		Elements.add("jim2");
		Elements.add("jim3");
		Elements.add("jim4");
		Elements.add("jim5");
		Elements.add("jim6");
		Elements.add("jim7");
		
		Elements1 = new AdaptiveList<String>();
		
		Elements2 = new AdaptiveList<String>();
		
		Elements3 = new AdaptiveList<String>();
		
		Elements4 = new AdaptiveList<String>();
		Elements4.add("jim");
		Elements4.add("slim");
		Elements4.add("ted");
		Elements4.add("william");
		Elements4.add("bear");
		Elements4.add("dog");
		Elements4.add("charlie");
		Elements4.add("kate");
		Elements4.add("kim");

		listI = Elements4.listIterator();
		listIatMiddle = Elements4.listIterator(3);
		listIatEnd = Elements4.listIterator(9);
		
	}
	// hint with all of these the numsize will have to be equal in list for them to equals each other.
	@Test
	public void JimTest(){
		Elements1.addAll(Elements);
		assertTrue("Elements should equal Elements", Elements1.equals(Elements));
	}
	@Test
	public void ContainsWithBiggerList(){
		Elements1.add("apple");
		Elements1.add("pear");
		Elements1.add("peach");
		Elements1.add("orange");
		Elements2.add("apple");
		Elements2.add("pear");
		Elements2.add("peach");
		Elements2.add("plum");
		assertTrue("contains a apple", Elements1.contains("apple"));
		assertFalse("Elements1 doesn't contain an plum", Elements1.contains("plum"));
		assertTrue("Elements2 does contain an plum", Elements2.contains("plum"));
		assertFalse("List sould return false", Elements1.containsAll(Elements2));
		Elements1.add("plum");
		assertTrue("List sould return True since plum was added", Elements1.containsAll(Elements2));
		
	}
	@Test
	public void AddingWithBiggerList(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("jim3");
		Elements3.add("jim4");
		Elements3.add("jim5");
		Elements3.add("jim6");
		Elements3.add("jim7");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements3.add(11, "plum");
		Elements1.add("apple");
		Elements1.add("pear");
		Elements1.add("peach");
		Elements1.add("orange");
		Elements2.add("apple");
		Elements2.add("pear");
		Elements2.add("peach");
		Elements2.add("plum");
		Elements2.add("orange");
		Elements1.add(3, "plum");
		Elements.addAll(Elements1);
		assertTrue("List sould return True since plum was added", Elements1.equals(Elements2));
		assertTrue("List is big", Elements3.equals(Elements));
	}
	@Test
	public void AddAtPostion(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements1.add("apple");
		Elements1.add("pear");
		Elements1.add("peach");
		Elements1.add("orange");
		Elements2.add("jim");
		Elements2.add("jim1");
		Elements2.add("jim2");
		Elements1.addAll(0, Elements2);
		assertTrue("List sould return True since Elements2 was added to the begining", Elements1.equals(Elements3));
	}
	@Test
	public void RemovingThings(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements3.remove("apple");
		assertTrue("apple was removed", Elements3.head.link.link.link.link.data.equals("pear"));
		assertTrue("JavaDoc Says return value", Elements3.remove(3).equals("pear"));
		assertTrue("JavaDoc Says return value", Elements3.remove(3).equals("peach"));
		
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void addingOutsideList(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("apple");
		Elements3.add(5, "error");
	}
	@Test
	public void edgeAdding(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("apple");
		Elements3.add(4, "hamster");
		assertTrue("adds at end", Elements3.head.link.link.link.link.link.data.equals("hamster"));
		Elements3.add(0,"pig");
		assertTrue("adds at start", Elements3.head.link.data.equals("pig"));
	}
	@Test
	public void indexsofobjects(){
		Elements3.add("hamster");
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("apple");
		Elements3.add("hamster");
		assertEquals("last should be 5", Elements3.lastIndexOf("hamster"), 5);
		assertEquals("last should be 0", Elements3.indexOf("hamster"), 0);
	}
	@Test
	public void getThings(){
		Elements3.add("hamster");
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("apple");
		Elements3.add("hamster");
		assertTrue("last should be 5", Elements3.get(0).equals("hamster"));
		assertTrue("last should be 5", Elements3.get(3).equals("jim2"));
		assertTrue("last should be 5", Elements3.get(4).equals("apple"));
	}
	@Test
	public void removingAll(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("jim3");
		Elements3.add("jim4");
		Elements3.add("jim5");
		Elements3.add("jim6");
		Elements3.add("jim7");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements3.add("plum");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("plum");
		Elements3.add("orange");
		Elements3.add("plum");
		AdaptiveList<String> Elements5 = new AdaptiveList<String>();
		Elements5.add("plum");
		Elements5.add("apple");
		Elements5.add("jim2");
		Elements3.removeAll(Elements5);
		Elements2.add("jim");
		Elements2.add("jim1");
		Elements2.add("jim3");
		Elements2.add("jim4");
		Elements2.add("jim5");
		Elements2.add("jim6");
		Elements2.add("jim7");
		Elements2.add("pear");
		Elements2.add("peach");
		Elements2.add("orange");
		Elements2.add("pear");
		Elements2.add("peach");
		Elements2.add("orange");
		Elements2.add("pear");
		Elements2.add("peach");
		Elements2.add("orange");
		assertTrue("removes all occurences of that value", Elements2.equals(Elements3));
	}
	@Test
	public void retainAllOfTheList(){
		Elements3.add("jim");
		Elements3.add("jim1");
		Elements3.add("jim2");
		Elements3.add("jim3");
		Elements3.add("jim4");
		Elements3.add("jim5");
		Elements3.add("jim6");
		Elements3.add("jim7");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements3.add("plum");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("orange");
		Elements3.add("apple");
		Elements3.add("pear");
		Elements3.add("peach");
		Elements3.add("plum");
		Elements3.add("orange");
		Elements3.add("plum");
		AdaptiveList<String> Elements5 = new AdaptiveList<String>();
		Elements5.add("plum");
		Elements5.add("apple");
		Elements5.add("jim2");
		Elements3.retainAll(Elements5);
		Elements2.add("jim2");
		Elements2.add("apple");
		Elements2.add("plum");
		Elements2.add("apple");
		Elements2.add("apple");
		Elements2.add("plum");
		Elements2.add("plum");
		assertTrue("removes all occurences of that value", Elements2.equals(Elements3));
		
	}
	
	
	@Test(expected = NoSuchElementException.class)
	public void zIteratorAtEndOfList(){
		listIatEnd.next();
	}
	@Test
	public void zIteratorAtEndTest(){
		assertTrue("should equal kim", listIatEnd.previous().equals("kim"));
		listIatEnd.remove();
		assertTrue("should equal kate", listIatEnd.previous().equals("kate"));
		assertEquals("", listIatEnd.previousIndex(), 6);
	}
	@Test(expected = IllegalStateException.class)
	public void zErrorafter2Removes(){
		listI.next();
		listI.remove();
		listI.remove();
	}
	@Test(expected = IllegalStateException.class)
	public void zErrorAfterAddThenRemove(){
		listI.next();
		listI.add("gary");
		listI.remove();
	}
	@Test(expected = IndexOutOfBoundsException.class)
	public void zSetingOverLimit(){
		AdaptiveList<String> Elements5 = new AdaptiveList<String>();
		Elements5.add("bear");
		Elements5.add("dog");
		Elements5.add("charlie");
		Elements5.set(3, "paul");
	}
	@Test(expected = IndexOutOfBoundsException.class)
	public void zAddOutOfBounds(){
		AdaptiveList<String> Elements5 = new AdaptiveList<String>();
		Elements5.add("bear");
		Elements5.add("dog");
		Elements5.add("charlie");
		Elements5.add(4, "paul");
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void zGetOutOfBounds(){
		Elements.get(100);
	}
	
}

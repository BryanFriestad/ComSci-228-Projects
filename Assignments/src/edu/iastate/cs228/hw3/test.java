package edu.iastate.cs228.hw3;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class test {
	
	public AdaptiveList<Integer> testList;
	public AdaptiveList<Integer> testList2;
	public AdaptiveList<Integer> emptyList;
	
	ListIterator<Integer> iter1;
	ListIterator<Integer> iter2;
	
	@Before
	public void setup(){
		testList = new AdaptiveList<Integer>();
		testList.add(1);
		testList.add(2);
		testList.add(3);
		testList.add(4);
		testList.add(5);
		
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(1);
		temp.add(2);
		temp.add(3);
		temp.add(4);
		temp.add(5);
		temp.add(0, 0);
		temp.add(6, 6);
		testList2 = new AdaptiveList<Integer>(temp);
		
		emptyList = new AdaptiveList<Integer>();
		
		iter1 = testList.listIterator();
	}
	
	
	@Test
	public void test1(){
		//assertTrue(testList.size() == 5);
		//assertTrue(testList2.size() == 5);
		//assertTrue(testList.head.link.data == 1);
		//assertTrue(testList.tail.link == null);
		//assertTrue(testList.tail.prev.data == 5);
		//assertTrue(testList2.head.link.data == 1);
		//assertTrue(testList2.tail.link == null);
		//assertTrue(testList2.tail.prev.data == 5);
	}
	
	@Test
	public void test2(){
		assertTrue(iter1.hasNext());
	}
	
	@Test
	public void test3(){
		AdaptiveList<Integer> shortList1 = new AdaptiveList<Integer>();
		shortList1.add(2);
		shortList1.add(4);
		shortList1.add(5);
		emptyList.add(1);
		emptyList.add(3);
		testList.removeAll(shortList1);
		assertTrue(emptyList.equals(testList));
		emptyList.add(69);
		assertFalse(emptyList.equals(testList));
	}


}

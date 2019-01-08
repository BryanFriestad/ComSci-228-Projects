package edu.iastate.cs228.hw4;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class EntryTreeTester {
	
	EntryTree<Character, String> norm;
	EntryTree<Character, Integer> numTree;
	EntryTree<Integer, Integer> doubleNumTree;
	
	@Before
	public void setUp(){
		 norm = new EntryTree<Character, String>();
		 numTree = new EntryTree<Character, Integer>();
		 doubleNumTree = new EntryTree<Integer, Integer>();
	}
	
	@Test
	public void prefixTestKeyArrNull(){
		assertNull(norm.prefix(null));
		assertNull(numTree.prefix(null));
		assertNull(doubleNumTree.prefix(null));
	}
	
	@Test
	public void prefixTestKeyArrEmpty(){
		Character[] keyarr = {};
		assertTrue(keyarr.length == 0);
		Character[] keyarr2 = {};
		assertTrue(keyarr2.length == 0);
		Integer[] keyarr3 = {};
		assertTrue(keyarr3.length == 0);
		
		assertNull(norm.prefix(keyarr));
		assertNull(numTree.prefix(keyarr2));
		assertNull(doubleNumTree.prefix(keyarr3));
	}
	
	@Test(expected = NullPointerException.class)
	public void prefixNullPointerTest1(){
		Character[] keyarr = new Character[3];
		keyarr[0] = 'C';
		//keyarr[1] = 'B'; oops!
		keyarr[2] = 'A';
		
		norm.prefix(keyarr);
	}
	
	@Test(expected = NullPointerException.class)
	public void prefixNullPointerTest2(){
		Character[] keyarr = new Character[3];
		keyarr[0] = 'C';
		//keyarr[1] = 'B'; oops!
		keyarr[2] = 'A';
		
		numTree.prefix(keyarr);
	}
	
	@Test(expected = NullPointerException.class)
	public void prefixNullPointerTest3(){
		Integer[] keyarr = new Integer[3];
		keyarr[0] = 1;
		//keyarr[1] = 2; oops!
		keyarr[2] = 3;
		
		doubleNumTree.prefix(keyarr);
	}
	
	@Test
	public void prefixDNE(){
		Character[] keyarr1 = {'A', 'C', 'T'};
		Character[] keyarr2 = {'C', 'A', 'T'};
		norm.add(keyarr1, "test");
		assertNull(norm.prefix(keyarr2));
		
		Character[] keyarr3 = {'A', 'C', 'T'};
		Character[] keyarr4 = {'C', 'A', 'T'};
		numTree.add(keyarr3, 36);
		assertNull(numTree.prefix(keyarr4));
		
		Integer[] keyarr5 = {1, 2, 3};
		Integer[] keyarr6 = {3, 2, 1};
		doubleNumTree.add(keyarr5, 4);
		assertNull(doubleNumTree.prefix(keyarr6));
	}
	
	@Test
	public void prefixExistsButLowerCase(){
		Character[] keyarr1 = {'A', 'C', 'T'};
		Character[] keyarr2 = {'a', 'c', 't'};
		norm.add(keyarr1, "test");
		assertNull(norm.prefix(keyarr2));
		
		Character[] keyarr3 = {'A', 'C', 'T'};
		Character[] keyarr4 = {'a', 'c', 't'};
		numTree.add(keyarr3, 36);
		assertNull(numTree.prefix(keyarr4));	
	}
	
	@Test
	public void prefixPartiallyExists(){
		Character[] keyarr1 = {'A', 'C', 'T'};
		Character[] keyarr2 = {'A', 'C'};
		norm.add(keyarr1, "test");
		assertNotNull(norm.prefix(keyarr2));
		
		Character[] keyarr3 = {'A', 'C', 'T'};
		Character[] keyarr4 = {'A', 'C'};
		numTree.add(keyarr3, 36);
		assertNotNull(numTree.prefix(keyarr4));	
		
		Integer[] keyarr5 = {1, 2, 3};
		Integer[] keyarr6 = {1, 2};
		doubleNumTree.add(keyarr5, 4);
		assertNotNull(doubleNumTree.prefix(keyarr6));
	}
	
	@Test
	public void searchKeyArrNull(){
		assertNull(norm.search(null));
		assertNull(numTree.search(null));
		assertNull(doubleNumTree.search(null));
	}
	
	@Test
	public void searchKeyArrIsEmpty(){
		Character[] keyarr = {};
		Character[] keyarr2 = {};
		Integer[] keyarr3 = {};
		
		assertNull(norm.search(keyarr));
		assertNull(numTree.search(keyarr2));
		assertNull(doubleNumTree.search(keyarr3));
	}
	
	@Test(expected = NullPointerException.class)
	public void searchNullPointerTest1(){
		Character[] keyarr = new Character[3];
		keyarr[0] = 'C';
		//keyarr[1] = 'B'; oops!
		keyarr[2] = 'A';
		
		norm.search(keyarr);
	}
	
	@Test(expected = NullPointerException.class)
	public void searchNullPointerTest2(){
		Character[] keyarr = new Character[3];
		keyarr[0] = 'C';
		//keyarr[1] = 'B'; oops!
		keyarr[2] = 'A';
		
		numTree.search(keyarr);
	}
	
	@Test(expected = NullPointerException.class)
	public void searchNullPointerTest3(){
		Integer[] keyarr = new Integer[3];
		keyarr[0] = 1;
		//keyarr[1] = 2; oops!
		keyarr[2] = 3;
		
		doubleNumTree.search(keyarr);
	}
	
	@Test
	public void searchIsEmpty(){
		assertTrue(norm.isEmpty());
		assertTrue(numTree.isEmpty());
		assertTrue(doubleNumTree.isEmpty());
		
		Character[] keyarr1 = {'A', 'C', 'T'};
		assertNull(norm.search(keyarr1));
		
		Character[] keyarr2 = {'A', 'C', 'T'};
		assertNull(numTree.search(keyarr2));	
		
		Integer[] keyarr3 = {1, 2, 3};
		assertNull(doubleNumTree.search(keyarr3));
	}
	
	@Test
	public void searchEmptyValueTest(){
		Character[] keyarr1 = {'D', 'O', 'G'};
		Character[] keyarr2 = {'D', 'O'};
		assertTrue(norm.add(keyarr1, "puppy"));
		assertNull(norm.search(keyarr2));
	}
	
	@Test
	public void searchDNE(){
		Character[] keyarr1 = {'D', 'O', 'G'};
		Character[] keyarr2 = {'S', 'O', 'G'};
		assertTrue(norm.add(keyarr1, "puppy"));
		assertNull(norm.search(keyarr2));
	}
	
	@Test
	public void searchEntryExists(){
		Character[] keyarr1 = {'D', 'O', 'G'};
		String value1 = "puppy";
		String value2 = "big puppy";
		Character[] keyarr2 = {'D', 'O', 'G'};
		Character[] keyarr3 = {'D', 'O', 'G', 'G', 'Y'};
		assertTrue(norm.add(keyarr3, value2));
		assertTrue(norm.add(keyarr1, value1));
		assertTrue(value1.equals(norm.search(keyarr2)));
		assertTrue(value2.equals(norm.search(keyarr3)));
		assertFalse(value2.equals(norm.search(keyarr2)));
		assertFalse(value1.equals(norm.search(keyarr3)));
	}
	
	@Test
	public void addKeyArrNull(){
		Character[] keyarr1 = null;
		assertFalse(norm.add(keyarr1, "kitty"));
	}
	
	@Test
	public void addKeyArrEmpty(){
		Character[] keyarr1 = {};
		assertFalse(norm.add(keyarr1, "kitty"));
	}
	
	@Test
	public void addValueNull(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		String value = null;
		assertFalse(norm.add(keyarr1, value));
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullPointer(){
		Character[] keyarr1 = new Character[3];
		keyarr1[0] = 'C';
		keyarr1[2] = 'T';
		norm.add(keyarr1, "kitty");
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullPointer2(){
		Character[] keyarr2 = new Character[3];
		keyarr2[0] = 'C';
		keyarr2[2] = 'T';
		numTree.add(keyarr2, 3);
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullPointer3(){
		Integer[] keyarr3 = new Integer[3];
		keyarr3[0] = 1;
		keyarr3[2] = 3;
		doubleNumTree.add(keyarr3, 4);
	}
	
	@Test
	public void addEarlierNodeSaved(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		Character[] keyarr2 = {'C', 'A', 'T', 'C', 'H'};
		assertTrue(norm.add(keyarr1, "kitty"));
		assertTrue(norm.add(keyarr2, "hold"));
		assertTrue("kitty".equals(norm.search(keyarr1)));
	}
	
	@Test
	public void addReplacingTest(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		assertTrue(norm.add(keyarr1, "kitty"));
		assertTrue(norm.add(keyarr1, "feline"));
		assertFalse("kitty".equals(norm.search(keyarr1)));
		assertTrue("feline".equals(norm.search(keyarr1)));
	}
	
	@Test
	public void removeKeyArrNull(){
		assertNull(norm.remove(null));
		assertNull(numTree.remove(null));
		assertNull(doubleNumTree.remove(null));
	}
	
	@Test
	public void removeKeyArrEmpty(){
		Character[] keyarr1 = {};
		Integer[] keyarr2 = {};
		assertNull(norm.remove(keyarr1));
		assertNull(numTree.remove(keyarr1));
		assertNull(doubleNumTree.remove(keyarr2));
	}
	
	@Test(expected = NullPointerException.class)
	public void removeNullPointer1(){
		Character[] keyarr1 = new Character[3];
		keyarr1[0] = 'C';
		keyarr1[2] = 'T';
		norm.remove(keyarr1);
	}
	
	@Test(expected = NullPointerException.class)
	public void removeNullPointer2(){
		Character[] keyarr1 = new Character[3];
		keyarr1[0] = 'C';
		keyarr1[2] = 'T';
		numTree.remove(keyarr1);
	}
	
	@Test(expected = NullPointerException.class)
	public void removeNullPointer3(){
		Integer[] keyarr1 = new Integer[3];
		keyarr1[0] = 1;
		keyarr1[2] = 2;
		doubleNumTree.remove(keyarr1);
	}
	
	@Test
	public void removeDNE(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		Character[] keyarr2 = {'D', 'O', 'G'};
		assertTrue(norm.add(keyarr1, "kitty"));
		assertNull(norm.remove(keyarr2));
	}
	
	@Test
	public void removeExists(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		String value = "kitty";
		assertTrue(norm.add(keyarr1, value));
		assertTrue(value.equals(norm.remove(keyarr1)));
	}
	
	@Test
	public void removeDoesntRemoveAbove(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		Character[] keyarr2 = {'C', 'A', 'T', 'C', 'H'};
		String value = "kitty";
		assertTrue(norm.add(keyarr1, value));
		assertTrue(norm.add(keyarr2, "grab"));
		norm.remove(keyarr2);
		assertTrue("kitty".equals(norm.search(keyarr1)));
	}
	
	@Test
	public void removeDoesntRemoveBelow(){
		Character[] keyarr1 = {'C', 'A', 'T'};
		Character[] keyarr2 = {'C', 'A', 'T', 'C', 'H'};
		String value = "kitty";
		assertTrue(norm.add(keyarr1, value));
		assertTrue(norm.add(keyarr2, "grab"));
		norm.remove(keyarr1);
		assertTrue("grab".equals(norm.search(keyarr2)));
	}
}

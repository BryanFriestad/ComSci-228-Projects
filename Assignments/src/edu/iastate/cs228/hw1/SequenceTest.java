package edu.iastate.cs228.hw1;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class SequenceTest {
	
	private Sequence seq;
	//goodSeq 1-4 should all be equal, but good seq5 should not
	//these equalities should work both ways
	private Sequence goodSeq = new Sequence("abDCgsht".toCharArray());
	private Sequence goodSeq2 = new Sequence("abDCgsht".toCharArray()); 
	private Sequence goodSeq3 = new Sequence("ABdcGsht".toCharArray());
	private Sequence goodSeq4 = goodSeq;
	private Sequence goodSeq5 = new Sequence("abDdgSht".toCharArray());
	
	//this test should pass because of the % sign passed into the init
	@Test(expected = IllegalArgumentException.class)
	public void ConstructorTest1(){
		String invalidSeq = "abbab%ags";
		seq = new Sequence(invalidSeq.toCharArray());
	}
	
	//this test should pass because of the number passed into the init
	@Test(expected = IllegalArgumentException.class)
	public void ConstructorTest2(){
		String invalidSeq = "abbab3ags";
		seq = new Sequence(invalidSeq.toCharArray());
	}
	
	//this test should pass even though there is a capital
	@Test
	public void ConstructorTest3(){
		String validSeq = "abbabAags";
		seq = new Sequence(validSeq.toCharArray());
	}
	
	//this test should pass
	@Test
	public void ConstructorTest4(){
		String validSeq = "abbabzags";
		seq = new Sequence(validSeq.toCharArray());
	}
		
	//Starting to test all of the public methods using the goodSeq
	@Test
	public void IsValidLetterTest1(){
		assertTrue(goodSeq.isValidLetter('a'));
	}
	@Test
	public void IsValidLetterTest2(){
		assertTrue(goodSeq.isValidLetter('B'));
	}
	@Test
	public void IsValidLetterTest3(){
		assertTrue(goodSeq.isValidLetter('c'));
	}
	@Test
	public void IsValidLetterTest4(){
		assertTrue(goodSeq.isValidLetter('Z'));
	}
	@Test
	public void IsValidLetterTest5(){
		assertFalse(goodSeq.isValidLetter('1'));
	}
	@Test
	public void IsValidLetterTest6(){
		assertFalse(goodSeq.isValidLetter('!'));
	}
	
	@Test
	public void FunctionTest(){
		Sequence newSeq = new Sequence("".toCharArray());
		Sequence newSeq2 = new Sequence("aBAHSJsysVSsj".toCharArray());
		Sequence newSeq3 = newSeq2;
		
		assertEquals(newSeq3.seqLength(), newSeq2.seqLength());
		assertEquals(newSeq2.seqLength(), 13);
		assertEquals(newSeq3.seqLength(), 13);
		assertEquals(newSeq.seqLength(), 0);
		
		//there is no good way to test these functions using assertions
		//so i'm just printing them out to make sure they print empty
		
		//this one should print out the location
		System.out.println("newSeq.getSeq(): " + newSeq.getSeq()); //some location
		System.out.println("newSeq2.getSeq(): " + newSeq2.getSeq()); //some location
		System.out.println("newSeq3.getSeq(): " + newSeq3.getSeq()); //same location as above
		
		//this one should print out the contents
		System.out.println("newSeq.toString(): " + newSeq.toString()); //empty
		System.out.println("newSeq2.toString(): " + newSeq2.toString()); //"aBAHSJsysVSsj"
		System.out.println("newSeq3.toString(): " + newSeq3.toString()); //same as above
	}
	
	//Testing to see what happens when the sequence is empty
	//according to the assignment instructions, this should 
	//be allowed as long as it follows the same rules
	@Test
	public void EmptySeqTest(){
		Sequence newSeq = new Sequence("".toCharArray());
		Sequence newSeq2 = new Sequence("".toCharArray());
		Sequence newSeq3 = newSeq;
		
		assertEquals(0, newSeq.seqLength());
		//there is no good way to test these functions using assertions
		//so i'm just printing them out to make sure they print empty
		System.out.println("newSeq.getSeq(): " + newSeq.getSeq());
		System.out.println("newSeq.toString(): " + newSeq.toString());
		
		assertTrue(newSeq.equals(newSeq3));
		assertTrue(newSeq3.equals(newSeq));
		assertTrue(newSeq.equals(newSeq2));
		assertTrue(newSeq2.equals(newSeq));
		assertTrue(newSeq2.equals(newSeq3));
		assertTrue(newSeq3.equals(newSeq2));
	}
	
	//Huge test for equality between different sequences
	@Test
	public void EqualityStuff(){
		assertTrue(goodSeq.equals(goodSeq2));
		assertTrue(goodSeq2.equals(goodSeq));
		assertTrue(goodSeq.equals(goodSeq3));
		assertTrue(goodSeq3.equals(goodSeq));
		assertTrue(goodSeq.equals(goodSeq4));
		assertTrue(goodSeq4.equals(goodSeq));
		assertTrue(goodSeq4.equals(goodSeq2));
		assertTrue(goodSeq4.equals(goodSeq3));
		assertFalse(goodSeq.equals(goodSeq5));
		assertFalse(goodSeq5.equals(goodSeq));
		assertFalse(goodSeq5.equals(goodSeq4));
		assertFalse(goodSeq5.equals(goodSeq3));
		assertFalse(goodSeq5.equals(goodSeq2));
		//this should honestly be enough tests to show that it works
		//with a lot of different parameters
	}
	
}

package edu.iastate.cs228.hw1;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.*;

public class ProteinSequenceTest {
	
	ProteinSequence pseq;
	ProteinSequence goodPSeq = new ProteinSequence("MVRPQVRRCL".toCharArray());
	
	@Test
	public void ConstructorTest1(){
		pseq = new ProteinSequence("MVRPQ".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ConstructorTest2(){
		pseq = new ProteinSequence("MVRPQ2".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ConstructorTest3(){
		pseq = new ProteinSequence("MV R PQ".toCharArray());
	}
	
	@Test
	public void ConstructorTest4(){
		pseq = new ProteinSequence("MvrPQ".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ConstructorTest5(){
		pseq = new ProteinSequence("MVbPQ".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ConstructorTest6(){
		pseq = new ProteinSequence("MVRPUQ".toCharArray());
	}
	
	
	@Test
	public void OneLastEqualityTest(){
		ProteinSequence goodPSeq  = new ProteinSequence("MVRPQVRRCL".toCharArray());
		ProteinSequence goodPSeq2 = new ProteinSequence("MVRpQvRRCL".toCharArray());
		ProteinSequence goodPSeq3 = goodPSeq;
		ProteinSequence goodPSeq4 = new ProteinSequence("MVRWWWRRCL".toCharArray());
		
		assertTrue(goodPSeq.equals(goodPSeq2));
		assertTrue(goodPSeq.equals(goodPSeq3));
		assertFalse(goodPSeq.equals(goodPSeq4));
		assertTrue(goodPSeq2.equals(goodPSeq));
		assertTrue(goodPSeq2.equals(goodPSeq3));
		assertFalse(goodPSeq2.equals(goodPSeq4));
		assertTrue(goodPSeq3.equals(goodPSeq));
		assertTrue(goodPSeq3.equals(goodPSeq2));
		assertFalse(goodPSeq3.equals(goodPSeq4));
	}
	
	
	
	
	@Test
	public void IsValidTests(){
		assertTrue(goodPSeq.isValidLetter('a'));
		assertTrue(goodPSeq.isValidLetter('A'));
		assertFalse(goodPSeq.isValidLetter('B'));
		assertFalse(goodPSeq.isValidLetter('b'));
		assertFalse(goodPSeq.isValidLetter('j'));
		assertFalse(goodPSeq.isValidLetter('J'));
		assertFalse(goodPSeq.isValidLetter('z'));
		assertFalse(goodPSeq.isValidLetter('Z'));
		assertTrue(goodPSeq.isValidLetter('M'));
		assertTrue(goodPSeq.isValidLetter('V'));
		assertTrue(goodPSeq.isValidLetter('p'));
		assertFalse(goodPSeq.isValidLetter(' '));
		assertFalse(goodPSeq.isValidLetter('@'));
		assertFalse(goodPSeq.isValidLetter('/'));
		assertFalse(goodPSeq.isValidLetter('0')); //this is zero btw
	}

}

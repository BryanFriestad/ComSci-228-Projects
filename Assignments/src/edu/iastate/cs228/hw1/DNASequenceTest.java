package edu.iastate.cs228.hw1;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class DNASequenceTest {
	
	DNASequence dnaseq;
	

	@Test
	//this test instantiation should work!
	public void ConstructerTest1(){
		dnaseq = new DNASequence("ACGTacgtAcGtaCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the number
	public void ConstructerTest2(){
		dnaseq = new DNASequence("ACGTacgtA2GtaCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the symbols
	public void ConstructerTest3(){
		dnaseq = new DNASequence("ACGTacgtA$Gt!CgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the invalid letter
	public void ConstructerTest4(){
		dnaseq = new DNASequence("ACGTacgtABCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the invalid letter
	public void ConstructerTest5(){
		dnaseq = new DNASequence("ACGTacgtAaGtbCgT".toCharArray());
	}
	
	
	
	@Test
	public void IsValidLetterTest(){
		DNASequence goodDNA = new DNASequence("AcGtaCgTACGTacgtaaacccgggtttAAACCCGGGTTT".toCharArray());
		assertTrue(goodDNA.isValidLetter('a'));
		assertTrue(goodDNA.isValidLetter('A'));
		assertFalse(goodDNA.isValidLetter('b'));
		assertFalse(goodDNA.isValidLetter('B'));
		assertFalse(goodDNA.isValidLetter('$'));
		assertFalse(goodDNA.isValidLetter('2'));
	}
	
	
	
	@Test
	//tests to make sure that the equals function still works at different level of implementation
	public void EqualityTest(){
		DNASequence goodDNA  = new DNASequence("AcGtaCgT".toCharArray());
		DNASequence goodDNA2 = new DNASequence("aCgTAcGt".toCharArray());
		DNASequence goodDNA3 = goodDNA;
		
		assertTrue(goodDNA.equals(goodDNA2));
		assertTrue(goodDNA.equals(goodDNA3));
		assertTrue(goodDNA2.equals(goodDNA));
		assertTrue(goodDNA2.equals(goodDNA3));
		assertTrue(goodDNA3.equals(goodDNA));
		assertTrue(goodDNA3.equals(goodDNA2));
	}
}

package edu.iastate.cs228.hw1;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.*;

public class CodingDNASequenceTest {
	
	CodingDNASequence cdnaseq;
	CodingDNASequence startSeq    = new CodingDNASequence("ATGGTCCGTA".toCharArray());
	CodingDNASequence nonStartSeq = new CodingDNASequence("ATcGTCCGTA".toCharArray()); 

	//MOST OF THESE FIRST TESTS ARE THE SAME AS DNASequence
	@Test
	//this test instantiation should work!
	public void ConstructerTest1(){
		cdnaseq = new CodingDNASequence("ACGTacgtAcGtaCgT".toCharArray());
	}
		
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the number
	public void ConstructerTest2(){
		cdnaseq = new CodingDNASequence("ACGTacgtA9GtaCgT".toCharArray());
	}
		
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the symbols
	public void ConstructerTest3(){
		cdnaseq = new CodingDNASequence("ACGTacgt GtaCgT".toCharArray());
	}
		
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the invalid letter
	public void ConstructerTest4(){
		cdnaseq = new CodingDNASequence("ACGTacgZAcGtaCgT".toCharArray());
	}
		
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the invalid letter
	public void ConstructerTest5(){
		cdnaseq = new CodingDNASequence("ACGTacgtcGtaCgz".toCharArray());
	}
	
	
	
	//Testing CheckStartCodon
	@Test
	public void CheckStartCodonTest(){
		assertTrue(startSeq.checkStartCodon());
		assertFalse(nonStartSeq.checkStartCodon());
	}
	
	
	
	//Starting to test and make sure that translate() is picking up the proper exceptions
	@Test
	public void TranslationTest(){
		String temp = new String(startSeq.translate());
		System.out.println(temp);
		assertTrue("MVR".equals(temp.toString()));
	}
	
	@Test(expected = RuntimeException.class)
	public void TranslationTest2(){
		nonStartSeq.translate();
	}
}

package edu.iastate.cs228.hw1;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class GenomicDNASequenceTest {
	
	GenomicDNASequence gdna;
	GenomicDNASequence goodSeq = new GenomicDNASequence("AGTTGCGA".toCharArray());
	GenomicDNASequence goodSeq2 = new GenomicDNASequence("ACTGACTGATC".toCharArray());
	int[] good1 = {0, 1, 4, 6};
	int[] good2 = {2, 5};
	int[] bad1 = {1, 2, 3};
	int[] bad2 = {2, 1, 4, 5};
	int[] bad3 = {};
	int[] bad4 = {-2, 7};
	int[] bad5 = {1, 12};
	
	
	//MOST OF THESE FIRST TESTS ARE THE SAME AS DNASequence
	@Test
	//this test instantiation should work!
	public void ConstructerTest1(){
		gdna = new GenomicDNASequence("ACGTacgtAcGtaCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the number
	public void ConstructerTest2(){
		gdna = new GenomicDNASequence("ACGTacgtAc3taCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the symbols
	public void ConstructerTest3(){
		gdna = new GenomicDNASequence("ACGTacgt%cGtaCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the invalid letter
	public void ConstructerTest4(){
		gdna = new GenomicDNASequence("ACGTacBBBtAcGtaCgT".toCharArray());
	}
	
	@Test(expected = IllegalArgumentException.class)
	//this test should pass because of the invalid letter
	public void ConstructerTest5(){
		gdna = new GenomicDNASequence("ACGTacgbbbAcGtaCgT".toCharArray());
	}
	
	
	
	//goodSeq is only 8 characters long
	@Test
	public void MarkCodingTest(){
		goodSeq.markCoding(1, 5); //this should work
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void MarkCodingTest2(){
		goodSeq.markCoding(6, 2); //first is greater than last;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void MarkCodingTest3(){
		goodSeq.markCoding(-1, 5); //first is less than 0
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void MarkCodingTest4(){
		goodSeq.markCoding(1, 10); //last is greater than sequence length
	}
	
	
	
	//Testing the extractExons function now
	@Test
	public void ExtractExonTest1(){
		goodSeq2.markCoding(0, 6);
		goodSeq2.extractExons(good1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ExtractExonTest2(){
		goodSeq2.markCoding(0, 10);
		goodSeq2.extractExons(bad1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ExtractExonTest3(){
		goodSeq2.markCoding(0, 10);
		goodSeq2.extractExons(bad2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ExtractExonTest4(){
		goodSeq2.markCoding(0, 10);
		goodSeq2.extractExons(bad3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ExtractExonTest7(){
		goodSeq2.markCoding(0, 10);
		goodSeq2.extractExons(bad4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ExtractExonTest8(){
		goodSeq2.markCoding(0, 10);
		goodSeq2.extractExons(bad5);
	}
	
	@Test(expected = IllegalStateException.class)
	public void ExtractExonTest5(){
		//oops i forgot to mark iscoding! SHIT!
		goodSeq2.extractExons(good1);
	}
	
	@Test
	public void ExtractExonTest6(){
		goodSeq2.markCoding(0, 7);
		goodSeq2.extractExons(good2);
	}

}

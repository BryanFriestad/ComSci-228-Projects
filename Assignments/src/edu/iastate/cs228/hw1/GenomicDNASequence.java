package edu.iastate.cs228.hw1;

/*
 * @author	Bryan Friestad
*/

public class GenomicDNASequence extends DNASequence
{
	private boolean[] iscoding;
	
	/**
	 * Passes gdnaarr on to the super class: {@link DNASequence#DNASequence(char[])}.
	 * After doing so, iscoding is instatiated to the same length as the sequence and
	 * then all of the fields in the array are set to false;
	 * 
	 * @param gdnaarr The data to be set to seqarr, representing the object.
	 */
  	public GenomicDNASequence(char[] gdnaarr)
  	{
  		super(gdnaarr);
  		//System.out.println("DEBUG: " + seqLength()); //seems legit so I took it out
  		iscoding = new boolean[seqLength()];
  		for(boolean b : iscoding){
  			b = false;
  		}
  	}

  	/**
  	 * Marks all indexes of iscoding to true between first and last,
  	 * inclusive. 
  	 * 
  	 * @throws IllegalArgumentException When the parameters don't make sense or are out of bounds
  	 * @param first Index to start marking true
  	 * @param last Last index to mark true
  	 */
  	public void markCoding(int first, int last)
  	{
  		//makes sure that the parameters makes sense
  		if(first > last || first < 0 || last >= seqLength()){
  			throw new IllegalArgumentException("Coding border is out of bound");
  		}
  		//else sets iscoding equal to true between first and last
  		for(int i = first; i <= last; ++i){
  			iscoding[i] = true;
  		}
  	}

  	/**
  	 * Extracts the sequence characters from seqarr in which the same indexes in
  	 * iscoding are true and in which are between the parameters specified in exonpos
  	 * 
  	 * @throws IllegalArgumentException If exonpos is empty or has an odd number, if any exon position is out of bounds of the seqarr, or if any exon position is greater than the next one
  	 * @throws IllegalStateException If iscoding is false at an index which is marked for exon extraction.
  	 * @param exonpos an array of the start and end positions for each coding exon
  	 * @return Returns a character array of the exon coding sequence
  	 */
  	public char[] extractExons(int[] exonpos)
  	{
  		if((exonpos.length == 0) || (exonpos.length % 2 != 0)){
  			throw new IllegalArgumentException("Empty array or odd number of array elements");
  		}
  		for(int i = 0; i < exonpos.length; ++i){
  			if(exonpos[i] < 0 || exonpos[i] >= seqLength()){
  				throw new IllegalArgumentException("Exon position is out of bound");
  			}
  			//first checks to make sure it won't access out of bounds
  			//then checks to see if term i is bigger than term (i+1)
  			//i think it should be bigger than or equal to, but oh well
  			if((i < exonpos.length - 1) && (exonpos[i] > exonpos[i+1])){
  				throw new IllegalArgumentException("Exon positions not in order");
  			}
  		}
  		
  		String temp = new String();
  		for(int i = 0; i < exonpos.length; i += 2){
			for(int j = exonpos[i]; j <= exonpos[i+1]; ++j){
				if(!iscoding[j]){
					throw new IllegalStateException("Noncoding position is found");
				}
				temp += seqarr[j];
			}
		}
  		return (temp.toCharArray());
  	}
}

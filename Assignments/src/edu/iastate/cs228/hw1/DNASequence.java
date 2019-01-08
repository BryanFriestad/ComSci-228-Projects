package edu.iastate.cs228.hw1;

/*
 * @author	Bryan Friestad
*/

public class DNASequence extends Sequence
{
	/**
	 * Passes dnaarr onto the super class's constructor.
	 * {@link Sequence#Sequence(char[])}
	 * 
	 * @param dnaarr DNA Sequence to be used to represent this object
	 */
	public DNASequence(char[] dnaarr)
	{
		super(dnaarr);
	}

	/**
	 * Checks to see if the passed character is a valid one.
	 * The passed character must be A, C, G, or T (case ignored)
	 * 
	 * @param let The character to be checked
	 * @return True or False
	 */
	@Override
	public boolean isValidLetter(char let)
	{
		if(!super.isValidLetter(let)) return false; //checks to see if its even a letter quick
		char[] key = {'a','A','c','C','g','G','t','T'};
		for(char c : key){
			if(let == c) return true;
		}
		return false;
	}
}

package edu.iastate.cs228.hw1;

/*
 * @author	Bryan Friestad
*/

public class ProteinSequence extends Sequence
{
	/**
	 * Passes psarr onto the super class: {@link Sequence#Sequence(char[])}
	 * where each character is check to make sure it is valid. If all characters
	 * are valid, then psarr is saved in the protected variable seqarr.
	 * 
	 * @param psarr The character array to be saved in seqarr and that represents the object.
	 */
	public ProteinSequence(char[] psarr)
	{
		super(psarr);
	}

	
	/**
	 * Checks to see if the given character is a valid character by
	 * first running it through the overridden super.isValidLetter().
	 * Then it is checked to make sure it is not one of the invalid letters
	 * B, J, O, U, X, or Z (case insensitive).
	 * 
	 * @param aa The character to be checked for validity.
	 * @return Returns True is valid and False if invalid.
	 */
	@Override
	public boolean isValidLetter(char aa)
	{
		if(!super.isValidLetter(aa)) return false;
		char[] illegal = {'B', 'b', 'J', 'j', 'O', 'o', 'U', 'u', 'X', 'x', 'Z', 'z'};
		for(char c : illegal){
			if(aa == c) return false;
		}
		return true;
	}
}

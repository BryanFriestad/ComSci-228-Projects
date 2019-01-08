package edu.iastate.cs228.hw1;

/*
 * @author	Bryan Friestad
 * 
*/

public class Sequence
{
	protected char[] seqarr;

	/**
	 * Checks to see if each of the characters in sarr are valid.
	 * Throws an IllegalArgumentException if a character is not valid.
	 * If all characters in sarr are valid, then it saves a copy to
	 * the objects field seqarr.
	 * 
	 * @param sarr The sequence array to be saved in seqarr
	 */
	public Sequence(char[] sarr)
	{
		for(char c : sarr){
			if(!isValidLetter(c)){
				throw new IllegalArgumentException("Invalid sequence letter for class " + this.getClass().getCanonicalName());
			}
		}
		seqarr = sarr;
	}
	
	/**
	 * 
	 * @return Returns the length of the sequence array
	 */
	public int seqLength()
	{
		return seqarr.length;
	}
	
	/**
	 * 
	 * @return Returns the sequence array itself
	 */
	public char[] getSeq()
	{
		return seqarr;
	}

	/**
	 * Returns the contents of the sequence array in a string form
	 * 
	 * @return The String form of this object
	 */
	public String toString()
	{
		String outS = new String();
		for(char c : seqarr){
			outS += c;
		}
		return outS;
	}

	/**
	 * Determines whether or not the calling object and the passed object
	 * are equal by first making sure they are of the same type and then
	 * comparing the contents of each object's sequence array
	 * 
	 * @param obj The object to compare
	 * @return True or false
	 */
	public boolean equals(Object obj)
	{
		if(this == obj){
			return true;
		}
		if(obj == null || (this.getClass() != obj.getClass())){
			return false;
		}
		Sequence temp = (Sequence) obj;
		//need to figure out how to make this cast to whatever type is calling it //actually what I have done here works properly I think
		if(seqLength() != temp.seqLength()){
			return false;
		}
		for(int i = 0; i < seqLength(); ++i){
			if(Character.toUpperCase(seqarr[i]) != Character.toUpperCase(temp.getSeq()[i])){
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks to see if the passed character is in fact a valid character.
	 * Character must be an uppercase or lowercase letter
	 *
	 * @param let The character to check 
	 * @return True or False
	 */
	public boolean isValidLetter(char let)
	{
		return Character.isUpperCase(let) || Character.isLowerCase(let);
	}

}

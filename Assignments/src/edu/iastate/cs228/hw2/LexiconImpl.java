package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author Bryan Friestad
 *
 */
public class LexiconImpl implements Lexicon, Comparator<String> {

    /***
     * Lookup table mapping characters in lexicographical order to
     * to their input order. This is public to support automated grading. 
     */
	public CharacterValue[] characterOrdering; 

	
    /***
     * Creates an array of CharacterValue from characterOrdering.  Sorts
     * it using Arrays.sort().
     * @param characterOrdering character order from configuration file
     */	
	public LexiconImpl(char[] characterOrdering) {
		//TODO: I think this is done, but not 100% sure
		this.characterOrdering = new CharacterValue[characterOrdering.length];
		for(int i = 0; i < characterOrdering.length; i++){
			this.characterOrdering[i] = new CharacterValue(characterOrdering[i], i); //I honestly have no fucking clue if this will work
		}
		Comparator<CharacterValue> anonSorter = new Comparator<CharacterValue>(){

			@Override
			public int compare(CharacterValue o1, CharacterValue o2) {
				//this is all based of off ASCII codes, which is standard in java for comparing chars
				if(o1.character > o2.character) return 1; //1 if o1 is greater than o2
				else if(o1.character < o2.character) return -1; //-1 if o2 is greater than o1
				else return 0; //0 if o1 is == to o2
			}
			
		};
		Arrays.sort(this.characterOrdering, anonSorter); //this should sort this nicely :)
		/*
		System.out.println("Test: alphabet should be in \"java order\" and numbers should be based on the input alphabet");
		for(CharacterValue cv : this.characterOrdering){
			System.out.println(cv.character + ": " + cv.value);
		}
		*/
	}


    /***
     * Compares two words based on the configuration. 
     * @param a first word
     * @param b second word
     * @return negative if a < b, 0 if equal, positive if a > b
     */
	@Override
	public int compare(String a, String b) {
		if(a.length() == 0 && b.length() == 0){
			return 0;
		}
		else if(a.length() == 0 && b.length() > 0){
			return -1;
		}
		else if(a.length() > 0 && b.length() == 0){
			return 1;
		}
		char aChar = a.charAt(0);
		char bChar = b.charAt(0);
		if(getCharacterOrdering(aChar) == getCharacterOrdering(bChar)){
			return compare(a.substring(1), b.substring(1));
		}
		else if(getCharacterOrdering(aChar) > getCharacterOrdering(bChar)){
			return 1;
		}
		else{
			return -1;
		}
		//recursive function that basically send the string with the first letter(followed by next letter, etc) removed
		//if the first letters are the same, compare(a.substring(1), b.substring(1));
	}
	
	/**
	 * Uses binary search to find the order of key.
	 * @param key
	 * @return ordering value for key or -1 if key is an invalid character.
	 */
	public int getCharacterOrdering(char key) {
		int mid;
		int high = characterOrdering.length;
		int low = 0;
		while(high >= low){
			mid = low + ((high - low) / 2);
			if(characterOrdering[mid].character == key) return characterOrdering[mid].value;
			else if(characterOrdering[mid].character < key) low = mid + 1;
			else if(characterOrdering[mid].character > key) high = mid - 1;
		}
		return -1; 
	}

	/**
	 * Searches characterOrdering for key via binary search.
	 * This is public only to facilitate automated grading. 
	 * @param characterOrdering the specified sort order
         * @param key the search term
	 * @return ordering value for key or -1 if key is an invalid character.
	 */
	public static class CharacterValue {
		public int value;
		public char character;
		
		public CharacterValue(char char_in, int val_in){
			value = val_in;
			character = char_in;
		}
		//TODO: I think this is all that is needed, not sure (maybe equals)
	}
	
	/**
	 * Returns whether or not word is valid according to the alphabet
	 * known to this lexicon. 
	 * 
	 * @param word word to be checked.
	 *
	 * @return true if valid. false otherwise
	 */
	public boolean isValid(String word) {
		char[] temp = word.toCharArray();
		for(char c : temp){
			if(getCharacterOrdering(c) == -1) return false;
		}
		return true;
	}
	
}

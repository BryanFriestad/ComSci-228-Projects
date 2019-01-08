package edu.iastate.cs228.hw1;

/*
 * @author	Bryan Friestad
*/

public class CodingDNASequence extends DNASequence
{
	/**
	 * Takes cdnaarr and passes it onto the super-class, {@link DNASequence#DNASequence(char[])}, where
	 * it is checked to see if all characters in cdnaarr are valid
	 * 
	 * @param cdnaarr Is passed onto the super class and represents this sequence
	 */
	public CodingDNASequence(char[] cdnaarr)
	{
		super(cdnaarr);
	}

	/**
	 * Checks first if seqarr is long enough to have a starting codon,
	 * then it checks to see if the first three characters in the sequence
	 * are ATG (case insensitive).
	 * 
	 * @return True or False
	 */
	public boolean checkStartCodon()
	{
		if(seqLength() < 3) return false;
		else{
			if(seqarr[0] == 'a' || seqarr[0] == 'A'){
				if(seqarr[1] == 't' || seqarr[1] == 'T'){
					if(seqarr[2] == 'g' || seqarr[2] == 'G'){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Translates the sequence array into a character array of amino acids
	 * using the getAminoAcid function. If getAminoAcid returns a $, do not
	 * return that in the translated sequence.
	 * 
	 * @throws RuntimeException If the sequence does not start with a starting codon
	 * @return Returns the character array representing the translation of the DNA sequence into it's corresponding animo acid chain
	 */
	public char[] translate()
	{
		if(!checkStartCodon()){
			throw new RuntimeException("No start codon");
		}
	
		String s = new String();
		char temp;
		for(int i = 0; i < seqLength(); i += 3){
			temp = '$';
			if((seqLength() - 1 - i) >= 2){
				temp = getAminoAcid("" + seqarr[i] + seqarr[i+1] + seqarr[i+2]);
			}
			else if((seqLength() - 1 - i) == 1){
				temp = getAminoAcid("" + seqarr[i] + seqarr[i+1]);
			}
			else if((seqLength() - 1 - i) == 0){
				temp = getAminoAcid("" + seqarr[i]);
			}
			if(temp == '$') break;
			s += temp;
		}
		return s.toCharArray();
	}

	/**
	 * Translates a codon to it's corresponding amino acid. If no
	 * amino acid exsists for a given codon, it returns $.
	 * 
	 * @param codon The codon to translate
	 * @return The corresponding amino acid character, or it returns $, which signifies the end of the chain
	 */
	private char getAminoAcid(String codon)
	{
		if ( codon == null ) return '$';
		char aa = '$';
		switch ( codon.toUpperCase() )
		{
			case "AAA": aa = 'K'; break;
			case "AAC": aa = 'N'; break;
			case "AAG": aa = 'K'; break;
			case "AAT": aa = 'N'; break;

			case "ACA": aa = 'T'; break;
			case "ACC": aa = 'T'; break;
			case "ACG": aa = 'T'; break;
			case "ACT": aa = 'T'; break;

			case "AGA": aa = 'R'; break;
			case "AGC": aa = 'S'; break;
			case "AGG": aa = 'R'; break;
			case "AGT": aa = 'S'; break;

			case "ATA": aa = 'I'; break;
			case "ATC": aa = 'I'; break;
			case "ATG": aa = 'M'; break;
			case "ATT": aa = 'I'; break;

			case "CAA": aa = 'Q'; break;
			case "CAC": aa = 'H'; break;
			case "CAG": aa = 'Q'; break;
			case "CAT": aa = 'H'; break;

			case "CCA": aa = 'P'; break;
			case "CCC": aa = 'P'; break;
			case "CCG": aa = 'P'; break;
			case "CCT": aa = 'P'; break;

			case "CGA": aa = 'R'; break;
			case "CGC": aa = 'R'; break;
			case "CGG": aa = 'R'; break;
			case "CGT": aa = 'R'; break;

			case "CTA": aa = 'L'; break;
			case "CTC": aa = 'L'; break;
			case "CTG": aa = 'L'; break;
			case "CTT": aa = 'L'; break;

			case "GAA": aa = 'E'; break;
			case "GAC": aa = 'D'; break;
			case "GAG": aa = 'E'; break;
			case "GAT": aa = 'D'; break;

			case "GCA": aa = 'A'; break;
			case "GCC": aa = 'A'; break;
			case "GCG": aa = 'A'; break;
			case "GCT": aa = 'A'; break;

			case "GGA": aa = 'G'; break;
			case "GGC": aa = 'G'; break;
			case "GGG": aa = 'G'; break;
			case "GGT": aa = 'G'; break;

			case "GTA": aa = 'V'; break;
			case "GTC": aa = 'V'; break;
			case "GTG": aa = 'V'; break;
			case "GTT": aa = 'V'; break;

			case "TAA": aa = '$'; break;
			case "TAC": aa = 'Y'; break;
			case "TAG": aa = '$'; break;
			case "TAT": aa = 'Y'; break;

			case "TCA": aa = 'S'; break;
			case "TCC": aa = 'S'; break;
			case "TCG": aa = 'S'; break;
			case "TCT": aa = 'S'; break;

			case "TGA": aa = '$'; break;
			case "TGC": aa = 'C'; break;
			case "TGG": aa = 'W'; break;
			case "TGT": aa = 'C'; break;

			case "TTA": aa = 'L'; break;
			case "TTC": aa = 'F'; break;
			case "TTG": aa = 'L'; break;
			case "TTT": aa = 'F'; break;
			default:    aa = '$'; break;
		}
		return aa;
	}
}
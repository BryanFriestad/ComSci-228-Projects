package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author Bryan Friestad
 *
 */
public class MergeSort extends SorterWithStatistics {
	
	//This method will be called by the base class sort() method to 
	// actually perform the sort. 
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		String[] workingArray = new String[words.length];
		mergeSort(words, workingArray, 0, words.length - 1, comp);
		
		/*
		//just a test
		for(String s: words){
			System.out.println(s);
		}
		*/
	}
	
	private void mergeSort(String[] words, String[] work, int start, int end, Comparator<String> alphabet){
		if(start >= end){
			return;
		}
		
		int middle = (start + end) / 2;
		mergeSort(words, work, start, middle, alphabet);
		mergeSort(words, work, middle + 1, end, alphabet);
		
		int i, j;
		int k = start;
		for(i = start, j = middle + 1; i <= middle && j <= end; ){
			if(alphabet.compare(words[j], words[i]) >= 0){
				work[k] = words[i];
				k++;
				i++;
			}
			else{
				work[k] = words[j];
				k++;
				j++;
			}
		}
		while(i <= middle){
			work[k] = words[i];
			k++;
			i++;
		}
		while(j <= end){
			work[k] = words[j];
			k++;
			j++;
		}
		
		for(k = start; k <= end; k++){
			words[k] = work[k];
		}
	}

	@Override
	public String getReport(){
		return "Merge Sort(O(n logn)):\n" + super.getReport();
	}
}

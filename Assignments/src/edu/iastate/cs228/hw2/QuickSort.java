package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author Bryan Friestad
 *
 */
public class QuickSort extends SorterWithStatistics {

	//This method will be called by the base class sort() method to 
	// actually perform the sort. 
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		quickSort(words, 0, words.length - 1, comp);
		
		/* Seems to work!
		//just for a test
		for(String s: words){
			System.out.println(s);
		}
		*/
	}
	
	private void quickSort(String[] words, int start, int end, Comparator<String> alphabet){
		if(start >= end){
			return;
		}
		else{
			int pivot = makePartition(words, start, end, alphabet);
			quickSort(words, start, pivot, alphabet);
			quickSort(words, pivot + 1, end, alphabet);
		}
	}
	
	private int makePartition(String[] words, int start, int end, Comparator<String> alphabet){
		int pivot = end;
		int low = start;
		int high = end - 1;
		while(true){
			while(alphabet.compare(words[pivot], words[low]) > 0){
				low++;
			}
			while(alphabet.compare(words[pivot], words[high]) < 0){
				high++;
			}
			if(high > low){
				//swap
				String temp = words[low];
				words[low] = words[high];
				words[high] = temp;
				low++;
				high++;
			}
			else{
				break;
			}
		}
		return high;
	}
	
	@Override
	public String getReport(){
		return "Quick Sort(O(n^2)):\n" + super.getReport();
	}
}

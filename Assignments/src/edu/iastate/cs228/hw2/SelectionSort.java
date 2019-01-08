package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author Bryan Friestad
 *
 */
public class SelectionSort extends SorterWithStatistics {
	
	//This method will be called by the base class sort() method to 
	// actually perform the sort. 
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		int pos;
		for(int i = 0; i < words.length; i++){
			pos = i;
			for(int j = i + 1; j < words.length; j++){
				if(comp.compare(words[pos], words[j]) > 0){
					pos = j;
				}
			}
			if(pos != i){
				String temp = words[pos];
				words[pos] = words[i];
				words[i] = temp;
			}
		}
		
		/*
		//just a test
		for(String s: words){
			System.out.println(s);
		}
		*/
	}
	
	@Override
	public String getReport(){
		return "Selection Sort(O(n^2)):\n" + super.getReport();
	}
}

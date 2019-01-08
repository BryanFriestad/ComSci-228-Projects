package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;

public class Test {

	public static void main(String[] args) {

		char[] charOrder = {'a', 'b', 'c', 'G', 'g', 'B', 'C', ' ', '	', '1', '5', '3'};
		CharacterValue[] charValueOrder = new CharacterValue[charOrder.length];
		for(int i = 0; i < charOrder.length; i++){
			charValueOrder[i] = new CharacterValue(charOrder[i], i); //I honestly have no fucking clue if this will work
		}
		Comparator<CharacterValue> test = new Comparator<CharacterValue>(){

			@Override
			public int compare(CharacterValue o1, CharacterValue o2) {
				//-1 if o2 is greater than o1
				//1 if o1 is greater than o2
				//0 if o1 is == to o2
				if(o1.character > o2.character) return 1;
				else if(o1.character < o2.character) return -1;
				else return 0;
			}
		};
		Arrays.sort(charValueOrder, test);
		for(CharacterValue cv: charValueOrder){
			System.out.println(cv.character + ": " + cv.value);
		}
		
		char goal = ' ';
		int mid;
		int high = charValueOrder.length;
		int low = 0;
		int passes = 0;
		while(high >= low){
			passes++;
			mid = low + ((high - low) / 2);
			if(charValueOrder[mid].character == goal){
				System.out.println(charValueOrder[mid].character + " found in " + passes + " passes.");
				break;
			}
			else if(charValueOrder[mid].character < goal) low = mid + 1;
			else if(charValueOrder[mid].character > goal) high = mid - 1;
		}
		if(high < low) System.out.println("not found :(");
	}
	public static class CharacterValue{
		
		private char character;
		private int value;
		public CharacterValue(char c, int val)
		{
			character = c;
			value = val;
		}
			
	}
}

package edu.iastate.cs228.hw4;

import java.util.Arrays;

public class Tester {

	public static void main(String[] args) {
		Character[] key = {'I', 'S', 'U'};
		Character[] key2 = Arrays.copyOfRange(key, 1, key.length);
		Character[] key3 = {'i', 's', 'u'};
		Character[] key4 = {'i', 's', 'u'};
		Character[] key5 = Arrays.copyOf(key4, key4.length);
		System.out.println(key);
		System.out.println(key2);
		
		System.out.println("should be false: " + key.equals(key2));
		System.out.println("should be true: " + key.equals(key)); //the only one that is true :(
		System.out.println("should also be false: " + key2.equals(key));
		System.out.println("should be false: " + key3.equals(key));
		System.out.println("should be false: " + key.equals(key3));
		System.out.println("should be true: " + key3.equals(key4)); //:(
		System.out.println("should be true: " + key4.equals(key5)); //:'(
		System.out.println("should be true: " + key4[0].equals(key5[0]));
		System.out.println("should be false: " + key[0].equals(key3[0]));
		
		System.out.println();
		String before = "  hello      bryan ";
		System.out.println(before);
		String after = before.trim().replaceAll("( )+", " ");
		System.out.println(after);
		String[] splt = after.split(" ");
		System.out.println(splt[0]);
		System.out.println(splt[1]);
		//System.out.println(splt[2]);

		
		System.out.println();
		String before2 = "  hello      bryan edit   ";
		System.out.println(before2);
		String[] splt2 = before2.trim().split("( )+");
		System.out.println(splt2[0]);
		System.out.println(splt2[1] == "bryan"); //false
		System.out.println(splt2[2].equals("edit")); //true
		//System.out.println(splt2[3]);
	}

}

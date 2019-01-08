package edu.iastate.cs228.hw2;

import java.util.Scanner;

public class Test2 {

	public static void main(String[] args) {
		char one = 'a';
		char two = 'b';
		char three = 'A';
		char four = ' ';
		char five = '	';
		
		System.out.println(one > two); //should be false
		System.out.println(one > three); //not sure -- true
		System.out.println(one == three); //could be true -- false
		System.out.println(one == two); //should be false
		System.out.println(one > four); //no idea -- true
		System.out.println(one > five); //no idea again -- true
		System.out.println("Hello\nHello.");
		
		String s = "";
		Scanner scan = new Scanner(s);
		String s2 = scan.nextLine();
		char[] carr = s.toCharArray();
		System.out.println(s2.length());
		
		for(int i = 0; i < carr.length; i++){
			System.out.println(carr[i]);
		}

	}

}

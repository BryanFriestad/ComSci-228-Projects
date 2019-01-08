package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;

/**
 * @author Bryan Friestad
 * 
 *         An application class
 */
public class Dictionary {
	public static void main(String[] args) {
		// TODO
		EntryTree<Character, String> tree = new EntryTree<Character, String>();
		String infile = args[0];
		List<String> commandList = setUpInputFile(infile);
		for(String s : commandList){
			doCommand(s, tree);
		}
		
	}
	
	public static void doCommand(String command, EntryTree<Character, String> tree){
		if(command.isEmpty()){
			return;
		}
		String[] splitArr = command.trim().split("( )+");
		if(splitArr[0].equals("showTree")){
			tree.showTree();
		}
		if(splitArr[0].equals("add")){
			Character[] keyarr = toCharacterArray(splitArr[1]);
			System.out.println(tree.add(keyarr, splitArr[2]));
		}
		if(splitArr[0].equals("remove")){
			Character[] keyarr = toCharacterArray(splitArr[1]);
			System.out.println(tree.remove(keyarr));
		}
		if(splitArr[0].equals("prefix")){
			Character[] keyarr = toCharacterArray(splitArr[1]);
			Object[] temp = tree.prefix(keyarr);
			for(Object c : temp){
				System.out.printf("%c", c);
			}
			System.out.printf("\n");
		}
		if(splitArr[0].equals("search")){
			Character[] keyarr = toCharacterArray(splitArr[1]);
			System.out.println(tree.search(keyarr));
		}
	}
	
	private static Character[] toCharacterArray(String s){
		Character[] out = new Character[s.length()];
		for(int i = 0; i < s.length(); i++){
			out[i] = new Character(s.charAt(i));
		}
		return out;
	}
	
	private static ArrayList<String> setUpInputFile(String in){
		ArrayList<String> output = new ArrayList<String>();
		String line;
		BufferedReader file;
		try{
			file = new BufferedReader(new FileReader(in));
			try {
				while((line = file.readLine()) != null){
					output.add(line);
				}
				file.close();
			}
			catch (IOException e){
				System.out.println("Error IO Exception");
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e){
			System.out.println(in + " not found");
			e.printStackTrace();
		}
		return output;
	}
}
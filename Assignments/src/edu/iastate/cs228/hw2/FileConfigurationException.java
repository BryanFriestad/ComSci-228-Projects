package edu.iastate.cs228.hw2;

/**
 * 
 * @author Bryan Friestad
 *
 */
public class FileConfigurationException extends Exception {
	
	public FileConfigurationException(String err){
		System.err.println(err);
	}
	
}
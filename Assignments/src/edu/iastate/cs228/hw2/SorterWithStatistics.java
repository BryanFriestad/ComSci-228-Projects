package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * 
 * @author Bryan Friestad
 *
 */
public abstract class SorterWithStatistics implements Sorter {

	private Stopwatch timer = new Stopwatch();
	private int totalWordsSorted, lastSortNumWords;
	private long totalTime, lastSortTime; //90% sure this is in nanoseconds
	private boolean hasSortedSomething;

	/***
	 * Default constructor
	 */
	public SorterWithStatistics() {
		totalWordsSorted = 0;
		totalTime = 0L;
		hasSortedSomething = false;
	}

	/***
	 * Public interface to sortHelper that keeps track of performance
	 * statistics, including counting words sorted and timing sort instances.
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param comp
	 *            Comparator used to sort the input array.
	 */
	public void sort(String[] words, Comparator<String> comp) {
		hasSortedSomething = true;
		lastSortNumWords = 0;
		lastSortTime = 0;
		timer.start();
		sortHelper(words, comp);
		timer.stop();
		lastSortNumWords = words.length;
		lastSortTime = timer.getElapsedTime();
		totalWordsSorted += lastSortNumWords;
		totalTime += lastSortTime;
	}

	/**
	 * Sorts the array words.
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param comp
	 *            Comparator used to sort the input array.
	 */
	protected abstract void sortHelper(String[] words, Comparator<String> comp);

	/**
	 * Returns number of words sorted in last sort. Throws IllegalStateException
	 * if nothing has been sorted.
	 * 
	 * @return number of words sorted in last sort.
	 */
	public int getWordsSorted() {
		if(!hasSortedSomething){
			throw new IllegalStateException("Nothing has been sorted by this object yet");
		}
		return lastSortNumWords;
	}

	/**
	 * Returns time the last sort took. Throws IllegalStateException if nothing
	 * has been sorted.
	 * 
	 * @return time last sort took in nanoseconds.
	 */
	public long getTimeToSortWords() {
		if(!hasSortedSomething){
			throw new IllegalStateException("Nothing has been sorted by this object yet");
		}
		return lastSortTime;
	}

	/**
	 * Returns total words sorted by this instance.
	 * 
	 * @return total number of words sorted.
	 */
	public int getTotalWordsSorted() {
		return totalWordsSorted;
	}

	/**
	 * Returns the total amount of time spent sorting by this instance.
	 * 
	 * @return total time spent sorting in nanoseconds
	 */
	public long getTotalTimeToSortWords() {
		return totalTime;
	}

	/**
	 * @return a summary of statistics for the last sorting run.
	 * 
	 *         See the project description for details about what to include.
	 *         This method should NOT generate output directly.
	 */
	public String getReport() {
		/*  Length of the word list
		 *  Total number of words sorted
		 *  Total time spent sorting
		 *  Average time required to sort the word list
		 *  Words sorted per second
		 */
		String retString = new String();
		
		retString += ("Total length of word list: " + getWordsSorted() + " words.\n");
		retString += ("Total number of words sorted: " + getTotalWordsSorted() + " words.\n");
		retString += ("Total time spent sorting: " + getTotalTimeToSortWords() + " nanoseconds, or " + ((double) getTotalTimeToSortWords() / 1000000000.0) + " seconds.\n");
		retString += ("Average time needed to sort the word list: " + (((double) getTotalTimeToSortWords() / 1000000000.0) / ((double) getTotalWordsSorted() / (double) getWordsSorted()))  + " seconds.\n");
		retString += ("Average number of words sorted per second: " + ((double) getTotalWordsSorted() / ((double) getTotalTimeToSortWords() / 1000000000.0)) + " words per second.\n");
		
		return retString;
	}
}

package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * @author Bryan Friestad
 *
 * An entry tree class
 * Add Javadoc comments to each method
 */
public class EntryTree<K, V> {
	/**
	 * dummy root node made public for grading
	 */
	protected Node root;
	
	/**
	 * prefixlen is the largest index such that the keys in the subarray keyarr
	 * from index 0 to index prefixlen - 1 are, respectively, with the nodes on
	 * the path from the root to a node. prefixlen is computed by a private
	 * method shared by both search() and prefix() to avoid duplication of code.
	 */
	protected int prefixlen;

	protected class Node implements EntryNode<K, V> {
		protected Node child; // link to the first child node
		protected Node parent; // link to the parent node
		protected Node prev; // link to the previous sibling
		protected Node next; // link to the next sibling
		protected K key; // the key for this node
		protected V value; // the value at this node

		public Node(K aKey, V aValue) {
			key = aKey;
			value = aValue;
			child = null;
			parent = null;
			prev = null;
			next = null;
		}

		@Override
		public EntryNode<K, V> parent() {
			return (EntryNode<K, V>) parent;
		}

		@Override
		public EntryNode<K, V> child() {
			return (EntryNode<K, V>) child;
		}

		@Override
		public EntryNode<K, V> next() {
			return (EntryNode<K, V>) next;
		}

		@Override
		public EntryNode<K, V> prev() {
			return (EntryNode<K, V>) prev;
		}

		@Override
		public K key() {
			return key;
		}

		@Override
		public V value() {
			return value;
		}
	}

	public EntryTree() {
		root = new Node(null, null);
	}
	
	/**
	 * Returns whether or not the tree is empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty(){
		return (root.child == null);
	}

	/**
	 * Returns the value of the entry with a specified key sequence, or null if
	 * this tree contains no entry with the key sequence.
	 * 
	 * @param keyarr
	 * @return V
	 */
	public V search(K[] keyarr) {
		if(keyarr == null || keyarr.length == 0) return null;
		for(K k : keyarr){
			if(k == null) throw new NullPointerException("No index of keyarr can be null");
		}
		if(isEmpty()) return null;
		Node temp = findNodeRec(root.child, keyarr);
		if(temp == null) return null;
		K[] tempArr = getKeyArrOf(temp);
		if(keyArrsEqual(tempArr, keyarr)){
			return temp.value;
		}
		else{
			return null;
		}
	}
	
	/**
	 * Either returns the node at the end of the given keyarr OR returns the
	 * node whose key array is a prefix of keyarr, but whose child field is null
	 * 
	 * @param startNode the node the first check the key field of against the value of the first index of keyarr
	 * @param keyarr the Key Array which you want to find the node of
	 * @return Node
	 */
	private Node findNodeRec(Node startNode, K[] keyarr){ //we will start by passing in root.child //the only way this should return null is if startNode is null
		if(keyarr == null) throw new IllegalArgumentException("keyarr cannot be null");
		if(keyarr[0] == null) throw new NullPointerException("No index of keyarr can be null"); //i will leave this here, but it shouldn't be thrown here ever
		if(startNode == null) return null;
		if(startNode.key.equals(keyarr[0])){
			if(!(keyarr.length > 1)){
				return startNode;
			}
			else{
				Node temp = findNodeRec(startNode.child, Arrays.copyOfRange(keyarr, 1, keyarr.length));
				if(temp == null){
					return startNode;
				}
				else{
					return temp;
				}
			}
		}
		else{
			return findNodeRec(startNode.next, keyarr); //this is checking the next node and will return null if there is no next
		}
	}

	/**
	 * The method returns an array of type K[] with the longest prefix of the
	 * key sequence specified in keyarr such that the keys in the prefix label
	 * the nodes on the path from the root to a node. The length of the returned
	 * array is the length of the longest prefix.
	 * 
	 * @param keyarr
	 * @return K[]
	 */
	public K[] prefix(K[] keyarr) {
		if(keyarr == null || keyarr.length == 0) return null;
		for(K k : keyarr){
			if(k == null) throw new NullPointerException("No index of keyarr can be null");
		}
		prefixlen = 0;
		Node longest = findNodeRec(root.child, keyarr);
		if(longest == null){
			return null; //this means that the root was the farthest we could go down
		}
		else{
			K[] newKeyArr = getKeyArrOf(longest); //this goes from the leaf to the root, adding new keys to the front of the array
			for(K k : newKeyArr){
				k = (K) k; //idk trying to make sure that it's a character??
			}
			return newKeyArr;
		}
	}
	
	/**
	 * A private recursive method for internal use. This function returns the keyarr of the passed
	 * in node and adds it to the front of the passed array.
	 * 
	 * @param end Node
	 * @param keyArr K[]
	 * @return K[]
	 */
	private K[] prefixRec(Node end, K[] keyArr){
		keyArr = Arrays.copyOf(keyArr, keyArr.length + 1); //create new array that is one longer
		for(int i = keyArr.length - 1; i > 0; i--){
			keyArr[i] = keyArr[i-1]; //shifts all elements right one space in the array
		}
		keyArr[0] = end.key; //adds new key to the front
		prefixlen++;
		if(end.parent == root){
			return keyArr;
		}
		else{
			return prefixRec(end.parent, keyArr);
		}
	}
	
	/**
	 * Function that calls prefixRec and passes in an empty keyArr. This is the main way to use the function
	 * and this wrapper function makes it more versitile.
	 * 
	 * @param end Node
	 * @return K[]
	 */
	private K[] getKeyArrOf(Node end){
		return prefixRec(end, (K[]) new Object[0]);
	}

	/**
	 * The method locates the node P corresponding to the longest prefix of the
	 * key sequence specified in keyarr such that the keys in the prefix label
	 * the nodes on the path from the root to the node. If the length of the
	 * prefix is equal to the length of keyarr, then the method places aValue at
	 * the node P and returns true. Otherwise, the method creates a new path of
	 * nodes (starting at a node S) labelled by the corresponding suffix for the
	 * prefix, connects the prefix path and suffix path together by making the
	 * node S a child of the node P, and returns true.
	 * 
	 * @param keyarr
	 * @param aValue
	 * @return boolean
	 */
	public boolean add(K[] keyarr, V aValue) {
		
		if(keyarr == null || aValue == null || keyarr.length == 0) return false;
		for(K k : keyarr){
			if(k == null) throw new NullPointerException("No index of keyarr can be null");
		}
		
		K[] pfx = prefix(keyarr);
		if(pfx == null){ //this means we are at the root because there is no prefix
			addRec(root, keyarr, aValue);
			return true;
		}
		else{
			if(keyArrsEqual(keyarr, pfx)){ //yay it already exists! all we need to do is change the value
				Node temp = findNodeRec(root.child, pfx); //find the node at the end of the prefix
				temp.value = aValue; //basically we are just changing the value here
				return true;
			}
			else{
				Node temp = findNodeRec(root.child, pfx); //find the node at the end of the prefix
				if(prefixlen != pfx.length) throw new RuntimeException("prefixlen is: " + prefixlen + " and pfx.length is: " + pfx.length);
				addRec(temp, Arrays.copyOfRange(keyarr, pfx.length, keyarr.length) , aValue);
				return true;
			}
		}
	}
	
	/**
	 * Checks to see if two passed key arrays are equal to each other. This is true if
	 * every element of the keyarr1 is equal to the corresponding element in keyarr2.
	 * 
	 * @param keyarr1 K[]
	 * @param keyarr2 K[]
	 * @return boolean
	 */
	private boolean keyArrsEqual(K[] keyarr1, K[] keyarr2){
		if(keyarr1.length != keyarr2.length){
			return false;
		}
		if(keyarr1 == keyarr2 || ((keyarr1 != null && keyarr2 != null) && keyarr1.equals(keyarr2))){
			return true;
		}
		for(int i = 0; i < keyarr1.length; i++){
			if( !keyarr1[i].equals(keyarr2[i]) ) return false;
		}
		return true;
	}
	
	/**
	 * private recursive method used to create and add, as a child of startFrom, a string of Nodes whose keys
	 * correspond with the keys in keyarr and in which the node at the end of the string's value
	 * is the passed variable value.
	 * 
	 * @param startFrom Node
	 * @param keyarr K[]
	 * @param value V
	 */
	private void addRec(Node startFrom, K[] keyarr, V value){
		Node toAdd;
		if(keyarr.length == 0){
			return;
		}
		else if(keyarr.length == 1){ //this is the last or only index in the keyarr, so we can add the value
			toAdd = new Node(keyarr[0], value);
		}
		else{
			toAdd = new Node(keyarr[0], null);
		}
		if(startFrom.child != null){//it already has a child, so try next until it's null
			Node parentNode = startFrom; //might wanna keep track of this
			startFrom = startFrom.child;
			while(startFrom.next != null){ //next is also taken, try the next next
				startFrom = startFrom.next;
			}
			if(startFrom.next != null) throw new RuntimeException("startFrom.next fucked up bro");
			startFrom.next = toAdd; //there is now a link to toAdd
			toAdd.prev = startFrom; //toAdd now links back to other things
			toAdd.parent = parentNode; //i knew this would come in handy 
			addRec(toAdd, Arrays.copyOfRange(keyarr, 1, keyarr.length), value);
			return;
		}
		else{ //startFrom does not have a child, cool!
			startFrom.child = toAdd;
			toAdd.parent = startFrom;
			addRec(toAdd, Arrays.copyOfRange(keyarr, 1, keyarr.length), value);
			return;
		}
	}

	/**
	 * Removes the entry for a key sequence from this tree and returns its value
	 * if it is present. Otherwise, it makes no change to the tree and returns
	 * null.
	 * 
	 * @param keyarr
	 * @return V
	 */
	public V remove(K[] keyarr) {
		V temp;
		if(keyarr == null || keyarr.length == 0) return null;
		for(K k : keyarr){
			if(k == null) throw new NullPointerException("No index of keyarr can be null");
		}
		Node look = findNodeRec(root.child, keyarr);
		if(look == null){ //is at the root
			return null;
		}
		K[] keyArrOfLook = getKeyArrOf(look);
		if(!keyArrsEqual(keyArrOfLook, keyarr)){
			return null; //no such entry exists
		}
		else{
			temp = look.value;
			look.value = null;
			removeRec(look);
		}
		return temp;
	}
	
	/**
	 * A private recursive method used to remove a node and any therefore unneeded parent nodes.
	 * The function starts at Node bottom and works it's way up until it finds a relevant node, then it stops deleting nodes.
	 * 
	 * @param bottom Node
	 */
	private void removeRec(Node bottom){
		if(bottom.child != null || bottom.value != null || bottom == root){ //if the node has a value or a child we can't remove it
			return;
		}
		if(bottom.prev == null){ //if bottom is the first child of parent
			Node par = bottom.parent;
			par.child = bottom.next; //bottom.next could very easily be null
			if(bottom.next != null){//throws nullPointerException if bottom.next doesn't exist
				bottom.next.prev = null; //nothing links back to it now
			}
			bottom.next = null;
			bottom.parent = null;
			removeRec(par);
		}
		else{//this means there is a node before this node and the parent CANNOT be removed
			bottom.prev.next = bottom.next; //removing links to this node
			bottom.next.prev = bottom.prev;
			bottom.parent = null; //removing links from this node for safety
			bottom.prev = null;
			bottom.next = null;
			return; //again, there is a defined previous node, so the parent can't be removed and we now return
		}
	}

	/**
	 * The method prints the tree on the console in the output format shown in
	 * an example output file.
	 */
	public void showTree() {
		Node start = root;
		System.out.printf("%c->%s\n", start.key, start.value);
		showTreeRec(start.child, 6);
	}
	
	/**
	 * A private recursive method that is used by showTree to show the entire tree. V fancy
	 * 
	 * @param n Node
	 * @param numSpaces int
	 */
	private void showTreeRec(Node n, int numSpaces){
		if(n == null){
			return;
		}
		for(int i = 0; i < numSpaces; i++){
			System.out.printf(" ");
		}
		System.out.printf("%c->%s\n", n.key, n.value);
		showTreeRec(n.child, numSpaces + 2);
		showTreeRec(n.next, numSpaces);
	}
}
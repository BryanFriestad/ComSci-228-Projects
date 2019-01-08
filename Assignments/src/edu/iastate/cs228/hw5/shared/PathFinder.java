package edu.iastate.cs228.hw5.shared;

import java.util.ArrayList;

/**
 * @author
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PathFinder
{

    /**
     * This member always holds the cost of the path (if any)
     * found by the most recently finished solving operation. 
     * MIN_VALUE is used to signal that the value is not yet valid. 
     */
    public static int lastCost = Integer.MIN_VALUE;
    
	/**
	 * First, computes a shortest path from a source vertex to a destination
	 * vertex in a graph by using Dijkstra's algorithm. Second, visits and saves
	 * (in a stack) each vertex in the path, in reverse order starting from the
	 * destination vertex, by using the map object pred. Third, uses a
	 * List and Stack to generate the return Integer List by first pushing 
	 * each vertex into the stack, and then poping vertices 
	 * from the stack and adding the index of each to the 
	 * return list. The vertex indices in the return object are now in the
	 * right order. Note that the getIndex() method is called from a
	 * BareV object (vertex) to get its original integer name.
	 *
	 * @param g
	 *            - The graph in which a shortest path is to be computed
	 * @param source
	 *            - The first vertex of the shortest path
	 * @param dest
	 *            - The last vertex of the shortest path
	 *            
	 * @return A List of Integers corresponding the the vertices on the path
	 *         in order from source to dest. 
	 *
	 *         The contents of an example String object: Path Length: 5 Path
	 *         Cost: 4 Path: 0 4 2 5 7 9
	 *
	 * @throws NullPointerException
	 *             - If any arugment is null
	 * @throws RuntimeException
	 *             - If the given source or dest vertex is not in the graph
	 *
	 */
    public static List<Integer> findPath(BareG g, BareV source, BareV dest)
    {
          lastCost = Integer.MIN_VALUE;
          if(g == null || source == null || dest == null) throw new NullPointerException("An argument to findPath was null");
          if(!g.checkVertex(source)) throw new RuntimeException("Source vertex does not exist");
          if(!g.checkVertex(dest)) throw new RuntimeException("Destination vertex does not exist");
          
          Heap<Vpair<BareV, Integer>> verts = new Heap<Vpair<BareV, Integer>>();
          HashMap<BareV, Integer> dist = new HashMap<BareV, Integer>(); //Hashmap for distances
          HashMap<BareV, BareV> pred = new HashMap<BareV, BareV>(); //HashMap for predecessors
          HashSet<BareV> visited = new HashSet<BareV>(); //this will be a list of the vertices that have been "current" in the past
          
          pred.put(source, null);
          verts.add(new Vpair<BareV, Integer>(source, 0));
          dist.put(source, 0);
          while(!verts.isEmpty()){
        	  Vpair<BareV, Integer> current = verts.removeMin();
        	  BareV currVert = (BareV) current.getVertex();
        	  
        	  if(!visited.contains(currVert)){ //checks to see if it has been visited before
        		  
        		  visited.add(currVert);
	        	  //dist.put(currVert, (Integer) current.getCost());
	        	  Iterator<BareE> iter = (Iterator<BareE>) currVert.getBareEdges().iterator();
	        	  while(iter.hasNext()){
	        		  BareE edge = iter.next();
	        		  BareV nextVert = edge.getToVertex();
	        		  Integer currDist = dist.get(nextVert); //will be a number if the vertex is already in the distance hashmap, else NULL
	        		  Integer newDist = dist.get(currVert) + edge.getWeight(); //the new distance is the distance to the current vertex + the weight of the current edge
	        		  if(currDist == null || (newDist < currDist)){ //if there is no currently calculated distance, or if the new path is shorter
	        			  verts.add(new Vpair<BareV, Integer>(nextVert, newDist));
	        			  dist.put(nextVert, newDist);
	        			  pred.put(nextVert, currVert);
	        		  }
	        	  }
        	  }
          }
          
          lastCost = dist.get(dest);
          BareV temp = dest;
          List<Integer> output = new ArrayList<Integer>();
          LinkedStack<BareV> stack = new LinkedStack<BareV>();
          while(temp != null){
        	  stack.push(temp);
        	  temp = pred.get(temp);
          }
          while(!stack.isEmpty()){
        	  output.add(stack.pop().getIndex());
          }
          System.out.println("number of vertices visited: " + visited.size()); //this should print out the number of vertices
          return output; 
    }
    
    /**
     * This function recursively adds all connected vertexes in a graph to the ArrayList "verts"
     * when given a starting vertex "start".
     * 
     * @param verts ArrayList<Vpair<BareV, Integer>>
     * @param start BareV
     */
    private static void addRec(ArrayList<Vpair<BareV, Integer>> verts, BareV start)
    {
    	Vpair<BareV, Integer> temp;
    	if(verts.isEmpty()){
    		temp = new Vpair<BareV, Integer>(start, 0); //the list is empty, which means we are adding the source vertex, so it's distance is zero
    	}
    	else{
    		temp = new Vpair<BareV, Integer>(start, Integer.MAX_VALUE); //basically infinity
    	}
    	if(start == null || verts.contains(temp))
    		return;
    	verts.add(temp);
    	Iterator<BareE> iter = (Iterator<BareE>) start.getBareEdges().iterator();
    	while(iter.hasNext()){
    		addRec(verts, iter.next().getToVertex());
    	}
    	return;
    }
    
    /**
     * A pair class with two components of types V and C, where V is a vertex
     * type and C is a cost type.
     */
    private static class Vpair<V, C extends Comparable<? super C>> implements Comparable<Vpair<V, C>>
    {
        private V node;
        private C cost;

        Vpair(V n, C c)
        {
            node = n;
            cost = c;
        }

        public V getVertex()
        {
            return node;
        }

        public C getCost()
        {
            return cost;
        }

        public int compareTo(Vpair<V, C> other)
        {
            return cost.compareTo(other.getCost());
        }

        public String toString()
        {
            return "<" + node.toString() + ", " + cost.toString() + ">";
        }

        public int hashCode()
        {
            return node.hashCode();
        }

        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if ((obj == null) || (obj.getClass() != this.getClass()))
                return false;
            // object must be Vpair at this point
            Vpair<?, ?> test = (Vpair<?, ?>) obj;
            return (node == test.node || (node != null && node
                    .equals(test.node)));
        }
    }
}
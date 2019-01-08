import java.util.*;

/**
 * An edge class as a pair class with two components of types V and C, where
 * V is a vertex type and C is a cost type.
 */

class Edge<V, C extends Comparable<? super C> > implements Comparable<Edge<V, C>>
   {
     private V  node;
     private C  cost;

     Edge(V n, C c)
     {
       node = n;
       cost = c;
     }

     public V getVertex() { return node;}
     public C getCost() { return cost;}
     public int compareTo( Edge<V, C> other )
     {
       return cost.compareTo(other.getCost() );
     }

     public String toString()
     {
       return "<" +  node.toString() + ", " + cost.toString() + ">";
     }

     public int hashCode()
     {
       return node.hashCode();
     }

     public boolean equals(Object obj)
     {
       if(this == obj) return true;
       if((obj == null) || (obj.getClass() != this.getClass()))
        return false;
       // object must be Edge at this point
       Edge<?, ?> test = (Edge<?, ?>)obj;
       return
         (node == test.node || (node != null && node.equals(test.node)));
//       (node == test.node || (node != null && node.equals(test.node))) &&
//       (cost == test.cost || (cost != null && cost.equals(test.cost)));
     }

   }

public class DiGraph<V> {


    // symbol table: key = string vertex, value = set of neighboring vertices
    private HashMap<V, HashSet< Edge<V, Integer> > > map;

    private Heap<Edge<V, Integer>> priq;

    // number of edges
    private int E;

    // create an empty graph
    public DiGraph() {
        map = new HashMap<V, HashSet< Edge<V, Integer> > >();
        priq = new Heap<Edge<V, Integer>> ();
    }

    // read a graph from an input stream
    /*
    public DiGraph(In in, String delimiter) {
        st = new ST<String, SET<String>>();
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] names = line.split(delimiter);
            for (int i = 1; i < names.length; i++) {
                addEdge(names[0], names[i]);
            }
        }
    }
    */


    // return number of vertices and edges
    /*
    public int V() { return st.size(); }
    public int E() { return E;         }

    // return the degree of vertex v 
    public int degree(String v) {
        if (!st.contains(v)) return 0;
        else return st.get(v).size();
    }
    */

    // add t to f's set of neighbors.
    public void addEdge(V f, V t, Integer c) {
        if (!hasEdge(f, t)) E++;
        if (!hasVertex(f)) addVertex(f);
        map.get(f).add( new Edge<V, Integer>(t, c) );
        if (!hasVertex(t)) addVertex(t);
    }

    // add a new vertex f with no neighbors (if vertex does not yet exist)
    public void addVertex(V f) {
        if (!hasVertex(f)) map.put(f, new HashSet< Edge<V, Integer> >());
    }

    // return iterator over all vertices in graph
    public Iterable<V> vertices()
    {
        return map.keySet();
    }

    // return an iterator over the neighbors of vertex f
    public Iterable<Edge<V, Integer>> adjacentTo(V f) {
        // return empty set if vertex isn't in graph
        if (!hasVertex(f)) return new HashSet<Edge<V, Integer>>();
        else               return map.get(f);
    }

    // is f a vertex in the graph?
    public boolean hasVertex(V f) {
        return map.containsKey(f);
    }

    // is v-w an edge in the graph?
    public boolean hasEdge(V f, V t) {
        if (!hasVertex(f)) return false;
        for (Edge<V, Integer> e : map.get(f))
	{
	  if ( t.equals( e.getVertex() ) )
	    return true;
	}
	return false;
    }

    // not very efficient, intended for debugging only
    public String toString() {
        String s = "";
        for (V f : map.keySet() ) {
            s += f.toString() + ": ";
            for (Edge<V, Integer> e : map.get(f)) {
                s += "[" + e.getVertex().toString() + ", "
		         + e.getCost().toString() + "] ";
            }
            s += "\n";
        }
        return s;
    }

    public static <V> void Dijkstra(DiGraph<V> G, V source)
    {
		HashMap<V, Integer> dist = new HashMap<V, Integer>();
		HashMap<V, V> pred = new HashMap<V, V>();
		Heap<Edge<V, Integer>> priq = new Heap<Edge<V, Integer>>();
		HashSet<V> vset = new HashSet<V>();
		int step = 1;

		dist.put(source, 0);
		priq.add( new Edge<V, Integer>(source, 0) );
		while ( ! priq.isEmpty() )
		{
			Edge<V, Integer> pair = priq.removeMin();
			V u = pair.getVertex();
			if ( ! vset.contains(u) )
			{ 
				vset.add(u);
				for ( Edge<V, Integer> tup: G.adjacentTo(u) )
				{
					V v = tup.getVertex();
					Integer altdist = dist.get(u) + tup.getCost();
					Integer vdist = dist.get(v);
					if ( vdist == null || vdist > altdist )
					{
						dist.put(v, altdist);
						pred.put(v, u);
						priq.add( new Edge<V, Integer>(v, altdist) );
					}
				}
			}

			System.out.println( "Iteration " + step++ );
			for ( V w: G.vertices() )
			{
				System.out.println( "Vertex: " + w.toString() + " Dist: " + dist.get(w) + " Pred: " + pred.get(w) );
			}
			System.out.println( "Priq: " + priq.toString() );
			System.out.println( "Vset: " + vset.toString() );
		}  
    }

    // It visits the vertices of a graph
    // in depth-first traversal, produces a depth-first forest, and
    // generates a list of vertices in a topological order.
    public static <V> void depthFirstSearch(DiGraph<V> aGraph)
    {
      HashMap<V, String> color = new HashMap<V, String>();
      HashMap<V, V> pred = new HashMap<V, V>();
      LinkedStack<V> topoOrder = new LinkedStack<V>();
      System.out.println("Initialization");
      System.out.println();
      for ( V w : aGraph.vertices() )
        {
          color.put(w, "green"); // unreached
          pred.put(w, null);
          System.out.println("Vertex: " + w + "  Color: " + color.get(w) + "  Pred: null");
        }

      System.out.println();
      for ( V w : aGraph.vertices() )
        if ( color.get(w).equals( "green" ) )
	{
          recvisitDFS(aGraph, w, color, pred, topoOrder);
          System.out.println();
	}

      System.out.println("\nDFS Forest");  // This display part is omitted in class.
      for ( V w : aGraph.vertices() )
       if ( pred.get(w) == null )
         System.out.println( "The root of a DFS tree: " + w.toString() );
       else
         System.out.println( "Tree edge: "
	       + pred.get(w).toString()
	       + "->" +  w.toString() );

      System.out.println( "Topological Sorting:");
      while ( ! topoOrder.isEmpty() )
         System.out.print(" " +  topoOrder.pop().toString() );
      System.out.println();
    }

    /* This recursive method is covered in class. */
    private static <V> void recvisitDFS(DiGraph<V> aGraph, V s,
            HashMap<V, String> color, HashMap<V, V> pred, LinkedStack<V> topoOrder)
    {
      color.put(s, "red"); // reached but not processed
      System.out.println("A search is started at vertex " + s + ".");
      String tmp = pred.get(s) == null ? "null" : pred.get(s).toString();
      System.out.println("Vertex: " + s + "  Color: " + color.get(s) + "  Pred: " + tmp );
      for ( Edge<V, Integer> tup: aGraph.adjacentTo(s) )
      {
	V w = tup.getVertex();
	if ( color.get(w).equals( "green" ) )
	{
          pred.put(w, s);
	  recvisitDFS(aGraph, w, color, pred, topoOrder);
	}
      }
      color.put(s, "black"); // processed
      topoOrder.push(s);
      System.out.println("The search is complted at vertex " + s + ".");
      tmp = pred.get(s) == null ? "null" : pred.get(s).toString();
      System.out.println("Vertex: " + s + "  Color: " + color.get(s) + "  Pred: " + tmp );
    }

    public static void main(String[] args) {
        DiGraph<String> G = new DiGraph<String>();
        G.addEdge("A", "B", 3);
        G.addEdge("A", "C", 5);
        G.addEdge("B", "C", 1);
        G.addEdge("B", "D", 6);
        G.addEdge("C", "B", 2);
        G.addEdge("C", "D", 3);
        G.addEdge("C", "E", 6);
        G.addEdge("D", "E", 2);
        G.addEdge("E", "D", 7);
        G.addEdge("E", "A", 3);
        System.out.println( " Edges of A");
	for ( Edge<String, Integer> e: G.adjacentTo("A") )
	{
           System.out.println(
                "[" + e.getVertex().toString() + ", "
		         + e.getCost().toString() + "] " );

	}

        System.out.println( " end \n ");
        System.out.println( " Vertices \n ");
	for ( String n : G.vertices() )
          System.out.println( n.toString() + ", ");
        System.out.println( " end \n ");


        // print out graph
        System.out.println(G);
        Dijkstra(G, "A");

        System.out.println( "\nAnother Example:");
        G = new DiGraph<String>();
	G.addEdge("A", "B", 1);
	G.addEdge("A", "C", 5);
	G.addEdge("A", "D", 3);
	G.addEdge("B", "D", 1);
	G.addEdge("C", "E", 3);
	G.addEdge("C", "F", 8);
	G.addEdge("D", "C", 2);
	G.addEdge("D", "F", 10);
	G.addEdge("E", "F", 4);
        Dijkstra(G, "A");
        depthFirstSearch(G);
/*
        // print out graph again by iterating over vertices and edges
        for (String v : G.vertices()) {
            StdOut.print(v + ": ");
            for (String w : G.adjacentTo(v)) {
                StdOut.print(w + " ");
            }
            StdOut.println();
        }
	*/

    }
}

interface PurePriorityQueue<E>
{
  int size();
  boolean isEmpty();
  void add(E element);
  // Returns a high-priority element without removing it.
  E getMin();
  // Removes a high-priority element.
  E removeMin();
}

class Heap<E extends Comparable<? super E>>
   implements PurePriorityQueue<E>
{
  private static final int INIT_CAP = 10;
  private ArrayList<E> list;

  public Heap()
  {
    list = new ArrayList<E>(INIT_CAP);
  }

  public int size()
  {
    return list.size();
  }

  public boolean isEmpty()
  {
    return list.isEmpty();
  }

  public String toString()
  {
    return list.toString();
  }

  public void add(E element)
  {
    if ( element == null )
      throw new NullPointerException("add");
    list.add(element); // append it to the end of the list
    percolateUp(); // move it up to the proper place
  }

  // move the last element up to the proper place.
  private void percolateUp()
  {
     int child = list.size() - 1; // last element in the list
     int parent;
     while ( child > 0 )
     {
       parent = (child - 1) / 2; // use the (j-1)/2 formula
       if ( list.get(child).compareTo(list.get(parent)) >= 0 )
          break;
       swap(parent, child);
       child = parent;
     }
  }

  private void swap(int parent, int child)
  {
    E tmp = list.get(parent);
    list.set( parent, list.get(child) );
    list.set(child, tmp);
  }

  public E getMin()
  {
    if ( list.isEmpty() )
      throw new NoSuchElementException();
    return list.get(0);
  }

  public E removeMin()
  {
    if ( list.isEmpty() )
      throw new NoSuchElementException();
    E minElem = list.get(0); // get the min element at the root
    list.set(0, list.get(list.size() - 1) ); // copy the last element to the root
    list.remove( list.size() - 1 ); // remove the last element from the list
    if ( ! list.isEmpty() )
     percolateDown(0); // move the element at the root down to the proper place
    return minElem;
  }

  // Move the element at index start down to the proper place.
  private void percolateDown(int start)
  {
    if ( start < 0 || start >= list.size() )
      throw new RuntimeException("start < 0 or >= n");
    int parent = start;
    int child = 2 * parent + 1; // use the 2*i+1 formula
    while ( child < list.size() )
    {
      if ( child + 1 < list.size() &&
           list.get(child).compareTo(list.get(child + 1)) > 0 )
          child++; // select the smaller child
      if ( list.get(child).compareTo(list.get(parent)) >= 0 )
          break; // reach the proper place
      swap(parent, child);
      parent = child;
      child = 2 * parent + 1;
    }
  }
} // Heap

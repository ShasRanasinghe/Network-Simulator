package network;

import java.util.ArrayList;


public class Node 
{
	
	private ArrayList<Edge> thehood; // an ArrayList of routes
	public String id; // the "ID" for the vertex
	
	
	
	/**
	 * @param id Creates a Vertex with a String id
	 */
	public Node(String id)
	{
		this.id = id;
		this.thehood = new ArrayList<Edge>();
	}
	
	
	/**
	 * @param edge This method adds an edge to a Vertex
	 */
	public void addNeighbor(Edge edge)
	{
		// if the edge object is already in the neighborhood return
		if(this.thehood.contains(edge))
		{
			return;
		}
		//otherwise add the edge to the arrayList of neighbors 
		this.thehood.add(edge);
	}
	
	/**
	 * @param edge
	 * the edge for which the algorithm will use to search
	 * @return
	 */
	public boolean containsNeighbor(Edge edge)
	{
		return this.thehood.contains(edge);
	}
	
	/**
	 * @param index
	 * returns the edge(neighbor route) at the specified index
	 * @return
	 */
	public Edge getNeighbor(int index)
	{
		return this.thehood.get(index);
	}
	
	/**
	 * @param index
	 * remove neighbor at specific index
	 * @return
	 */
	public Edge removeNeighbor(int index)
	{
		return this.thehood.remove(index);
	}
	
	/**
	 * @param e remove a specified edge
	 */
	public void removeNeighbor(Edge e)
	{
		this.thehood.remove(e);
	}
	
	/**
	 * @return return the number of possible neighbors for this vector
	 */
	public int getNeighborhoodsize()
	{
		return this.thehood.size();
	}
	

	/**
	 * @return the id of the vertex
	 */
	public String getID()
	{
		return this.id;
	}
	// String representation of this vertex
	public String toString()
	{
		return "Vertex " + id;
	}
	
	// return hash code of this vertex id
	public int hashCode()
	{
		return this.id.hashCode();
	}


	public boolean equals(Object vertex)
	{
		// if they aren't equal return false
		if(!(vertex instanceof Node))
		{
			return false;
		}
		// returns true if they are
	    Node v = (Node)vertex;
	    return this.id.equals(v.id);
	}
	
	/**
	 * @return returns a copy of all the neighbors for the vertex
	 */
	public ArrayList<Edge> getNeighbors()
	{
		return this.thehood;
	}
	
}

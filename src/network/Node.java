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
		if(thehood.contains(edge))
		{
			return;
		}
		//otherwise add the edge to the arrayList of neighbors 
		thehood.add(edge);
	}
	
	/**
	 * @param edge
	 * the edge for which the algorithm will use to search
	 * @return
	 */
	public boolean containsNeighbor(Edge edge)
	{
		return thehood.contains(edge);
	}
	
	/**
	 * @param index
	 * returns the edge(neighbor route) at the specified index
	 * @return
	 */
	public Edge getNeighbor(int index)
	{
		return thehood.get(index);
	}
	
	/**
	 * @param index
	 * remove neighbor at specific index
	 * @return
	 */
	public Edge removeNeighbor(int index)
	{
		return thehood.remove(index);
	}
	
	/**
	 * @param e remove a specified edge
	 */
	public void removeNeighbor(Edge e)
	{
		thehood.remove(e);
	}
	
	/**
	 * @return return the number of possible neighbors for this vector
	 */
	public int getNeighborhoodsize()
	{
		return thehood.size();
	}
	

	/**
	 * @return the id of the vertex
	 */
	public String getID()
	{
		return id;
	}
	
	/**
	 * @return returns a copy of all the neighbors for the vertex
	 */
	public ArrayList<Edge> getNeighbors()
	{
		return thehood;
	}
	
}

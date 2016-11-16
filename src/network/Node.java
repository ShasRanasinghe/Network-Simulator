package network;

import java.util.ArrayList;

/**
 * 
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Class Node implements a possible routing location and contains a List of possible neighbors that may be "visited"
 */

public class Node 
{
	///////////////////////////////////////////////////////////////////////////////////
	//			NOTE: POSSIBLE OTHER IMPLEMENTAION COULD HAVE THE HOOD FILLED WITH OTHER NODES
	//////////////////////////////////////////////////////////////////////////////////
	private ArrayList<Node> thehood; // an ArrayList of routes
	private String id; // the "ID" for the vertex
	
	
	
	/**
	 * @param id Creates a Vertex with a String id initialize an arraylist of edges(Neighbors)
	 */
	public Node(String id)
	{
		this.id = id;
		this.thehood = new ArrayList<Node>();
	}
	
	/**
	 * @param node
	 * constructor used for testing purposes
	 */
	public Node(Node node)
	{
		this.id = node.getID();
		this.thehood = node.getNeighbors();
	}
	
	
	/**
	 * @param edge This method adds an edge to a Vertex
	 */
	public void addNeighbor(Node neighbor)
	{
		// if the edge object is already in the neighborhood return
		if(thehood.contains(neighbor))
		{
			return;
		}
		//otherwise add the edge to the arrayList of neighbors 
		thehood.add(neighbor);
	}
	
	/**
	 * @param edge
	 * the edge for which the algorithm will use to search
	 * @return Returns true if edge exists for Node else false
	 */
	public boolean containsNeighbor(Node neighbor)
	{
		return thehood.contains(neighbor);
	}
	
	/**
	 * @param index
	 * returns the node(neighbor) at the specified index
	 * @return The node given index
	 */
	public Node getNeighbor(int index)
	{
		return thehood.get(index);
	}
	
	
	/**
	 * @param e remove a specified node
	 */
	public void removeNeighbor(Node n)
	{
		thehood.remove(n);
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
	public ArrayList<Node> getNeighbors()
	{
		return thehood;
	}
	
	@Override
	public String toString()
	{
		return id;
	}
	
	@Override
	public boolean equals(Object obj){
		Node node = (Node)obj;
		if(this.id.equals(node.getID()) 
				&& this.thehood.equals(node.getNeighbors())){ return true;}
		return false;
	}
	
}
package network;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Class Node implements a possible routing location and contains a List of possible neighbors that may be "visited"
 */

@XmlRootElement(name = "node")
@XmlAccessorType (XmlAccessType.FIELD)
public class Node {

	///////////////////////////////////////////////////////////////////////////////////
	//			NOTE: POSSIBLE OTHER IMPLEMENTAION COULD HAVE THE HOOD FILLED WITH OTHER NODES
	//////////////////////////////////////////////////////////////////////////////////
	@XmlTransient
	private ArrayList<Node> thehood; // an ArrayList of routes
	
	private String id; // the "ID" for the vertex
	private String hoodIDs;
	
	
	/**
	 * Default constructor used for marshalling
	 */
	public Node()
	{
		this.thehood = new ArrayList<Node>();
	}
	
	/**
	 * @param id Creates a Vertex with a String id initialize an arraylist of edges(Neighbors)
	 */
	public Node(String id)
	{
		this.id = id;
		this.thehood = new ArrayList<Node>();
		hoodIDs = "";
		
	}
	
	/**
	 * @param node
	 * constructor used for testing purposes
	 */
	public Node(Node node)
	{
		this.id = node.toString();
		this.thehood = node.getNeighbors();
	}
	
	/**
	 * @return The hood ids
	 */
	public String getHoodIDs()
	{
		return hoodIDs.toString();
	}
	
	/**
	 * @return Node ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id ID to be set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param hoodIDs Hood to be set
	 */
	public void setHoodIDs(String hoodIDs) {
		this.hoodIDs = hoodIDs;
	}
	
	/**
	 * @param neighbor the edge for which the algorithm will use to search
	 * @return Returns true if edge exists for Node else false
	 */
	public boolean containsNeighbor(Node neighbor)
	{
		return thehood.contains(neighbor);
	}
	
	/**
	 * @param index the neighbor that is requested
	 * returns the node(neighbor) at the specified index
	 * @return The node given index
	 */
	public Node getNeighbor(int index)
	{
		return thehood.get(index);
	}
	
	
	/**
	 * Remove a specified node
	 * @param n node to be removed
	 */
	public void removeNeighbor(Node n)
	{
		thehood.remove(n);
		removeNodeFromHoodIDs(n.getId());
	}
	
	/**
	 * @return return the number of possible neighbors for this vector
	 */
	public int getNeighborhoodsize()
	{
		return thehood.size();
	}
	
	/**
	 * @param neighbor This method adds an edge to a Vertex
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
		//Generate XML hood IDs string
		addNodeToHoodIDs(neighbor.getId());
	}
	
	
	/**
	 * @param newNodeID New Node ID
	 */
	public void setNodeID(String newNodeID) {
		this.id = newNodeID;
	}
	
	/**
	 * @return returns a copy of all the neighbors for the vertex
	 */
	public ArrayList<Node> getNeighbors()
	{
		return thehood;
	}

	
	/**
	 * Remove neighbor node id from hood ids
	 * Used for XML purposes
	 */
	private void removeNodeFromHoodIDs(String newNeighbor){
		
		//Remove old neighbor
		String[] neighborIDs = hoodIDs.split("[\\|\\s]+");
		for (int i = 0; i < neighborIDs.length; i++){
		    if (neighborIDs[i].equals(newNeighbor))
		    {
		    	neighborIDs[i] = null;
		    }
		}
		
		//Recreate hoodIDs
		StringBuilder sb = new StringBuilder();
		for(String neighborID: neighborIDs){
			if(neighborID != null){
				sb.append(neighborID);
				sb.append("|");
			}
		}
		
		//Set hoodIDs with removed neighbor
		hoodIDs = sb.toString();		
		
	}
	
	/**
	 * Add neighbor node id to hood ids 
	 * Used for XML purposes
	 */
	private void addNodeToHoodIDs(String newNeighbor){
		hoodIDs = hoodIDs +newNeighbor+"|";
	}

	@Override
	public String toString()
	{
		return id;
	}
	
	@Override
	public boolean equals(Object obj){
		Node node = (Node)obj;
		if(this.id.equals(node.toString()) 
				&& this.thehood.equals(node.getNeighbors())){ return true;}
		return false;
	}
	
}
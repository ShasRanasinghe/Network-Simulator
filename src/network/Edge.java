package network;
/**
 * 
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Class Edge implements a "Link" between two nodes consisting of a source and destination
 */
public class Edge 
{
	private String id; // String id for an edge
	private Node source; // Node source for edge
	private Node destination; // Node destination for edge
	
	/**
	 * @param id ID
	 * @param s Source
	 * @param d Destination
	 * Creates an Edge with a String ID, a Node object source a Node object destination 
	 */
	public Edge(String id, Node s, Node d)
	{
		this.id = id ;
        this.source = s;
        this.destination = d;
  
	}

	/**
	 * @param edge
	 *  constructor for testing purposes
	 */
	public Edge(Edge edge)
	{
		this.id = edge.getId();
		this.source = edge.getSource();
		this.destination = edge.getDestination();
	}
	
	
	/**
	 * @return the source Vertex
	 */
	public Node getSource()
	{
		return source;
	}
	
	/**
	 * @return returns the destination vertex
	 */
	public Node getDestination()
	{
		return destination;
	}


	/**
	 * @return ID of the Edge
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id set the ID of the Edge
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj){
		Edge edge = (Edge)obj;
		if(this.id.equals(edge.getId()) 
				&& this.getSource().equals(edge.getSource()) 
				&& this.getDestination().equals(edge.getDestination()))
		{
			return true;
		}
		return false;
	}
	
	


}
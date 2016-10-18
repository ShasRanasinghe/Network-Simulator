package network;

public class Edge implements Comparable<Edge>
{
	private String id; // String id for an edge
	private Node source; // Vertex source for edge
	private Node destination; // Vertex destination for edge
	private int weight; // used to determine shortest path
	
	/**
	 * @param id
	 * @param source
	 * @param destination
	 * @param weight
	 * Creates an Edge with a String ID, a Vector object source a Vector object destination and the weight of the edge
	 */
	public Edge(String id, Node source, Node destination, int weight)
	{
		this.id = id ;
        this.source = (source.getID().compareTo(destination.getID()) <= 0) ? source : destination;
        this.destination = (this.source == source) ? destination : source;
        this.weight = weight;
	}
	
	/**
	 * @param v returns the neighbor along this edge
	 * @return
	 */
	public Node getNeighbor(Node v)
	{
		if(!(v.equals(source) || v.equals(destination)))
		{
			return null;
		}
		return (v.equals(source)) ? destination : source;
	}
	
	/**
	 * @return returns the weight of the current weight
	 */
	public int getWeight()
	{
		return weight;
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
	
	// compareTo overwrites the Comparable interface

	public int compareTo(Edge edge)
	{
		return this.weight - edge.weight;
	}
	
	// returns a string representation of the current edge.
	public String toString()
	{
		return "({" + source + ", " + destination + "}, " + weight + ")" ;
	}
	
	// return the hashCode for this edge
	public int hashCode()
	{
		return (source.getID().hashCode() + destination.getID().hashCode());
	}
	// returns true if edge is an Edge with same Vertices as "this"
	
	public boolean equals(Object edge)
	{
		if(!(edge instanceof Edge))
		{
			return false;
		}
		Edge e = (Edge)edge;
		
		return e.source.equals(this.source) && e.destination.equals(this.destination);
	}
	

}

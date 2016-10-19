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
	public Edge(String id, Node s, Node d, int weight)
	{
		this.id = id ;
        this.source = s;
        this.destination = d;
        this.weight = weight;
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
	

}

package network;

import java.util.*;

/**
 * @author Andrew
 *
 */

public class Graph 
{
	private HashMap<String, Node> vertices; // hashmap for vertices
	private HashMap<Integer,Edge> edges; // hashmap for edges
	


	/**
	 * @param vertices
	 * Graph constructor accepts an ArrayList<Vertex> and populates the HashMap
	 * If multiple Vertex's have the same ID
	 * The last Vertex with the ID is used
	 */
	public Graph(ArrayList<Node> vertices)
	{
		this.vertices = new HashMap<String,Node>();
		this.edges = new HashMap<Integer, Edge>();
		
		for(Node v: vertices)
		{
			this.vertices.put(v.getID(), v);
		}
	}
	
	
	/**
	 * @param id edge id
	 * @param source vertex source
	 * @param destination vertex
	 * @param weight of edge
	 * Passses two vertices and creates an edge if no edge already exists
	 * @return
	 */
	public boolean addEdge(String id, Node source, Node destination, int weight)
	{
		
		if(source.equals(destination))
		{
			return false;
		}
		Edge e = new Edge(id ,source, destination, weight);
		if(edges.containsKey(e.hashCode()))
		{
			return false;
		}
		else if(source.containsNeighbor(e) || destination.containsNeighbor(e))
		{
			return false;
		}
		
		edges.put(e.hashCode(), e);
		source.addNeighbor(e);
		destination.addNeighbor(e);
		
		return true;
		
		
	}
	
	/**
	 * @param e
	 * @return true if the edge exists
	 */
	public boolean containsEdge(Edge e)
	{
		if(e.getSource() == null || e.getDestination() == null)
		{
			return false;
		}
		return this.edges.containsKey(e.hashCode());
	}
	
	/**
	 * @param id
	 * @return the vertex with the specified id
	 */
	public Node getVertex(String id)
	{
		return vertices.get(id);
	}
	
	
	/**
	 * @param vertex
	 * adds a vertex to vertices
	 */
	public void addVertex(Node vertex)
	{
		vertices.put(vertex.getID(), vertex);
	}
	
	/**
	 * @return the id's of the vertex objects
	 */
	public Set<String> vertexKeys()
	{
		return this.vertices.keySet();
	}
	
	/**
	 * @return the edges of the graph
	 */
	public Set<Edge> getEdges()
	{
		return new HashSet<Edge>(this.edges.values());
	}
	
	

}
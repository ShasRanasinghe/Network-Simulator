package network;

import java.util.*;

/**
 * @author Andrew
 *
 */

public class Graph implements RoutingAlgorithms
{
	private HashMap<String, Node> vertices; // hashmap for vertices
	private HashMap<Integer,Edge> edges; // hashmap for edges
	private ArrayList<Table> table;
	
	private ArrayList<Message> messageList;
	
	private int hops;
	
	private int totalHops;
	private int totalmessages;
	

	public Graph()
	{
		this.vertices = new HashMap<String,Node>();
		this.edges = new HashMap<Integer, Edge>();
		this.table = new ArrayList<Table>();
		
		this.messageList = new ArrayList<Message>();
		this.hops = 0;
		totalHops = 0;
		totalmessages = 0;
	}

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

		this.table = new ArrayList<Table>();
		this.messageList = new ArrayList<Message>();
		
		this.hops = 0;
		totalHops = 0;
		totalmessages = 0;
		
		for(Node v: vertices)
		{
			this.vertices.put(v.getID(), v);
		}
	}
	
	
	public Message getMessage()
	{
		return messageList.get(0);
	}
	
	public void createMessage()
	{
		Random random = new Random();
		Node tempS = vertices.get(random.nextInt(vertices.size()));
		Node tempD = vertices.get(random.nextInt(vertices.size()));
		while(tempS.equals(tempD))
		{
			tempD = vertices.get(random.nextInt(vertices.size()));
		}
		Message message = new Message(tempS,tempD);
		messageList.add(message);
		
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
		return new HashSet<Edge>(edges.values());
	}
	
	/**
	 * 
	 * @param c - The Current node of the pair being added
	 * @param n - The Next node of the pair being added
	 */
	public void addToTable(Node c, Node n)
	{
		// Creating a pair to add to the table
		Table currentHop = new Table(c,n);
		table.add(currentHop);
	}
	
	public void printTable()
	{
		for(Table t: table)
		{
			System.out.println(t.makeString());
		}
	}
	
	/**
	 * 
	 * @param i - A passed int from the algorithm that counted how many
	 * hops took place.
	 */
	public void setHops(int i)
	{
		hops = i;
	}
	public void printHops()
	{
		System.out.println(hops);
	}
	
	public static void main(String args[])
	{
		// Vertex's
		Node v1 = new Node("A");
		Node v2 = new Node("B");
		Node v3 = new Node("C");
		Node v4 = new Node("D");
		Node v5 = new Node("E");
		
		// A
		Edge ea1 = new Edge("A->B",v1,v2,1);
		Edge ea2 = new Edge("A->C",v1,v3,1);
		Edge ea3 = new Edge("A->E",v1,v5,1);
		
		//adding routes
		v1.addNeighbor(ea1);
		v1.addNeighbor(ea2);
		v1.addNeighbor(ea3);
		
		//B
		Edge eb1 = new Edge("B->A",v2,v1,1);
		Edge eb2 = new Edge("B->D",v2,v4,1);
		Edge eb3 = new Edge("B->E",v2,v5,1);
		 
		//adding routes
		v2.addNeighbor(eb1);
		v2.addNeighbor(eb2);
		v2.addNeighbor(eb3);
		
		//C 
		Edge ec1 = new Edge("C->A",v3,v1,1);
		Edge ec2 = new Edge("C->D",v3,v4,1);
		
		// adding routes
		v3.addNeighbor(ec1);
		v3.addNeighbor(ec2);
		
		//D
		Edge ed1 = new Edge("D->C",v4,v3,1);
		Edge ed2 = new Edge("D->B",v4,v2,1);
		
		// adding routes 
		v4.addNeighbor(ed1);
		v4.addNeighbor(ed2);
		
		//E
		Edge ee1 = new Edge("E->A",v5,v1,1);
		Edge ee2 = new Edge("E->B",v5,v1,1);
		
		//adding routes
		v5.addNeighbor(ee1);
		v5.addNeighbor(ee2);
		
		
		Graph graph = new Graph();
		
		int f = 5;
		
		graph.runAlgorithm(graph, f, ALGORITHM.RANDOM); 
		
		graph.printTable();
		graph.printHops();
		
	}
	
	

}
package network;

import java.util.*;

/**
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Implementing the ShortestPath algorithm
 * 
 * 		- ShortestPath: Each message will start at its root (Source/Current) node, and explore the root's hood.
 * 							LOOP:
 * 							-If the message finds its destination in the hood, record hop and end message
 * 							-Else send temp message to each neighbor to explore the neighbor hoods
 * 								-If temp finds destination in a neighbor's hood, message steps toward temp and hop count is incrumented
 * 								-Message creation frequency is checked
 * 							loop
 * 
 * ALL ALGORITHM CLASSES EXTEND THE SUPERCLASS GRAPH, ALLOWING ALL ALGORITHMS TO SHARE SPECIFIC BEHAVIOR AND VARIABLES
 * 
 */

public class ShortestPathAlgorithm extends Graph{
	
	// Total hops that occur during the SHORTESTPATH algorithm
	private int totalShortestPathHops;
	// A copy of the messageQueue to remove concurrency issues
	private ArrayList<Message> currentShortestPathMessageQueue;
	
	private ArrayList<ArrayList<Node>> shortestPaths;
	
	

	public ShortestPathAlgorithm(ArrayList<Node> nodes, int frequency)
	{
		// INHERITED FROM GRAPH!!!!!
		// List of Nodes within the graph
		this.graphNodes = nodes;
		// List of ALL messages created during the simulation
		this.completeMessageList = new ArrayList<Message>();
		// List of messages currently in the network
		this.messageQueue = new ArrayList<Message>();
		// The rate at which messages will be created
		this.creationFrequency = frequency;
		
		totalShortestPathHops = 0;
		currentShortestPathMessageQueue = new ArrayList<Message>();
		shortestPaths = new ArrayList<ArrayList<Node>>();
		
		calculateAllShortestPaths(graphNodes);
		createNewMessage();
	}


	/**
	 * 
	 * @param graphNodes
	 */
	private void calculateAllShortestPaths(ArrayList<Node> graphNodes) 
	{
		for(Node source : graphNodes)
		{
			for(Node destination : graphNodes)
			{
				// find shortest path for ALL source/destination combos
				findShortestPath(source, destination);
			}
		}
	}

	
	private void findShortestPath(Node s, Node d)
	{
		int dist = 0;
		Node current = s;
		ArrayList<Node> shortest;
		
		LinkedList<Node> lL = new LinkedList<Node>();
		lL.add(current);
		
		while(!lL.isEmpty())
		{
			current = lL.removeFirst();
			
			for(Node n : current.getNeighbors())
			{
				if(!lL.contains(n))
				{
					lL.addFirst(n);
				}
			}
		}
		
		System.out.println(lL);
	}

	
	@Override
	void run(int stepSize) 
	{
		// Step 1) Loop n times, where n is = to the stepSize
		int n = 0;
		while(n != stepSize)
		{
			// Step 1.1) Refresh the currentMessageQueue (Clear and Copy)
			currentShortestPathMessageQueue.clear();
			currentShortestPathMessageQueue.addAll(messageQueue);
			
			// Step 1.2) Iterate through the currentMessageQueue
			for(Message message : currentShortestPathMessageQueue)
			{
				// Have message follow shortest path
				
				
				/*
				
				// Step 1.2.3.2) If the message has reached its destination
				if(neighbor.equals(message.getDestination()))
				{
					// Step 1.2.3.2.1) Remove the message from the queue
					removeMessage(message);
					
					// Step 1.2.3.2.2) Check to see if the queue is now empty
					if(messageQueue.size() == 0)
					{
						// TERMINATE ALGORITHM
						break;
					}
				}
				*/
				// Step 1.2.) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrumentHopCount();
				totalShortestPathHops++;
				
				// Step 1.2.) Check to see if the message should create a new message
				checkFrequency(message);
			}
			
			
			
		}
		// END OF ALGORITHM
	}



	@Override
	int getTotalHops() 
	{
		return totalShortestPathHops;
	}



	@Override
	int getNumberOfCurrentMessages() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public static void main(String[] args)
	{
		Node a = new Node("A");
		Node b = new Node("B");
		Node c = new Node("C");
		Node d = new Node("D");
		Node e = new Node("E");
		
		a.addNeighbor(b);
		a.addNeighbor(c);
		a.addNeighbor(e);
		
		b.addNeighbor(a);
		b.addNeighbor(d);
		b.addNeighbor(e);
		
		c.addNeighbor(a);
		c.addNeighbor(d);
		
		d.addNeighbor(b);
		d.addNeighbor(c);
		
		e.addNeighbor(a);
		e.addNeighbor(b);
		
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.add(a);
		nodeList.add(b);
		nodeList.add(c);
		nodeList.add(d);
		nodeList.add(e);
		
		ShortestPathAlgorithm sPT = new ShortestPathAlgorithm(nodeList,5);
	}
	
}

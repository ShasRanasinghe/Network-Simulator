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
	
	// The generated shortest path for each message possibility
	public Map<String, List<Node>> paths= new HashMap<String, List<Node>>();
	
	/**
	 * Main constructor for the Shortest Path Algorithm
	 * 
	 * @param nodes	the nodes contained within the network
	 * @param frequency	the rate at which a new message is created	
	 */
	public ShortestPathAlgorithm(ArrayList<Node> nodes, int frequency)
	{
		// List of Nodes within the graph
		this.graphNodes = nodes;
		// The rate at which messages will be created
		this.creationFrequency = frequency;
		
		// Generate ALL shortest paths
		for(Node n1 : graphNodes)
		{
			for(Node n2 : graphNodes)
			{
				if(!n1.equals(n2))
				{
					Path p = new Path(n1,n2);
					paths.put(p.getiD(), p.findPath());
				}
			}
		}
		
		// Create the first message
		createNewMessage();
	}
	
	/**
	 * Javadoc in Graph
	 */
	@Override
	void run(int stepSize) 
	{
		// Step 1) Loop n times, where n is = to the step
		steps = 0;
		while(steps != stepSize)
		{
			// Step 1.1) Refresh the currentMessageQueue (Clear and Copy)
			currentMessageQueue.clear();
			currentMessageQueue.addAll(messageQueue);
			
			// Step 1.2) Iterate through the currentMessageQueue
			for(Message message : currentMessageQueue)
			{
				String id = "" + message.getSource() + message.getDestination();
				List<Node> path = paths.get(id);
				int location = message.getHopCount();
				
				// Set current node of message to location in path
				ArrayList<Node> temp = new ArrayList<Node>();
				temp.add(path.get(location));
				message.setCurrent(temp);
				
				// Step 1.2.3.2) If the message has reached its destination
				if(path.get(location).equals(message.getDestination()))
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
				// Otherwise, Step
				// Step 1.2.) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrementHopCount();
				totalHops++;
				
				// Step 1.2.) Check to see if the message should create a new message
				checkFrequency(message);
			}
			steps++;
		}
		// END OF ALGORITHM
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @author alexhoecht
	 *
	 */
	public class Path {
		
		private String id;
		
		// FCFS queue (Linked list) to traverse through hoods
		private Queue<Node> queue;
		// Where we traverse to next
		private Map<Node, Node> nextHood;
		// The found currently found path
		private List<Node> path;
		
		private Node source;
		private Node destination;
		
		/**
		 * 
		 * @param s Source node of path
		 * @param d	Destination node of path
		 */
		public Path(Node s, Node d)
		{
			id = "" + s.toString() + d.toString();
			
			// Initialize everything
			queue = new LinkedList<Node>();
			nextHood = new HashMap<Node, Node>();
			path = new LinkedList<Node>();
			
			source = s;
			destination = d;
		}
		
		/**
		 * 
		 * @return
		 */
		private List<Node> findPath()
		{ 
			Map<Node, Boolean> passed = new HashMap<Node, Boolean>();
			Node current = source;
			
			queue.add(current);
			passed.put(current, true);
			while(!queue.isEmpty())
			{
				current = queue.remove();
				if(current.equals(destination))
				{
					break;
				}
				else
				{
					for(Node n : current.getNeighbors())
					{
						if(!passed.containsKey(n))
						{
							queue.add(n);
							passed.put(n, true);
							nextHood.put(n, current);
						}
					}
				}
			}
			if(!current.equals(destination))
			{
				//Fail
			}
			for(Node node = destination; node != null; node = nextHood.get(node))
			{
				path.add(node);
			}
			Collections.reverse(path);
			return path;
		}
		
		/**
		 * 
		 * @return String id of the Node
		 */
		public String getiD()
		{
			return id;
		}
	}
	
}

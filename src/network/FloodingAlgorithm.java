package network;

import java.util.*;

/**
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Implementing the Flooding algorithm
 * 
 * 		- Flooding: For every step that occurs, every message in the network will forward its self to ALL current neighbors, except where the message 
 * 					previously was received from. 
 * 
 * ALL ALGORITHM CLASSES EXTEND THE SUPERCLASS GRAPH, ALLOWING ALL ALGORITHMS TO SHARE SPECIFIC BEHAVIOR AND VARIABLES
 * 
 */

public class FloodingAlgorithm extends Graph{
	
		
	/**
	 * Main constructor for the Flooding Algorithm
	 * 
	 * @param nodes	the nodes contained within the network
	 * @param frequency	the rate at which a new message is created
	 */
	public FloodingAlgorithm(ArrayList<Node> nodes, int frequency)
	{
		// List of Nodes within the graph
		this.graphNodes = nodes;
		// The rate at which messages will be created
		this.creationFrequency = frequency;
		
		createNewMessage();
	}
	
	/**
	 * Javadoc in Graph
	 */
	@Override
	void run(int stepSize) 
	{
		// Loop n times, where n is = to the step
		steps = 0;
		while(steps != stepSize)
		{
			// Step 1) Refresh the currentMessageQueue (Clear and Copy)
			currentMessageQueue.clear();
			currentMessageQueue.addAll(messageQueue);
			
			// Step 2) Iterate through the currentMessageQueue
			for(Message message : currentMessageQueue)
			{	
				// New neighbor hood to fetch the current neighbors of the message
				ArrayList<Node> newHood = new ArrayList<Node>();
				
				// Step 2.1) For all current node(s)
				for(Node n1 : message.getCurrent())
				{
					// Step 2.1.1) Get neighborhood(s)
					for(Node neighbor : n1.getNeighbors())
					{
						// Step 2.1.1.1) Flood neighbors
						if(!message.getPrevious().contains(neighbor))
						{
							newHood.add(neighbor);
						}

						// Step 2.1.1.2) Check if Flooding reaches destination
						if(neighbor.equals(message.getDestination()))
						{
							// Remove the message from the queue
							removeMessage(message);
							
							// Check to see if the queue is now empty
							if(messageQueue.size() == 0)
							{
								// TERMINATE ALGORITHM
								break;
							}
						}
					}
				}
				// Step 2.2) Set the message's previous and current
				message.setPrevious(message.getCurrent());
				message.setCurrent(newHood);
				
				// Step 2.3) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrementHopCount();
				totalHops++;
				
				// Step 2.4) Check to see if the message should create a new message
				checkFrequency(message);
				
			}
			steps++;
		}
		
	}

}
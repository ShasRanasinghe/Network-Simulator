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
	
		

	public FloodingAlgorithm(ArrayList<Node> nodes, int frequency)
	{
		// List of Nodes within the graph
		this.graphNodes = nodes;
		// The rate at which messages will be created
		this.creationFrequency = frequency;
		
		createNewMessage();
	}
	

	void run(int stepSize) 
	{
		// Step 1) Loop n times, where n is = to the step
		// The amount of steps completed
		steps = 0;
		while(steps != stepSize)
		{
			// Step 1.1) Refresh the currentMessageQueue (Clear and Copy)
			currentMessageQueue.clear();
			currentMessageQueue.addAll(messageQueue);
			
			// Step 1.2) Iterate through the currentMessageQueue
			for(Message message : currentMessageQueue)
			{

				// Step 1.2.1) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrementHopCount();
				totalHops++;
				
				// Step 1.2.2) Check to see if the message should create a new message
				checkFrequency(message);
				
				ArrayList<Node> newHood = new ArrayList<Node>();
				
				for(Node n1 : message.getCurrent())
				{
					for(Node neighbor : n1.getNeighbors())
					{
						// Step 1.2.3.1) Flood neighbors
						if(!message.getPrevious().contains(neighbor))
						{
							newHood.add(neighbor);
						}
						
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
					}
				}
				// Set the message's previous and current
				message.setPrevious(message.getCurrent());
				message.setCurrent(newHood);
				
			}
			steps++;
		}
		
	}

}
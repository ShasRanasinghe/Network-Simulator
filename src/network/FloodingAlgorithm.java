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
	
	// Total hops that occur during the RANDOM algorithm
	private int totalFloodingHops;
	// A copy of the messageQueue to remove concurrency issues
	private ArrayList<Message> currentFloodingMessageQueue;
	
	

	public FloodingAlgorithm(ArrayList<Node> nodes, int frequency)
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
		
		totalFloodingHops = 0;
		currentFloodingMessageQueue = new ArrayList<Message>();
		
		createNewMessage();
	}
	

	void run(int stepSize) 
	{
		// Step 1) Loop n times, where n is = to the stepSize
		int n = 0;
		while(n != stepSize)
		{
			// Step 1.1) Refresh the currentMessageQueue (Clear and Copy)
			currentFloodingMessageQueue.clear();
			currentFloodingMessageQueue.addAll(messageQueue);
			
			// Step 1.2) Iterate through the currentMessageQueue
			for(Message message : currentFloodingMessageQueue)
			{

				// Step 1.2.1) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrementHopCount();
				totalFloodingHops++;
				
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
			n++;
		}
		
	}

	
	int getTotalHops() 
	{
		return totalFloodingHops;
	}

	
	int getNumberOfCurrentMessages() 
	{
		return messageQueue.size();
	}

}
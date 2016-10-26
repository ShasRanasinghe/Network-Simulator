package network;

import java.util.*;

/**
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Implementing the Random algorithm
 * 
 * 		- Random: Every step, each message is forwarded to a random neighbor. If a message reaches its destination, it terminates and is removed from the
 * 					message queue. Every time any message in the network hops a specific amount of times (user frequency), a new message is created and
 * 					added to the message queue. The algorithm will print the current state of each message in the GUI as often as the user specifies.
 * 					The algorithm terminates when there are no more messages left in the queue.
 * 
 * ALL ALGORITHM CLASSES EXTEND THE SUPERCLASS GRAPH, ALLOWING ALL ALGORITHMS TO SHARE SPECIFIC BEHAVIOR AND VARIABLES
 * 
 */

public class RandomAlgorithm extends Graph{
	
	// Total hops that occur during the RANDOM algorithm
	private int totalRandomHops;
	// A copy of the messageQueue to remove concurrency issues
	private ArrayList<Message> currentMessageQueue;
	
	// ALL OTHER INSTANCE VARIABLES INHERITED FROM GRAPH!!!!
	
	
	public RandomAlgorithm(int frequency)
	{
		// INHERITED FROM GRAPH!!!!!
		// List of Nodes within the graph
		this.graphNodes = new ArrayList<Node>();
		// List of ALL messages created during the simulation
		this.completeMessageList = new ArrayList<Message>();
		// List of messages currently in the network
		this.messageQueue = new ArrayList<Message>();
		// The rate at which messages will be created
		this.creationFrequency = frequency;
		
		// INDEPENDENT RANDOM ALGORITHM VARIABLES
		this.totalRandomHops = 0;
		this.currentMessageQueue = new ArrayList<Message>();
	}
	
	
	public void run(ArrayList<Node> nodes, int stepSize)
	{
		// Step 1) Define nodes to send messages across
		graphNodes = nodes;
		
		// Step 2) Create first message and add it to the queue and total
		createNewMessage();
		
		// Step 3) Loop n times, where n is = to the stepSize
		int n = 0;
		while(n != stepSize)
		{
			// Step 3.1) Refresh the currentMessageQueue (Clear and Copy)
			currentMessageQueue.clear();
			currentMessageQueue.addAll(messageQueue);
			
			// Step 3.2) Iterate through the currentMessageQueue
			for(Message message : currentMessageQueue)
			{
				// Step 3.2.1) Choose a random edge of the current message's source, and save the destination of the edge
				Random random = new Random();
				Node messageSource = message.getSource();
				Edge randomEdge = messageSource.getNeighbor(random.nextInt(messageSource.getNeighborhoodsize()));
				Node nextNode = randomEdge.getDestination();
				
				// Step 3.2.2) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrumentHopCount();
				totalRandomHops++;
				
				// Step 3.2.3) Check to see if the message should create a new message
				checkFrequency(message);
				
				// Step 3.2.4) Check if nextNode is the destination of the message
				if(message.getDestination().equals(nextNode))
				{
					// Step 3.2.4.1) Remove the message from the queue
					removeMessage(message);
					
					// Step 3.2.4.2) Check to see if the queue is now empty
					if(messageQueue.size() == 0)
					{
						// TERMINATE ALGORITHM
						n = stepSize;
						break;
					}
				}
				
				// Step 3.2.5) If nextNode is not the destination of the message
				else
				{
					// Step 3.2.5.1) Forward the message to the nextNode
					message.setSource(nextNode);
				}
			}
		}
	}
	
	/**
	 * @return the total amount of hops it took the random algorithm to terminate
	 */
	public int getTotalHops()
	{
		return totalRandomHops;
	}

}

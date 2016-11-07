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
	
	
	public RandomAlgorithm(ArrayList<Node> nodes, int frequency)
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
		
		// INDEPENDENT RANDOM ALGORITHM VARIABLES
		this.totalRandomHops = 0;
		this.currentMessageQueue = new ArrayList<Message>();
		
		// Create the first message
		createNewMessage();
	}
	
	
	public void run(int stepSize)
	{
		// Step 1) Loop n times, where n is = to the stepSize
		int n = 0;
		while(n != stepSize)
		{
			// Step 1.1) Refresh the currentMessageQueue (Clear and Copy)
			currentMessageQueue.clear();
			currentMessageQueue.addAll(messageQueue);
			
			// Step 1.2) Iterate through the currentMessageQueue
			for(Message message : currentMessageQueue)
			{
				// Step 1.2.1) Choose a random edge of the current message's location, and save the destination of the edge
				Random random = new Random();
				Node messageCurrent = message.getCurrentNode();
				Edge randomEdge = messageCurrent.getNeighbor(random.nextInt(messageCurrent.getNeighborhoodsize()));
				Node nextNode = randomEdge.getDestination();
				
				while(nextNode == messageCurrent)
				{
					randomEdge = messageCurrent.getNeighbor(random.nextInt(messageCurrent.getNeighborhoodsize()));
					nextNode = randomEdge.getDestination();
				}
				
				// Step 1.2.2) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrumentHopCount();
				totalRandomHops++;
				
				// Step 1.2.3) Check to see if the message should create a new message
				checkFrequency(message);
				
				// Step 1.2.4) Check if nextNode is the destination of the message
				if(message.getDestination().equals(nextNode))
				{
					// Step 1.2.4.1) Remove the message from the queue
					removeMessage(message);
					
					// Step 1.2.4.2) Check to see if the queue is now empty
					if(messageQueue.size() == 0)
					{
						// TERMINATE ALGORITHM
						break;
					}
				}
				
				// Step 1.2.5) If nextNode is not the destination of the message
				else
				{
					// Step 1.2.5.1) Forward the message to the nextNode
					message.setCurrentNode(nextNode);
				}
			}
			n++;
		}
	}
	
	/**
	 * @return the total amount of hops it took the random algorithm to terminate
	 */
	public int getTotalHops()
	{
		return totalRandomHops;
	}
	
	public int getNumberOfCurrentMessages()
	{
		return messageQueue.size();
	}

}
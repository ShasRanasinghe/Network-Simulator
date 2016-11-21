package network;

import java.util.*;

/**
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Implementing the Custom algorithm
 * 
 * 		- Custom: Every step, each message is forwarded to a random neighbor (EXCEPT ITS PREVIOUS NEIGHBOR). If a message reaches its destination, 
 * 				  it terminates and is removed from the message queue. Every time any message in the network hops a specific amount of times (user frequency),
 * 				  a new message is created and added to the message queue. The algorithm will print the current state of each message in the GUI as often as 
 * 				  the user specifies. The algorithm terminates when there are no more messages left in the queue.
 * 
 * ALL ALGORITHM CLASSES EXTEND THE SUPERCLASS GRAPH, ALLOWING ALL ALGORITHMS TO SHARE SPECIFIC BEHAVIOR AND VARIABLES
 * 
 */

public class CustomAlgorithm extends Graph{
	
	// Total hops that occur during the RANDOM algorithm
	private int totalCustomHops;
	// A copy of the messageQueue to remove concurrency issues
	private ArrayList<Message> currentCustomMessageQueue;
	
	// ALL OTHER INSTANCE VARIABLES INHERITED FROM GRAPH!!!!
	
	
	/**
	 * @param nodes list of nodes in the network
	 * @param frequency The frequency at which new messages are injected
	 */
	public CustomAlgorithm(ArrayList<Node> nodes, int frequency)
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
		
		// INDEPENDENT CUSTOM ALGORITHM VARIABLES
		this.totalCustomHops = 0;
		this.currentCustomMessageQueue = new ArrayList<Message>();
		
		// Create the first message
		createNewMessage();
	}
	
	
	/* (non-Javadoc)
	 * @see network.Graph#run(int)
	 */
	public void run(int stepSize)
	{
		// Step 1) Loop n times, where n is = to the stepSize
		int n = 0;
		while(n != stepSize)
		{
			// Step 1.1) Refresh the currentMessageQueue (Clear and Copy)
			currentCustomMessageQueue.clear();
			currentCustomMessageQueue.addAll(messageQueue);
			
			// Step 1.2) Iterate through the currentMessageQueue
			for(Message message : currentCustomMessageQueue)
			{
				// Step 1.2.1) Choose a random edge of the current message's location, and save the destination of the edge
				//											(IN CUSTOM THERE IS ONLY ONE NODE IN THE CURRENT ARRAY LIST)
				Random random = new Random();
				Node messageCurrent = message.getCurrent().get(0);
				Node messagePrevious = message.getPrevious().get(0);

				int next = random.nextInt(messageCurrent.getNeighborhoodsize());
				Node nextNode = messageCurrent.getNeighbor(next);
				
				// Next node can't be equal to current or previous
				while(nextNode == messageCurrent || nextNode == messagePrevious)
				{
					next = random.nextInt(messageCurrent.getNeighborhoodsize());
					nextNode = messageCurrent.getNeighbor(next);
				}
				
				// Step 1.2.2) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrementHopCount();
				totalCustomHops++;
				
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
					ArrayList<Node> newCurrent = new ArrayList<Node>();
					message.setPrevious(message.getCurrent());
					newCurrent.add(nextNode);
					message.setCurrent(newCurrent);
				}
			}
			n++;
		}
	}
	
	/**
	 * @return the total amount of hops it took the custom algorithm to terminate
	 */
	public int getTotalHops()
	{
		return totalCustomHops;
	}
	
	/* (non-Javadoc)
	 * @see network.Graph#getNumberOfCurrentMessages()
	 */
	public int getNumberOfCurrentMessages()
	{
		return messageQueue.size();
	}

}

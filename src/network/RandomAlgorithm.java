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
	
	/**
	 * Main constructor for the Random Algorithm
	 * 
	 * @param nodes list of nodes in the network
	 * @param frequency The frequency at which new messages are injected
	 */
	public RandomAlgorithm(ArrayList<Node> nodes, int frequency)
	{
		// List of Nodes within the graph
		this.graphNodes = nodes;
		// The rate at which messages will be created
		this.creationFrequency = frequency;
		
		// Create the first message
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
				// Step 2.1) Choose a random edge of the current message's location, and save the destination of the edge
				//											(IN RANDOM THERE IS ONLY ONE NODE IN THE CURRENT ARRAY LIST)
				Random random = new Random();
				Node messageCurrent = message.getCurrent().get(0);
				
				//Edge randomEdge = messageCurrent.getNeighbor(random.nextInt(messageCurrent.getNeighborhoodsize()));
				//Node nextNode = randomEdge.getDestination();
				int next = random.nextInt(messageCurrent.getNeighborhoodsize());
				Node nextNode = messageCurrent.getNeighbor(next);
				
				while(nextNode == messageCurrent)
				{
					//randomEdge = messageCurrent.getNeighbor(random.nextInt(messageCurrent.getNeighborhoodsize()));
					//nextNode = randomEdge.getDestination();
					next = random.nextInt(messageCurrent.getNeighborhoodsize());
					nextNode = messageCurrent.getNeighbor(next);
				}
				
				// Step 2.2) Increment the message's hop count and the total amount of hops for the algorithm
				message.incrementHopCount();
				totalHops++;
				
				// Step 2.3) Check to see if the message should create a new message
				checkFrequency(message);
				
				// Step 2.4) Check if nextNode is the destination of the message
				if(message.getDestination().equals(nextNode))
				{
					// Step 2.4.1) Remove the message from the queue
					removeMessage(message);
					
					// Step 2.4.2) Check to see if the queue is now empty
					if(messageQueue.size() == 0)
					{
						// TERMINATE ALGORITHM
						break;
					}
				}
				
				// Step 2.5) If nextNode is not the destination of the message
				else
				{
					// Step 2.5.1) Forward the message to the nextNode
					ArrayList<Node> newCurrent = new ArrayList<Node>();
					newCurrent.add(nextNode);
					message.setCurrent(newCurrent);
				}
			}
			steps++;
		}
	}

}

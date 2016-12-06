package network;

import java.util.*;


/**
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 * 
 * Graph implements the "Network" consisting of Nodes that are linked together by edges
 */
public abstract class Graph {
	
	// VARIABLES TO BE SHARED BY ALL ALGORITHMS
	
	// List of Nodes within the graph
	protected ArrayList<Node> graphNodes;
	// List of ALL messages created during the simulation
	protected ArrayList<Message> completeMessageList = new ArrayList<Message>();
	// List of messages currently in the network
	protected ArrayList<Message> messageQueue = new ArrayList<Message>();
	// A dynamic copy of the messageQueue to remove concurrency issues
	protected ArrayList<Message> currentMessageQueue = new ArrayList<Message>();
	// Total hops that occur during the algorithm
	protected int totalHops = 0;
	// A message is created each time a message completes x amount of hops
	protected int creationFrequency;
	// The starting amount of messages in the network
	protected int messageCount = 0;
	// The amount of steps completed
	protected int steps;
	// Used to name each created meassage
	protected String message = "Message";
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//		METHODS WITH SET BEHAVIOR TO BE SHARED BY ALL ALGORITHMS											 //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * While the algorithms are running, every time the amount of hops equals the user set frequency
	 * a new message is created and added to the network
	 */
	public void createNewMessage()
	{
		messageCount++;
		String messageName = message + messageCount;
		
		// The message's source and destination are set randomly
		Random random = new Random();
		Node tempS = graphNodes.get(random.nextInt(graphNodes.size()));
		Node tempD = graphNodes.get(random.nextInt(graphNodes.size()));
		
		// The destination CAN NOT be the same node as the source
		while(tempS.equals(tempD))
		{
			// If it is, get a new destination
			tempD = graphNodes.get(random.nextInt(graphNodes.size()));
		}
		
		// Form the message and add it to the message queue and total 
		Message message = new Message(tempS,tempD);
		message.setPrevious(message.getCurrent());
		message.setName(messageName);
		messageQueue.add(message);
		completeMessageList.add(message);
	}
	
	/**
	 * @param message The message to be removed from the queue when it has reached its destination
	 */
	public void removeMessage(Message message)
	{
		// Iterate over the message queue
		for(int i = 0; i < messageQueue.size(); i++)
		{
			// Find the message that has reached its destination
			if(messageQueue.get(i).equals(message))
			{
				// Remove
				messageQueue.get(i).setRunning(false);
				messageQueue.remove(i);
			}
		}
	}
	
	/**
	 * @return the next Message in the queue to be serviced
	 */
	public Message getMessage()
	{
		Message temp = messageQueue.get(0);
		return temp;
	}
	
	/**
	 * @return Arraylist of all the messages created during the simulation
	 */
	public ArrayList<Message> getCompleteMessageList()
	{
		return completeMessageList;
	}
	/**
	 * @return Arraylist of messages still in the network
	 */
	public ArrayList<Message> getCurrentMessageList()
	{
		return messageQueue;
	}
	
	/**
	 * 
	 * @param message The current message the algorithm is handling 
	 */
	public void checkFrequency(Message message)
	{
		// Retrieve the amount of hops the current message has completed
		int currentHops = message.getHopCount();
		
		// If the amount of hops is a multiple of the creationFrequency
		if((currentHops % creationFrequency) == 0)
		{
			createNewMessage();
		}
	}
	
	/**
	 * @return Total hops taken in algorithm
	 */
	public int getTotalHops()
	{
		return totalHops;
	}
	
	/**
	 * @return The current number of messages in algorithm
	 */
	public int getNumberOfCurrentMessages()
	{
		return messageQueue.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//		METHODS THAT HAVE SPECIFIC BEHAVIOR DEPENDING ON THE ALGORITHM										 //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Method that runs the algorithm against the Graph
	 * @param stepSize Step size used when running algorithm against graph
	 */
	abstract void run(int stepSize);
	
	
}

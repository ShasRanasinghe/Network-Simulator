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
	protected ArrayList<Message> completeMessageList = new ArrayList<>();
	// List of messages currently in the network
	protected ArrayList<Message> messageQueue;
	
	protected int creationFrequency;
	
	protected int messageCount = 0;
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
	protected void checkFrequency(Message message)
	{
		// Retrieve the amount of hops the current message has completed
		int currentHops = message.getHopCount();
		
		// If the amount of hops is a multiple of the creationFrequency
		if((currentHops % creationFrequency) == 0)
		{
			createNewMessage();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//		METHODS THAT HAVE SPECIFIC BEHAVIOR DEPENDING ON THE ALGORITHM										 //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Method that runs the algorithm against the Graph
	 * @param stepSize Step size used when running algorithm against graph
	 */
	abstract void run(int stepSize);
	/**
	 * @return Total hops taken in algorithm
	 */
	abstract int getTotalHops();
	/**
	 * @return The current number of messages in algorithm
	 */
	abstract int getNumberOfCurrentMessages();
	
}

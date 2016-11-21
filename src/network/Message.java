package network;


import java.util.*;

/**  
 * 
 * This class represents a message that travels through a Graph
 * 
 * @author	Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 */

public class Message {
	
	private Node source; // source node
	private ArrayList<Node> current; // current nodes of message
	private ArrayList<Node> previous; // previous node of message
	private Node destination; // destination node
	private String name;	// string id of the message
	private boolean running;	// is the message still in the network?
	private int hopCount; // hop count for this message

	/**
	 * @param source node
	 * @param destination node
	 * creates a message to be injected into the network 
	 */
	public Message(Node source, Node destination)
	{
		this.source = source;
		this.destination = destination;
		running = true;
		name = "";
		hopCount = 0;
		current = new ArrayList<Node>();
		current.add(source);
		previous = new ArrayList<Node>();
		
		running = true;
		name = "";
		hopCount = 0;
		
	}
	
	/**
	 * @param message
	 * constructor for testing purposes
	 */
	public Message(Message message)
	{
		source = message.getSource();
		destination = message.getDestination();
		hopCount = message.getHopCount();
		current = message.getCurrent();
		running = message.isRunning();
		name = message.toString();
		previous = new ArrayList<Node>();
		previous = message.getPrevious();
		
	}

	/**
	 * 
	 * @return The name id of the message
	 */
	public String getName()
	{
		return name;
	}
	
	public Node getSource()
	{
		return source;
	}
	
	/**
	 * @return the current node
	 */
	public ArrayList<Node> getCurrent() 
	{
		return current;
	}
	
	/**
	 * 
	 * @return the previous node
	 */
	public ArrayList<Node> getPrevious() 
	{
		return previous;
	}

	/**
	 * @return The destination of the message
	 */
	public Node getDestination() 
	{
		return destination;
	}

	/**
	 * 
	 * @return the hop count of the message
	 */
	public int getHopCount() 
	{
		return hopCount;
	}

	/**
	 * @param name name of the message eg. "Message 1"
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * @param source Vertex to be set as the source
	 */
	public void setSource(Node source) 
	{
		this.source = source;
	}

	/**
	 * @param current sets the current node
	 */
	public void setCurrent(ArrayList<Node> current) 
	{
		this.current = current;
	}

	public void setPrevious(ArrayList<Node> previous)
	{
		this.previous = previous;
	}

	/**
	 * @param destination Vertex to be set as the destination
	 */
	public void setDestination(Node destination) 
	{
		this.destination = destination;
	}
	
	/**
	 * adds to the hop count the message has taken
	 * @param hopCount  set the hopCount 
	 */
	public void incrementHopCount()
	{
		hopCount++;
	}
	
	/**
	 * Source ID
	 * @return The source ID
	 */
	public String getSourceID(){
		return source.toString();
	}

	/**
	 * @return true id message is running, false if message has reached its destination
	 */
	public boolean isRunning() 
	{
		return running;
	}
	
	public void setRunning(boolean b)
	{
		running = b;
	}
	
	/**
	 * Source ID
	 * @return The destination ID
	 */
	public String getDestinationID(){
		return destination.toString();
	}
	
	/**
	 * Compares message to see if it has the same source and destination
	 * @param obj Message to be compared against
	 * @return True if messages have same source and destination else false
	 */
	public boolean hasSameSourceAndDestination(Object obj){
		Message message = (Message)obj;
		if(this.getSource().equals(message.getSource()) && this.getDestination().equals(message.getDestination())){
			return true;
		}
		return false;
	}
	
	/**
	 * Test to compare the full object of Message
	 */
	@Override
	public boolean equals(Object obj){
		Message message = (Message)obj;
		if(this.getSource().equals(message.getSource()) 
				&& this.getDestination().equals(message.getDestination()) 
				&& this.getHopCount() == message.getHopCount()
				&& this.getCurrent().equals(message.getCurrent())
				&& this.isRunning() == message.isRunning())
		{
			return true;
		}
		return false;
	}
	
	public String getDetailedString(){
		String returnString;
		returnString = source + " -> " + destination + "\n"
				+ "Hop Count: " + hopCount + "\n"
				+ "Current Node: ";
		if(!running){
			returnString += destination;
		}else{
			returnString += current;
		}
		
		return returnString;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
}

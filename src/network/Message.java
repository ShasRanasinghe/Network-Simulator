package network;
/**  
 * 
 * This class represents a message that travels through a Graph
 * 
 * @author	Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 */

public class Message {
	
	private Node source; // source node
	private Node current; // current node of message
	private Node destination; // destination node
	
	private String name;
	
	private boolean running;
	
	private int hopCount; // hop count for this message
	

	/**
	 * @param source node
	 * @param destination node
	 * creates a message to be injected into the network 
	 */
	public Message(Node source, Node destination){
		this.source = source;
		this.current = source;
		this.destination = destination;
		this.running = true;
		this.name = "";
		this.hopCount = 0;
	}

	/**
	 * @return The source of the message
	 */
	public Node getSource() 
	{
		return source;
	}

	/**
	 * @param source Vertex to be set as the source
	 */
	public void setSource(Node source) 
	{
		this.source = source;
	}

	/**
	 * @return The destination of the message
	 */
	public Node getDestination() 
	{
		return destination;
	}

	/**
	 * @param destination Vertex to be set as the destination
	 */
	public void setDestination(Node destination) 
	{
		this.destination = destination;
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
	 * 
	 * @param hopCount  set the hopCount 
	 */
	public void setHopCount(int hopCount) 
	{
		this.hopCount = hopCount;
	}
	
	/**
	 * @return true id message is running, false if message has reached its destination
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running true if running, false if message has reached its destination
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @param name name of the message eg. "Message 1"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Current Node the message is in
	 */
	public Node getCurrentNode() {
		return current;
	}

	/**
	 * @param current The current node the message is in
	 */
	public void setCurrentNode(Node current) {
		this.current = current;
	}

	/**
	 * adds to the hop count the message has taken
	 */
	public void incrumentHopCount()
	{
		hopCount++;
	}
	
	
	
}

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
	 * @param message
	 * constructor for testing purposes
	 */
	public Message(Message message)
	{
		this.source = message.getSource();
		this.destination = message.getDestination();
		this.hopCount = message.getHopCount();
		this.current = message.getCurrent();
		this.running = message.isRunning();
		this.name = message.toString();
	}

	/**
	 * @return The source of the message
	 */
	public Node getSource() 
	{
		return source;
	}
	
	/**
	 * @return the current node
	 */
	public Node getCurrent() {
		return current;
	}

	/**
	 * @param current sets the current node
	 */
	public void setCurrent(Node current) {
		this.current = current;
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
	
	@Override
	public boolean equals(Object obj){
		Message message = (Message)obj;
		if(this.getSource().equals(message.getSource()) 
				&& this.getDestination().equals(message.getDestination()) 
				&& this.getHopCount() == message.getHopCount()
				&& this.getCurrentNode().equals(message.getCurrentNode())
				&& this.isRunning() == message.isRunning())
		{
			return true;
		}
		return false;
	}
	
	
	
}

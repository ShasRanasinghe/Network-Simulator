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
	private Node destination; // destination node
	private boolean running;
	private int hopCount; // hop count for this message
	private String name;

	/**
	 * @param source node
	 * @param destination node
	 * creates a message to be injected into the network 
	 */
	public Message(Node source, Node destination){
		this.source = source;
		this.destination = destination;
		this.hopCount = 0;
		this.running = true;
		this.name = "";
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
	
	public void incrumentHopCount()
	{
		hopCount++;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public String toString(){
		return name;
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}

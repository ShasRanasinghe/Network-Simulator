package network;
/**  
 * 
 * This class represents a message that travels through a Graph
 * 
 * @author	Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 */

public class Message {
	
	private Node source;
	private Node destination;
	
	public Message(Node source, Node destination){
		this.source = source;
		this.destination = destination;
	}

	/**
	 * @return The source of the message
	 */
	public Node getSource() {
		return source;
	}

	/**
	 * @param source Vertex to be set as the source
	 */
	public void setSource(Node source) {
		this.source = source;
	}

	/**
	 * @return The destination of the message
	 */
	public Node getDestination() {
		return destination;
	}

	/**
	 * @param destination Vertex to be set as the destination
	 */
	public void setDestination(Node destination) {
		this.destination = destination;
	}
	
}

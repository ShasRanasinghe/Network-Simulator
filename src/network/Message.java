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
	private ArrayList<ArrayList<Node>> listOfCurrent; //list of the lists containing the current node of the message
	//private ArrayList<Node> current; // current nodes of message
	private ArrayList<Node> previous; // previous node of message
	private Node destination; // destination node
	private String name;	// string id of the message
	private boolean running;	// is the message still in the network?
	private int hopCount; // hop count for this message
	private int currentListPosition = 0;

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
		listOfCurrent = new ArrayList<>();
		ArrayList<Node> current = new ArrayList<Node>();
		current.add(source);
		listOfCurrent.add(current);
		previous = new ArrayList<Node>();		
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
		listOfCurrent = message.getListOfCurrent();
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
	
	/**
	 * 
	 * @return the source node of the message
	 */
	public Node getSource()
	{
		return source;
	}
	
	/**
	 * @return the current node
	 */
	public ArrayList<Node> getCurrent() 
	{
		return listOfCurrent.get(currentListPosition);
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
	 * @return Previous array list of nodes
	 */
	public ArrayList<Node> getPrevious(){
		return previous;
	}
	
	/**
	 * 
	 * @param c the new list of current nodes that the message is currently at
	 */
	public void setCurrent(ArrayList<Node> c)
	{
		listOfCurrent.add(c);
		currentListPosition++;
	}
	
	/**
	 * @param previous Sets the previous array list of Nodes
	 */
	public void setPrevious(ArrayList<Node> previous){
		this.previous = previous;
	}

	/**
	 * @param hopCount Hop count of message 
	 */
	public void setHopCount(int hopCount) 
	{
		this.hopCount = hopCount;
	}
	
	/**
	 * Used to get the previous positions of the message
	 */
	public void stepBack(){
		if(!running){
			running = true;
		}else{
			currentListPosition--;
			hopCount--;
		}
	}
	
	/**
	 * Used to move the message forward once stepped back
	 * @param firstTime true if its the first time the message is being seen
	 * @return returns false, if we have reached the end
	 */
	public boolean stepForward(boolean firstTime){
		if(currentListPosition == listOfCurrent.size()-1){
			if(listOfCurrent.get(listOfCurrent.size()-1).get(0).equals(destination)){
				running = false;
			}
			return false;
		}
		if(!firstTime){
			currentListPosition++;
		}
		return true;
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
	 * adds to the hop count the message has taken
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
		if(this.getSourceID().equals(message.getSourceID()) && this.getDestinationID().equals(message.getDestinationID())){
			return true;
		}
		if(this.getSourceID().equals(message.getDestinationID()) && this.getDestinationID().equals(message.getSourceID())){
			return true;
		}
		return false;
	}
	
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
	
	/**
	 * @return Detailed string output of message
	 */
	public String getDetailedString(){
		String returnString;
		returnString = source + " -> " + destination + "\n"
				+ "Hop Count: " + hopCount + "\n"
				+ "Current Node: ";
		if(!running){
			returnString += destination;
		}else{
			for(Node node : listOfCurrent.get(currentListPosition)){
				returnString += node.toString() + " ";
			}
		}
		
		return returnString;
	}

	public ArrayList<ArrayList<Node>> getListOfCurrent() {
		return listOfCurrent;
	}
	
}

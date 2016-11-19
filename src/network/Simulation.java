package network;

import java.util.*;
import java.lang.Integer;

/**
 * 
 * This class creates a simulation of the Network
 * 
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 */

public class Simulation {
	
	//Metrics:
	//Maybe I should have total hops too
	private int averageHops;
	private int packets;
	private int totalMessages;
	private int frequency;
	
	private ALGORITHM algorithm;
	private Graph selectedAlgorithm;
	
	// Nodes of the simulation
	private ArrayList<Node> simulationNodes;
	
	// Message instance of the simulation (returned after the algorithm finishes a set amount of steps)
	public ArrayList<Message> totalMessageList;
	public ArrayList<Message> currentMessageList;
	
	/**
	 * Constructor initializes the simulation
     */
	public Simulation()
	{
		averageHops = 0;
		packets = 0;
		totalMessages = 0;
		frequency = 0;
		simulationNodes = new ArrayList<Node>();
		totalMessageList = new ArrayList<Message>();
	}
	
	/**
	 * @param nodeIDs Array list of node IDs
	 */
	public void createNodes(ArrayList<String> nodeIDs) 
	{
		simulationNodes.clear();
	
		for(String nodeId : nodeIDs)
		{
			Node temp = new Node(nodeId);
			simulationNodes.add(temp);
		}

	}

    /**
	 * @param A Node 
	 * @param B Node
	 * Creates a link between the two nodes. IE adds an edge to each nodes "hood" (ArrayList)
	 */
	public void createLink(String A, String B)
	{
		Node node1 = getNodeGivenID(A);
		Node node2 = getNodeGivenID(B);
		Edge e = new Edge(node1.getID() + "->" + node2.getID(),node1,node2);
		Edge e2 = new Edge(node2.getID() + "->" + node1.getID(),node2,node1);
		node1.addNeighbor(node2);
		node2.addNeighbor(node1);
	}
	
	/**
	 * @param A Node
	 * @param B Node
	 * Removes the links between nodes in the nodes
	 */
	public void removeLink(String A, String B){
		Node node1 = getNodeGivenID(A);
		Node node2 = getNodeGivenID(B);
		Edge e = new Edge(node1.getID() + "->" + node2.getID(),node1,node2);
		Edge e2 = new Edge(node2.getID() + "->" + node1.getID(),node2,node1);
		node1.removeNeighbor(node2);
		node2.removeNeighbor(node1);
	}

	/**
	 * 
	 * @param id ID of node
	 * @return Node object if an ID matches
	 */
	public Node getNodeGivenID(String id) 
	{
	
		for(Node node : simulationNodes){
			if(node.getID().equals(id)){
			//Returns the node when found
			return node;
		    	}
	    	}
	    	//Returns an empty node if not found
	    	return new Node("");
	}

	/**
     * @param edgeIDs Array list of edge IDs
	 */
	public void addNeighbors(ArrayList<String> edgeIDs) 
	{
		//Edges already validated when passed in
		for(String edgeID : edgeIDs)
		{
		    String[] splitEdge = edgeID.split("->");
		    String nodeOneID = splitEdge[0]; 
		    String nodeTwoID = splitEdge[1];
		    //Node nodeOne = getNodeGivenID(simulationNodes, nodeOneID);
		    //Node nodeTwo = getNodeGivenID(simulationNodes, nodeTwoID);

		    //Add neighbors
			createLink(nodeOneID, nodeTwoID);
		}
	}
	
	/**
	 * @param stepSize The size of each step when run algorithm is called
	 */
	public void runAlgorithm(int stepSize)
	{
		Integer tempF = frequency;
		if(frequency == 0 || frequency == 1 && tempF != null)
		{
			// ERROR: invalid message creation frequency
			System.out.println("ERROR: invalid message creation frequency (please select a frequency not equal to 0 or 1)");
		}
		else
		{
			selectedAlgorithm.run(stepSize);
			retrieveState();
		}
	}
	
	
	/**
	 * Retrieves data from the graph to be used in the simulation
	 */
	public void retrieveState()
	{
		totalMessageList = selectedAlgorithm.getCompleteMessageList();
		currentMessageList = selectedAlgorithm.getCurrentMessageList();
		
		setPackets(selectedAlgorithm.getTotalHops());
		setTotalMessages(totalMessageList.size());
		
		setAverageHops(packets/totalMessages);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//		GETTERS AND SETTERS																					 //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return the simulationNodes
	 */
	public ArrayList<Node> getSimulationNodes() {
		return simulationNodes;
	}

	/**
	 * @param simulationNodes the simulationNodes to set
	 */
	public void setSimulationNodes(ArrayList<Node> simulationNodes) {
		this.simulationNodes = simulationNodes;
	}
	
	/**
	 * @return The average number of hops each message goes through from start to end
	 */
	public int getAverageHops() 
	{
		return averageHops;
	}

	/**
	 * @param averageHops Set the number of hops of a message
	 */
	public void setAverageHops(int averageHops) 
	{
		this.averageHops = averageHops;
	}

	/**
	 * @return Total number of packets transmitted
	 */
	public int getPackets() 
	{
		return packets;
	}

	/**
	 * @param packets Set the number of packets transmitted
	 */
	public void setPackets(int packets) 
	{
		this.packets = packets;
	}

	/**
	 * @param totalMessages the totalMessages to set
	 */
	public void setTotalMessages(int totalMessages) {
		this.totalMessages = totalMessages;
	}
	
	/**
	 * @return the total messages in the simulation
	 */
	public int getTotalMessages()
	{
		return totalMessages;
	}

	/**
	 * @param frequency Frequency to run simulation with
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * @return frequency for simulation
	 */
	public int getFrequency()
	{
		return frequency;
	}

	/**
	 * @param algorithm The algorithm the simulation would be set to
	 */
	public void setAlgorithm(ALGORITHM algorithm) 
	{
		this.algorithm = algorithm;
		switch(this.algorithm)
		{
			case FLOODING:	selectedAlgorithm = new FloodingAlgorithm(simulationNodes, frequency);
			 				currentMessageList = selectedAlgorithm.messageQueue;
			 				break;
				
			case SHORTESTPATH:	selectedAlgorithm = new ShortestPathAlgorithm(simulationNodes, frequency);
			 					currentMessageList = selectedAlgorithm.messageQueue;
			 					break;
				
			case CUSTOM:	selectedAlgorithm = new CustomAlgorithm(simulationNodes, frequency);
			 				currentMessageList = selectedAlgorithm.messageQueue;
			 				break;
				
			default: selectedAlgorithm = new RandomAlgorithm(simulationNodes, frequency);
					 currentMessageList = selectedAlgorithm.messageQueue;
					 break;
		}			
	}
	
	/**
	 * @return the algorithm choosen
	 */
	public ALGORITHM getAlgorithm()
	{
		return algorithm;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//		MAIN METHOD FOR ENTIRE NETWORK!!!!																	 //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param args Main method input
	 */
/*	public static void main(String[] args) {
		
		//Initialize Simulation
		Simulation simulation = new Simulation();
		int frequency = 0;
		ArrayList<String> nodeIDs = new ArrayList<>(); 
		ArrayList<String> edgeIDs = new ArrayList<>(); 

		//Initialize Controller
		Controller controller = new Controller();
		
		if(!controller.isTesting()){
			//Get user input from controller
			controller.start();
			frequency = controller.getFrequency();
			nodeIDs = controller.getNodes();
			edgeIDs = controller.getEdges();
		}
		//Used to test network
		else
		{
			frequency = 5;
			nodeIDs.add("A");nodeIDs.add("B");nodeIDs.add("C");nodeIDs.add("D");nodeIDs.add("E");
			edgeIDs.add("A->B");edgeIDs.add("A->C");edgeIDs.add("A->E");edgeIDs.add("C->D");edgeIDs.add("D->B");
			edgeIDs.add("B->E");
		}
		
		// Create nodes in the simulation
		simulation.createNodes(nodeIDs);
		
		// Add edges to all nodes in simulation
		simulation.addNeighbors(edgeIDs);

		// Run specified algorithm
		ALGORITHM userAlgorithm = controller.getAlgorithm();
		
		// If the user wants to run the random algorithm
		if(userAlgorithm == ALGORITHM.RANDOM)
		{
			RandomAlgorithm randomAlgorithm = new RandomAlgorithm(simulation.simulationNodes, frequency);
			
			// USER MUST INPUT THE STEP SIZE!!!!!!!!!!!!!!!!!!!!!!!!!!
			randomAlgorithm.run(50);
			
			//Metrics
			simulation.setTotalMessages(randomAlgorithm.completeMessageList.size());
			//IS THIS THE RUNNING TOTAL!!!!
			simulation.setPackets(randomAlgorithm.getTotalHops());
		}

		// Calculate the average
		simulation.setAverageHops(simulation.packets/simulation.totalMessages);

		//Print metrics
		controller.printTotalPackets(simulation.getPackets());
		System.out.println("total number of messages created: " + simulation.totalMessages);
		//graph.printTable();
	}*/
}
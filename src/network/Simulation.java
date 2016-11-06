package network;

import java.util.*;

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
	}
	
	/**
	 * @param nodeIDs Array list of node IDs
	 */
	public void createNodes(ArrayList<String> nodeIDs) 
	{
		for(String nodeID : nodeIDs)
		{
			simulationNodes.add(new Node(nodeID));
		}
	}

    /**
	 * @param A Node 
	 * @param B Node
	 * Creates a link between the two nodes. IE adds an edge to each nodes "hood" (ArrayList)
	 */
	public void createLink(Node A, Node B)
	{
		Edge e = new Edge(A.getID() + "->" + B.getID(),A,B);
		Edge e2 = new Edge(B.getID() + "->" + A.getID(),B,A);
		A.addNeighbor(e);
		B.addNeighbor(e2);
	}

	/**
	 * 
	 * @param nodes List of nodes to traverse
	 * @param id ID of node
	 * @return Node object if an ID matches
	 */
	public Node getNodeGivenID(ArrayList<Node> nodes, String id) 
	{
	
		for(Node node : nodes){
			if(node.getID().equals(id)){
			//Returns the node when found
			return node;
		    	}
	    	}
	    	//Returns an empty node if not found
	    	return new Node("");
	}

	/**
     * @param allNodes Array list of nodes
     * @param edgeIDs Array list of edge IDs
	 */
	public void addNeighbors(ArrayList<Node> allNodes, ArrayList<String> edgeIDs) 
	{
		//Edges already validated when passed in
		for(String edgeID : edgeIDs)
		{
		    String[] splitEdge = edgeID.split("->");
		    String nodeOneID = splitEdge[0]; 
		    String nodeTwoID = splitEdge[1];
		    Node nodeOne = getNodeGivenID(allNodes, nodeOneID);
		    Node nodeTwo = getNodeGivenID(allNodes, nodeTwoID);

		    //Add neighbors
		    if(!nodeOne.getID().isEmpty() && !nodeTwo.getID().isEmpty()){
			createLink(nodeOne, nodeTwo);
		    }
		}
	}
	
	public void runAlgorithm(int stepSize)
	{
		if(frequency == 0 || frequency == 1)
		{
			// ERROR: invalid message creation frequency
			System.out.println("ERROR: invalid message creation frequency (please select a frequency not equal to 0 or 1)");
		}
		selectedAlgorithm.run(stepSize);
		retrieveState();
	}
	
	/*public void runFullAlgorithm()
	{
		
		while(selectedAlgorithm.getNumberOfCurrentMessages() != 0)
		{
			runAlgorithm(1);
		}
		
		retrieveState();
	}*/
	
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

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setAlgorithm(ALGORITHM algorithm) 
	{
		this.algorithm = algorithm;
		switch(this.algorithm)
		{
			case FLOODING: break;// No Flooding Algorithm implemented yet
				
			case SHORTESTPATH:	break;// No Shortest Path Algorithm implemented yet
				
			case CUSTOM:	break;// No Custom Algorithm implemented yet
				
			default: selectedAlgorithm = new RandomAlgorithm(simulationNodes, frequency);
					 currentMessageList = selectedAlgorithm.messageQueue;
					 break;
		}			
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//		MAIN METHOD FOR ENTIRE NETWORK!!!!																	 //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param args Main method input
	 */
	public static void main(String[] args) {
		
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
		simulation.addNeighbors(simulation.simulationNodes, edgeIDs);

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
	}
}
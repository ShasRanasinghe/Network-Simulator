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
	
	/**
	 * Constructor initializes the simulation
     */
	public Simulation(){
		averageHops = 0;
		packets = 0;
	}

	/**
	 * @return The average number of hops each message goes through from start to end
	 */
	public int getAverageHops() {
		return averageHops;
	}

	/**
	 * @param hops Set the number of hops of a message
	 */
	public void setAverageHops(int averageHops) {
		this.averageHops = averageHops;
	}

	/**
	 * @return Total number of packets transmitted
	 */
	public int getPackets() {
		return packets;
	}

	/**
	 * @param packets Set the number of packets transmitted
	 */
	public void setPackets(int packets) {
		this.packets = packets;
	}

	/**
	 * 
	 * @param nodes List of nodes to traverse
	 * @param id ID of node
	 * @return Node object if an ID matches
	 */
	public Node getNodeGivenID(ArrayList<Node> nodes, String id) {

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
	 * @param nodeIDs Array list of node IDs
	 * @return Array list of nodes
	 */
	public ArrayList<Node> createNodes(ArrayList<String> nodeIDs) {
		ArrayList<Node> nodes = new ArrayList<>();
		for(String nodeID : nodeIDs){
			nodes.add(new Node(nodeID));
		}
        return nodes;
	}

    /**
     * @param allNodes Array list of nodes
     * @param edgeIDs Array list of edge IDs
     * @return Array list of nodes with neighbors
	 */
	public Graph createGraph(ArrayList<Node> allNodes, ArrayList<String> edgeIDs) {
		
		//Initialize graph
		Graph graph = new Graph(allNodes);

		//Edges already validated when passed in
		for(String edgeID : edgeIDs){
		    String[] splitEdge = edgeID.split("->");
		    String nodeOneID = splitEdge[0]; 
		    String nodeTwoID = splitEdge[1];
		    Node nodeOne = getNodeGivenID(allNodes, nodeOneID);
		    Node nodeTwo = getNodeGivenID(allNodes, nodeTwoID);

		    //Add neighbors
		    if(!nodeOne.getID().isEmpty() && !nodeTwo.getID().isEmpty()){
			graph.createLink(nodeOne, nodeTwo);
		    }
		}

		return graph;
	}
	
	public static void main(String[] args) {
		//Internal Testing flag
		boolean testFlag = true;
		
		//Initialize Simulation
		Simulation simulation = new Simulation();
		int frequency = 0;
		ArrayList<String> nodeIDs = new ArrayList<>(); 
		ArrayList<String> edgeIDs = new ArrayList<>(); 

		//Initialize Controller
		Controller controller = new Controller();
		
		if(!testFlag){
			//Get user input from controller
			controller.start();
			frequency = controller.getFrequency();
			nodeIDs = controller.getNodes();
			edgeIDs = controller.getEdges();
		}
		
		//Used to test network
		if(testFlag){
			frequency = 2;
			nodeIDs.add("A");nodeIDs.add("B");nodeIDs.add("C");nodeIDs.add("D");nodeIDs.add("E");
			edgeIDs.add("A->B");edgeIDs.add("A->C");edgeIDs.add("A->E");edgeIDs.add("C->D");edgeIDs.add("D->B");
			edgeIDs.add("B->E");
		}
		
		//Handle the nodes
		ArrayList<Node> nodes = simulation.createNodes(nodeIDs);
		//Handle the edges
		Graph graph = simulation.createGraph(nodes, edgeIDs);
		
		graph.createMessage();

		//Algorithm  runs
		graph.runAlgorithm(frequency, ALGORITHM.RANDOM);

		//Metrics
		int totalMessages = graph.getTotalMessages();
		
		//IS THIS THE TOTAL FOR EACH MESSAGE OR IS IT THE RUNNING TOTAL???
		int totalHops = graph.getTotalHops();

		//Set simulation metrics
		simulation.setPackets(totalHops);
		simulation.setAverageHops(totalHops/totalMessages);

		//Print metrics
		controller.printMetrics(simulation.getPackets(), simulation.getAverageHops());
		System.out.println("total number of messages created: " + totalMessages);
		//graph.printTable();
	}
}
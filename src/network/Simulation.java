package network;

import java.util.ArrayList;
import java.util.Observable;


/**
 * 
 * This class creates a simulation of the Network
 * 
 * @author Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 */

public class Simulation extends Observable{
	
	//Metrics:
	//Maybe I should have total hops too
	private ArrayList<String> averageHops;
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
		averageHops = new ArrayList<>();
		packets = 0;
		totalMessages = 0;
		frequency = 0;
		simulationNodes = new ArrayList<Node>();
		totalMessageList = new ArrayList<Message>();
		selectedAlgorithm = null;
	}
	
	/**
	 * @param nodeIDs Array list of node IDs
	 */
	public void createNodes(String[] nodeIDs) 
	{
		simulationNodes.clear();
	
		for(String nodeId : nodeIDs)
		{
			Node temp = new Node(nodeId);
			simulationNodes.add(temp);
		}

	}
	
	public boolean addNewNode(String nodeID){
		for(Node node: simulationNodes){
			if(node.toString().equals(nodeID)){
				return false;
			}
		}
		simulationNodes.add(new Node(nodeID));
		return true;
	}
	
	public boolean removeNode(String nodeID){
		for(int i = 0; i<simulationNodes.size();i++){
			Node node = simulationNodes.get(i);
			if(node.toString().equals(nodeID)){
				simulationNodes.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean isNode(String nodeID){
		for(Node node: simulationNodes){
			if(node.toString().equals(nodeID)){
				return true;
			}
		}
		return false;
	}
	
	public void editNodeID(String prevNodeID, String newNodeID){
		for(Node node: simulationNodes){
			if(node.toString().equals(prevNodeID)){
				node.setNodeID(newNodeID);
				break;
			}
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
			if(node.toString().equals(id)){
				//Returns the node when found
				return node;
			}
		}
		//Returns an empty node if not found
		return null;
	}

	/**
     * @param edgeIDs Array list of edge IDs
	 */
	public void addNeighbors(String[] edgeIDs) 
	{
		//Edges already validated when passed in
		for(String edgeID : edgeIDs)
		{
		    String[] splitEdge = edgeID.split("->");
		    String nodeOneID = splitEdge[0]; 
		    String nodeTwoID = splitEdge[1];
		    
		    //Add neighbors
			createLink(nodeOneID, nodeTwoID);
		}
	}
	
	/**
	 * @param stepSize The size of each step when run algorithm is called
	 */
	public void runAlgorithm(int stepSize)
	{
			selectedAlgorithm.run(stepSize);
			State state = retrieveState();
			setChanged();
			notifyObservers(state);
	}
	
	
	/**
	 * Retrieves data from the graph to be used in the simulation
	 */
	private State retrieveState()
	{
		State state = new State();
		totalMessageList = selectedAlgorithm.getCompleteMessageList();
		state.setTotalMessageList(totalMessageList);
		
		currentMessageList = selectedAlgorithm.getCurrentMessageList();
		state.setCurrentMessageList(currentMessageList);
		
		setPackets(selectedAlgorithm.getTotalHops());
		
		setTotalMessages(totalMessageList.size());
		state.setTotalMessages(totalMessages);
		
		//TODO
		//setAverageHops(packets/totalMessages);
		String[] averageHopsList = new String[averageHops.size()];
		for(int i = 0; i<averageHops.size(); i++){
			averageHopsList[1] = averageHops.get(i);
		}
		state.setAverageHopsList(averageHopsList);
		
		return state;
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
	public ArrayList<String> getAverageHops() 
	{
		return averageHops;
	}

	/**
	 * @param averageHops Set the number of hops of a message
	 */
	public void setAverageHops(ArrayList<String> averageHops) 
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
	public void setupGraph() 
	{
		switch(algorithm)
		{
			case FLOODING: break;// No Flooding Algorithm implemented yet
				
			case SHORTESTPATH:	break;// No Shortest Path Algorithm implemented yet
				
			case CUSTOM:	break;// No Custom Algorithm implemented yet
				
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

	public void initializeDefaultNetwork() {
		this.frequency = 5;
		this.algorithm = ALGORITHM.RANDOM;
		createNodes(Constants.DEFAULT_NODES_SET);
		addNeighbors(Constants.DEFAULT_EDGES_SET);
	}
	
	public void initializeNewNetwork(){
		frequency = 0;
		algorithm = null;
		averageHops = new ArrayList<>();
		packets = 0;
		totalMessages = 0;
		simulationNodes.clear();
		totalMessageList.clear();
		currentMessageList = null;
		//selectedAlgorithm.reset();//TODO
	}
	
	public boolean checkFullInitialization(){
		
		if(frequency != 0 && algorithm != null){
			if(selectedAlgorithm == null){
				setupGraph();
			}
			return true;
		}
		return false;
	}

	public ArrayList<String> getStringArrayNodes() {
		ArrayList<String> nodes = new ArrayList<>();
		for(Node node: simulationNodes){
			nodes.add(node.toString());
		}
		return nodes;
	}

	public boolean isRunning() {
		if(currentMessageList.size() != 0){
			return true;
		}else{
			selectedAlgorithm = null;
			return false;
		}
	}

	public void setAlgorithm(ALGORITHM algorithm) {
		this.algorithm = algorithm;
	}

	public boolean networkExists() {
		if(simulationNodes.size()>=2 
				&& simulationNodes.get(0).getNeighborhoodsize()>0){
			return true;
		}
		return false;
	}

	public void resetSimulation() {
		frequency = 0;
		algorithm = null;
		averageHops = new ArrayList<>();
		packets = 0;
		totalMessages = 0;
		totalMessageList.clear();
		currentMessageList = null;
		selectedAlgorithm = null;
	}

}
package network;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.xml.bind.JAXBException;


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
	private Graph graph;
	
	// Nodes of the simulation
	private ArrayList<Node> simulationNodes;
	
	// Message instance of the simulation (returned after the algorithm finishes a set amount of steps)
	public ArrayList<Message> totalMessageList;
	public ArrayList<Message> currentMessageList;
	
	private List<State> stateStack;
	private int stackPosition;
	private boolean useAlgorithm;
	
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
		currentMessageList = new ArrayList<Message>();
		graph = null;
		stateStack = new ArrayList<>();
		useAlgorithm = true;
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
	
	/**
	 * Adds a new node to simulation
	 * @param nodeID New node to be added to Simulation
	 * @return true if new node was added
	 */
	public boolean addNewNode(String nodeID){
		for(Node node: simulationNodes){
			if(node.toString().equals(nodeID)){
				return false;
			}
		}
		simulationNodes.add(new Node(nodeID));
		return true;
	}
	
	/**
	 * Removes node from simulation
	 * @param nodeID Node to be removed from Simulation
	 * @return true if node removed
	 */
	public boolean removeNode(String nodeID){
		for(int i = 0; i<simulationNodes.size();i++){
			Node node = simulationNodes.get(i);
			if(node.toString().equals(nodeID)){
				for(Node neighbor: node.getNeighbors()){
					neighbor.removeNeighbor(node);
				}
				simulationNodes.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if node exists in simulation
	 * @param nodeID The node to be checked
	 * @return true if the node is in simulation
	 */
	public boolean isNode(String nodeID){
		for(Node node: simulationNodes){
			if(node.toString().equals(nodeID)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Edits node within simulation
	 * @param prevNodeID Previous node
	 * @param newNodeID New node ID to replace previous
	 */
	public void editNodeID(String prevNodeID, String newNodeID){
		for(Node node: simulationNodes){
			if(node.toString().equals(prevNodeID)){
				node.setNodeID(newNodeID);
				break;
			}
		}
	}

    /**
     * Creates a link between the two nodes. IE adds an edge to each nodes "hood" (ArrayList)
	 * @param A Node A
	 * @param B Node B
	 */
	public void createLink(String A, String B)
	{
		Node node1 = getNodeGivenID(A);
		Node node2 = getNodeGivenID(B);
		node1.addNeighbor(node2);
		node2.addNeighbor(node1);
	}
	
	/**
	 * Removes the links between nodes in the nodes
	 * @param A Node A
	 * @param B Node B
	 */
	public void removeLink(String A, String B){
		Node node1 = getNodeGivenID(A);
		Node node2 = getNodeGivenID(B);
		node1.removeNeighbor(node2);
		node2.removeNeighbor(node1);
	}

	/**
	 * Returns Node given ID
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
	 * Adds neighbors to simulation
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
	 * Runs the algorithm given step size
	 * @param stepSize The size of each step when run algorithm is called
	 */
	public void runAlgorithm(int stepSize)
	{
		graph.run(stepSize);
		State state = retrieveState();
		stateStack.add(state);
		stackPosition = stateStack.size()-1;
		setChanged();
		notifyObservers(state);
	}
	
	public void stepForward(int stepSize){
		if(useAlgorithm){
			runAlgorithm(stepSize);
		}else{
			stackPosition ++;
			State state = stateStack.get(stackPosition);
			if(!(stateStack.size() == 1 || stackPosition == 0)){
				stepForwardMessages(state.getCurrentMessageList(), stateStack.get(stackPosition-1).getCurrentMessageList());
			}
			state.setUndo(false);
			setChanged();
			notifyObservers(state);

			if(stackPosition == stateStack.size()-1){
				useAlgorithm = true;
			}
		}
	}

	/**
	 * Retrieves the last state and updates the view with it
	 */
	public void stepBack() {
		if(stateStack.size() > 1 && stackPosition != 0){
			stackPosition --;
			State state = stateStack.get(stackPosition);
			stepBackMessages(state.getCurrentMessageList());
			useAlgorithm = false;
			state.setUndo(true);
			setChanged();
			notifyObservers(state);
		}else{
			stackPosition--;
			useAlgorithm = false;
		}
	}
	
	
	/**
	 * Steps all the messages in the current list back
	 * @param currentMessageList list of current messages in the network
	 */
	private void stepBackMessages(ArrayList<Message> currentMessageList) {
		for(Message message : currentMessageList){
			message.stepBack();
		}
	}
	
	/**
	 * Steps all the messages in the current list forward
	 * @param currentMessageList list of current messages in the network
	 * @param prevCurrentMessageList previous list of current messages to compare
	 */
	private void stepForwardMessages(ArrayList<Message> currentMessageList, ArrayList<Message> prevCurrentMessageList) {	
		for(Message message : prevCurrentMessageList){
			if(!currentMessageList.contains(message)){
				message.setRunning(false);
			}
		}
		for(Message message : currentMessageList){
			message.stepForward(!prevCurrentMessageList.contains(message));
		}
	}

	/**
	 * Retrieves data from the graph to be used in the simulation
	 */
	private State retrieveState()
	{
		State state = new State();
		totalMessageList = graph.getCompleteMessageList();
		ArrayList<Message> total = new ArrayList<>();
		total.addAll(totalMessageList);
		state.setTotalMessageList(total);
		
		currentMessageList = graph.getCurrentMessageList();
		ArrayList<Message> current = new ArrayList<>();
		current.addAll(currentMessageList);
		state.setCurrentMessageList(current);
		
		setPackets(graph.getTotalHops());
		
		setTotalMessages(totalMessageList.size());
		state.setTotalMessages(totalMessages);
		
		//Calculates average hops - metric 2
		String[] averageHopsList = calculateAverageHops();
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
	 * algorithm The algorithm the simulation would be set to
	 */
	public void setupGraph() 
	{
		switch(algorithm)
		{
			case FLOODING:	graph = new FloodingAlgorithm(simulationNodes, frequency);
			 				currentMessageList = graph.messageQueue;
			 				break;
				
			case SHORTESTPATH:	graph = new ShortestPathAlgorithm(simulationNodes, frequency);
			 					currentMessageList = graph.messageQueue;
			 					break;
				
			case CUSTOM:	graph = new CustomAlgorithm(simulationNodes, frequency);
			 				currentMessageList = graph.messageQueue;
			 				break;
				
			default: graph = new RandomAlgorithm(simulationNodes, frequency);
					 currentMessageList = graph.messageQueue;
					 break;
		}			
	}
	
	/**
	 * @return the algorithm chosen for simulation
	 */
	public ALGORITHM getAlgorithm()
	{
		return algorithm;
	}

	/**
	 * Initializes the default network within the simulation
	 */
	public void initializeDefaultNetwork() {
		resetSimulation();
		frequency = 5;
		algorithm = ALGORITHM.RANDOM;
		createNodes(Constants.DEFAULT_NODES_SET);
		addNeighbors(Constants.DEFAULT_EDGES_SET);
	}
	
	/**
	 * Initializes a new network within the simulation
	 */
	public void initializeNewNetwork(){
		resetSimulation();
		simulationNodes.clear();
	}
	
	/**
	 * Checks if site is fully initialized
	 * @return True if fully initialized
	 */
	public boolean checkFullInitialization(){
		
		if(frequency != 0 && algorithm != null){
			if(graph == null){
				setupGraph();
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns array of nodes
	 * @return Array of Nodes
	 */
	public ArrayList<String> getStringArrayNodes() {
		ArrayList<String> nodes = new ArrayList<>();
		for(Node node: simulationNodes){
			nodes.add(node.toString());
		}
		return nodes;
	}

	/**
	 * Checks if simulation is still running
	 * @return True if simulation is still running
	 */
	public boolean isRunning() {
		if(currentMessageList.size() != 0){
			return true;
		}else{
			graph = null;
			return false;
		}
	}

	/**
	 * Sets the simulation algorithm
	 * @param algorithm Algorithm used to set simulation algorithm
	 */
	public void setAlgorithm(ALGORITHM algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Checks if network exists within simulation
	 * @return True if network exists
	 */
	public boolean networkExists() {
		if(simulationNodes.size()>=2 
				&& simulationNodes.get(0).getNeighborhoodsize()>0){
			return true;
		}
		return false;
	}

	/**
	 * Resets the simulation
	 */
	public void resetSimulation() {
		averageHops = new ArrayList<>();
		packets = 0;
		totalMessages = 0;
		totalMessageList.clear();
		currentMessageList.clear();
		graph = null;
		stateStack.clear();
		useAlgorithm = true;
	}
	
	/**
	 * Generates String[] that shows the source and destination, number of messages 
	 * with same source/destination and the average
	 * @return The string array of average hops of messages with same source and destination
	 */
	public String[] calculateAverageHops(){

		//Create a copy of the totalMessageList to be used
		ArrayList<Message> totalMsgLst = totalMessageList;
		//List that stores all like pairs of messages
		ArrayList<String> totalMsgLstPairs = new ArrayList<>();
		
		for(int i = 0; i < totalMsgLst.size(); i++){
			//Temporary list that stores like pairs
			ArrayList<Message> sameMsgList= new ArrayList<>();
			sameMsgList.add(totalMsgLst.get(i));
			for(int j = i+1; j<totalMsgLst.size(); j++){
				if(totalMsgLst.get(i).hasSameSourceAndDestination(totalMsgLst.get(j))){
					sameMsgList.add(totalMsgLst.get(j));
					//Removes the message if matched to avoid duplication
					totalMsgLst.remove(j);
					
				}
			}
			totalMsgLstPairs.add(generateLikeMessageString(sameMsgList));
		}
		
		//Generate String Array from List
		String[] averageHopsDescriptiveList = new String[totalMsgLstPairs.size()];
		for(int i = 0; i<totalMsgLstPairs.size(); i++){
			averageHopsDescriptiveList[i] = totalMsgLstPairs.get(i);
		}
		
		return averageHopsDescriptiveList;
}

	/**
	 * Generates string that shows the source-> destination, number of messages 
	 * with same source/destination and the average
	 * @param sameMsgList List of messages with same source and destination
	 * @return The string analytics of like messages
	 */
	private String generateLikeMessageString(ArrayList<Message> sameMsgList){
		StringBuilder sb = new StringBuilder();
		//First message has the same source and destination as rest
		String source = sameMsgList.get(0).getSourceID();
		String destination = sameMsgList.get(0).getDestinationID();
		String count = ""+ sameMsgList.size();
		String average = generateSameMessageAverages(sameMsgList);
		
		//delimiter used: |
		sb.append(source);
		sb.append("|");
		sb.append(destination);
		sb.append("|");
		sb.append(count);
		sb.append("|");
		sb.append(average);
		
		return sb.toString();
		
	}
	
	/**
	 * Returns the average hops of like source and destination messages
	 * @param sameMsgList List of messages with same source and destination
	 * @return The average hops of like source and destination messages
	 */
	private String generateSameMessageAverages(ArrayList<Message> sameMsgList){
		
		if (sameMsgList.size()==1){
			return "" + sameMsgList.get(0).getHopCount();
		}
		
		//Add all hops to be averaged to a array
		ArrayList<Integer> hopsToBeAveraged = new ArrayList<>();
		for(int i = 0; i<sameMsgList.size(); i++){
			hopsToBeAveraged.add(sameMsgList.get(i).getHopCount());
		}
		
		return calculateAverage(hopsToBeAveraged);
		
	}
	
	/**
	 * Returns the average hops of messages with same source and destination
	 * @param hopsToBeAveraged List of hops to be averaged
	 * @return Average of a given source and destination
	 */
	private String calculateAverage(ArrayList<Integer> hopsToBeAveraged) {
		Integer average = 0;
		Integer size = hopsToBeAveraged.size();
		if(!hopsToBeAveraged.isEmpty()) {
			for (Integer hop : hopsToBeAveraged) {
				average += hop;
			}
			//Return double value - more accurate
			return "" + average.doubleValue() / size;
		}
		return average.toString();
	}

	
	/**
	 * @param nodeOneID node ID of the first node
	 * @param nodeTwoID node ID of the second node
	 * @return true if both nodes contain each other as a neighbor
	 */
	public boolean containsEdge(String nodeOneID, String nodeTwoID) {
		Node node1 = getNodeGivenID(nodeOneID);
		Node node2 = getNodeGivenID(nodeTwoID);
		
		if(node1.containsNeighbor(node2) && node2.containsNeighbor(node1)){
			return true;
		}
		return false;
	}

	/**
	 * Export the state object which includes nodes, frequency and algorithm into a XML file
	 * @param file name of the file to export to
	 * @throws JAXBException thrown when file export failed
	 */
	public void exportXML(File file) throws JAXBException {
		
		SaveState ss = new SaveState();
		//TODO why after resetting does it initialize to random and 5?
		ss.setFrequency(frequency);
		ss.setAlgorithm(algorithm.getALGString());
		
		//Add the nodes with locations
		ss.setSimulationNodes(simulationNodes);
		
		//Creates and saves XML
		XMLDocument.writeSaveState(ss, file);
	}


	/**
	 * @param file to import from
	 * @return The state of the network being imported
	 * @throws JAXBException thrown when file export failed
	 */
	public SaveState importXML(File file) throws JAXBException{
		
		//Read the XML file
		SaveState ss = XMLDocument.readSaveState(file);
		
		//New List of simulation nodes -Model
		simulationNodes = ss.getSimulationNodes();
		
		//Add neighbors
		for(Node n: simulationNodes){
			if (n!=null){
				String[] neighborIDs = n.getHoodIDs().split("[\\|\\s]+");
				//Add all neighbors
				for(String neighborID :neighborIDs){
					//Set simulation nodes with the neighbors
					createLink(n.getId(), neighborID);
				}
			}
		}
		
		//Graphical Nodes and Edges
		GraphicPanel gp = new GraphicPanel();
		
		//Add graphical nodes along with their neighbors
		//Grab list of node IDs
		ArrayList<String> nodeIDs = new ArrayList<>();
		for(Node n: ss.getSimulationNodes()){
			nodeIDs.add(n.getId());
		}
		//Create graphical Nodes
		for(String nodeID: nodeIDs){
			gp.NewNodeAction(nodeID);
		}
		//Create graphical edges
		for(Node n: ss.getSimulationNodes()){	
			String[] neighborIDs = n.getHoodIDs().split("[\\|\\s]+");
			//Add all neighbors
			for(String neighborID :neighborIDs){
				gp.ConnectAction(n.getId(), neighborID);
			}
		}
		
		//Set frequency and algorithm
		frequency = ss.getFrequency();
		algorithm = ALGORITHM.getEnum(ss.getAlgorithm());
		
		//Set graphical nodes and edges
		ss.setGraphicNodes(gp.getGraphicNodes());
		ss.setGraphicEdges(gp.getGraphicEdges());
		
		return ss;
	}
}
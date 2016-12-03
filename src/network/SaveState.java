package network;

public class SaveState {

	private State state;
	private Object graphicNodes;
	private Object graphicEdges;
	private int packets;
	private int frequency;
	private ALGORITHM algorithm;
	private Graph graph;
	
	/**
	 * @return the list of graphic nodes
	 */
	public Object getGraphicNodes() {
		return graphicNodes;
	}

	/**
	 * @param graphicNodes list of graphic nodes
	 */
	public void setGraphicNodes(Object graphicNodes) {
		this.graphicNodes = graphicNodes;
	}

	/**
	 * @return the list of graphic edges
	 */
	public Object getGraphicEdges() {
		return graphicEdges;
	}

	/**
	 * @param graphicEdges list of graphic edges
	 */
	public void setGraphicEdges(Object graphicEdges) {
		this.graphicEdges = graphicEdges;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the packets
	 */
	public int getPackets() {
		return packets;
	}

	/**
	 * @param packets the packets to set
	 */
	public void setPackets(int packets) {
		this.packets = packets;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the algorithm
	 */
	public ALGORITHM getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(ALGORITHM algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
}

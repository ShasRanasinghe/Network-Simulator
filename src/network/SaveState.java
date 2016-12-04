package network;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Simulation")
@XmlAccessorType (XmlAccessType.FIELD)
public class SaveState{

	@XmlElement(name = "node")
	private ArrayList<Node> simulationNodes;
	
	@XmlTransient
	private List<GraphicNode> graphicNodes;
	
	@XmlTransient
	private List<GraphicEdge> graphicEdges;
	
	@XmlElement(name = "frequency")
	private int frequency;
	
	@XmlElement(name = "algorithm")
	private String algorithm;
	
	/**
	 * @return simulation nodes
	 */
	public ArrayList<Node> getSimulationNodes() {
		return simulationNodes;
	}

	/**
	 * Set simulation nodes
	 * @param simulationNodes Simulation nodes to be set
	 */
	public void setSimulationNodes(ArrayList<Node> simulationNodes) {
		this.simulationNodes = simulationNodes;
	}

	/**
	 * @return The graphic nodes
	 */
	public List<GraphicNode> getGraphicNodes() {
		return graphicNodes;
	}

	/**
	 * @param graphicNodes Graphic Nodes to be set
	 */
	public void setGraphicNodes(List<GraphicNode> graphicNodes) {
		this.graphicNodes = graphicNodes;
	}

	/**
	 * @return The graphic edges
	 */
	public List<GraphicEdge> getGraphicEdges() {
		return graphicEdges;
	}

	/**
	 * @param graphicEdges Graphic edges to be set
	 */
	public void setGraphicEdges(List<GraphicEdge> graphicEdges) {
		this.graphicEdges = graphicEdges;
	}

	/**
	 * @return Frequency int
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency Set the int for frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return The algorithm string
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm Algorithm string
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	
}

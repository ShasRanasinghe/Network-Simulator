package network;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Simulation")
@XmlAccessorType (XmlAccessType.FIELD)
public class SaveState{

	@XmlElement(name = "node")
	private List<Node> simulationNodes;
	
	/**
	 * @return simulation nodes
	 */
	public List<Node> getSimulationNodes() {
		return simulationNodes;
	}

	/**
	 * Set simulation nodes
	 * @param simulationNodes Simulation nodes to be set
	 */
	public void setSimulationNodes(List<Node> simulationNodes) {
		this.simulationNodes = simulationNodes;
	}
	
}

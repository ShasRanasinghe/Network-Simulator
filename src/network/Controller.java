package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComponent;

public class Controller implements ActionListener {

	private Simulation simulation;
	private View view;
	
	public Controller(Simulation simulation, View view){
		this.simulation = simulation;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent component = (JComponent)e.getSource();
		invokeMethod(component);
	}

	/**
	 * @param component
	 */
	private void invokeMethod(JComponent component) {
		METHODS method = (METHODS)component.getClientProperty(Constants.METHOD_SEARCH_STRING);
		
		switch(method){
		case DEFAULT_NETWORK:
			defaultNetwork();
			break;
		case DELETE_EDGE:
			deleteEdge();
			break;
		case DELETE_NODE:
			deleteNode();
			break;
		case EDIT_EDGE:
			editEdge();
			break;
		case EDIT_NODE:
			editNode();
			break;
		case NEW_EDGE:
			newEdge();
			break;
		case NEW_NETWORK:
			newNetwork();
			break;
		case NEW_NODE:
			newNode();
			break;
		case RUN:
			runSimulation();
			break;
		case SET_ALGORITHM:
			setAlgorithm();
			break;
		case SET_FREQUENCY:
			setFrequeny();
			break;
		case STEP_BACK:
			stepBack();
			break;
		case STEP_FORWARD:
			stepForward();
			break;
		}
	}

	private void stepForward() {
		view.stepForward();
	}

	private void stepBack() {
		view.stepBack();
	}

	private void setFrequeny() {
		view.setFrequency();
	}

	private void setAlgorithm() {
		view.setAlgorithm();
	}

	private void runSimulation() {
		view.run();
	}

	private void newNode() {
		for(;;){
			String nodeID = view.openSingleInputQuestionDialog("Create New Node","Enter NodeID:");
			if(nodeID == null){
				view.setStatus("No Nodes Created");
				break;
			}else{
				if(simulation.addNewNode(nodeID)){
					view.addNewNode(nodeID);
					break;
				}else{
					view.errorMessageDialog(Constants.NODE_ALREADY_EXISTS);
				}
			}
			
		}
	}

	private void newNetwork() {
		view.newNetwork();
	}

	private void newEdge() {
		view.newEdge();
	}

	private void editNode() {
		List<String> nodes = view.getSelectedNodes();
		if(nodes.size()>0){
			if(nodes.size() == 1){
				String prevNodeID = nodes.get(0);
				for(;;){
					String newNodeID = view.openSingleInputQuestionDialog("Edit Node","Enter New NodeID:");
					if(newNodeID == null){
						view.setStatus("No Nodes Edited");
						break;
					}else{
						if(!simulation.isNode(newNodeID)){
							view.editNodeID(prevNodeID, newNodeID);
							simulation.editNodeID(prevNodeID, newNodeID);
							break;
						}else{
							view.errorMessageDialog(Constants.NODE_ALREADY_EXISTS);
						}
					}
				}
			}else{
				view.errorMessageDialog("Too Many Nodes Selected, Select One Please");
			}
		}else{
			view.errorMessageDialog("You Have Not Selected Anything\nPlease Select a Single Node And Try Again.");
		}
	}

	private void editEdge() {
		view.editEdge();
	}

	private void deleteNode() {
		List<String> nodes = view.getSelectedNodes();
		if(!nodes.isEmpty()){
			for(String nodeID: nodes){
				simulation.removeNode(nodeID);
			}
		}else{
			view.errorMessageDialog("You Have Not Selected Anything\nPlease Select Node(s) And Try Again.");
		}
	}

	private void deleteEdge() {
		view.deleteEdge();
	}

	private void defaultNetwork() {
		simulation.initializeDefaultNetwork();
		view.initializeDefaultNetwork(simulation.getAlgorithm(),simulation.getFrequency());
	}

}

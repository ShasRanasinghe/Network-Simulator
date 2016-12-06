package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.xml.bind.JAXBException;

/**
 * Controller class that contains a model and view and handles actions
 * model = Simulation
 * view = View
 *
 */
public class Controller implements ActionListener {

	private Simulation simulation;
	private View view;
	
	/**
	 * Constructor that initializes model and view
	 * @param simulation The model - back end
	 * @param view The view - front end
	 */
	public Controller(Simulation simulation, View view){
		this.simulation = simulation;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent component = (JComponent)e.getSource();
		try {
			invokeMethod(component);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Handles all actions performed
	 * @param component JComponent
	 * @throws Exception 
	 */
	private void invokeMethod(JComponent component) throws Exception {
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
		case AVERAGE_HOPS_METRIC:
			view.averageHopsMetric();
			break;
		case RESET_SIMULATION:
			resetSimulation();
			break;
		case IMPORT_XML:
			importXML();
			break;
		case EXPORT_XML:
			exportXML();
			break;
		default:
			break;
		}
	}

	/**
	 * Saves the graphic nodes and edges in a state object and exports to an XML file
	 * @throws Exception 
	 */
	private void exportXML() throws Exception {
		File file = view.saveFile();
		if(file == null){
			view.setStatus("Export Incomplete");
		}else{
			simulation.exportXML(file);
			view.setStatus("Export Complete");
		}
	}

	/**
	 * Imports an XML file and sets the view and model with the information
	 * @throws JAXBException 
	 */
	private void importXML() throws JAXBException {
		File file = view.openFile();
		if(file == null){
			view.setStatus("Import Incomplete");
		}else{
			resetSimulation();
			newNetwork();
			SaveState ss = simulation.importXML(file);
			view.importXML(ss);
			//Update the view
			view.updateFrequencyMetric(simulation.getFrequency());
			view.updateAlgorithmMetric(simulation.getAlgorithm().getALGString());
			view.setStatus("Import Complete");
		}
	}

	/**
	 * Delegates the creation of a default network
	 */
	private void defaultNetwork() {
		simulation.initializeDefaultNetwork();
		view.initializeDefaultNetwork(simulation.getAlgorithm().getALGString(),simulation.getFrequency());
	}
	
	/**
	 * Delegates creating a new network
	 */
	private void newNetwork() {
		view.initializeNewNetwork();
		simulation.initializeNewNetwork();
	}
	
	/**
	 * Delegates setting the frequency 
	 */
	private void setFrequeny() {
		String frequency = view.setFrequency();
		if(frequency == null){
			view.setStatus("Frequency Not Set");
		}
		else{
			if(Integer.parseInt(frequency) == 1)
			{
				view.setRunButton(false);
				view.errorMessageDialog("When frequency is set to 1, infinite message creation is possible \nTherefore user can only step");
			}
			simulation.setFrequency(Integer.parseInt(frequency));
			view.updateFrequencyMetric(Integer.parseInt(frequency));
		}
	}

	/**
	 * Delegates setting the algorithm
	 */
	private void setAlgorithm() {
		String algorithm = view.setAlgorithm();
		if(algorithm == null){
			view.setStatus("Algortihm Not Set");
		}else{
			simulation.setAlgorithm(ALGORITHM.getEnum(algorithm));
			view.updateAlgorithmMetric(algorithm);
		}
	}

	/**
	 * Not yet implemented 
	 */
	private void stepBack() {
		view.stepBack();
		simulation.stepBack();
	}

	/**
	 * Delegates running the simulation
	 */
	private void runSimulation() {
		if (simulation.networkExists()) {
			if (simulation.checkFullInitialization()) {
				view.prepairForSimulation(simulation.getStringArrayNodes());
				while (simulation.isRunning()) {
					simulation.runAlgorithm(1);
				}
				view.simulationComplete();
			} else {
				view.errorMessageDialog("Frequency Or Algorithm Is Missing");
			} 
		}else{
			view.errorMessageDialog("Please create a network and try again");
		}
	}
	
	/**
	 * Handles stepping forward
	 */
	private void stepForward() {
		if (simulation.networkExists()) {
			if (simulation.checkFullInitialization()) {
				view.prepairForSimulation(simulation.getStringArrayNodes());
				simulation.stepForward(1);
				if (!simulation.isRunning()) {
					view.simulationComplete();
				}
			} else {
				view.errorMessageDialog("Frequency Or Algorithm Is Missing");
			} 
		}else{
			view.errorMessageDialog("Please create a network and try again");
		}
	}

	/**
	 * Handles the creation of a new edge
	 */
	private void newEdge() {
		List<String> nodes = view.getSelectedNodes();
		if(nodes.size()>0){
			if(nodes.size() == 2){
				String nodeOneID = nodes.get(0);
				String nodeTwoID = nodes.get(1);
				if(!simulation.containsEdge(nodeOneID,nodeTwoID)){
					view.addNewEdge(nodeOneID,nodeTwoID);
					simulation.createLink(nodeOneID, nodeTwoID);
				}else{
					view.errorMessageDialog("Edge Already Exists");
				}
			}else{
				view.errorMessageDialog("Please Select Two Nodes And Try Again");
			}
		}else{
			view.errorMessageDialog("Please Select Two Node And Try Again.");
		}
	}
	
	/**
	 * Handles the deletion of an edge
	 */
	private void deleteEdge() {
		List<String> nodes = view.getSelectedNodes();
		if(nodes.size()>0){
			if(nodes.size() == 2){
				String nodeOneID = nodes.get(0);
				String nodeTwoID = nodes.get(1);
				if(simulation.containsEdge(nodeOneID, nodeTwoID)){
					view.removeEdge(nodeOneID,nodeTwoID);
					simulation.removeLink(nodeOneID, nodeTwoID);
				}else{
					view.errorMessageDialog("No Edges Exist");
				}
			}else{
				view.errorMessageDialog("Please Select Two Nodes And Try Again");
			}
		}else{
			view.errorMessageDialog("Please Select Two Node And Try Again.");
		}
	}
	
	/**
	 * Handles the creation of a new node
	 */
	private void newNode() {
		for(;;){
			String nodeID = view.openSingleInputQuestionDialog("Create New Node","Enter NodeID:");
			if(nodeID == null || nodeID.equals("")){
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

	/**
	 * Handles the editing of a node 
	 */
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
				view.errorMessageDialog("Please Select a Single Node And Try Again");
			}
		}else{
			view.errorMessageDialog("Please Select a Single Node And Try Again");
		}
	}

	/**
	 * Handles the deletion of a node
	 */
	private void deleteNode() {
		List<String> nodes = view.getSelectedNodes();
		if(!nodes.isEmpty()){
			for(String nodeID: nodes){
				simulation.removeNode(nodeID);
				view.removeNode(nodeID);
			}
		}else{
			view.errorMessageDialog("You Have Not Selected Anything\nPlease Select Node(s) And Try Again.");
		}
	}
	
	/**
	 * Handles resetting simulation
	 */
	private void resetSimulation() {
		view.resetSimulation();
		simulation.resetSimulation();
	}
	
}

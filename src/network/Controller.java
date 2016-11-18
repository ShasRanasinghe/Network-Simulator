package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		view.newNode();
	}

	private void newNetwork() {
		view.newNetwork();
	}

	private void newEdge() {
		view.newEdge();
	}

	private void editNode() {
		view.editNode();
	}

	private void editEdge() {
		view.editEdge();
	}

	private void deleteNode() {
		view.deleteNode();
	}

	private void deleteEdge() {
		view.deleteEdge();
	}

	private void defaultNetwork() {
		view.defaultNetwork();
	}

}

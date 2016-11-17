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
			view.errorMessageDialog("default network");
			break;
		case DELETE_EDGE:
			view.errorMessageDialog("delete edge");
			break;
		case DELETE_NODE:
			view.errorMessageDialog("delete node");
			break;
		case EDIT_EDGE:
			view.errorMessageDialog("edit edge");
			break;
		case EDIT_NODE:
			view.errorMessageDialog("edit node");
			break;
		case NEW_EDGE:
			view.errorMessageDialog("new edge");
			break;
		case NEW_NETWORK:
			view.errorMessageDialog("new network");
			break;
		case NEW_NODE:
			view.errorMessageDialog("new node");
			break;
		case RUN:
			view.errorMessageDialog("run");
			break;
		case SET_ALGORITHM:
			view.errorMessageDialog("set algorithm");
			break;
		case SET_FREQUENCY:
			view.errorMessageDialog("set frequency");
			break;
		case STEP_BACK:
			view.errorMessageDialog("step back");
			break;
		case STEP_FORWARD:
			view.errorMessageDialog("step forward");
			break;
		}
	}

}

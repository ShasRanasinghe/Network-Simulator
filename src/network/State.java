package network;

import java.util.ArrayList;

/**
 * Class that stores the state of the simulation
 * Important for Milestone 4 and used as intermediate between models and view
 *
 */
public class State {
	
	private String[] averageHopsList;
	private int totalMessages;
	private ArrayList<Message> totalMessageList;
	private ArrayList<Message> currentMessageList;
	private boolean undo = false;
	
	/**
	 * @param totalMessageList Total messages currently in network
	 */
	public void setTotalMessageList(ArrayList<Message> totalMessageList) {
		this.totalMessageList = totalMessageList;
	}

	/**
	 * @param currentMessageList Array of current messages in network
	 */
	public void setCurrentMessageList(ArrayList<Message> currentMessageList) {
		this.currentMessageList = currentMessageList;
	}

	/**
	 * @param totalMessages Number of total messages in simulation
	 */
	public void setTotalMessages(int totalMessages) {
		this.totalMessages = totalMessages;
	}

	/**
	 * @param averageHopsList Average hops array 
	 */
	public void setAverageHopsList(String[] averageHopsList) {
		this.averageHopsList = averageHopsList;
	}

	/**
	 * @return The average hops of like messages (same source and destination)
	 */
	public String[] getAverageHopsList() {
		return averageHopsList;
	}

	/**
	 * @return Total messages in network
	 */
	public int getTotalMessages() {
		return totalMessages;
	}

	/**
	 * @return The total message list in network
	 */
	public ArrayList<Message> getTotalMessageList() {
		return totalMessageList;
	}

	/**
	 * @return The current messages in network
	 */
	public ArrayList<Message> getCurrentMessageList() {
		return currentMessageList;
	}
	
	/**
	 * Sets the Undo status of this state
	 * @param undo is this state is called from a step back or not
	 */
	public void setUndo(boolean undo){
		this.undo = undo;
	}

	/**
	 * @return true is this state is sent cause of a step back
	 */
	public boolean isUndo() {
		return undo;
	}
}

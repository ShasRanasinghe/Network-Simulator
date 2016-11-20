package network;

import java.util.ArrayList;

public class State {
	
	private String[] averageHopsList;
	private int totalMessages;
	private ArrayList<Message> totalMessageList;
	private ArrayList<Message> currentMessageList;

	public void setTotalMessageList(ArrayList<Message> totalMessageList) {
		this.totalMessageList = totalMessageList;
	}

	public void setCurrentMessageList(ArrayList<Message> currentMessageList) {
		this.currentMessageList = currentMessageList;
	}

	public void setTotalMessages(int totalMessages) {
		this.totalMessages = totalMessages;
	}

	public void setAverageHopsList(String[] averageHopsList) {
		this.averageHopsList = averageHopsList;
	}

	public String[] getAverageHopsList() {
		return averageHopsList;
	}

	public int getTotalMessages() {
		return totalMessages;
	}

	public ArrayList<Message> getTotalMessageList() {
		return totalMessageList;
	}

	public ArrayList<Message> getCurrentMessageList() {
		return currentMessageList;
	}

}

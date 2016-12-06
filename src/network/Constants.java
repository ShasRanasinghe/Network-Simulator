package network;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Constants class mainly used in View
 *
 */
final class Constants {

	/**
	 * Private constructor that doesn't do anything as this class is for constants only
	 */
	private Constants(){}
	
	
	protected static final String ABOUT = "SYSC 3110 Group Project: Network Routing Simulator\n"
			+ "Group Members: Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe\n\n"
			+ "Summary:\n"
			+ "----------------------------------------------\n"
			+ "The goal of this team project is to build a simulator to evaluate the performance of various routing\n"
			+ "algorithms. This project will implement the following routing algorithms:\n"
			+ "* Random\n"
			+ "* Flooding\n"
			+ "* Shortest path\n"
			+ "* Custom method\n\n"
			+ "Version: Milestone 4";
	
	protected static final String README = "UserManual.txt";
	
	protected static final String UML = "Milestone4_UML.pdf";
	
	protected static final String JAVADOC1 = "doc\\index.html";
	
	protected static final String JAVADOC2 = "doc\\network\\Simulation.html";
	
	protected static final String[] DEFAULT_NODES_SET = {"A","B","C","D","E"};
	
	protected static final String[] DEFAULT_EDGES_SET = {"A->B","A->C","A->E","B->D","B->E","C->D"};
	
	protected static final String COULD_NOT_OPEN_FILE = "Could not open file properly";
	
	protected static final String FILE_DOES_NOT_EXIST = "Could Not Find Files Required";
	
	protected static final String WARNING = "WARNING";

	protected static final String NODE_ALREADY_EXISTS = "Node Already Exists";

	protected static final int METRIC_FIELD_SIZE = 10;
	
	protected static final int ALGORITHM_METRIC_FIEL_SIZE = 15;
	
	protected static final int FREQUENCY_OPTIONS_MAX = 30;
	
	protected static final int FREQUENCY_OPTIONS_MIN = 1;
	
	protected static final int FRAME_HEIGHT = 600;
	
	protected static final int FRAME_WIDTH = 1000;
	
	protected static final int INITIAL_ROW_COUNT = 0;
	
	protected static final Dimension SCREEN_DIMENTIONS  = Toolkit.getDefaultToolkit().getScreenSize();
	
	protected static final String METHOD_SEARCH_STRING = "METHOD";
	
	protected static final String DISABLED_WHEN_STEPPING = "Disabled When Stepping";
	
	protected static final String EASTER_EGG_MESSAGE = "YOU'VE BEEN HIT BY!...\nYOU'VE BEEN STRUCK BY...\nA SMOOTH CRIMINAL!!!!\n\nIs This What You Wanted? :D";
	
    //Easter Egg
	protected static final String CHARLESUPPER = "Charles";
	protected static final String CHARLESLOWER = "charles";
	protected static final String CHARLESALLUPPER = "CHARLES";
	protected static final String CHARLESFACEPATH = "charles.jpg";
}

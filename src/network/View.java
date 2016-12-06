package network;

import static network.Constants.*;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author Shasthra Ranasinghe, Alex Hoecht, Andrew Ward, Mohamed Dahrouj
 * 
 * This class creates the GUI for the simulation
 * It also contains all the methods used in the GUI 
 *
 */
public class View implements Observer{
	//Variables used to create the GUI
	private String[] algorithmChoices;
	private String frequencyList[];
	private MessageListModel<Message> messageList;
	private JList<Message> list;
	private DefaultTableModel tableModel;
	private String[] averageHopsList;
	private int rowCount;
	
	//Metrics
	private JTextField totalMessagesMetric;
	private JButton averageHopsMetricButton;
	private JTextField frequencyMetric;
	private JTextField algorithmMetric;
	
	
	//GUI
	private JFrame frame;
	private GraphicPanel gp;
	private JPanel outputsPanel;
	private JPanel playPanel;
	private JPanel toolBar;
	private JPanel listBar;
	private JLabel statusLabel;
	private JScrollPane scrollPaneList;
	private JScrollPane scrollPaneTable;
	private JButton newNodeButton;
	private JButton newEdgeButton;
	private JButton setFreqButton;
	private JButton setAlgorithmButton;
	private JButton deleteNodeButton;
	private JButton deleteEdgeButton;
	private JButton runButton;
	private JButton stepForwardButton;
	private JButton stepBackButton;
	private JButton resetButton;
	private JTable table;
	private JPanel tableBar;
	private DefaultOptionDialog dialog;
	
	//Edit Menu Items
	private JMenuItem setAlgorithmMenu;
	private JMenuItem setFrequencyMenu;
	private JMenuItem newNodeMenu;
	private JMenuItem editNodeMenu;
	private JMenuItem deleteNodeMenu;
	private JMenuItem newEdgeMenu;
	private JMenuItem deleteEdgeMenu;
	private JMenuItem newNetworkMenu;
	private JMenuItem defaultNetworkMenu;
	private JMenuItem quitMenu;
	private JMenuItem runMenuItem;
	private JMenuItem stepForwardMenuItem;
	private JMenuItem stepBackMenuItem;
	private JMenuItem exportXML;
	private JMenuItem importXML;
	private JSplitPane splitPane;
	
	/**
	 * MAIN CONSTRUCTOR OF GUI
	 */
	public View()
	{
		initialize();
		makeFrame();
	}
	
	/**
	 * Initializes all the required variables to create the frame
	 */
	private void initialize(){
		
		//Initialize frequency list
		frequencyList = new String[FREQUENCY_OPTIONS_MAX];
		for (int i = FREQUENCY_OPTIONS_MIN; i < frequencyList.length; i++) {
			frequencyList[i-FREQUENCY_OPTIONS_MIN] = Integer.toString(i);
		}
		
		//Initialize array of Algorithms available
		algorithmChoices = new String[ALGORITHM.values().length];
		for(int i = 0; i<ALGORITHM.values().length;i++){
			algorithmChoices[i] = ALGORITHM.values()[i].getALGString();
		}
		
		tableModel = new DefaultTableModel();
		rowCount = INITIAL_ROW_COUNT;
		
		messageList = new MessageListModel<>();
	}
	
	/**
	 * @param controller Controller to be used in conjunction
	 */
	public void setController(Controller controller) {
		stepBackButton.addActionListener(controller);
		stepBackButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.STEP_BACK);
		
		runButton.addActionListener(controller);
		runButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.RUN);
		
		stepForwardButton.addActionListener(controller);
		stepForwardButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.STEP_FORWARD);
		
		newNodeButton.addActionListener(controller);
		newNodeButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.NEW_NODE);
		
		newEdgeButton.addActionListener(controller);
		newEdgeButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.NEW_EDGE);
		
		deleteNodeButton.addActionListener(controller);
		deleteNodeButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.DELETE_NODE);
		
		deleteEdgeButton.addActionListener(controller);
		deleteEdgeButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.DELETE_EDGE);
		
		setFreqButton.addActionListener(controller);
		setFreqButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.SET_FREQUENCY);
		
		setAlgorithmButton.addActionListener(controller);
		setAlgorithmButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.SET_ALGORITHM);
		
		newNetworkMenu.addActionListener(controller);
		newNetworkMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.NEW_NETWORK);
		
		defaultNetworkMenu.addActionListener(controller);
		defaultNetworkMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.DEFAULT_NETWORK);
		
		newNodeMenu.addActionListener(controller);
		newNodeMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.NEW_NODE);
		
		newEdgeMenu.addActionListener(controller);
		newEdgeMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.NEW_EDGE);
		
		editNodeMenu.addActionListener(controller);
		editNodeMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.EDIT_NODE);;
		
		deleteNodeMenu.addActionListener(controller);
		deleteNodeMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.DELETE_NODE);
		
		deleteEdgeMenu.addActionListener(controller);
		deleteEdgeMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.DELETE_EDGE);
		
		setFrequencyMenu.addActionListener(controller);
		setFrequencyMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.SET_FREQUENCY);
		
		setAlgorithmMenu.addActionListener(controller);
		setAlgorithmMenu.putClientProperty(METHOD_SEARCH_STRING, METHODS.SET_ALGORITHM);
		
		runMenuItem.addActionListener(controller);
		runMenuItem.putClientProperty(METHOD_SEARCH_STRING, METHODS.RUN);
		
		stepForwardMenuItem.addActionListener(controller);
		stepForwardMenuItem.putClientProperty(METHOD_SEARCH_STRING, METHODS.STEP_FORWARD);
		
		stepBackMenuItem.addActionListener(controller);
		stepBackMenuItem.putClientProperty(METHOD_SEARCH_STRING, METHODS.STEP_BACK);
		
		averageHopsMetricButton.addActionListener(controller);
		averageHopsMetricButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.AVERAGE_HOPS_METRIC);
		
		resetButton.addActionListener(controller);
		resetButton.putClientProperty(METHOD_SEARCH_STRING, METHODS.RESET_SIMULATION);
		
		importXML.addActionListener(controller);
		importXML.putClientProperty(METHOD_SEARCH_STRING, METHODS.IMPORT_XML);
		
		exportXML.addActionListener(controller);
		exportXML.putClientProperty(METHOD_SEARCH_STRING, METHODS.EXPORT_XML);
		
		dialog = new DefaultOptionDialog(frame, "Default");
		dialog.addButtonActionListener(controller);
		dialog.putDefaultClientProperty(METHOD_SEARCH_STRING, METHODS.DEFAULT_NETWORK);
		dialog.putNewNetworkClientProperty(METHOD_SEARCH_STRING, METHODS.NEW_NETWORK);
		dialog.setVisible(true);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////GUI SETUP BEGINS AT THIS POINT./////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates the frame and adds layouts
	 */
	private void makeFrame(){
		frame = new JFrame("Network Simulator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel contentPane = (JPanel)frame.getContentPane();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			errorMessageDialog("Something Messed Up Yo");
			quit();
		} catch (InstantiationException e) {
			errorMessageDialog("Something Messed Up Yo");
			quit();
		} catch (IllegalAccessException e) {
			errorMessageDialog("Something Messed Up Yo");
			quit();
		} catch (UnsupportedLookAndFeelException e) {
			errorMessageDialog("Something Messed Up Yo");
			quit();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		makeMenu();
		
		contentPane.setLayout(new BorderLayout());
		
		makeToolBar(contentPane);
		
		JPanel center = makeGraphPanel(contentPane);
		
		makeOutputBar(contentPane);
		
		makeMessageListBar(center);
		
		makeTableBar(center);
		
		//Finish it off
		frame.pack();
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setLocation(SCREEN_DIMENTIONS.width/2 - frame.getWidth()/2,SCREEN_DIMENTIONS.height/2 - frame.getHeight()/2);
		
		frame.setVisible(true);
	}

	/**
	 * Creates the north panel with the table
	 * @param center
	 */
	private void makeTableBar(JPanel center) {
		//set table bar
		tableBar = new JPanel();
		tableBar.setLayout(new GridLayout(0,1));
		table = new JTable(tableModel);
		table.setEnabled(false);
		scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setPreferredSize(new Dimension(frame.getWidth(),100));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableBar.add(scrollPaneTable);
		center.add(tableBar,BorderLayout.NORTH);
		tableBar.setVisible(false);
	}

	/**
	 * Creates the panel on the right with the list of messages
	 * @param center
	 */
	private void makeMessageListBar(JPanel center) {
		//set list bar
		listBar = new JPanel();
		listBar.setLayout(new GridLayout(0,1));
		
		list =  new JList<>(messageList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(e -> createPopup());
		list.setBackground(center.getBackground());
		list.setCellRenderer(new MessageListCellRenderer());
		listBar.setPreferredSize(new Dimension(100,0));
		scrollPaneList = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listBar.add(scrollPaneList);
		
		//set list renderer
		list.setCellRenderer(new MessageListCellRenderer());
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(gp), listBar);
		listBar.setMinimumSize(new Dimension(100,500));
		splitPane.setOneTouchExpandable(true);
		center.add(splitPane, BorderLayout.CENTER);
		listBar.setVisible(false);
	}

	/**
	 * The south bar with the metrics and status bar
	 * @param contentPane
	 */
	private void makeOutputBar(JPanel contentPane) {
		//output bar
		JPanel south = new JPanel();
		//south
		south.setLayout(new BorderLayout());
		outputsPanel = new JPanel();
		outputsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		
		totalMessagesMetric = new JTextField(METRIC_FIELD_SIZE);
		totalMessagesMetric.setText("Not Set");
		
		frequencyMetric = new JTextField(METRIC_FIELD_SIZE);
		algorithmMetric = new JTextField(ALGORITHM_METRIC_FIEL_SIZE);
		
		//Metric block
		averageHopsMetricButton = new JButton("Average Hops");
		outputsPanel.add(averageHopsMetricButton);
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Total Messages:"));
		panel.add(totalMessagesMetric);
		outputsPanel.add(panel);
		
		panel = new JPanel();
		panel.add(new JLabel("Frequency"));
		panel.add(frequencyMetric);
		outputsPanel.add(panel);

		panel = new JPanel();
		panel.add(new JLabel("Algorithm"));
		panel.add(algorithmMetric);
		outputsPanel.add(panel);

		totalMessagesMetric.setEditable(false);
		averageHopsMetricButton.setEnabled(false);
		frequencyMetric.setEditable(false);
		algorithmMetric.setEditable(false);
		//Metric Block Ends
		
		//status label
		statusLabel = new JLabel();
		TitledBorder title;
		title = BorderFactory.createTitledBorder("STATUS");
		statusLabel.setBorder(title);
		south.add(outputsPanel,BorderLayout.CENTER);
		south.add(statusLabel,BorderLayout.SOUTH);
		statusLabel.setText(" ");
		//status label block ends
		
		contentPane.add(south,BorderLayout.SOUTH);
	}

	/**
	 * Created the Center where the Graph panel goes
	 * @param contentPane
	 * @return the graphpanel
	 */
	private JPanel makeGraphPanel(JPanel contentPane) {
		//graph panel
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		gp = new GraphicPanel();
		//commented out from previous implementation
		//center.add(new JScrollPane(gp), BorderLayout.CENTER);
		
		
		playPanel = new JPanel();
		center.add(playPanel,BorderLayout.SOUTH);
		
		playPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,0));
		stepBackButton = new JButton("Back");
		stepBackButton.setEnabled(false);
		runButton = new JButton("Run");
		stepForwardButton = new JButton("Next");
		
		playPanel.add(stepBackButton);
		playPanel.add(runButton);
		playPanel.add(stepForwardButton);	
		
		contentPane.add(center,BorderLayout.CENTER);
		return center;
	}

	/**
	 * Created the tool bar of button on the west of the GUI
	 * @param contentPane
	 */
	private void makeToolBar(JPanel contentPane) {
		//tool bar
		toolBar = new JPanel();
		toolBar.setLayout(new GridLayout(0,1,0,20));
		
		newNodeButton = new JButton("New Node");
		toolBar.add(newNodeButton);
		
		newEdgeButton = new JButton("New Edge");
		toolBar.add(newEdgeButton);
		
		setFreqButton = new JButton("Set Frequency");
		toolBar.add(setFreqButton);
		
		setAlgorithmButton = new JButton("Set Algorithm");
		toolBar.add(setAlgorithmButton);
		
		deleteNodeButton = new JButton("Delete Node");
		toolBar.add(deleteNodeButton);
		
		deleteEdgeButton = new JButton("Delete Edge");
		toolBar.add(deleteEdgeButton);
		
		resetButton = new JButton("Reset");
		toolBar.add(resetButton);
		
		JPanel west = new JPanel();
		west.setLayout(new FlowLayout(FlowLayout.CENTER));
		west.add(toolBar);
		contentPane.add(west, BorderLayout.WEST);
	}
	
	/**
	 * Creates the menu bar and all its items
	 */
	private void makeMenu(){
		final int SHORTCUT_MASK =
	            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu;
		JMenuItem item;
		
		//NETWORK menu
		menu = new JMenu("Network");
		menuBar.add(menu);
		
		newNetworkMenu = new JMenuItem("New Network");
		newNetworkMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,SHORTCUT_MASK));
		menu.add(newNetworkMenu);
		
		defaultNetworkMenu = new JMenuItem("Default Network");
		menu.add(defaultNetworkMenu);
		
		quitMenu = new JMenuItem("Quit");
		quitMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,SHORTCUT_MASK));
		quitMenu.addActionListener(e -> quit());
		menu.add(quitMenu);
		
		JMenu importFile = new JMenu("Import...");
		importXML = new JMenuItem("XML File");
		importFile.add(importXML);
		menu.add(importFile);
		
		JMenu exportFile = new JMenu("Export...");
		exportXML = new JMenuItem("XML File");
		exportXML.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,SHORTCUT_MASK));
		exportFile.add(exportXML);
		menu.add(exportFile);
		
		//EDIT menu
		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		setAlgorithmMenu = new JMenuItem("Set Algorithm");
		setAlgorithmMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,SHORTCUT_MASK));
		menu.add(setAlgorithmMenu);
		
		setFrequencyMenu = new JMenuItem("Set Frequency");
		setFrequencyMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,SHORTCUT_MASK));
		menu.add(setFrequencyMenu);
		
		newNodeMenu = new JMenuItem("New Node");
		newNodeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,SHORTCUT_MASK));
		menu.add(newNodeMenu);
		
		editNodeMenu = new JMenuItem("Edit Node");
		menu.add(editNodeMenu);
		
		deleteNodeMenu = new JMenuItem("Delete Node");
		deleteNodeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,SHORTCUT_MASK));
		menu.add(deleteNodeMenu);

		newEdgeMenu = new JMenuItem("New Edge");
		newEdgeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,SHORTCUT_MASK));
		menu.add(newEdgeMenu);
		
		deleteEdgeMenu = new JMenuItem("Delete Edge");
		menu.add(deleteEdgeMenu);
		
		//TOOLS menu
		menu = new JMenu("Tools");
		menuBar.add(menu);
		
		runMenuItem = new JMenuItem("Run");
		runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,SHORTCUT_MASK));
		menu.add(runMenuItem);
		
		stepForwardMenuItem = new JMenuItem("Step Forward");
		stepForwardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,SHORTCUT_MASK));
		menu.add(stepForwardMenuItem);
		
		stepBackMenuItem = new JMenuItem("Step Back");
		stepBackMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,SHORTCUT_MASK));
		menu.add(stepBackMenuItem);
		stepBackMenuItem.setEnabled(false);
		
		//HELP menu
		menu = new JMenu("Help");
		menuBar.add(menu);
		
		item = new JMenuItem("User Manual");
		item.addActionListener(e -> showREADME());
		menu.add(item);
		
		item = new JMenuItem("About");
		item.addActionListener(e -> showAbout());
		menu.add(item);
		
		item = new JMenuItem("Javadoc");
		item.addActionListener(e -> showJavadoc());
		menu.add(item);
		
		item = new JMenuItem("UML Diagram");
		item.addActionListener(e -> showUML());
		menu.add(item);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////VIEW MANIPULATION METHODS/////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Steps back to a precious state
	 */
	public void stepBack() 
	{
		if(tableModel.getRowCount()>1){
			
			tableModel.removeRow(table.getRowCount() - 1);
			tableModel.removeRow(table.getRowCount() - 1);
			table.changeSelection(table.getRowCount() - 1, 0, false, false);
			rowCount = rowCount - 2;
		}else{
			if(tableModel.getRowCount() == 1){tableModel.removeRow(0);}
			stepBackButton.setEnabled(false);
			stepBackMenuItem.setEnabled(false);
			messageList.clear();
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		State state = (State)arg1;
		updateFromState(state);
	}
	
	/**
	 * Ends the current simulation
	 */
	public void simulationComplete(){
		runButton.setEnabled(false);
		runMenuItem.setEnabled(false);
		stepForwardButton.setEnabled(false);
		stepForwardMenuItem.setEnabled(false);
		averageHopsMetricButton.setEnabled(true);
		stepBackButton.setEnabled(false);
		stepBackMenuItem.setEnabled(false);
		setStatus("Simulation Complete!");
	}

	/**
	 * Run the full simulation to completion
	 * @param nodes List of nodeID Strings
	 */
	public void prepairForSimulation(ArrayList<String> nodes) 
	{
		if(!tableBar.isVisible()){openTableAndList(nodes);}
		setEnabledOptionsWhenStepping(false);
		setStatus("Simulation Running...");
	}
	
	/**
	 * Remove graphical edge from View
	 * @param startNodeID Node 1
	 * @param endNodeID Node 2
	 */
	public void removeEdge(String startNodeID, String endNodeID){
		gp.removeGraphicEdge(startNodeID, endNodeID);
		setStatus("Edge " + startNodeID + "-" + endNodeID + " Removed");
	}
	
	/**
	 * Add graphical edge
	 * @param startNodeID Node 1
	 * @param endNodeID Node 2
	 */
	public void addNewEdge(String startNodeID, String endNodeID){
		gp.ConnectAction(startNodeID, endNodeID);
		setStatus("Edge " + startNodeID + "-" + endNodeID + " Created");
	}

	/**
	 * @param nodeID Graphical Node ID to be removed
	 */
	public void removeNode(String nodeID){
		gp.removeGraphicNode(nodeID);
		if(gp.numberOfSelectedNodes() > 1){
			setStatus("Multiple Nodes Removed");
		}else{
			setStatus("Node " + nodeID + " Removed");
		}
	}
	
	/**
	 * @return Selected nodes in graphic panel
	 */
	public List<String> getSelectedNodes(){
		gp.populateSelectedNodesList();
		List<String> nodes = new ArrayList<>();
		for(GraphicNode gn: gp.getSelectedNodesList()){
			nodes.add(gn.getNodeID());
		}
		return nodes;
	}
	
	/**
	 * Edit graphical node
	 * @param prevNodeID Previous Node ID
	 * @param newNodeID New Node ID
	 */
	public void editNodeID(String prevNodeID, String newNodeID){
		for(GraphicNode gn: gp.getSelectedNodesList()){
			if(gn.getNodeID().equals(prevNodeID)){gn.setNodeID(newNodeID);}
			gp.repaint();
			break;
		}
	}

	/**
	 * Generic JOption Pane requesting input
	 * @param title Title of JOptionPane
	 * @param inputfieldString Input string
	 * @return JOptionPane
	 */
	public String openSingleInputQuestionDialog(String title, String inputfieldString){
		return JOptionPane.showInputDialog(frame,inputfieldString,title,JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Add new graphical node
	 * @param nodeID Node ID to be added
	 */
	public void addNewNode(String nodeID){
		gp.NewNodeAction(nodeID);
		setStatus("Node " + nodeID + " Created");
	}

	/**
	 * Set the frequency
	 * @return String representation of the given frequency
	 */
	public String setFrequency() {
	    return (String) JOptionPane.showInputDialog(frame, "Pick a Frequency",
		        "Set Frequency", JOptionPane.QUESTION_MESSAGE, null, // Use
		        frequencyList, // Array of choices
		        frequencyList[0]); // Initial choice
	}
	
	/**
	 * Set the algorithm 
	 * @return String representation of the given algorithm
	 */
	public String setAlgorithm() {
	    return (String) JOptionPane.showInputDialog(null, "Choose Algorithm",
	        "Set Algorithm", JOptionPane.QUESTION_MESSAGE, null, // Use
	        algorithmChoices, // Array of choices
	        algorithmChoices[0]); // Initial choice
	}
	
	/**
	 * 
	 * @param b is the user allowed to use the RUN function
	 */
	public void setRunButton(boolean b)
	{
		runButton.setEnabled(b);
	}
	
	/**
	 * Displays the average hops (Metric 2) as a JTable in a JOptionPane
	 */
	public void averageHopsMetric() {
		
		DefaultTableModel averageTableModel = new DefaultTableModel(); 
		JTable averageTable = new JTable(averageTableModel);
		
		averageTableModel.addColumn("Source");
		averageTableModel.addColumn("Destination");
		averageTableModel.addColumn("Count");
		averageTableModel.addColumn("Average");
		
		for (String line: averageHopsList){
			String[] splited = line.split("[\\|\\s]+");
			averageTableModel.addRow(new Object[]{splited[0], splited[1], splited[2], splited[3]});
			
		}
		
		JOptionPane.showMessageDialog(frame, new JScrollPane(averageTable),"Average Hops",JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Initialize the Default Network
	 * @param algorithm Algorithm to be used
	 * @param frequency Frequency to be used
	 */
	public void initializeDefaultNetwork(String algorithm, int frequency) {
		clearInstance();
		setEnabledOptionsWhenStepping(true);
		stepForwardButton.setEnabled(true);
		stepForwardMenuItem.setEnabled(true);
		runButton.setEnabled(true);
		runMenuItem.setEnabled(true);
		dialog.setVisible(false);
		for(String nodeID : DEFAULT_NODES_SET)
		{
			gp.NewNodeAction(nodeID);
		}
		for(String edgeID : DEFAULT_EDGES_SET)
		{
			String[] splitEdge = edgeID.split("->");
			gp.ConnectAction(splitEdge[0],splitEdge[1]);
		}
		totalMessagesMetric.setText("Not Set");
		updateFrequencyMetric(frequency);
		updateAlgorithmMetric(algorithm);
		setStatus("Default Network");
	}

	/**
	 * Create a new network and reset all information
	 */
	public void initializeNewNetwork() 
	{
		clearInstance();
		frequencyMetric.setText("Not Set");
		algorithmMetric.setText("Not Set");
		totalMessagesMetric.setText("Not Set");

		setEnabledOptionsWhenStepping(true);
		stepForwardButton.setEnabled(true);
		stepForwardMenuItem.setEnabled(true);
		runButton.setEnabled(true);
		runMenuItem.setEnabled(true);
		setStatus("New Network");
		dialog.setVisible(false);
	}
	
	/**
	 * Measures taken to reset the current simulation-graphically
	 */
	public void resetSimulation() {
		messageList.clear();
		tableModel.setNumRows(0);
		tableModel.setColumnCount(0);
		table.revalidate();
		rowCount = 0;
		totalMessagesMetric.setText("Not Set");

		setEnabledOptionsWhenStepping(true);
		stepForwardButton.setEnabled(true);
		stepForwardMenuItem.setEnabled(true);
		runButton.setEnabled(true);
		runMenuItem.setEnabled(true);
		setStatus("Network Reset");
		dialog.setVisible(false);
	}
	
	/**
	 * Updates the Algorithm metric
	 * @param algorithm String representation of the algorithm
	 */
	public void updateAlgorithmMetric(String algorithm) {
		algorithmMetric.setText(algorithm);
		setStatus("Algortihm set to: " + algorithm);
	}

	/**
	 * Updates the Frequency Metric
	 * @param frequency frequency of the simulation
	 */
	public void updateFrequencyMetric(int frequency) {
		frequencyMetric.setText("" + frequency);
		setStatus("Frequency set to: " + frequency);
	}
	
	
	/**
	 * Generic error message dialog
	 * @param message Warning message
	 */
	public void errorMessageDialog(String message) {
		JOptionPane.showMessageDialog(frame,
				message,
			    WARNING,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * @param str Message to be written in the status bar
	 * Updates the status bar and repaints the GUI needed
	 */
	public void setStatus(String str) {
		statusLabel.setText(str);
		frame.repaint();
	}
	
	/**
	 * @return the list of graphic edges
	 */
	public List<GraphicEdge> getGraphicEdges() {
		return gp.getGraphicEdges();
	}

	/**
	 * @return list of graphic nodes
	 */
	public List<GraphicNode> getGraphicNodes() {
		return gp.getGraphicNodes();
	}
	

	/**
	 * @param saveState State to import
	 */
	public void importXML(SaveState saveState) {
		gp.setGraphicNodes((List<GraphicNode>)saveState.getGraphicNodes());
		gp.setGraphicEdges((List<GraphicEdge>)saveState.getGraphicEdges());
	}

	/**
	 * @return File to be opened
	 */
	public File openFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML File (*.xml)", "xml");
		fileChooser.setFileFilter(xmlFilter);
		fileChooser.setCurrentDirectory(new File("."));
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;
		}else{
			return null;
		}
	}

	/**
	 * @return File to be saved
	 */
	public File saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML File (*.xml)", "xml");
		fileChooser.setFileFilter(xmlFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File("."));
		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().getName() + ".xml");
			return file;
		}else{
			return null;
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////VIEW HELPER METHODS//////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Update the view using the information in the state Object
	 * @param state State class holding all the information needed to update the view
	 */
	private void updateFromState(State state){
		updateMessageList(state.getTotalMessageList(),state.isUndo());
		updateMessageTable(state.getCurrentMessageList());
		updateTotalMessagesMetrics(state.getTotalMessages());
		averageHopsList = state.getAverageHopsList();
	}
	
	/**
	 * Creates the table with the nodes list
	 */
	private void createTable(ArrayList<String> nodes){
		tableModel.addColumn("Step");
		for(String node: nodes){
			tableModel.addColumn(node);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableBar.setVisible(true);
		frame.repaint();
	}
	
	/**
	 * Opens message log table and the message list upon stepping through or running
	 * @param nodes List of nodes
	 */
	private void openTableAndList(ArrayList<String> nodes){
		createTable(nodes);
		listBar.setVisible(true);
		splitPane.setDividerLocation(splitPane.getWidth()-100);
	}
	
	/**
	 * @param e List Selection Event
	 * Creates a pop up when a message is selected in the chart
	 * Pop up shows details of the message
	 */
	private void createPopup() 
	{
		Message message = (Message)list.getSelectedValue();
		if(list.getSelectedIndex() != -1)
		{
			int response = JOptionPane.showConfirmDialog(null,message.getDetailedString(),message.toString(),
														JOptionPane.PLAIN_MESSAGE,
														JOptionPane.CLOSED_OPTION);
			if (response == JOptionPane.OK_OPTION || response == JOptionPane.CLOSED_OPTION) 
			{
				list.clearSelection();
			}
		}
	}
	
	/**
	 * Adds a message to the JList of messages to be displayed in the chart
	 */
	private void updateMessageList(ArrayList<Message> totalMessageList,boolean undo)
	{
		/*if(undo){*/messageList.clear();//}
		for(Message m : totalMessageList)
		{
			//if(undo){
				messageList.addElement(m);
			/*}else{
				if(!messageList.contains(m))
				{
					messageList.addElement(m);
				}
			}*/
			messageList.update(messageList.indexOf(m));
		}
	}
	
	/**
	 * Adds a row to the table showing the current location of all the messages in all the nodes
	 */
	private void updateMessageTable(ArrayList<Message> currentMessageList)
	{
		rowCount++;
		
		ArrayList<ArrayList<String>> allMessages = new ArrayList<>();
		for(int i = 0;i < tableModel.getColumnCount()-1;i++){
			allMessages.add(i, new ArrayList<String>());
		}
		
		ArrayList<String> rowColumn = new ArrayList<>();
		rowColumn.add(Integer.toString(rowCount));
		allMessages.add(0,rowColumn);
		
		for(Message message: currentMessageList){
			for(Node node: message.getCurrent()){
				appendMessageToColumn(getColumnNumberOfTable(node.toString()),allMessages, message.toString());
			}
		}
		
		tableModel.addRow(allMessages.toArray());
		table.changeSelection(table.getRowCount() - 1, 0, false, false);
	}
	
	/**
	 * Appends a message to the Message log table
	 * @param i Index
	 * @param columns ArrayList with ArrayList that stores the columns
	 * @param message Message string
	 */
	private void appendMessageToColumn(int i,ArrayList<ArrayList<String>> columns,String message){
		ArrayList<String> str = columns.get(i);
		str.add(message);
	}
	
	/**
	 * @param nodeID Node string ID
	 * @return The column number of table given node ID
	 */
	private int getColumnNumberOfTable(String nodeID){
		for(int i = 1;i < tableModel.getColumnCount();i++){
			if(tableModel.getColumnName(i).equals(nodeID)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Update the metrics in the view
	 */
	private void updateTotalMessagesMetrics(int totalMessages)
	{
		
		totalMessagesMetric.setText("" + totalMessages);
	}
	
	/**
	 * Visually clears all current settings
	 */
	private void clearInstance(){
		messageList.clear();
		tableModel.setNumRows(0);
		tableModel.setColumnCount(0);
		table.revalidate();
		rowCount = 0;
		gp.ClearAction();
	}
	
	/**
	 * @param bool true if buttons should be enabled, false otherwise
	 * Disables buttons and menu items when the algorithm is running
	 * So that the user cannot edit the simulation while its running
	 */
	private void setEnabledOptionsWhenStepping(boolean bool){
		stepBackButton.setEnabled(!bool);
		stepBackMenuItem.setEnabled(!bool);
		averageHopsMetricButton.setEnabled(!bool);
		tableBar.setVisible(!bool);
		listBar.setVisible(!bool);
		setAlgorithmButton.setEnabled(bool);
		setFreqButton.setEnabled(bool);
		newEdgeButton.setEnabled(bool);
		newNodeButton.setEnabled(bool);
		setAlgorithmMenu.setEnabled(bool);
		setFrequencyMenu.setEnabled(bool);
		newNodeMenu.setEnabled(bool);
		editNodeMenu.setEnabled(bool);
		deleteNodeMenu.setEnabled(bool);
		newEdgeMenu.setEnabled(bool);
		deleteEdgeMenu.setEnabled(bool);
		deleteEdgeButton.setEnabled(bool);
		deleteNodeButton.setEnabled(bool);
		if(!bool){
			setAlgorithmButton.setToolTipText(DISABLED_WHEN_STEPPING);
			setFreqButton.setToolTipText(DISABLED_WHEN_STEPPING);
			newEdgeButton.setToolTipText(DISABLED_WHEN_STEPPING);
			newNodeButton.setToolTipText(DISABLED_WHEN_STEPPING);
			setAlgorithmMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			setFrequencyMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			newNodeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			editNodeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteNodeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			newEdgeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteEdgeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteEdgeButton.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteNodeButton.setToolTipText(DISABLED_WHEN_STEPPING);
		}else{
			setAlgorithmButton.setToolTipText(null);
			setFreqButton.setToolTipText(null);
			newEdgeButton.setToolTipText(null);
			newNodeButton.setToolTipText(null);
			setAlgorithmMenu.setToolTipText(null);
			setFrequencyMenu.setToolTipText(null);
			newNodeMenu.setToolTipText(null);
			editNodeMenu.setToolTipText(null);
			deleteNodeMenu.setToolTipText(null);
			newEdgeMenu.setToolTipText(null);
			deleteEdgeMenu.setToolTipText(null);
			deleteEdgeButton.setToolTipText(null);
			deleteNodeButton.setToolTipText(null);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////VIEW DEFINED OPTION METHODS////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Open the UML diagram for the project
	 */
	private void showUML() {
		File file = new File(UML);
		if(file.exists()){
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				errorMessageDialog(COULD_NOT_OPEN_FILE);
			}
		}else{
			errorMessageDialog(FILE_DOES_NOT_EXIST);
		}
		setStatus(" ");
	}

	/**
	 * Open javadocs in the default browser
	 */
	private void showJavadoc() {
		File index = new File(JAVADOC1);
		File classes = new File(JAVADOC2);
		if(index.exists() && classes.exists()){
			try {
				Desktop.getDesktop().open(index);
				Desktop.getDesktop().open(classes);
			} catch (IOException e) {
				errorMessageDialog(COULD_NOT_OPEN_FILE);
			}
		}else{
			errorMessageDialog(FILE_DOES_NOT_EXIST);
		}
		setStatus(" ");
	}

	/**
	 * Show details of the project and authors
	 */
	private void showAbout() {
		JOptionPane.showMessageDialog(frame, ABOUT,"About",JOptionPane.INFORMATION_MESSAGE);
		setStatus(" ");
	}

	/**
	 * Shows the README.txt file with instructions
	 */
	private void showREADME() {
		File file = new File(README);
		if(file.exists()){
			try {
				JFrame readmeFrame = new JFrame("README");
				FileReader reader = new FileReader(file.getAbsolutePath());
				BufferedReader br = new BufferedReader(reader);
				JTextArea text = new JTextArea();
				text.read(br,null);
				br.close();
				text.requestFocus();
				readmeFrame.add(new JScrollPane(text));
				//Desktop.getDesktop().open(file);
				
				readmeFrame.pack();
				readmeFrame.setSize(new Dimension(800, 600));
				readmeFrame.setLocation(SCREEN_DIMENTIONS.width/2 - frame.getWidth()/2,SCREEN_DIMENTIONS.height/2 - frame.getHeight()/2);
				readmeFrame.setVisible(true);
			} catch (IOException e) {
				errorMessageDialog(COULD_NOT_OPEN_FILE);
			}
		}else{
			errorMessageDialog(FILE_DOES_NOT_EXIST);
		}
		setStatus(" ");
	}
	
	/**
	 * Ask the user if they are sure they want to quit,
	 * quit if yes
	 */
	private void quit() {
		int response = JOptionPane.showConfirmDialog(null,"Are you sure?","Network Simulator",
				JOptionPane.YES_NO_OPTION,JOptionPane.NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
		      System.exit(0);
		}
	}
}

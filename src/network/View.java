package network;

import static network.Constants.*;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * @author Shasthra Ranasinghe, Alex Hoecht, Andrew Ward, Mohamed Dahrouj
 * 
 * This class creates the GUI for the simulation
 * It also contains all the methods used in the GUI 
 *
 */
public class View {

	//Simulation
	private Simulation network;
	
	//Store Network information
	//private int frequency;
	private ArrayList<String> nodes;
	private ALGORITHM algorithm;
	
	//Variables used to create the GUI
	private List<String> algorithms = new ArrayList<>();
	private String frequencyList[];
	private MessageListModel<Message> messageList;
	private JList<Message> list;
	private DefaultTableModel tableModel;
	private int rowCount;
	
	//Metrics
	JTextField totalMessagesMetric;
	JTextField averageHopsMetric;
	JTextField frequencyMetric;
	JTextField algorithmMetric;
	
	
	//GUI
	private JFrame frame;
	private GraphicPanel gp;
	private JPanel outputsPanel;
	private JPanel playPanel;
	private JPanel toolBar;
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
	private JMenuItem editEdgeMenu;
	private JMenuItem deleteEdgeMenu;
	private JMenuItem newNetworkMenu;
	private JMenuItem defaultNetworkMenu;
	private JMenuItem quitMenu;
	private JMenuItem runMenuItem;
	private JMenuItem stepForwardMenuItem;
	private JMenuItem stepBackMenuItem;
	
	/**
	 * MAIN CONSTRUCTOR OF GUI
	 * @param sim Associated simulation with the View
	 */
	public View(Simulation sim)
	{
		this.network = sim;
		initialize();
		makeFrame();
	}
	
	/**
	 * Initializes all the required variables to create the frame
	 */
	private void initialize(){
		nodes = new ArrayList<String>();
		//edges = new ArrayList<String>();
		
		//Initialize frequency list
		frequencyList = new String[FREQUENCY_OPTIONS_MAX];
		for (int i = FREQUENCY_OPTIONS_MIN; i < frequencyList.length; i++) {
			frequencyList[i-FREQUENCY_OPTIONS_MIN] = Integer.toString(i);
		}
		
		//Initialize array of Algorithms available
		ALGORITHM[] algorithms;
		algorithms = ALGORITHM.values();
		for(ALGORITHM alg: algorithms){
			this.algorithms.add(alg.getALGString());
		}
		
		tableModel = new DefaultTableModel();
		rowCount = INITIAL_ROW_COUNT;
		
		messageList = new MessageListModel<>();
	}
	
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
		
		dialog = new DefaultOptionDialog(frame, "Default");
		
		//TODO
		//I DONT LIKE THIS....COME BACK TO THIS
		//THIS SMALL THING SHOULDNT NEED AN ENTIRE NEW CLASS
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
		
		updateMetrics();//TODO
		
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
		JPanel listBar = new JPanel();
		listBar.setLayout(new GridLayout(0,1));
		
		list =  new JList<>(messageList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(e -> createPopup());//TODO
		//listBar.add(list);
		list.setBackground(center.getBackground());
		list.setCellRenderer(new MessageListCellRenderer());
		listBar.setPreferredSize(new Dimension(100,0));
		scrollPaneList = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listBar.add(scrollPaneList);
		
		//set list renderer
		list.setCellRenderer(new MessageListCellRenderer());		
		center.add(listBar,BorderLayout.EAST);
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
		averageHopsMetric = new JTextField(METRIC_FIELD_SIZE);
		frequencyMetric = new JTextField(METRIC_FIELD_SIZE);
		algorithmMetric = new JTextField(ALGORITHM_METRIC_FIEL_SIZE);
		
		//Metric block
		JPanel panel = new JPanel();
		panel.add(new JLabel("Total Messages:"));
		panel.add(totalMessagesMetric);
		outputsPanel.add(panel);
		
		panel = new JPanel();
		panel.add(new JLabel("Average amount of Hops:"));
		panel.add(averageHopsMetric);
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
		averageHopsMetric.setEditable(false);
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
		center.add(new JScrollPane(gp), BorderLayout.CENTER);
		
		
		playPanel = new JPanel();
		center.add(playPanel,BorderLayout.SOUTH);
		
		playPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,0));
		stepBackButton = new JButton("Back");
		stepBackButton.setEnabled(false);
		stepBackButton.setToolTipText("Not Implemented yet");
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
		stepForwardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT,SHORTCUT_MASK));
		menu.add(stepForwardMenuItem);
		
		stepBackMenuItem = new JMenuItem("Step Back");
		stepBackMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT,SHORTCUT_MASK));
		menu.add(stepBackMenuItem);
		stepBackMenuItem.setEnabled(false);
		stepBackMenuItem.setToolTipText("Not Implemented Yet");
		
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
	////////////////////////////////GUI SETUP COMPLETE. NO GUI SET UP PAST THIS POINT.///////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Steps back to a precious state
	 */
	public void stepBack() 
	{
		// ALGORITHMS CURRENTLY DO NOT SUPPORT BACK STEPS!!!!!!!!!!!!!!!!!!!!!
		// WILL BE IMPLEMENTED LATER!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	/**
	 * Step through the network one step at a time
	 */
	public void stepForward() 
	{
		if(checkFullInitialization())
		{
			if(!tableBar.isVisible()){createTable();}
			setEnabledOptionsWhenStepping(false);
			network.runAlgorithm(1);
			if(network.currentMessageList.size() == 0){stepForwardButton.setEnabled(false);runButton.setEnabled(false);defaultNetworkMenu.setEnabled(true);}
			addMessagesToList();
			addMessagesToTable();
			updateListRender();
			updateMetrics();
			table.changeSelection(table.getRowCount() - 1, 0, false, false);
		}
	}

	/**
	 * Run the full simulation to completion
	 */
	public void run() 
	{
		if(checkFullInitialization())
		{
			if(!tableBar.isVisible()){createTable();}
			setEnabledOptionsWhenStepping(false);
			while(network.currentMessageList.size() != 0)
			{
				network.runAlgorithm(1);
				addMessagesToList();
				addMessagesToTable();
				updateListRender();
				updateMetrics();
				table.changeSelection(table.getRowCount() - 1, 0, false, false);
			}
			runButton.setEnabled(false);
			stepForwardButton.setEnabled(false);
			defaultNetworkMenu.setEnabled(true);
		}
	}
	
	public void removeEdge(String startNodeID, String endNodeID){
		gp.removeGraphicEdge(startNodeID, endNodeID);
		setStatus("Edge " + startNodeID + "-" + endNodeID + " Removed");
	}
	
	public void addNewEdge(String startNodeID, String endNodeID){
		gp.ConnectAction(startNodeID, endNodeID);
		setStatus("Edge " + startNodeID + "-" + endNodeID + " Created");
	}

	public void removeNode(String nodeID){
		gp.removeGraphicNode(nodeID);
		if(gp.numberOfSelectedNodes() > 1){
			setStatus("Multiple Nodes Removed");
		}else{
			setStatus("Node " + nodeID + " Removed");
		}
	}
	
	public List<String> getSelectedNodes(){
		gp.populateSelectedNodesList();
		List<String> nodes = new ArrayList<>();
		for(GraphicNode gn: gp.getSelectedNodesList()){
			nodes.add(gn.getNodeID());
		}
		return nodes;
	}
	
	public void editNodeID(String prevNodeID, String newNodeID){
		for(GraphicNode gn: gp.getSelectedNodesList()){
			if(gn.getNodeID().equals(prevNodeID)){gn.setNodeID(newNodeID);}
			gp.repaint();
			break;
		}
	}

	public String openSingleInputQuestionDialog(String title, String inputfieldString){
		return JOptionPane.showInputDialog(frame,inputfieldString,title,JOptionPane.QUESTION_MESSAGE);
	}
	
	public void addNewNode(String nodeID){
		gp.NewNodeAction(nodeID);
		setStatus("Node " + nodeID + " Created");
	}
	
	/**
	 * Creates the table with the nodes list
	 */
	public void createTable(){
		tableModel.addColumn("Step");
		for(String node: nodes){
			tableModel.addColumn(node);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableBar.setVisible(true);
		frame.repaint();
	}

	/**
	 * Set the frequency
	 */
	public String setFrequency() {
	    return (String) JOptionPane.showInputDialog(frame, "Pick a Frequency",
		        "Set Frequency", JOptionPane.QUESTION_MESSAGE, null, // Use
		        frequencyList, // Array of choices
		        frequencyList[0]); // Initial choice
	}

	/**
	 * Set the algorithm 
	 */
	public String setAlgorithm() {
		String[] choices = algorithms.toArray(new String[0]);
	    return (String) JOptionPane.showInputDialog(null, "Choose Algorithm",
	        "Set Algorithm", JOptionPane.QUESTION_MESSAGE, null, // Use
	        choices, // Array of choices
	        choices[0]); // Initial choice
	}

	public void initializeDefaultNetwork(ALGORITHM algorithm, int frequency) {
		clearInstance();
		stepForwardButton.setEnabled(true);
		runButton.setEnabled(true);
		tableBar.setVisible(false);
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
		totalMessagesMetric.setText("0");
		averageHopsMetric.setText("0");
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
		totalMessagesMetric.setText("0");
		averageHopsMetric.setText("0");

		defaultNetworkMenu.setEnabled(true);
		setEnabledOptionsWhenStepping(true);
		stepForwardButton.setEnabled(true);
		runButton.setEnabled(true);
		tableBar.setVisible(false);
		defaultNetworkMenu.setEnabled(true);
		dialog.setVisible(false);
		setStatus("New Network");
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	private void addMessagesToList()
	{
		for(Message m : network.totalMessageList)
		{
			if(!messageList.contains(m))
			{
				messageList.addElement(m);
			}
		}
	}
	
	/**
	 * Adds a row to the table showing the current location of all the messages in all the nodes
	 */
	private void addMessagesToTable()
	{
		rowCount++;
		
		ArrayList<ArrayList<String>> allMessages = new ArrayList<>();
		
		ArrayList<String> rowColumn = new ArrayList<>();
		rowColumn.add(Integer.toString(rowCount));
		allMessages.add(0,rowColumn);
		
		for(String n : nodes)
		{
			ArrayList<String> nodeMessages = new ArrayList<>();
			
			for(Message m : network.currentMessageList)
			{
				for(int i = 0; i < m.getCurrent().size(); i++)
				{
					if(m.getCurrent().get(i).toString().equals(n)){
						nodeMessages.add(m.toString());
					}
				}
			}
			allMessages.add(nodes.indexOf(n)+1, nodeMessages);
		}
		tableModel.addRow(allMessages.toArray());
	}
	
	
	/**
	 * @return true if all the information needed to run the simulation was provided by the user, false otherwise
	 */
	private boolean checkFullInitialization()
	{
		Integer tempF = frequency;
		if((frequency != 0 && tempF != null) && algorithm != null)
		{
			return true;
		}
		else
		{
			if(algorithm == null)
			{
				errorMessageDialog(ALGORITHM_NOT_SPECIFIED);
			}
			if(frequency == 0)
			{
				errorMessageDialog(FREQUENCY_NOT_SPECIFIED);
			}
			return false;
		}
	}
	
	/**
	 * Update the metrics in the view
	 */
	public void updateMetrics()//TODO
	{
		
		totalMessagesMetric.setText("" + network.totalMessageList.size());
		averageHopsMetric.setText("" + network.getAverageHops());
		//updateFrequencyMetric(frequency);
		//updateAlgorithmMetric(algorithm);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////VIEW MANIPULATION METHODS////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Updates the Algorithm metric
	 * @param algorithm
	 */
	public void updateAlgorithmMetric(ALGORITHM algorithm) {
		algorithmMetric.setText(algorithm.getALGString());
		setStatus("Algortihm set to: " + algorithm.getALGString());
	}

	/**
	 * Updates the Frequency Metric
	 * @param frequency
	 */
	public void updateFrequencyMetric(int frequency) {
		frequencyMetric.setText("" + frequency);
		setStatus("Frequency set to: " + frequency);
	}
	
	
	public void errorMessageDialog(String message) {
		JOptionPane.showMessageDialog(frame,
				message,
			    WARNING,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	private void clearInstance(){
		messageList.clear();
		tableModel.setNumRows(0);
		tableModel.setColumnCount(0);
		table.revalidate();
		rowCount = 0;
		gp.ClearAction();
	}
	
	/**
	 * @param str Message to be written in the status bar
	 * Updates the status bar and repaints the GUI needed
	 */
	public void setStatus(String str) {
		statusLabel.setText(str);
		frame.repaint();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////VIEW HELPER METHODS//////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void updateListRender(){
		for(Message m : network.totalMessageList)
		{
			messageList.update(messageList.indexOf(m));
		}
	}
	
	/**
	 * @param bool true if buttons should be enabled, false otherwise
	 * Disables buttons and menu items when the algorithm is running
	 * So that the user cannot edit the simulation while its running
	 */
	private void setEnabledOptionsWhenStepping(boolean bool){
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
		editEdgeMenu.setEnabled(bool);
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
			editEdgeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteEdgeMenu.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteEdgeButton.setToolTipText(DISABLED_WHEN_STEPPING);
			deleteNodeButton.setToolTipText(DISABLED_WHEN_STEPPING);;
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
	 * SHow details of the project and authors
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

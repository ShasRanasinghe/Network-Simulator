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
import javax.swing.event.ListSelectionEvent;
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
	private int frequency;
	private ArrayList<String> nodes;
	private ArrayList<String> edges;
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
	private JButton newNode;
	private JButton newEdge;
	private JButton freqButton;
	private JButton algorithmButton;
	private JButton deleteNodeButton;
	private JButton deleteEdgeButton;
	private JButton run;
	private JButton stepNext;
	private JButton stepBack;
	private JTable table;
	private JPanel tableBar;
	
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
		edges = new ArrayList<String>();
		
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
		
		defaultOption();
		updateMetrics();
		
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
		list.addListSelectionListener(e -> createPopup(e));
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
		stepBack = new JButton("Back");
		stepBack.addActionListener(e -> stepBack());
		stepBack.setEnabled(false);
		stepBack.setToolTipText("Not Implemented yet");
		run = new JButton("Run");
		run.addActionListener(e -> run());
		stepNext = new JButton("Next");
		stepNext.addActionListener(e -> stepForward());
		
		playPanel.add(stepBack);
		playPanel.add(run);
		playPanel.add(stepNext);	
		
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
		
		newNode = new JButton("New Node");
		newNode.addActionListener(e -> newNode());
		toolBar.add(newNode);
		
		newEdge = new JButton("New Edge");
		newEdge.addActionListener(e -> newEdge());
		toolBar.add(newEdge);
		
		freqButton = new JButton("Set Frequency");
		freqButton.addActionListener(e -> setFrequency());
		toolBar.add(freqButton);
		
		algorithmButton = new JButton("Set Algorithm");
		algorithmButton.addActionListener(e -> setAlgorithm());
		toolBar.add(algorithmButton);
		
		deleteNodeButton = new JButton("Delete Node");
		deleteNodeButton.addActionListener(e -> deleteNode());
		toolBar.add(deleteNodeButton);
		
		deleteEdgeButton = new JButton("Delete Edge");
		deleteEdgeButton.addActionListener(e -> deleteEdge());
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
		newNetworkMenu.addActionListener(e -> newNetwork());
		menu.add(newNetworkMenu);
		
		defaultNetworkMenu = new JMenuItem("Default Network");
		defaultNetworkMenu.addActionListener(e -> defaultNetwork());
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
		setAlgorithmMenu.addActionListener(e -> setAlgorithm());
		menu.add(setAlgorithmMenu);
		
		setFrequencyMenu = new JMenuItem("Set Frequency");
		setFrequencyMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,SHORTCUT_MASK));
		setFrequencyMenu.addActionListener(e -> setFrequency());
		menu.add(setFrequencyMenu);
		
		newNodeMenu = new JMenuItem("New Node");
		newNodeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,SHORTCUT_MASK));
		newNodeMenu.addActionListener(e -> newNode());
		menu.add(newNodeMenu);
		
		editNodeMenu = new JMenuItem("Edit Node");
		editNodeMenu.addActionListener(e -> editNode());
		menu.add(editNodeMenu);
		
		deleteNodeMenu = new JMenuItem("Delete Node");
		deleteNodeMenu.addActionListener(e -> deleteNode());
		menu.add(deleteNodeMenu);

		newEdgeMenu = new JMenuItem("New Edge");
		newEdgeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,SHORTCUT_MASK));
		newEdgeMenu.addActionListener(e -> newEdge());
		menu.add(newEdgeMenu);
		
		editEdgeMenu = new JMenuItem("Edit Edge");
		editEdgeMenu.addActionListener(e -> editEdge());
		menu.add(editEdgeMenu);
		
		deleteEdgeMenu = new JMenuItem("Delete Edge");
		deleteEdgeMenu.addActionListener(e -> deleteEdge());
		menu.add(deleteEdgeMenu);
		
		//TOOLS menu
		menu = new JMenu("Tools");
		menuBar.add(menu);
		
		item = new JMenuItem("Run");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,SHORTCUT_MASK));
		item.addActionListener(e -> run());
		menu.add(item);
		
		item = new JMenuItem("Step Forward");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT,SHORTCUT_MASK));
		item.addActionListener(e -> stepForward());
		menu.add(item);
		
		item = new JMenuItem("Step Back");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT,SHORTCUT_MASK));
		item.addActionListener(e -> stepBack());
		menu.add(item);
		item.setEnabled(false);
		item.setToolTipText("Not Implemented Yet");
		
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

	/**
	 * @param str Message to be written in the status bar
	 * Updates the status bar and repaints the GUI needed
	 */
	private void setStatus(String str) {
		if(!str.equals("")){
			statusLabel.setText(" ");
			frame.repaint();
		}else{
			statusLabel.setText(str);
		}
	}

	/**
	 * Ask the user at start up if they would like to use the default network or not
	 */
	private void defaultOption() {
		int response = JOptionPane.showConfirmDialog(null,"Would you like to use the Default Network?","Default",
				JOptionPane.YES_NO_OPTION,JOptionPane.NO_OPTION);
		if (response == JOptionPane.NO_OPTION ||response == JOptionPane.CLOSED_OPTION) {
		      newNetwork();
		      setStatus("New Network");
		    } else if (response == JOptionPane.YES_OPTION) {
		      defaultNetwork();
		      setStatus("Default Network");
		    }
	}

	/**
	 * Steps back to a precious state
	 */
	private void stepBack() 
	{
		// ALGORITHMS CURRENTLY DO NOT SUPPORT BACK STEPS!!!!!!!!!!!!!!!!!!!!!
		// WILL BE IMPLEMENTED LATER!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	/**
	 * Step through the network one step at a time
	 */
	private void stepForward() 
	{
		if(checkFullInitialization())
		{
			if(!tableBar.isVisible()){createTable();}
			setEnabledOptionsWhenStepping(false);
			network.runAlgorithm(1);
			if(network.currentMessageList.size() == 0){stepNext.setEnabled(false);run.setEnabled(false);defaultNetworkMenu.setEnabled(true);}
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
	private void run() 
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
			run.setEnabled(false);
			stepNext.setEnabled(false);
			defaultNetworkMenu.setEnabled(true);
		}
	}

	/**
	 * Delete an existing edge
	 */
	private void deleteEdge() {
		String edge1 = "";
		String edge2 = "";
		JTextField startNode = new JTextField(5);
		JTextField endNode = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Start NodeID:")); 
		panel.add(startNode);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter End NodeID:")); 
		panel.add(endNode);
		
		for(;;){
			startNode.addHierarchyListener(new RequestFocusListener());
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Remove Edge", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(startNode.getText()) && nodes.contains(endNode.getText())){
					edge1 = startNode.getText() + "->" + endNode.getText();
					edge2 = endNode.getText() + "->" + startNode.getText();
					if(edges.contains(edge1) && edges.contains(edge2)){
						//Remove graphic edge from graphic panel
						gp.removeGraphicEdge(edge1);
						gp.removeGraphicEdge(edge2);
						edges.remove(edge1);
						edges.remove(edge2);
						network.removeLink(startNode.getText(), endNode.getText());
						setStatus("Edge " + edge1 + " Removed");
						break;
					}else{
						errorMessageDialog(EDGE_DOESNT_EXIST);
					}
				}else{
					errorMessageDialog(NODE_S_SPECIFIED_DOES_NOT_EXIST);
				}
			}else{
				setStatus("No Edges Removed");
				break;
			}
		}
	}

	/**
	 * Edit an existing edge between two nodes
	 */
	private void editEdge() {
		String edge1 = "";
		String edge2 = "";
		String newEdge1 = "";
		String newEdge2 = "";
		JTextField startNode = new JTextField(5);
		JTextField endNode = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Start NodeID:")); panel.add(startNode);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter End NodeID:")); panel.add(endNode);
		
		for(;;){
			startNode.addHierarchyListener(new RequestFocusListener());
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Edit Existing Edge", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(startNode.getText()) && nodes.contains(endNode.getText())){
					edge1 = startNode.getText() + "->" + endNode.getText();
					edge2 = endNode.getText() + "->" + startNode.getText();
					if(edges.contains(edge1) && edges.contains(edge2)){
						String newEndNodeID = JOptionPane.showInputDialog(frame,"Enter New End NodeID:","Edit Edge",JOptionPane.QUESTION_MESSAGE);
						if(newEndNodeID == null){
							setStatus("No Edges Changed");
							break;
						}else{
							if(nodes.contains(newEndNodeID)){
								if(!edges.contains(startNode.getText() + "->" + newEndNodeID)
										||!edges.contains(newEndNodeID + "->" + startNode.getText())){
									//remove/replace
									gp.removeGraphicEdge(edge1);
									gp.removeGraphicEdge(edge2);
									newEdge1 = startNode.getText() + "->" + newEndNodeID;
									newEdge2 = newEndNodeID + "->" + startNode.getText();
									edges.set(edges.indexOf(edge1), newEdge1);
									edges.set(edges.indexOf(edge2), newEdge2);
									network.removeLink(startNode.getText(), endNode.getText());

									//add
									gp.ConnectAction(startNode.getText(), newEndNodeID);
									network.createLink(startNode.getText(), newEndNodeID);
									//set status
									setStatus("Edge " + edge1 + " Changes to " + newEdge1);
									break;
								}else{
									errorMessageDialog(EDGE_ALREADY_EXISTS);
								}
							}else{
								errorMessageDialog(NODE_S_SPECIFIED_DOES_NOT_EXIST);
							}
						}
					}else{
						errorMessageDialog(EDGE_DOESNT_EXIST);
					}
				}else{
					errorMessageDialog(NODE_S_SPECIFIED_DOES_NOT_EXIST);
				}
			}else{
				setStatus("No Edges Changed");
				break;
			}
		}
	}

	/**
	 * Create a new edge
	 */
	private void newEdge() {
		String edge1 = "";
		String edge2 = "";
		JTextField startNode = new JTextField(5);
		JTextField endNode = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Start NodeID:")); panel.add(startNode);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter End NodeID:")); panel.add(endNode);
		
		for(;;){
			startNode.addHierarchyListener(new RequestFocusListener());
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Create New Edge", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(startNode.getText()) && nodes.contains(endNode.getText())){
					String startNodeID = startNode.getText();
					String endNodeID = endNode.getText();
					edge1 = startNodeID + "->" + endNodeID;
					edge2 = endNodeID + "->" + startNodeID;
					if(!edges.contains(edge1) || !edges.contains(edge2)){
						edges.add(edge1);
						edges.add(edge2);
						
						network.createLink(startNodeID, endNodeID);
						
						gp.ConnectAction(startNodeID, endNodeID);
						setStatus("Edge " + edge1 + " Created");
						break;
					}else{
						errorMessageDialog(EDGE_ALREADY_EXISTS);
					}
				}else{
					errorMessageDialog(NODE_S_SPECIFIED_DOES_NOT_EXIST);
				}
			}else{
				setStatus("No New Edges Created");
				break;
			}
		}
	}

	/**
	 * Delete an existing node
	 */
	private void deleteNode() {
		for(;;){
			
			//Remove graphic node from graphic panel
			gp.populateSelectedNodesList();
			if(gp.numberOfSelectedNodes()>0){
				gp.DeleteAction();
				for(GraphicNode gn: gp.getSelectedNodesList()){
					String nodeID = gn.getNodeID();
					if(nodes.contains(nodeID)){
						nodes.remove(nodeID);
						setStatus("Node " + nodeID + " Removed");
					}
				}
				break;
			}
			
			String nodeID = JOptionPane.showInputDialog(frame,"Enter NodeID:","Delete Node",JOptionPane.QUESTION_MESSAGE);
			if(nodeID == null){
				setStatus("No Nodes Deleted");
				break;
			}else{
				if(nodes.contains(nodeID)){
					nodes.remove(nodeID);
					gp.removeGraphicNode(nodeID);
					setStatus("Node " + nodeID + " Removed");
					break;	
				}else{
					errorMessageDialog(NODE_S_SPECIFIED_DOES_NOT_EXIST);
				}
			}
		}
	}

	/**
	 * Edit a current nodes ID
	 * doesn't let you edit a non existent node
	 */
	private void editNode() {
		JTextField nodeID = new JTextField(5);
		JTextField newNodeID = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter NodeID:")); panel.add(nodeID);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter New NodeID:")); panel.add(newNodeID);
		
		for(;;){
			nodeID.addHierarchyListener(new RequestFocusListener());
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Edit Node", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(nodeID.getText())){
					nodes.set(nodes.indexOf(nodeID.getText()), newNodeID.getText());
					setStatus("Node " + nodeID.getText() +" Changed to " + newNodeID.getText());
					break;
				}else{
					errorMessageDialog(NODE_S_SPECIFIED_DOES_NOT_EXIST);
				}
			}else{
				setStatus("No Nodes Were Changed");
				break;
			}
		}
	}

	/**
	 * Create a new node
	 * Doesn't let you create a node that already exists
	 */
	private void newNode() {
		for(;;){
			String nodeID = JOptionPane.showInputDialog(frame,"Enter NodeID:","Create New Node",JOptionPane.QUESTION_MESSAGE);
			if(nodeID == null){
				setStatus("No Nodes Created");
				break;
			}else{
				if(!nodes.contains(nodeID)){
					nodes.add(nodeID);
					network.createNodes(nodes);
		            gp.NewNodeAction(nodeID);
		            
					setStatus("Node " + nodeID + " Created");
					break;
				}else{
					errorMessageDialog(NODE_ALREADY_EXISTS);
				}
			}
		}
	}
	
	/**
	 * Creates the table with the nodes list
	 */
	private void createTable(){
		tableModel.addColumn("Step");
		for(String node: nodes){
			tableModel.addColumn(node);
		}
		tableBar.setVisible(true);
		frame.repaint();
	}

	/**
	 * Set the frequency
	 */
	private void setFrequency() {
	    String frequency = (String) JOptionPane.showInputDialog(frame, "Pick a Frequency",
		        "Set Frequency", JOptionPane.QUESTION_MESSAGE, null, // Use
		        frequencyList, // Array of choices
		        frequencyList[0]); // Initial choice
	    if(frequency == null){
	    	setStatus("Frequency Not Set");
	    }
	    else
	    {
	    	setStatus("Frequency set to: " + frequency);
	    	frequencyMetric.setText("" + frequency);
			this.frequency = Integer.parseInt(frequency);
			network.setFrequency(this.frequency);
	    }
	}

	/**
	 * Set the algorithm 
	 */
	private void setAlgorithm() {
		String[] choices = algorithms.toArray(new String[0]);
	    String algorithm = (String) JOptionPane.showInputDialog(null, "Choose Algorithm",
	        "Set Algorithm", JOptionPane.QUESTION_MESSAGE, null, // Use
	        choices, // Array of choices
	        choices[0]); // Initial choice
	    if(algorithm == null){
	    	setStatus("Algortihm Not Set");
	    }else{
	    	setStatus("Algortihm set to: " + algorithm);
	    	this.algorithm = ALGORITHM.getEnum(algorithm);
	    	network.setAlgorithm(this.algorithm);
	    	updateMetrics();
	    }
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
	
	/**
	 * @param bool true if buttons should be enabled, false otherwise
	 * Disables buttons and menu items when the algorithm is running
	 * So that the user cannot edit the simulation while its running
	 */
	private void setEnabledOptionsWhenStepping(boolean bool){
		algorithmButton.setEnabled(bool);
		freqButton.setEnabled(bool);
		newEdge.setEnabled(bool);
		newNode.setEnabled(bool);
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
			algorithmButton.setToolTipText("Disabled When Stepping");
			freqButton.setToolTipText("Disabled When Stepping");
			newEdge.setToolTipText("Disabled When Stepping");
			newNode.setToolTipText("Disabled When Stepping");
			setAlgorithmMenu.setToolTipText("Disabled When Stepping");
			setFrequencyMenu.setToolTipText("Disabled When Stepping");
			newNodeMenu.setToolTipText("Disabled When Stepping");
			editNodeMenu.setToolTipText("Disabled When Stepping");
			deleteNodeMenu.setToolTipText("Disabled When Stepping");
			newEdgeMenu.setToolTipText("Disabled When Stepping");
			editEdgeMenu.setToolTipText("Disabled When Stepping");
			deleteEdgeMenu.setToolTipText("Disabled When Stepping");
			deleteEdgeButton.setToolTipText("Disabled When Stepping");
			deleteNodeButton.setToolTipText("Disabled When Stepping");;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//						WILL BE CHANGED DEPENDING ON GRAPHIC IMPLEMENTATION!!!!!!!!!!!
	//
	/**
	 * Creates a default network to be use used for testing or as a basis to start on
	 */
	private void defaultNetwork() 
	{		
		newNetwork();
		frequency = 5;
		algorithm = ALGORITHM.RANDOM;
		nodes.add("A");nodes.add("B");nodes.add("C");nodes.add("D");nodes.add("E");
		edges.add("A->B");edges.add("A->C");edges.add("A->E");edges.add("B->D");edges.add("B->E");
		edges.add("C->D");
		
		for(String s : nodes)
		{
			gp.NewNodeAction(s);
		}
		gp.ConnectAction("A","B");
		gp.ConnectAction("A","C");
		gp.ConnectAction("A","E");
		gp.ConnectAction("B","D");
		gp.ConnectAction("B","E");
		gp.ConnectAction("C","D");
		
		network.createNodes(nodes);
		network.addNeighbors(edges);
		network.setFrequency(frequency);
		network.setAlgorithm(ALGORITHM.RANDOM);
		defaultNetworkMenu.setEnabled(false);
		stepNext.setEnabled(true);
		run.setEnabled(true);
		updateMetrics();
		tableBar.setVisible(false);		
	}

	/**
	 * Create a new network and reset all information
	 */
	private void newNetwork() 
	{
		messageList.clear();
		tableModel.setNumRows(0);
		tableModel.setColumnCount(0);
		table.revalidate();
		frequency = 0;
		nodes.clear();
		edges.clear();
		algorithm = null;
		rowCount = 0;
		
		this.network = new Simulation();
		
		gp.ClearAction();
		
		setStatus("Creating a new Network");
		defaultNetworkMenu.setEnabled(true);
		setEnabledOptionsWhenStepping(true);
		stepNext.setEnabled(true);
		run.setEnabled(true);
		updateMetrics();
		tableBar.setVisible(false);
		defaultNetworkMenu.setEnabled(true);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return ALGIRITHM enum set by user
	 */
	@SuppressWarnings("unused")
	private ALGORITHM getAlgorithm() {
		return algorithm;
	}

	/**
	 * @return frequency set by user
	 */
	@SuppressWarnings("unused")
	private int getFrequency(){
		return frequency;
	}
	
	/**
	 * @param e List Selection Event
	 * Creates a pop up when a message is selected in the chart
	 * Pop up shows details of the message
	 */
	protected void createPopup(ListSelectionEvent e) 
	{
		Message message = (Message)list.getSelectedValue();
		if(list.getSelectedIndex() != -1)
		{
			String info = message.getSource() + " -> " + message.getDestination() + "\n"
					+ "Hop Count: " + message.getHopCount() + "\n"
							+ "Current Node: ";
			if(!message.isRunning()){
				info += message.getDestination();
			}else{
				info += message.getCurrentNode();
			}
			int response = JOptionPane.showConfirmDialog(null,info,message.toString(),
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
				messageList.update(messageList.indexOf(m));
			}
		}
	}
	
	private void updateListRender(){
		for(Message m : network.totalMessageList)
		{
			messageList.update(messageList.indexOf(m));
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
				if(m.getCurrentNode().toString().equals(n))
				{
					nodeMessages.add(m.toString());
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
	private void updateMetrics()
	{
		totalMessagesMetric.setText("" + network.totalMessageList.size());
		averageHopsMetric.setText("" + network.getAverageHops());
		frequencyMetric.setText("" + frequency);
		if(algorithm == null){
			algorithmMetric.setText("Not set");
		}else{
			algorithmMetric.setText(algorithm.getALGString());
		}
	}

	/**
	 * Open the UML diagram for the project
	 */
	private void showUML() {
		File file = new File(UML);
		if(file.exists()){
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				errorMessageDialog(COUTLD_NOT_OPEN_FILE);
			}
		}else{
			errorMessageDialog(FILE_DOES_NOT_EXIST);
		}
		setStatus("");
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
				errorMessageDialog(COUTLD_NOT_OPEN_FILE);
			}
		}else{
			errorMessageDialog(FILE_DOES_NOT_EXIST);
		}
		setStatus("");
	}

	/**
	 * SHow details of the project and authors
	 */
	private void showAbout() {
		JOptionPane.showMessageDialog(frame, ABOUT);
		setStatus("");
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
				errorMessageDialog(COUTLD_NOT_OPEN_FILE);
			}
		}else{
			errorMessageDialog(FILE_DOES_NOT_EXIST);
		}
		setStatus("");
	}

	/**
	 * 
	 */
	public void errorMessageDialog(String message) {
		JOptionPane.showMessageDialog(frame,
				message,
			    WARNING,
			    JOptionPane.ERROR_MESSAGE);
	}
}

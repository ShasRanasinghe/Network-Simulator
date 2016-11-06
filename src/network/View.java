package network;

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

public class View {
	
	//Model
	Simulation model;

	//test
	Object rows[][] = { { "AMZN", "Amazon", "67 9/16" },
	        { "AOL", "America Online", "68 3/4" },
	        { "BOUT", "About.com", "56 3/8" },
	        { "AOL", "America Online", "68 3/4" },
	        { "BOUT", "About.com", "56 3/8" },
	        { "YHOO", "Yahoo!", "151 1/8" },
			{ "AOL", "America Online", "68 3/4" },
	        { "BOUT", "About.com", "56 3/8" },
	        { "YHOO", "Yahoo!", "151 1/8" },
	        { "YHOO", "Yahoo!", "151 1/8" }};
		    final Object headers[] = { "Symbol", "Name", "Price" };;

	
	
	//constants
	private final String ABOUT = "SYSC 3110 Group Project: Network Routing Simulator\n"
			+ "Group Members: Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe\n\n"
			+ "Summary:\n"
			+ "----------------------------------------------\n"
			+ "The goal of this team project is to build a simulator to evaluate the performance of various routing\n"
			+ "\nalgorithms. This project will implement the following routing algorithms:\n"
			+ "* Random\n"
			+ "* Flooding\n"
			+ "* Shortest path\n"
			+ "* Custom method";
	private final String TEST_CASES = "Test 1: \n"
			+ "Test 2: \n"
			+ "Test 3: \n"
			+ "Test 4: \n"
			+ "Test 5: \n"
			+ "Test 6: \n";
	private final String README = "README.txt";
	private final String UML = "Milestone2_UML.jpg";
	private final String JAVADOC1 = "doc\\index.html";
	private final String JAVADOC2 = "doc\\network\\Simulation.html";
	private final String COUTLD_NOT_OPEN_FILE = "Could not open file properly";
	private final String FILE_DOES_NOT_EXIST = "Could Not Find Files Required";
	private List<String> algorithms = new ArrayList<>();
	private String frequencyList[];
	private DefaultListModel<Message> messageList;
	private JList<Message> list;
	private DefaultTableModel tableModel;
	
	//Store Network information
	private Simulation network;
	private int frequency;
	private ArrayList<String> nodes;
	private ArrayList<String> edges;
	private ALGORITHM algorithm;
	
	
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
	private JButton run;
	private JButton stepNext;
	private JButton stepBack;
	private JTable table;
	
	/**
	 * 
	 * @param MAIN FUNCTION OF GUI
	 */
	public static void main(String[] arg0)
	{
		Simulation sim = new Simulation();
		new View(sim);
	}
	
	/**
	 * MAIN CONSTRUCTOR OF GUI
	 */
	public View(Simulation sim)
	{
		this.network = sim;
		initialize();
		makeFrame();
	}
	
	private void initialize(){
		nodes = new ArrayList<String>();
		edges = new ArrayList<String>();
		
		frequencyList = new String[30];
		for (int i = 1; i < frequencyList.length; i++) {
			frequencyList[i] = Integer.toString(i);
		}
		
		ALGORITHM[] algorithms;
		algorithms = ALGORITHM.values();
		for(ALGORITHM alg: algorithms){
			this.algorithms.add(alg.getALGString());
		}
		
		tableModel = new DefaultTableModel();
		
		//TODO
		messageList = new DefaultListModel<>();
	}
	
	private void makeFrame(){
		frame = new JFrame("Network Simulator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel contentPane = (JPanel)frame.getContentPane();
		//set border here
		
		makeMenu(frame);
		
		contentPane.setLayout(new BorderLayout());
		
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
		
		JPanel west = new JPanel();
		west.setLayout(new FlowLayout(FlowLayout.CENTER));
		west.add(toolBar);
		
		
		//scroll bar
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		gp = new GraphicPanel();
		center.add(new JScrollPane(gp), BorderLayout.CENTER);
		center.add(west,BorderLayout.WEST);
		
		playPanel = new JPanel();
		center.add(playPanel,BorderLayout.SOUTH);
		
		playPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,0));
		stepBack = new JButton("Back");
		stepBack.addActionListener(e -> stepBack());
		run = new JButton("Run");
		run.addActionListener(e -> run());
		stepNext = new JButton("Next");
		stepNext.addActionListener(e -> stepForward());
		
		playPanel.add(stepBack);
		playPanel.add(run);
		playPanel.add(stepNext);	
		
		contentPane.add(center,BorderLayout.CENTER);
		
		//output bar
		
		JPanel south = new JPanel();
		//south
		south.setLayout(new BorderLayout());
		outputsPanel = new JPanel();
		
		//Metric block
		JTextField metric1 = new JTextField(20);
		JTextField metric2 = new JTextField(20);
		JTextField frequencyMetric = new JTextField(20);
		outputsPanel.add(new JLabel("Metric 1"));
		outputsPanel.add(metric1);
		outputsPanel.add(new JLabel("Metric 2"));
		outputsPanel.add(metric2);
		outputsPanel.add(new JLabel("Frequency"));
		outputsPanel.add(frequencyMetric);
		metric1.setText("walala...");
		metric2.setText("running..");
		frequencyMetric.setText("" + frequency);
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
		
		//set list bar
		
		JPanel east = new JPanel();
		east.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel listBar = new JPanel();
		listBar.setLayout(new GridLayout(0,1));
		east.add(listBar);
		
		list =  new JList<>(messageList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(e -> createPopup(e));
		listBar.add(list);
		list.setCellRenderer(new messageListCellRenderer());
		east.setPreferredSize(new Dimension(100,0));
		scrollPaneList = new JScrollPane(east,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		contentPane.add(scrollPaneList,BorderLayout.EAST);
		
		//set list renderer
		list.setCellRenderer(new messageListCellRenderer());
		
		scrollPaneList = new JScrollPane(east,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		center.add(scrollPaneList,BorderLayout.EAST);
		
		//set table bar
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel tableBar = new JPanel();
		tableBar.setLayout(new GridLayout(0,1));
		north.add(tableBar);
		table = new JTable(tableModel);
		scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setPreferredSize(new Dimension(frame.getWidth(),100));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableBar.add(scrollPaneTable);
		center.add(tableBar,BorderLayout.NORTH);
		
		//Finish it off
		frame.pack();
		frame.setSize(new Dimension(1000,500));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2,d.height/2 - frame.getHeight()/2);
		
		frame.setVisible(true);
		
		defaultOption();
	}
	

	private void setStatus(String str) {
		statusLabel.setText(str);
		if(!str.equals("")){
			frame.repaint();
		}
	}

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

	private void makeMenu(JFrame frame){
		final int SHORTCUT_MASK =
	            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu;
		JMenuItem item;
		
		menu = new JMenu("Network");
		menuBar.add(menu);
		
		item = new JMenuItem("New Network");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,SHORTCUT_MASK));
		item.addActionListener(e -> newNetwork());
		menu.add(item);
		
		item = new JMenuItem("Default Network");
		item.addActionListener(e -> defaultNetwork());
		menu.add(item);
		
		item = new JMenuItem("Quit");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,SHORTCUT_MASK));
		item.addActionListener(e -> quit());
		menu.add(item);
		
		
		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		item = new JMenuItem("Set Algorithm");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,SHORTCUT_MASK));
		item.addActionListener(e -> setAlgorithm());
		menu.add(item);
		
		item = new JMenuItem("Set Frequency");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,SHORTCUT_MASK));
		item.addActionListener(e -> setFrequency());
		menu.add(item);
		
		item = new JMenuItem("New Node");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,SHORTCUT_MASK));
		item.addActionListener(e -> newNode());
		menu.add(item);
		
		item = new JMenuItem("Edit Node");
		item.addActionListener(e -> editNode());
		menu.add(item);
		
		item = new JMenuItem("Delete Node");
		item.addActionListener(e -> deleteNode());
		menu.add(item);

		item = new JMenuItem("New Edge");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,SHORTCUT_MASK));
		item.addActionListener(e -> newEdge());
		menu.add(item);
		
		item = new JMenuItem("Edit Edge");
		item.addActionListener(e -> editEdge());
		menu.add(item);
		
		item = new JMenuItem("Delete Edge");
		item.addActionListener(e -> deleteEdge());
		menu.add(item);
		
		
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
		
		
		menu = new JMenu("Help");
		menuBar.add(menu);
		
		item = new JMenuItem("README");
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
		
		item = new JMenuItem("Test Cases");
		item.addActionListener(e -> showTestCases());
		menu.add(item);
	}

	private void stepBack() 
	{
		// ALGORITHMS CURRENTLY DO NOT SUPPORT BACK STEPS!!!!!!!!!!!!!!!!!!!!!
		// WILL BE IMPLEMENTED LATER!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	private void stepForward() 
	{
		if(checkFullInitialization())
		{
			network.runAlgorithm(1);
			addMessagesToChart();
			addMessagesToTable();
		}
	}

	private void run() 
	{
		if(checkFullInitialization())
		{
			while(network.currentMessageList.size() != 0)
			{
				network.runAlgorithm(1);
				addMessagesToChart();
				addMessagesToTable();
				
				table.changeSelection(table.getRowCount() - 1, 0, false, false);
			}
		}
	}

	private void deleteEdge() {
		String edge = "";
		JTextField startNode = new JTextField(5);
		JTextField endNode = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Start NodeID:")); panel.add(startNode);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter End NodeID:")); panel.add(endNode);
		
		for(;;){
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Remove Edge", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(startNode.getText()) && nodes.contains(endNode.getText())){
					edge = startNode.getText() + "->" + endNode.getText();
					if(edges.contains(edge)){
						edges.remove(edge);
						setStatus("Edge " + edge + " Removed");
						break;
					}else{
						JOptionPane.showMessageDialog(frame,
								"Edge Doesnt Exist",
								"WARNING",
								JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(frame,
							"Node(s) Specified Does not Exist",
							"WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
			}else{
				setStatus("No Edges Removed");
				break;
			}
		}
	}

	private void editEdge() {
		String edge = "";
		String newEdge = "";
		JTextField startNode = new JTextField(5);
		JTextField endNode = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Start NodeID:")); panel.add(startNode);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter End NodeID:")); panel.add(endNode);
		
		for(;;){
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Edit Existing Edge", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(startNode.getText()) && nodes.contains(endNode.getText())){
					edge = startNode.getText() + "->" + endNode.getText();
					if(edges.contains(edge)){
						String nodeID = JOptionPane.showInputDialog(frame,"Enter New End NodeID:","Edit Edge",JOptionPane.QUESTION_MESSAGE);
						if(nodeID == null){
							setStatus("No Edges Changed");
							break;
						}else{
							if(nodes.contains(nodeID)){
								newEdge = startNode.getText() + "->" + nodeID;
								edges.set(edges.indexOf(edge), newEdge);
								setStatus("Edge " + edge + " Changes to " + newEdge);
								break;
							}else{
								JOptionPane.showMessageDialog(frame,
										"Node Doesnt Exist",
										"WARNING",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}else{
						JOptionPane.showMessageDialog(frame,
								"Edge Doesnt Exist",
								"WARNING",
								JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(frame,
							"Node(s) Specified Does not Exist",
							"WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
			}else{
				setStatus("No Edges Changed");
				break;
			}
		}
	}

	private void newEdge() {
		String edge = "";
		JTextField startNode = new JTextField(5);
		JTextField endNode = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Start NodeID:")); panel.add(startNode);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter End NodeID:")); panel.add(endNode);
		
		for(;;){
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Create New Edge", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(startNode.getText()) && nodes.contains(endNode.getText())){
					String startNodeID = startNode.getText();
					String endNodeID = endNode.getText();
					edge = startNodeID + "->" + endNodeID;
					if(!edges.contains(edge)){
						edges.add(edge);
						gp.ConnectAction(startNodeID, endNodeID);
						setStatus("Edge " + edge + " Created");
						break;
					}else{
						JOptionPane.showMessageDialog(frame,
								"Edge Already Exists",
								"WARNING",
								JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(frame,
							"Node(s) Specified Does not Exist",
							"WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
			}else{
				setStatus("No New Edges Created");
				break;
			}
		}
	}

	private void deleteNode() {
		for(;;){
			String nodeID = JOptionPane.showInputDialog(frame,"Enter NodeID:","Delete Node",JOptionPane.QUESTION_MESSAGE);
			if(nodeID == null){
				setStatus("No Nodes Deleted");
				break;
			}else{
				if(nodes.contains(nodeID)){
					nodes.remove(nodeID);
					setStatus("Node " + nodeID + " Removed");
					break;
				}else{
					JOptionPane.showMessageDialog(frame,
							"Node Doesnt Exist",
							"WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void editNode() {
		JTextField nodeID = new JTextField(5);
		JTextField newNodeID = new JTextField(5);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter NodeID:")); panel.add(nodeID);
		panel.add(Box.createHorizontalStrut(15)); // a spacer
		panel.add(new JLabel("Enter New NodeID:")); panel.add(newNodeID);
		
		for(;;){
			int result = JOptionPane.showConfirmDialog(null, panel, 
					"Edit Node", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if(nodes.contains(nodeID.getText())){
					nodes.set(nodes.indexOf(nodeID.getText()), newNodeID.getText());
					setStatus("Node " + nodeID.getText() +" Changed to " + newNodeID.getText());
					break;
				}else{
					JOptionPane.showMessageDialog(frame,
							"Node Doesnt Exist",
							"WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
			}else{
				setStatus("No Nodes Were Changed");
				break;
			}
		}
	}

	private void newNode() {
		for(;;){
			String nodeID = JOptionPane.showInputDialog(frame,"Enter NodeID:","Create New Node",JOptionPane.QUESTION_MESSAGE);
			if(nodeID == null){
				setStatus("No Nodes Created");
				break;
			}else{
				if(!nodes.contains(nodeID)){
					nodes.add(nodeID);
					tableModel.addColumn(nodeID);
		            gp.NewNodeAction(nodeID);
		            
					setStatus("Node " + nodeID + " Created");
					break;
				}else{
					JOptionPane.showMessageDialog(frame,
							"Node Already Exists",
							"WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void setFrequency() {
	    String frequency = (String) JOptionPane.showInputDialog(frame, "Pick a Frequency",
		        "Set Frequency", JOptionPane.QUESTION_MESSAGE, null, // Use
		        frequencyList, // Array of choices
		        frequencyList[0]); // Initial choice
	    if(frequency == null){
	    	setStatus("Frequency Not Set");
	    }else{
	    	setStatus("Frequency set to: " + frequency);
			this.frequency = Integer.parseInt(frequency);
	    }
	}

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
	    	this.algorithm = ALGORITHM.getEnum(algorithm); //TODO
	    }
	}

	private void quit() {
		int response = JOptionPane.showConfirmDialog(null,"Are you sure?","Network Simulator",
				JOptionPane.YES_NO_OPTION,JOptionPane.NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
		      System.exit(0);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//						WILL BE CHANGED DEPENDING ON GRAPHIC IMPLEMENTATION!!!!!!!!!!!
	//
	private void defaultNetwork() 
	{		
		frequency = 5;
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
		
		addNodesToTable(nodes);
		
		network.createNodes(nodes);
		network.addNeighbors(network.getSimulationNodes(), edges);
		network.setFrequency(frequency);
		network.setAlgorithm(ALGORITHM.RANDOM);
	}

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
		
		this.network = new Simulation();
		
		gp.ClearAction();
		
		setStatus("Creating a new Network");
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ALGORITHM getAlgorithm() {
		return algorithm;
	}

	public int getFrequency(){
		return frequency;
	}
	
	protected void createPopup(ListSelectionEvent e) 
	{
		Message message = (Message)list.getSelectedValue();
		if(list.getSelectedIndex() != -1)
		{
			int response = JOptionPane.showConfirmDialog(null,message.toString(),message.toString(),
														JOptionPane.PLAIN_MESSAGE,
														JOptionPane.CLOSED_OPTION);
			if (response == JOptionPane.OK_OPTION || response == JOptionPane.CLOSED_OPTION) 
			{
				list.clearSelection();
			}
		}
	}
	
	public void addNodesToTable(ArrayList<String> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			String node = list.get(i);
			tableModel.addColumn(node);
		}
	}
	
	public void addMessagesToChart()
	{
		for(Message m : network.totalMessageList)
		{
			if(!messageList.contains(m))
			{
				messageList.addElement(m);
			}
		}
	}
	
	public void addMessagesToTable()
	{
		ArrayList<ArrayList<String>> allMessages = new ArrayList<>();
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
			allMessages.add(nodes.indexOf(n), nodeMessages);
		}
		tableModel.addRow(allMessages.toArray());
	}
	
	
	public boolean checkFullInitialization()
	{
		Integer tempF = frequency;
		if(nodes.size() != 0 && edges.size() != 0 && tempF != null && algorithm != null)
		{
			return true;
		}
		return false;

	}

	private void showTestCases() {
		JOptionPane.showMessageDialog(frame, TEST_CASES);
		setStatus("");
	}

	private void showUML() {
		File file = new File(UML);
		if(file.exists()){
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
						COUTLD_NOT_OPEN_FILE,
			    	    "WARNING",
			    	    JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(frame,
					FILE_DOES_NOT_EXIST,
		    	    "WARNING",
		    	    JOptionPane.ERROR_MESSAGE);
		}
		setStatus("");
	}

	private void showJavadoc() {
		File index = new File(JAVADOC1);
		File classes = new File(JAVADOC2);
		if(index.exists() && classes.exists()){
			try {
				Desktop.getDesktop().open(index);
				Desktop.getDesktop().open(classes);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
			    	    COUTLD_NOT_OPEN_FILE,
			    	    "WARNING",
			    	    JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(frame,
					FILE_DOES_NOT_EXIST,
		    	    "WARNING",
		    	    JOptionPane.ERROR_MESSAGE);
		}
		setStatus("");
	}

	private void showAbout() {
		JOptionPane.showMessageDialog(frame, ABOUT);
		setStatus("");
	}

	private void showREADME() {
		File file = new File(README);
		if(file.exists()){
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
			    	    COUTLD_NOT_OPEN_FILE,
			    	    "WARNING",
			    	    JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(frame,
					FILE_DOES_NOT_EXIST,
		    	    "WARNING",
		    	    JOptionPane.ERROR_MESSAGE);
		}
		setStatus("");
	}
}

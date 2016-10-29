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

import javax.swing.*;

public class View {

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
	
	private JFrame frame;
	private JPanel networkPanel;
	private JTextArea outputsPanel;
	private JPanel playPanel;
	private JPanel toolBar;
	private JLabel statusLabel;
	private JScrollPane scrollPane;
	private JButton newNode;
	private JButton newEdge;
	private JButton frequency;
	private JButton algorithm;
	private JButton run;
	private JButton stepNext;
	private JButton stepBack;
	
	public static void main(String[] arg0){
		View v = new View();
	}
	
	public View(){
		makeFrame();
	}
	
	private void makeFrame(){
		frame = new JFrame("Network Simulator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel contentPane = (JPanel)frame.getContentPane();
		//set border here
		
		makeMenu(frame);
		
		contentPane.setLayout(new BorderLayout(50,50));
		
		//tool bar
		
		toolBar = new JPanel();
		toolBar.setLayout(new GridLayout(0,1,0,20));
		
		newNode = new JButton("New Node");
		newNode.addActionListener(e -> newNode());
		toolBar.add(newNode);
		
		newEdge = new JButton("New Edge");
		newEdge.addActionListener(e -> newEdge());
		toolBar.add(newEdge);
		
		frequency = new JButton("Set Frequency");
		frequency.addActionListener(e -> setFrequency());
		toolBar.add(frequency);
		
		algorithm = new JButton("Set Algorithm");
		algorithm.addActionListener(e -> setAlgorithm());
		toolBar.add(algorithm);
		
		JPanel west = new JPanel();
		west.setLayout(new FlowLayout(FlowLayout.CENTER));
		west.add(toolBar);
		
		contentPane.add(west,BorderLayout.WEST);
		
		//scroll bar
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		networkPanel = new JPanel();
		scrollPane = new JScrollPane(networkPanel);
		center.add(scrollPane,BorderLayout.CENTER);
		
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
		south.setLayout(new GridLayout(0,1));
		outputsPanel = new JTextArea("hello");
		statusLabel = new JLabel();
		south.add(outputsPanel);
		south.add(statusLabel);
		contentPane.add(south,BorderLayout.SOUTH);
		
		setButtonsEnabled(true);
		frame.pack();
		frame.setSize(new Dimension(1000,500));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2,d.height/2 - frame.getHeight()/2);
		
		frame.setVisible(true);
		
		defaultOption();
	}

	private void setStatus(String str) {
		statusLabel.setText(str);
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
	
	private void setButtonsEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private Object showTestCases() {
		// TODO Auto-generated method stub
		return null;
	}

	private void showUML() {
		File file = new File("Milestone2_UML.jpg");
		if(file.exists()){
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
			    	    "Could not open file properly",
			    	    "WARNING",
			    	    JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(frame,
		    	    "Could Not File Files Required",
		    	    "WARNING",
		    	    JOptionPane.ERROR_MESSAGE);
		}
		setStatus("");
	}

	private void showJavadoc() {
		File index = new File("doc\\index.html");
		File classes = new File("doc\\network\\Simulation.html");
		if(index.exists() && classes.exists()){
			try {
				Desktop.getDesktop().open(index);
				Desktop.getDesktop().open(classes);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
			    	    "Could not open file properly",
			    	    "WARNING",
			    	    JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(frame,
		    	    "Could Not File Files Required",
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
		File file = new File("README.txt");
		if(file.exists()){
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
			    	    "Could not open file properly",
			    	    "WARNING",
			    	    JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(frame,
		    	    "Could Not File Files Required",
		    	    "WARNING",
		    	    JOptionPane.ERROR_MESSAGE);
		}
		setStatus("");
	}

	private Object stepBack() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object stepForward() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object run() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object deleteEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object editEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object newEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object deleteNode() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object editNode() {
		// TODO Auto-generated method stub
		return null;
	}

	private void newNode() {
		String nodeID = JOptionPane.showInputDialog("Enter NodeID:");
	    setStatus("Node " + nodeID + " Created");
	}

	private void setFrequency() {
		String frequency;
		String list[] = new String[30];

	    for (int i = 1; i < list.length; i++) {
	      list[i] = Integer.toString(i);
	    }
	    frequency = (String) JOptionPane.showInputDialog(frame, "Pick a Frequency", "Set Frequency", JOptionPane.QUESTION_MESSAGE
	    		, null, list, "1");
		setStatus("Frequency set to: " + frequency);
	}

	private void setAlgorithm() {
		String[] choices = { "Random", "Flooding", "Shortest Path", "Custom"};
	    String algorithm = (String) JOptionPane.showInputDialog(null, "Choose Algorithm",
	        "Algorithm", JOptionPane.QUESTION_MESSAGE, null, // Use
	        choices, // Array of choices
	        choices[0]); // Initial choice
	    setStatus("Algortihm set to: " + algorithm);
	}

	private void quit() {
		int response = JOptionPane.showConfirmDialog(null,"Are you sure?","Network Simulator",
				JOptionPane.YES_NO_OPTION,JOptionPane.NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
		      System.exit(0);
		    }
	}

	private Object defaultNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object newNetwork() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

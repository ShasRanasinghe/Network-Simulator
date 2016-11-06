package network;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.*;

/**
 * GraphPanel creates the panel that allows a visual of the network to be displayed
 * The graph is displayed with dots and edges connecting them and can be resized
 * Functionalities are: Add node, delete node, add edge and clear graph
 *  
 * @author Mohamed Dahrouj, Andrew Ward
 * Highly refactored to meet project needs from Dr.John B. Matthews implementation(https://sites.google.com/site/drjohnbmatthews/graphpanel)
 */
@SuppressWarnings("serial")
public class GraphicPanel extends JComponent {

    private static final int WIDE = 640;
    private static final int HIGH = 480;
    private static final Color color = new Color(0x1E12FF); //Color blue
    private static final int radius = 20;
    private int OFFSETRIGHT = 600;
    private int OFFSETLEFT = radius * 3;
    private int OFFSETUP = radius * 3;
    private int OFFSETDOWN = 200;
    private int nodeCount = 0;
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;
    private Point mousePt = new Point(WIDE / 2, HIGH / 2);
    public JPopupMenu popup;
    public JMenuItem newNode;
    public JMenuItem clearAll;
    public JMenuItem connect;
    public JMenuItem delete;
    public List<GraphicNode> graphicNodes;
    public List<GraphicNode> selected;
    public List<GraphicEdge> graphicEdges;

    /**
     * Runs the GraphPanel
     * @param args Arguments for main method
     * @throws Exception when error is found
     */
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("GraphPanel");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphicPanel gp = new GraphicPanel();
                f.add(new JScrollPane(gp), BorderLayout.CENTER);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }

    
    /**
     * Constructor for GraphPanel
     */
    public GraphicPanel() {
    	//Initialize
    	popup = new JPopupMenu();
        newNode = new JMenuItem("New");
        clearAll = new JMenuItem("Clear");
        connect = new JMenuItem("Connect");
        delete = new JMenuItem("Delete Node");
        graphicNodes = new ArrayList<>();
        selected = new ArrayList<>();
        graphicEdges = new ArrayList<>();
        
        //Add action listeners
        newNode.addActionListener(e-> NewNodeAction());
        clearAll.addActionListener(e-> ClearAction());
        connect.addActionListener(e-> ConnectAction());
        delete.addActionListener(e-> DeleteAction());

        popup.add(newNode);
        popup.add(connect);
        popup.add(delete);
        popup.add(clearAll);
    	
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (GraphicEdge e : graphicEdges) {
            e.draw(g);
        }
        for (GraphicNode n : graphicNodes) {
            n.draw(g);
        }
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                mouseRect.width, mouseRect.height);
        }
    }
    
    /**
     * @return The mouse point in the GraphPanel
     */
    public Point getMousePoint(){
    	return mousePt.getLocation();
    }
    
    /**
     * @return The color of the node i.e Blue
     */
    public Color getNodeColor(){
    	return color;
    }
    
    /**
     * @return The radius of the graphic node
     */
    public int getNodeRadius(){
    	return radius;
    }
    
	
    /**
     * Controller for New Node Action that adds a node from pop up menu
     * Therefore a dialog requesting an ID is triggered
     *
     */
    public void NewNodeAction(){
    	
    	nodeCount++;
	    GraphicNode.selectNone(graphicNodes);
	    Point p = offsetP();

	    //Retrieves node ID
	    String nodeID = "";
		JTextField id = new JTextField(1);
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.Y_AXIS));
		idPanel.add(new JLabel("Node ID:"));
		idPanel.add(id);
	    int result = JOptionPane.showConfirmDialog(null, idPanel, "New node:", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION)
	    {
	    	if(!id.getText().equals("")){
	    		nodeID = id.getText();
	    	}
	    }
		
	    GraphicNode n = new GraphicNode(nodeID, p, radius, color);
	    n.setSelected(true);
	    graphicNodes.add(n);
	    repaint();
    }
    
    /**
     * Controller for New Node Action that adds a node
     *
     */
    public void NewNodeAction(String nodeID){
	
    	nodeCount++;
	    GraphicNode.selectNone(graphicNodes);
	    Point p = offsetP();
	    GraphicNode n = new GraphicNode(nodeID, p, radius, color);
	    n.setSelected(true);
	    graphicNodes.add(n);
	    repaint();
    }
    
    /**
     * Controller for Connect Action that connects two nodes
     *
     */
    public void ConnectAction() {

	    GraphicNode.getSelected(graphicNodes, selected);
	    
	    //You can only connect 2 nodes together
	    if (selected.size() > 2){
			JPanel idPanel = new JPanel();
			idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.Y_AXIS));
			idPanel.add(new JLabel("You may only connect two nodes"));
		    JOptionPane.showConfirmDialog(null, idPanel, "New node:", JOptionPane.OK_CANCEL_OPTION);
	    }
	    if (selected.size() == 2) {
	        for (int i = 0; i < selected.size() - 1; ++i) {
	            GraphicNode n1 = selected.get(i);
	            GraphicNode n2 = selected.get(i + 1);
	            graphicEdges.add(new GraphicEdge(n1, n2));
	        }
	    }
	    repaint();
    }
    
    /**
     * Controller for Connect Action that connects two nodes
     *
     */
    public void ConnectAction(String n1ID, String n2ID) {

    	//Can only connect two nodes with a single action
    	GraphicNode n1 = getGraphicNodeGivenID(n1ID);
        GraphicNode n2 = getGraphicNodeGivenID(n2ID);
        
		if(!n1.getNodeID().equals("")&&!n2.getNodeID().equals("")){
			graphicEdges.add(new GraphicEdge(n1, n2));
		}
	    repaint();
    }
    
	/**
	 * 
	 * @param graphicNodes List of nodes to traverse
	 * @param id ID of node
	 * @return GraphicNode object if an ID matches
	 */
	public GraphicNode getGraphicNodeGivenID(String id) 
	{
	
		for(GraphicNode gn : graphicNodes){
			if(gn.getNodeID().equals(id)){
			//Returns the node when found
			return gn;
		    	}
	    	}
	    	//Returns an empty node if not found
	    	return new GraphicNode("");
	}

    /**
     * Controller for Delete Action that deletes the nodes
     *
     */
    public void DeleteAction() {

            ListIterator<GraphicNode> iter = graphicNodes.listIterator();
            while (iter.hasNext()) {
                GraphicNode n = iter.next();
                if (n.isSelected()) {
                    deleteEdges(n);
                    iter.remove();
                }
            }
            repaint();
    }
    
    /**
     * Deletes edges that are connected to the node to be deleted
     * @param n node to be deleted
     */
    public void deleteEdges(GraphicNode n) {
        ListIterator<GraphicEdge> iter = graphicEdges.listIterator();
        while (iter.hasNext()) {
            GraphicEdge e = iter.next();
            if (e.getN1() == n || e.getN2() == n) {
                iter.remove();
            }
        }
    }
    
    /**
     * Controller for Clear Action that clears the entire panel
     *
     */
    public void ClearAction() {

        graphicNodes.clear();
        graphicEdges.clear();
        repaint();
        
        OFFSETRIGHT = 600;
        OFFSETLEFT = radius * 3;
        OFFSETUP = radius * 3;
        OFFSETDOWN = 200;
        nodeCount = 0;
    }
    
    /**
     * 
     */
    public Point offsetP()
    {
    	Point p = getMousePoint();
    	
    	if(nodeCount % 2 == 0)
    	{
    		p.setLocation(OFFSETRIGHT, OFFSETUP);
    		OFFSETRIGHT -= radius * 3;
    		OFFSETUP += radius * 3;
    	}
    	else
    	{
    		p.setLocation(OFFSETLEFT, OFFSETDOWN);
    		OFFSETLEFT += radius * 3;
    		OFFSETDOWN -= radius *3;
    	}
    	
    	
    	return p;
    }
    
    /**
     * Internal class that handles mouse motion
     *
     */
    private class MouseHandler extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                GraphicNode.selectToggle(graphicNodes, mousePt);
            } else if (e.isPopupTrigger()) {
                GraphicNode.selectOne(graphicNodes, mousePt);
                showPopup(e);
            } else if (GraphicNode.selectOne(graphicNodes, mousePt)) {
                selecting = false;
            } else {
                GraphicNode.selectNone(graphicNodes);
                selecting = true;
            }
            e.getComponent().repaint();
        }

        public void showPopup(MouseEvent e) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /**
     * Internal class that handles mouse motion
     *
     */
    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                    Math.min(mousePt.x, e.getX()),
                    Math.min(mousePt.y, e.getY()),
                    Math.abs(mousePt.x - e.getX()),
                    Math.abs(mousePt.y - e.getY()));
                GraphicNode.selectRect(graphicNodes, mouseRect);
            } else {
                delta.setLocation(
                    e.getX() - mousePt.x,
                    e.getY() - mousePt.y);
                GraphicNode.updatePosition(graphicNodes, delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }
}
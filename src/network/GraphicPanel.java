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
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;
    private Point mousePt = new Point(WIDE / 2, HIGH / 2);
    public List<GraphicNode> graphicNodes;
    public List<GraphicNode> selected;
    public List<GraphicEdge> graphicEdges;

    /**
     * Runs the GraphicPanel
     * @param args Arguments for main method
     * @throws Exception when error is found
     */
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("GraphicPanel");
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
        graphicNodes = new ArrayList<>();
        selected = new ArrayList<>();
        graphicEdges = new ArrayList<>();
    	
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
    	
	    GraphicNode.selectNone(graphicNodes);
	    Point p = getMousePoint();
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
	
	    GraphicNode.selectNone(graphicNodes);
	    Point p = getMousePoint();
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
			idPanel.add(new JLabel("You may only connect two nodes together."));
		    JOptionPane.showConfirmDialog(null, idPanel, "Error", JOptionPane.OK_CANCEL_OPTION);
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
     * Deletes a single graphic node from graphic panel given ID
     * @param nodeID ID of graphic node to be deleted
     */
    public void removeGraphicNode(String nodeID) {
        
    	ListIterator<GraphicNode> iter = graphicNodes.listIterator();
        while (iter.hasNext()) {
            GraphicNode n = iter.next();
            if (n.getNodeID().equals(nodeID)) {
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
     * Deletes an edges given edge ID
     * @param edgeID ID of edge to be deleted
     */
    public void removeGraphicEdge(String edgeID) {
        ListIterator<GraphicEdge> iter = graphicEdges.listIterator();
        while (iter.hasNext()) {
            GraphicEdge e = iter.next();
            if (e.getEdgeID().equals(edgeID)) {
                iter.remove();
            }
        }
        repaint();
    }
    
    
    /**
     * Controller for Clear Action that clears the entire panel
     *
     */
    public void ClearAction() {

        graphicNodes.clear();
        graphicEdges.clear();
        repaint();
    }
    
    /**
     * Populates the selected nodes list
     */
    public void populateSelectedNodesList(){
    	GraphicNode.getSelected(graphicNodes, selected);
    }
    
    /**
     * Returns the list of selected nodes
     */
    public List<GraphicNode> getSelectedNodesList(){
    	return selected;
    }
    
    
    /**
     * Returns the number of nodes that are selected
     */
    public int numberOfSelectedNodes(){
    	return selected.size();
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
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                GraphicNode.selectToggle(graphicNodes, mousePt);
            } else if (e.isPopupTrigger()) {
                GraphicNode.selectOne(graphicNodes, mousePt);
            } else if (GraphicNode.selectOne(graphicNodes, mousePt)) {
                selecting = false;
            } else {
                GraphicNode.selectNone(graphicNodes);
                selecting = true;
            }
            e.getComponent().repaint();
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
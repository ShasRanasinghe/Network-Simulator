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
    public List<GraphicNode> graphicNodes;
    public List<GraphicNode> selected;
    public List<GraphicEdge> graphicEdges;
    
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
     * @param nodeID Node id to be created
     * 
     * Controller for New Node Action that adds a node
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
     * @param n1ID node id of the first node
     * @param n2ID node id of the second node
     * 
     * Controller for Connect Action that connects two nodes
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
     * @param nodeOneID node ID of the start of the edge
     * @param nodeTwoID node ID of the end of the edge
     */
    public void removeGraphicEdge(String nodeOneID, String nodeTwoID) {
        ListIterator<GraphicEdge> iter = graphicEdges.listIterator();
        String edge1 = nodeOneID + "->" + nodeTwoID;
		String edge2 = nodeTwoID + "->" + nodeOneID;
        while (iter.hasNext()) {
            GraphicEdge e = iter.next();
            if (e.getEdgeID().equals(edge1)) {iter.remove();}
            if (e.getEdgeID().equals(edge2)) {iter.remove();}
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
        
        OFFSETRIGHT = 600;
        OFFSETLEFT = radius * 3;
        OFFSETUP = radius * 3;
        OFFSETDOWN = 200;
        nodeCount = 0;
    }
    

    /**
     * @return Point of the location
     * Offsets the location when creating a new node
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
     * Populates the selected nodes list
     */
    public void populateSelectedNodesList(){
    	GraphicNode.getSelected(graphicNodes, selected);
    }
    
    /**
     * @return list of selected nodes
     * 
     */
    public List<GraphicNode> getSelectedNodesList(){
    	return selected;
    }

    /**
     * @return the number of nodes that are selected
     */
    public int numberOfSelectedNodes(){
    	return selected.size();
    }
    
    /**
     * @return list of nodes to export
     */
    public List<GraphicNode> getGraphicNodes(){
    	return graphicNodes;
    }
    
    /**
     * @return list of edges to export
     */
    public List<GraphicEdge> getGraphicEdges(){
    	return graphicEdges;
    }
    
    /**
     * Set the list of Nodes for the network when importing
     * @param nodes list of nodes
     */
    public void setGraphicNodes(List<GraphicNode> nodes){
    	graphicNodes = nodes;
    }
    
    /**
     * Set the list of Edges for the network when importing
     * @param edges list of edges
     */
    public void setGraphicEdges(List<GraphicEdge> edges){
    	graphicEdges = edges;
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
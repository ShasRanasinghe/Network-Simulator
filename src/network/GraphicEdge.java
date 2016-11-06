package network;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * GraphicEdge is a pair of Nodes and is used to visualize an Edge in GraphPanel
 */
public class GraphicEdge {

    private GraphicNode n1;
	private GraphicNode n2;
	private String edgeID;

    /**
     * @param n1 GraphicNode One
     * @param n2 GraphicNode Two
     */
    public GraphicEdge(GraphicNode n1, GraphicNode n2) {
        this.n1 = n1;
        this.n2 = n2;
        edgeID = n1.getNodeID() + "->" + n2.getNodeID();
    }
    
    /**
     * @return Edge ID 
     */
    public String getEdgeID(){
    	return edgeID;
    }
    
    /**
     * @return GraphicNode One
     */
    public GraphicNode getN1() {
		return n1;
	}

	/**
	 * Sets the source of edge
	 * @param n1 GraphicNode to be set for n1
	 */
	public void setN1(GraphicNode n1) {
		this.n1 = n1;
	}
	
    /**
     * @return GraphicNode Two
     */
	public GraphicNode getN2() {
		return n2;
	}

	/**
	 * Sets the destination of edge
	 * @param n2 GraphicNode to be set for n2
	 */
	public void setN2(GraphicNode n2) {
		this.n2 = n2;
	}

    /**
     * Draws the edge
     * @param g Graphics
     */
    public void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(Color.darkGray);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
}

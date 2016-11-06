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

    public GraphicEdge(GraphicNode n1, GraphicNode n2) {
        this.n1 = n1;
        this.n2 = n2;
        edgeID = n1.getNodeID() + "->" + n2.getNodeID();
    }
    
    public String getNodeID(){
    	return edgeID;
    }
    
    public GraphicNode getN1() {
		return n1;
	}

	public void setN1(GraphicNode n1) {
		this.n1 = n1;
	}

	public GraphicNode getN2() {
		return n2;
	}

	public void setN2(GraphicNode n2) {
		this.n2 = n2;
	}

    public void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(Color.darkGray);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
}

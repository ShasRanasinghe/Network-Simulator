package network;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

/**
 * GraphicNode represents a node in a graph.
 */
public class GraphicNode {

    private String nodeID;
	private Point p;
    private int r;
    private Color color;
    private boolean selected = false;
    private Rectangle b = new Rectangle();
	
    /**
     * Construct a new node given params
     */
	public GraphicNode(String nodeID, Point p, int r, Color color) {
        this.nodeID = nodeID;
		this.p = p;
        this.r = r;
        this.color = color;
        setBoundary(b);
    }
	
    /**
     * Constructs a temporary node
     */
	public GraphicNode(String nodeID) {
        this.nodeID = nodeID;
    }

    /**
     * Calculate this node's rectangular boundary.
     */
    private void setBoundary(Rectangle b) {
        b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }

    /**
     * Draw this node.
     */
    public void draw(Graphics g) {
    	//Draw Oval
    	g.setColor(this.color);
        g.fillOval(b.x, b.y, b.width, b.height);
        
        // Draw centered text
        FontMetrics fm = g.getFontMetrics();
        double textWidth = fm.getStringBounds(nodeID, g).getWidth();
        g.setColor(Color.WHITE);
        g.drawString(nodeID, (int) (b.x - (textWidth/2) + r), (int) (b.y + (fm.getMaxAscent() / 2)) +r);  
        
        if (selected) {
            g.setColor(Color.darkGray);
            g.drawRect(b.x, b.y, b.width, b.height);
        }
    }

    /**
     * Return this node's ID.
     */
    public String getNodeID() {
        return nodeID;
    }
    
    /**
     * Return this node's location.
     */
    public Point getLocation() {
        return p;
    }

    /**
     * Return true if this node contains p.
     */
    public boolean contains(Point p) {
        return b.contains(p);
    }

    /**
     * Return true if this node is selected.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Mark this node as selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Collected all the selected nodes in list.
     */
    public static void getSelected(List<GraphicNode> list, List<GraphicNode> selected) {
        selected.clear();
        for (GraphicNode n : list) {
            if (n.isSelected()) {
                selected.add(n);
            }
        }
    }

    /**
     * Select no nodes.
     */
    public static void selectNone(List<GraphicNode> list) {
        for (GraphicNode n : list) {
            n.setSelected(false);
        }
    }

    /**
     * Select a single node; return true if not already selected.
     */
    public static boolean selectOne(List<GraphicNode> list, Point p) {
        for (GraphicNode n : list) {
            if (n.contains(p)) {
                if (!n.isSelected()) {
                    GraphicNode.selectNone(list);
                    n.setSelected(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Select each node in r.
     */
    public static void selectRect(List<GraphicNode> list, Rectangle r) {
        for (GraphicNode n : list) {
            n.setSelected(r.contains(n.p));
        }
    }

    /**
     * Toggle selected state of each node containing p.
     */
    public static void selectToggle(List<GraphicNode> list, Point p) {
        for (GraphicNode n : list) {
            if (n.contains(p)) {
                n.setSelected(!n.isSelected());
            }
        }
    }

    /**
     * Update each node's position by d (delta).
     */
    public static void updatePosition(List<GraphicNode> list, Point d) {
        for (GraphicNode n : list) {
            if (n.isSelected()) {
                n.p.x += d.x;
                n.p.y += d.y;
                n.setBoundary(n.b);
            }
        }
    }
}